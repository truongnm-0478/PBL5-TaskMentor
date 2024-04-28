package service;

import dto.request.GroupCreateRequest;
import dto.response.TeamMemberResponse;
import dto.response.TeamResponse;
import dto.response.TeamStudentResponse;
import model.ClassRoom;
import model.Student;
import model.Team;
import model.TeamMember;
import repository.ClassRepository;
import repository.StudentRepository;
import repository.TeamMemberRepository;
import repository.TeamRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeamService {
    private final TeamRepository teamRepository = new TeamRepository();
    private final TeamMemberRepository teamMemberRepository = new TeamMemberRepository();
    private final ClassRepository classRepository = new ClassRepository();
    private final StudentRepository studentRepository = new StudentRepository();

    public Boolean saveTeam(GroupCreateRequest groupCreateRequest, int userId) {
        ClassRoom classRoom = classRepository.findByCode(groupCreateRequest.getCode());

        Team team = Team.builder()
                .name(groupCreateRequest.getGroup())
                .classRoom(classRoom)
                .insertTime(new java.sql.Timestamp(new Date().getTime()))
                .insertBy(userId)
                .build();
        Team teamResult = teamRepository.save(team);

        if(teamResult == null) {
            return false;
        } else {
            List<GroupCreateRequest.Member> listMember = groupCreateRequest.getMembers();
            for(GroupCreateRequest.Member member : listMember) {
                Student student = studentRepository.getStudentByStudentCode(member.getStudentId());
                boolean isLeader = member.isLeader();
                TeamMember teamMember = TeamMember.builder()
                        .team(team)
                        .student(student)
                        .role((isLeader) ? 1 : 0)
                        .insertTime(new java.sql.Timestamp(new Date().getTime()))
                        .insertBy(userId)
                        .build();
                teamMemberRepository.save(teamMember);
            }
            return true;
        }
    }

    public List<TeamResponse> getListTeamByClassCode(String classCode) {
        List<Team> teamList = teamRepository.getTeamsByClassCode(classCode);
        List<TeamResponse> teamResponseList = new ArrayList<>();
        for(Team team : teamList) {
            TeamResponse teamResponse = TeamResponse.builder()
                    .id(team.getId())
                    .name(team.getName())
                    .insertTime(team.getInsertTime())
                    .build();
            teamResponseList.add(teamResponse);
        }
        return teamResponseList;
    }

    public List<TeamMemberResponse> getTeamMemberByTeamID(int teamId) {
        List<TeamMember> teamMemberList = teamMemberRepository.getTeamMembersByTeam(teamId);
        List<TeamMemberResponse> teamMemberResponseList = new ArrayList<>();
        for(TeamMember teamMember : teamMemberList) {
            TeamMemberResponse teamMemberResponse = TeamMemberResponse.builder()
                    .id(teamMember.getId())
                    .name(teamMember.getStudent().getUser().getName())
                    .username(teamMember.getStudent().getUser().getUsername())
                    .phone(teamMember.getStudent().getUser().getPhone())
                    .mail(teamMember.getStudent().getUser().getEmail())
                    .studentID(teamMember.getStudent().getCode())
                    .isLeader(teamMember.getRole() == 1)
                    .build();
            teamMemberResponseList.add(teamMemberResponse);
        }
        return teamMemberResponseList;
    }

    public Boolean deleteTeam(int teamId, int userId) {
        Team team = teamRepository.getById(teamId);
        team.setDeleteBy(userId);
        team.setDeleteTime(new java.sql.Timestamp(new Date().getTime()));
        Team teamUpdate = teamRepository.update(team);
        return teamUpdate != null;
    }

    public List<TeamStudentResponse> getListTeamByUser(int userId) {
        Student student = studentRepository.getStudentByUserId(userId);
        List<TeamMember> teamMemberList = teamMemberRepository.getTeamMemberByStudentCode(student.getCode());
        List<TeamStudentResponse> teamStudentResponses = new ArrayList<>();

        for(TeamMember teamMember : teamMemberList) {
            List<TeamMemberResponse> listTeamMember = getTeamMemberByTeamID(teamMember.getTeam().getId());
            TeamStudentResponse.Leader leader = null;

            for(TeamMemberResponse tmb : listTeamMember) {
                if (tmb.getIsLeader()) {
                    leader = TeamStudentResponse.Leader.builder()
                            .studentId(tmb.getStudentID())
                            .name(tmb.getName())
                            .build();
                    break;
                }
            }

            TeamStudentResponse teamStudentResponse = TeamStudentResponse.builder()
                    .id(teamMember.getTeam().getId())
                    .name(teamMember.getTeam().getName())
                    .className(teamMember.getTeam().getClassRoom().getClassName())
                    .classCode(teamMember.getTeam().getClassRoom().getCode())
                    .role(teamMember.getRole())
                    .leader(leader)
                    .build();

            teamStudentResponses.add(teamStudentResponse);
        }

        return teamStudentResponses;
    }


}
