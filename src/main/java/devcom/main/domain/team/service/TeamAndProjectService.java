package devcom.main.domain.team.service;

import devcom.main.domain.project.ProjectCreateForm;
import devcom.main.domain.project.ProjectModifyForm;
import devcom.main.domain.project.entity.Project;
import devcom.main.domain.project.service.ProjectService;
import devcom.main.domain.projectState.ProjectStateCreateForm;
import devcom.main.domain.projectState.controller.ProjectStateController;
import devcom.main.domain.projectState.entity.ProjectState;
import devcom.main.domain.projectState.service.ProjectStateService;
import devcom.main.domain.team.TeamCreateForm;
import devcom.main.domain.team.TeamModifyForm;
import devcom.main.domain.team.entity.Team;
import devcom.main.domain.teamInvite.TeamInviteForm;
import devcom.main.domain.teamInvite.controller.TeamInviteController;
import devcom.main.domain.teamInvite.entity.TeamInvite;
import devcom.main.domain.teamInvite.service.TeamInviteService;
import devcom.main.domain.teamMember.entity.TeamMember;
import devcom.main.domain.teamMember.service.TeamMemberService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamAndProjectService {

    private final UserService userService;

    private final TeamService teamService;
    private final TeamMemberService teamMemberService;
    private final TeamInviteService teamInviteService;

    private final ProjectService projectService;
    private final ProjectStateService projectStateService;

    @Transactional
    public BindingResult createTeam(TeamCreateForm teamCreateForm, BindingResult bindingResult, SiteUser siteUser) {

        // input validation
        if(bindingResult.hasErrors()){
            return bindingResult;
        }

        // isUnique name
        if(!teamService.isUnique(teamCreateForm.getName())){
            bindingResult.reject("이름중복","이미 존재하는 팀 이름 입니다.");
            return bindingResult;
        }

        Team NewTeam = teamService.create(teamCreateForm, siteUser);
        teamMemberService.createTeamMember(NewTeam,siteUser);

        return bindingResult;
    }

    @SneakyThrows
    @Transactional
    public Team getTeamById(Long teamId, SiteUser siteUser) {
        Team team = teamService.getTeamById(teamId);

        if(!teamMemberService.isRegisteredMember(team,siteUser)){
            throw new Exception("권한이 없습니다.");
        }

        return team;
    }

    public List<Team> getTeamListByUser(SiteUser siteUser) {
        return teamService.getTeamListByUser(siteUser);
    }

    @Transactional
    public BindingResult modifyTeam(TeamModifyForm teamModifyForm, BindingResult bindingResult, SiteUser siteUser) {

        if(bindingResult.hasErrors()){
            return bindingResult;
        }

        Team team = getTeamById(teamModifyForm.getId(),siteUser);

        if(!team.getTeamAdmin().getUsername().equals(siteUser.getUsername())){
            bindingResult.reject("권한한이 없습니다.");
            return bindingResult;
        }

        teamService.modifyTeam(team,teamModifyForm);

        return bindingResult;
    }

    public BindingResult createProject(Team team, ProjectCreateForm projectCreateForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return bindingResult;
        }

        // 프로젝트명은 팀안에서만 유니크해도되겠지?
        if(!projectService.isUniqueProjectName(team,projectCreateForm.getName())){
            bindingResult.reject("이름중복","이미 존재하는 프로젝트 이름 입니다.");
            return bindingResult;
        }

        projectService.createProject(team,projectCreateForm);

        return bindingResult;
    }

    @SneakyThrows
    @Transactional
    public Project getProjectById(Long projectId,SiteUser siteUser) {

        Optional<Project> project = projectService.getProjectById(projectId);

        if(project.isEmpty()){
            throw new Exception("존재하지 않는 프로젝트 입니다.");
        }

        Team team = project.get().getTeam();

        if(!teamMemberService.isRegisteredMember(team,siteUser)){
            throw new Exception("잘못된 접근 입니다.");
        }

        return project.get();
    }

    @Transactional
    public void createProjectState(Project project, ProjectStateCreateForm projectStateCreateForm) {
        projectStateService.create(project,projectStateCreateForm);
    }

    @Transactional
    public void deleteProjectState(Long projectStateId) {
        ProjectState ps = projectStateService.findById(projectStateId);
        projectStateService.delete(ps);
    }

    public BindingResult modifyProject(Project project, ProjectModifyForm projectModifyForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return bindingResult;
        }

        projectService.modifyProject(project,projectModifyForm);

        return bindingResult;
    }

    public void deleteProject(Project project) {
        projectService.delete(project);
    }

    @Transactional
    @SneakyThrows
    public void deleteTeam(Long teamId, SiteUser siteUser) {
        Team team = getTeamById(teamId,siteUser);

        if(!team.getTeamAdmin().getUsername().equals(siteUser.getUsername())){
            throw new Exception("권한이 없습니다.");
        }
        teamService.delete(team);
    }

    @Transactional
    public TeamInviteController.TeamInviteResponse inviteMember(TeamInviteForm teamInviteForm, SiteUser siteUser, BindingResult bindingResult) {

        // 유효성 검사
        if(bindingResult.hasErrors()){
            return new TeamInviteController.TeamInviteResponse(false, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        // 팀 초대 권한 있는지 검사
        Team team = getTeamById(teamInviteForm.getTeamId(),siteUser);

        if(!team.getTeamAdmin().getUsername().equals(siteUser.getUsername())){
            return new TeamInviteController.TeamInviteResponse(false, "권한이 없습니다.");
        }

        // 존재하는 회원인지 검사
        SiteUser invitedUser = userService.findByNickname(teamInviteForm.getNickname());

        // 이미 팀 멤버인지 검사
        if(teamMemberService.isRegisteredMember(team, invitedUser)){
            return new TeamInviteController.TeamInviteResponse(false, "이미 팀 멤버인 회원입니다.");
        }

        teamInviteService.create(team, invitedUser);

        return new TeamInviteController.TeamInviteResponse(true, "초대 메세지를 전송하였습니다.");
    }

    @SneakyThrows
    @Transactional
    public Long deleteInvite(Long inviteId, SiteUser siteUser) {
        TeamInvite invite = teamInviteService.findById(inviteId);
        Team team = invite.getTeam();
        if(!team.getTeamAdmin().getUsername().equals(siteUser.getUsername())){
            throw new Exception("권한이 없습니다.");
        }

        teamInviteService.delete(invite);

        return invite.getTeam().getId();
    }

    public TeamMember getTeamMemberById(Long teamMemberId) {
        return teamMemberService.findById(teamMemberId);
    }

    @SneakyThrows
    @Transactional
    public void changeTeamAdmin(TeamMember teamMember, SiteUser siteUser) {

        Team team = teamMember.getTeam();

        if(!team.getTeamAdmin().getUsername().equals(siteUser.getUsername())){
            throw new Exception("권한이 없습니다.");
        }

        teamService.changeTeamAdmin(team,teamMember.getSiteUser());
    }

    @SneakyThrows
    @Transactional
    public void deleteTeamMember(TeamMember teamMember, SiteUser siteUser) {

        Team team = teamMember.getTeam();

        if(!team.getTeamAdmin().getUsername().equals(siteUser.getUsername())){
            throw new Exception("권한이 없습니다.");
        }

        if(team.getTeamAdmin().getUsername().equals(teamMember.getSiteUser().getUsername())){
            throw new Exception("팀장은 못나가~");
        }

        teamMemberService.deleteTeamMember(teamMember);
    }

    @SneakyThrows
    @Transactional
    public void leaveTeam(Long teamId, SiteUser siteUser) {
        Team team = teamService.getTeamById(teamId);

        if(team.getTeamAdmin().getUsername().equals(siteUser.getUsername())){
            throw new Exception("팀장은 못나가~");
        }

        TeamMember teamMember = teamMemberService.findByTeamAndSiteUser(team,siteUser);

        teamMemberService.deleteTeamMember(teamMember);

    }


    public ProjectStateController.changeProjectStateResponse changeProjectState(Project project, Long projectStateId, String state) {

        ProjectState ps = projectStateService.findById(projectStateId);

        projectStateService.update(ps,state);

        return new ProjectStateController.changeProjectStateResponse(true, "변경됨");
    }
}
