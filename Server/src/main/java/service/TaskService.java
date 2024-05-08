package service;

import dto.request.StatusRequest;
import dto.request.TaskAssignmentRequest;
import dto.request.TaskRequest;
import dto.response.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.*;

import model.*;
import org.json.JSONObject;
import repository.*;
import util.FormatUtil;

import java.util.stream.Collectors;

public class TaskService {
    private final TaskRepository taskRepository = new TaskRepository();
    private final TaskAssignmentRepository taskAssignmentRepository = new TaskAssignmentRepository();
    private final SprintRepository sprintRepository = new SprintRepository();
    private final TeamMemberRepository teamMemberRepository = new TeamMemberRepository();
    private final UserRepository userRepository = new UserRepository();
    private final StudentRepository studentRepository = new StudentRepository();

    private final FormatUtil formatUtil = new FormatUtil();


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

    public List<TaskResponse> getListTask(int teamId) {
        List<Task> tasks = taskRepository.findByTeamId(teamId);
        List<TaskAssignment> taskAssignments = taskAssignmentRepository.findByTeamId(teamId);

        if(taskAssignments == null) {
            return tasks.stream()
                .map(this::mapTaskToTaskResponse)
                .collect(Collectors.toList());
        }

        // Tạo một HashMap để ánh xạ từ taskId sang TaskAssignment
        Map<Integer, TaskAssignment> taskAssignmentMap = new HashMap<>();
        for (TaskAssignment assignment : taskAssignments) {
            taskAssignmentMap.put(assignment.getTask().getId(), assignment);
        }

        return tasks.stream()
                .map(task -> mapTaskToTaskResponse(task, taskAssignmentMap.getOrDefault(task.getId(), null)))
                .collect(Collectors.toList());
    }

    private TaskResponse mapTaskToTaskResponse(Task task, TaskAssignment taskAssignment) {
        TaskResponse.TaskResponseBuilder responseBuilder = TaskResponse.builder()
                .id(task.getId())
                .sprintId(task.getSprint().getId())
                .sprintName(task.getSprint().getName())
                .stage(task.getSprint().getStage())
                .taskName(task.getTaskName())
                .description(task.getDescription())
                .status(task.getStatus())
                .insertTime(task.getInsertTime());

        // Kiểm tra nếu taskAssignment và assignedTo không null, gán userName từ taskAssignment
        if (taskAssignment != null && taskAssignment.getAssignedTo() != null) {
            responseBuilder.userName(taskAssignment.getAssignedTo().getName());
        }

        return responseBuilder.build();
    }


    private TaskResponse mapTaskToTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .sprintId(task.getSprint().getId())
                .sprintName(task.getSprint().getName())
                .stage(task.getSprint().getStage())
                .taskName(task.getTaskName())
                .description(task.getDescription())
                .status(task.getStatus())
                .insertTime(task.getInsertTime())
                .build();
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

    public Boolean deleteTask(int taskId, int userId) {
        Task task = taskRepository.findById(taskId);
        task.setDeleteBy(userId);
        task.setDeleteTime(new java.sql.Timestamp(new Date().getTime()));
        Task taskResult = taskRepository.update(task);
        if (taskResult.getDeleteTime() != null) {
            return true;
        }
        return false;
    }

    // AI

    public List<TaskDifficultyResponse> evaluateTaskDifficulty(int teamId) {
        List<TaskAssignment> taskAssignments = taskAssignmentRepository.findByTeamId(teamId);
        List<TaskDifficultyResponse> taskDifficultyResponses = new ArrayList<>();

        for (TaskAssignment taskAssignment : taskAssignments) {
            String taskDescription = taskAssignment.getTask().getDescription();
            float predictedDifficulty = callAIServer(taskDescription);
            int userId = taskAssignment.getAssignedTo().getId();

            TaskDifficultyResponse task = TaskDifficultyResponse.builder()
                    .difficulty(predictedDifficulty)
                    .userName(taskAssignment.getAssignedTo().getName())
                    .status(taskAssignment.getTask().getStatus())
                    .studentId(studentRepository.getStudentByUserId(userId).getCode())
                    .build();
            taskDifficultyResponses.add(task);
        }

        return taskDifficultyResponses;
    }

    private float callAIServer(String taskDescription) {
        try {
            String apiUrl = "http://127.0.0.1:5000/predict_task_difficulty";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Convert taskDescription to lowercase for case-insensitive matching
            String lowercaseDescription = taskDescription.toLowerCase();

            String requestBody = "{\"task_name\": \"" + lowercaseDescription + "\"}";
            connection.getOutputStream().write(requestBody.getBytes());

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            double predictedDifficulty = jsonResponse.getDouble("predicted_difficulty");

            // Adjust difficulty based on keywords
            predictedDifficulty = formatUtil.format(predictedDifficulty, lowercaseDescription);

            return (float) predictedDifficulty;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }


}
