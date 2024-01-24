package devcom.main.global.init;

import devcom.main.domain.team.TeamCreateForm;
import devcom.main.domain.team.entity.Team;
import devcom.main.domain.team.service.TeamService;
import devcom.main.domain.teamMember.service.TeamMemberService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserSecurityService;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.beans.PropertyEditor;
import java.security.Principal;
import java.security.Security;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ServiceInit implements InitializingBean {

    private final UserService userService;
    private final TeamService teamService;
    private final TeamMemberService teamMemberService;

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    public void init(){

        userService.signup("user1", "유저1", "1234", "user1@gmai.com", "남".charAt(0), 21, 21, null, "Java");
        userService.signup("user2", "유저2", "1234", "user2@gmai.com", "남".charAt(0), 21, 21, null, "Java");
        userService.signup("user3", "유저3", "1234", "user3@gmai.com", "남".charAt(0), 21, 21, null, "Java");
        userService.signup("user4", "유저4", "1234", "user4@gmai.com", "남".charAt(0), 21, 21, null, "Java");
        userService.signup("user5", "유저5", "1234", "user5@gmai.com", "남".charAt(0), 21, 21, null, "Java");

        TeamCreateForm team1 = new TeamCreateForm();
        team1.setName("팀1");
        team1.setDescription("팀1 설명");

        TeamCreateForm team2 = new TeamCreateForm();
        team2.setName("팀2");
        team2.setDescription("팀2 설명");

        TeamCreateForm team3 = new TeamCreateForm();
        team3.setName("팀3");
        team3.setDescription("팀3 설명");

        TeamCreateForm team4 = new TeamCreateForm();
        team4.setName("팀4");
        team4.setDescription("팀4 설명");

        TeamCreateForm team5 = new TeamCreateForm();
        team5.setName("팀5");
        team5.setDescription("팀5 설명");

        SiteUser st = userService.findByusername("user1");

        teamMemberService.createTeamMemberAdmin(teamService.create(team1, st), st);
        teamMemberService.createTeamMemberAdmin(teamService.create(team2, st), st);
        teamMemberService.createTeamMemberAdmin(teamService.create(team3, st), st);
        teamMemberService.createTeamMemberAdmin(teamService.create(team4, st), st);
        teamMemberService.createTeamMemberAdmin(teamService.create(team5, st), st);

    }
}
