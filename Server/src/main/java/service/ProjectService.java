package service;

import dto.request.CommentRequest;
import dto.request.RequirementRequest;
import dto.response.CommentResponse;
import dto.response.RequirementResponse;
import model.Project;
import model.ProjectApproval;
import model.Team;
import model.User;
import repository.ProjectApprovalRepository;
import repository.ProjectRepository;
import repository.TeamRepository;
import repository.UserRepository;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class ProjectService {

    private final TeamRepository teamRepository = new TeamRepository();
    private final ProjectRepository projectRepository = new ProjectRepository();
    private final ProjectApprovalRepository projectApprovalRepository = new ProjectApprovalRepository();
    private final UserRepository userRepository = new UserRepository();


    public Project saveProject(RequirementRequest requirementRequest, int userId) throws DataFormatException, UnsupportedEncodingException {
        byte[] content = requirementRequest.getContent();

        Inflater inflater = new Inflater();
        inflater.setInput(content, 0, content.length);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int resultLength = inflater.inflate(buffer);
            result.write(buffer, 0, resultLength);
        }
        inflater.end();


        // Convert byte[] to String
        String decompressedData = new String(result.toByteArray(), "UTF-8");

        Team team = teamRepository.getById(Integer.parseInt(requirementRequest.getTeamId()));

        Project projectSave = Project.builder()
                .team(team)
                .title(requirementRequest.getTitle())
                .content(decompressedData)
                .insertTime(new java.sql.Timestamp(new Date().getTime()))
                .insertBy(userId)
                .build();
        projectRepository.saveProject(projectSave);
        System.out.println("decompressedData = " + decompressedData);

        Deflater deflater = new Deflater();
        byte[] input = projectSave.getContent().getBytes("UTF-8");
        deflater.setInput(input);
        deflater.finish();
        ByteArrayOutputStream compressedData = new ByteArrayOutputStream();
        while (!deflater.finished()) {
            int compressedDataLength = deflater.deflate(buffer);
            compressedData.write(buffer, 0, compressedDataLength);
        }
        deflater.end();

        // Convert byte[] to Base64 String
        String base64Data = Base64.getEncoder().encodeToString(compressedData.toByteArray());
        projectSave.setContent(base64Data);

        return projectSave;

    }

    public List<RequirementResponse> getProject(int teamId) throws UnsupportedEncodingException {
        List<Project> projectList = projectRepository.findProjectByTeamId(teamId);

        List<RequirementResponse> list = new ArrayList<>();
        for(Project pj : projectList) {
            Deflater deflater = new Deflater();
            byte[] input = pj.getContent().getBytes("UTF-8");
            deflater.setInput(input);
            deflater.finish();
            ByteArrayOutputStream compressedData = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int compressedDataLength = deflater.deflate(buffer);
                compressedData.write(buffer, 0, compressedDataLength);
            }
            deflater.end();

            // Convert byte[] to Base64 String
            String base64Data = Base64.getEncoder().encodeToString(compressedData.toByteArray());
            pj.setContent(base64Data);

            RequirementResponse requirementResponse = RequirementResponse.builder()
                    .id(pj.getId())
                    .content(base64Data)
                    .title(pj.getTitle())
                    .insertTime(pj.getInsertTime())
                    .build();
            list.add(requirementResponse);
        }
        return list;
    }

    public Boolean deleteRequirement(int id, int userId) {
        Project projectTmp = projectRepository.getById(id);
        projectTmp.setDeleteBy(userId);
        projectTmp.setDeleteTime(new java.sql.Timestamp(new Date().getTime()));
        Project project = projectRepository.update(projectTmp);

        return project != null;
    }

    public ProjectApproval addProjectApproval(CommentRequest commentRequest, int userId) {
        Project project = projectRepository.getById(commentRequest.getProjectId());
        ProjectApproval projectApproval = ProjectApproval.builder()
                .project(project)
                .status(commentRequest.isStatus())
                .comments(commentRequest.getComment())
                .insertTime(new java.sql.Timestamp(new Date().getTime()))
                .insertBy(userId)
                .build();
        projectApprovalRepository.save(projectApproval);
        return projectApproval;
    }

    public List<CommentResponse> getProjectApproval(int id) {
        List<ProjectApproval> approvalList = projectApprovalRepository.findByTeamId(id);
        List<CommentResponse> commentResponses = new ArrayList<>();

        for(ProjectApproval pa : approvalList) {
            User user = userRepository.getUserById(pa.getInsertBy());
            CommentResponse commentResponse = CommentResponse.builder()
                    .id(pa.getId())
                    .name(user.getName())
                    .username(user.getUsername())
                    .projectId(pa.getProject().getId())
                    .projectTitle(pa.getProject().getTitle())
                    .status(pa.isStatus())
                    .comment(pa.getComments())
                    .insertTime(pa.getInsertTime())
                    .build();
            commentResponses.add(commentResponse);
        }

        return commentResponses;
    }

    public ProjectApproval deleteApproval(int id, int userId) {
        ProjectApproval projectApproval = projectApprovalRepository.getById(id);
        projectApproval.setDeleteBy(userId);
        projectApproval.setDeleteTime(new java.sql.Timestamp(new Date().getTime()));
        return projectApprovalRepository.update(projectApproval);
    }

    public ProjectApproval destroyApproval(int id, int userId) {
        ProjectApproval projectApproval = projectApprovalRepository.getById(id);
        projectApproval.setUpdateBy(userId);
        projectApproval.setUpdateTime(new java.sql.Timestamp(new Date().getTime()));
        projectApproval.setStatus(false);
        return projectApprovalRepository.update(projectApproval);
    }
}

