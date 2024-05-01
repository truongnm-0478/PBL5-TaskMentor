package service;

import dto.request.StatusRequest;
import dto.request.TaskAssignmentRequest;
import dto.request.TaskRequest;
import dto.response.TaskAssignmentResponse;
import dto.response.TaskItemResponse;
import dto.response.TaskResponse;

import java.util.*;
import java.util.function.Function;
import model.*;
import repository.*;

import java.sql.Timestamp;
import java.util.stream.Collectors;

public class TaskService {
    private final TaskRepository taskRepository = new TaskRepository();
    private final TaskAssignmentRepository taskAssignmentRepository = new TaskAssignmentRepository();
    private final SprintRepository sprintRepository = new SprintRepository();
    private final TeamMemberRepository teamMemberRepository = new TeamMemberRepository();
    private final UserRepository userRepository = new UserRepository();

    public Task saveTask(TaskRequest taskRequest, int userId) {
        Sprint sprint = sprintRepository.getById(taskRequest.getSprint());
        Task task = Task.builder()
                .sprint(sprint)
                .taskName(taskRequest.getName())
                .description(taskRequest.getDescription())
                .status(Integer.parseInt(taskRequest.getStatus()))
                .insertTime(new java.sql.Timestamp(new Date().getTime()))
                .insertBy(userId)
                .build();
        Task taskResult = taskRepository.save(task);
        if (taskResult != null) {
            TaskAssignment taskAssignment = TaskAssignment.builder()
                    .task(taskResult)
                    .deadline(taskRequest.getDeadline())
                    .insertTime(new java.sql.Timestamp(new Date().getTime()))
                    .insertBy(userId)
                    .build();
            taskAssignmentRepository.save(taskAssignment);
        }
        return taskResult;
    }

//    public List<TaskResponse> getListTask(int teamId) {
//        List<Task> tasks = taskRepository.findByTeamId(teamId);
//        List<TaskAssignment> taskAssignmentList = taskAssignmentRepository.findByTeamId(teamId);
//        return tasks.stream()
//                .map(this::mapTaskToTaskResponse)
//                .collect(Collectors.toList());
//
//    }
//
//    private TaskResponse mapTaskToTaskResponse(Task task) {
//        return TaskResponse.builder()
//                .id(task.getId())
//                .sprintId(task.getSprint().getId())
//                .sprintName(task.getSprint().getName())
//                .stage(task.getSprint().getStage())
//                .taskName(task.getTaskName())
//                .description(task.getDescription())
//                .status(task.getStatus())
//                .insertTime(task.getInsertTime())
//                .build();
//    }

    public List<TaskResponse> getListTask(int teamId) {
        List<Task> tasks = taskRepository.findByTeamId(teamId);
        List<TaskAssignment> taskAssignments = taskAssignmentRepository.findByTeamId(teamId);

        Map<Integer, TaskAssignment> taskAssignmentMap = new HashMap<>();
        if (taskAssignments != null) {
            for (TaskAssignment taskAssignment : taskAssignments) {
                Task task = taskAssignment.getTask();
                if (task != null) {
                    taskAssignmentMap.put(task.getId(), taskAssignment);
                }
            }
        }

        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : tasks) {
            TaskAssignment taskAssignment = taskAssignmentMap.get(task.getId());
            taskResponses.add(mapTaskToTaskResponse(task, taskAssignment));
        }

        return taskResponses;
    }

    private TaskResponse mapTaskToTaskResponse(Task task, TaskAssignment taskAssignment) {
        TaskResponse.TaskResponseBuilder responseBuilder = TaskResponse.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .description(task.getDescription())
                .status(task.getStatus())
                .insertTime(task.getInsertTime())
                .sprintId(task.getSprint().getId())
                .sprintName(task.getSprint().getName())
                .stage(task.getSprint().getStage());

        if (taskAssignment != null) {
            responseBuilder.userName(taskAssignment.getAssignedTo().getName());
        }

        return responseBuilder.build();
    }




    public Task updateStatus(StatusRequest statusRequest, int userId) {
        Task task = taskRepository.findById(statusRequest.getId());
        task.setStatus(statusRequest.getStatus());
        task.setUpdateBy(userId);
        task.setUpdateTime(new java.sql.Timestamp(new Date().getTime()));
        return taskRepository.update(task);
    }

    public TaskAssignmentResponse getTaskAssignment(int taskId) {
        TaskAssignment assignment = taskAssignmentRepository.findByTaskId(taskId);
        List<TeamMember> memberList = teamMemberRepository.getTeamMembersByTeam(assignment.getTask().getSprint().getPlanning().getTeam().getId());
        List<TaskAssignmentResponse.AssignBy> assignByList = new ArrayList<>();
        for(TeamMember teamMember : memberList) {
            TaskAssignmentResponse.AssignBy user = TaskAssignmentResponse.AssignBy.builder()
                    .userId(teamMember.getStudent().getUser().getId())
                    .name(teamMember.getStudent().getUser().getName())
                    .build();
            assignByList.add(user);
        }
        int assignedToId = (assignment.getAssignedTo() != null) ? assignment.getAssignedTo().getId() : 0;


        return TaskAssignmentResponse.builder()
                .id(assignment.getId())
                .sprintId(assignment.getTask().getSprint().getId())
                .sprintName(assignment.getTask().getSprint().getName())
                .stage(assignment.getTask().getSprint().getStage())
                .taskId(assignment.getTask().getId())
                .taskName(assignment.getTask().getTaskName())
                .description(assignment.getTask().getDescription())
                .status(assignment.getTask().getStatus())
                .deadline(assignment.getDeadline())
                .listAssigned(assignByList)
                .assignedTo(assignedToId)
                .build();
    }

    public Task updateTaskAssignment(TaskAssignmentRequest taskAssignmentRequest, int userId) {
        Task task = taskRepository.findById(taskAssignmentRequest.getId());
        Sprint sprint = sprintRepository.getById(taskAssignmentRequest.getSprint());
        task.setTaskName(taskAssignmentRequest.getName());
        task.setSprint(sprint);
        task.setDescription(taskAssignmentRequest.getDescription());
        task.setUpdateBy(userId);
        task.setUpdateTime(new java.sql.Timestamp(new Date().getTime()));

        User user = userRepository.getUserById(taskAssignmentRequest.getAssignedTo());

        TaskAssignment taskAssignment = taskAssignmentRepository.findByTaskId(taskAssignmentRequest.getId());
        taskAssignment.setAssignedTo(user);
        taskAssignment.setDeadline(taskAssignmentRequest.getDeadline());
        taskAssignment.setUpdateBy(userId);
        taskAssignment.setUpdateTime(new java.sql.Timestamp(new Date().getTime()));

        taskAssignmentRepository.update(taskAssignment);
        return taskRepository.update(task);
    }
}
