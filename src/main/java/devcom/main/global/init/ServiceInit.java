package devcom.main.global.init;

import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.skill.service.SkillService;
import devcom.main.domain.team.TeamCreateForm;
import devcom.main.domain.team.service.TeamService;
import devcom.main.domain.teamMember.service.TeamMemberService;
import devcom.main.domain.user.UserCreateForm;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceInit implements InitializingBean {

    private final UserService userService;
    private final TeamService teamService;
    private final TeamMemberService teamMemberService;
    private final SkillService skillService;

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    public void init(){
        List<String> skillList = new ArrayList<>();
        skillList.add("Java");

        List<Skill> sl = skillService.findByskillList(skillList);

        UserCreateForm userCreateForm1 = new UserCreateForm();
        userCreateForm1.setUsername("user1");
        userCreateForm1.setSkill(skillList);
        userCreateForm1.setPassword2("1234");
        userCreateForm1.setPassword1("1234");
        userCreateForm1.setEmail("user1@gmai.com");
        userCreateForm1.setNickname("유저1");
        userCreateForm1.setAge(21);
        userCreateForm1.setSex("남".charAt(0));
        userCreateForm1.setProfileImg("https://cdn.spotvnews.co.kr/news/photo/202212/572523_797960_5232.jpg");

        UserCreateForm userCreateForm2 = new UserCreateForm();
        userCreateForm2.setUsername("user2");
        userCreateForm2.setSkill(skillList);
        userCreateForm2.setPassword2("1234");
        userCreateForm2.setPassword1("1234");
        userCreateForm2.setEmail("user2@gmai.com");
        userCreateForm2.setNickname("유저2");
        userCreateForm2.setAge(21);
        userCreateForm2.setSex("남".charAt(0));
        userCreateForm2.setProfileImg("https://cdn.spotvnews.co.kr/news/photo/202212/572523_797960_5232.jpg");

        UserCreateForm userCreateForm3 = new UserCreateForm();
        userCreateForm3.setUsername("user3");
        userCreateForm3.setSkill(skillList);
        userCreateForm3.setPassword2("1234");
        userCreateForm3.setPassword1("1234");
        userCreateForm3.setEmail("user3@gmai.com");
        userCreateForm3.setNickname("유저3");
        userCreateForm3.setAge(21);
        userCreateForm3.setSex("남".charAt(0));
        userCreateForm3.setProfileImg("https://cdn.spotvnews.co.kr/news/photo/202212/572523_797960_5232.jpg");

        UserCreateForm userCreateForm4 = new UserCreateForm();
        userCreateForm4.setUsername("user4");
        userCreateForm4.setSkill(skillList);
        userCreateForm4.setPassword2("1234");
        userCreateForm4.setPassword1("1234");
        userCreateForm4.setEmail("user4@gmai.com");
        userCreateForm4.setNickname("유저4");
        userCreateForm4.setAge(21);
        userCreateForm4.setSex("남".charAt(0));
        userCreateForm4.setProfileImg("https://cdn.spotvnews.co.kr/news/photo/202212/572523_797960_5232.jpg");

        UserCreateForm userCreateForm5 = new UserCreateForm();
        userCreateForm5.setUsername("user5");
        userCreateForm5.setSkill(skillList);
        userCreateForm5.setPassword2("1234");
        userCreateForm5.setPassword1("1234");
        userCreateForm5.setEmail("user5@gmai.com");
        userCreateForm5.setNickname("유저5");
        userCreateForm5.setAge(21);
        userCreateForm5.setSex("남".charAt(0));
        userCreateForm5.setProfileImg("https://cdn.spotvnews.co.kr/news/photo/202212/572523_797960_5232.jpg");


        userService.signup(userCreateForm1, sl);
        userService.signup(userCreateForm2, sl);
        userService.signup(userCreateForm3, sl);
        userService.signup(userCreateForm4, sl);
        userService.signup(userCreateForm5, sl);

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

        SiteUser st = userService.findByUsername("user1");

        teamMemberService.createTeamMemberAdmin(teamService.create(team1, st), st);
        teamMemberService.createTeamMemberAdmin(teamService.create(team2, st), st);
        teamMemberService.createTeamMemberAdmin(teamService.create(team3, st), st);
        teamMemberService.createTeamMemberAdmin(teamService.create(team4, st), st);
        teamMemberService.createTeamMemberAdmin(teamService.create(team5, st), st);

    }
}
