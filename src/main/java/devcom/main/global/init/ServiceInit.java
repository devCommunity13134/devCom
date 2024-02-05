//package devcom.main.global.init;
//
//import devcom.main.domain.answer.Service.AnswerService;
//import devcom.main.domain.article.entity.Article;
//import devcom.main.domain.article.service.ArticleService;
//import devcom.main.domain.category.entity.Category;
//import devcom.main.domain.category.service.CategoryService;
//import devcom.main.domain.skill.entity.Skill;
//import devcom.main.domain.skill.service.SkillService;
//import devcom.main.domain.team.TeamCreateForm;
//import devcom.main.domain.team.entity.Team;
//import devcom.main.domain.team.service.TeamAndProjectService;
//import devcom.main.domain.team.service.TeamService;
//import devcom.main.domain.teamInvite.TeamInviteForm;
//import devcom.main.domain.teamInvite.entity.TeamInvite;
//import devcom.main.domain.teamInvite.service.TeamInviteService;
//import devcom.main.domain.teamMember.service.TeamMemberService;
//import devcom.main.domain.user.UserCreateForm;
//import devcom.main.domain.user.entity.SiteUser;
//import devcom.main.domain.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.PropertyEditorRegistry;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.beans.PropertyEditor;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class ServiceInit implements InitializingBean {
//
//    private final UserService userService;
//    private final TeamService teamService;
//    private final TeamMemberService teamMemberService;
//    private final TeamAndProjectService teamAndProjectService;
//
//    private final SkillService skillService;
//    private final CategoryService categoryService;
//    private final ArticleService articleService;
//    private final AnswerService answerService;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        init();
//    }
//
//    public void init() throws IOException {
//        List<String> skillList = new ArrayList<>();
//        skillList.add("Java");
//
//        List<Skill> sl = skillService.findByskillList(skillList);
//
//        for(int i = 1; i<=10; i++){
//            UserCreateForm userCreateForm1 = new UserCreateForm();
//            userCreateForm1.setUsername("user"+i);
//            userCreateForm1.setSkill(skillList);
//            userCreateForm1.setPassword2("1234");
//            userCreateForm1.setPassword1("1234");
//            userCreateForm1.setEmail("user"+i+"@gmai.com");
//            userCreateForm1.setNickname("유저"+i);
//            userCreateForm1.setAge(21);
//            userCreateForm1.setPhoneNumber("01010047979");
//            userCreateForm1.setSex("남".charAt(0));
//
//            userService.signup(userCreateForm1, sl, new MultipartFile() {
//                @Override
//                public String getName() {
//                    return null;
//                }
//
//                @Override
//                public String getOriginalFilename() {
//                    return null;
//                }
//
//                @Override
//                public String getContentType() {
//                    return null;
//                }
//
//                @Override
//                public boolean isEmpty() {
//                    return false;
//                }
//
//                @Override
//                public long getSize() {
//                    return 0;
//                }
//
//                @Override
//                public byte[] getBytes() throws IOException {
//                    return new byte[0];
//                }
//
//                @Override
//                public InputStream getInputStream() throws IOException {
//                    return null;
//                }
//
//                @Override
//                public void transferTo(File dest) throws IOException, IllegalStateException {
//
//                }
//            });
//
//            TeamCreateForm team1 = new TeamCreateForm();
//            team1.setName("팀"+i);
//            team1.setDescription("팀"+i+" 설명");
//
//            SiteUser st = userService.findByUsername("user1");
//
//            teamMemberService.createTeamMember(teamService.create(team1, st), st);
//        }
//
//        for(long i = 1; i <10; i++) {
//            BindingResult br = new BindingResult() {
//                @Override
//                public String getObjectName() {
//                    return null;
//                }
//
//                @Override
//                public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {
//
//                }
//
//                @Override
//                public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {
//
//                }
//
//                @Override
//                public List<ObjectError> getGlobalErrors() {
//                    return null;
//                }
//
//                @Override
//                public List<FieldError> getFieldErrors() {
//                    return null;
//                }
//
//                @Override
//                public Object getFieldValue(String field) {
//                    return null;
//                }
//
//                @Override
//                public String toString() {
//                    return null;
//                }
//
//                @Override
//                public Object getTarget() {
//                    return null;
//                }
//
//                @Override
//                public Map<String, Object> getModel() {
//                    return null;
//                }
//
//                @Override
//                public Object getRawFieldValue(String field) {
//                    return null;
//                }
//
//                @Override
//                public PropertyEditor findEditor(String field, Class<?> valueType) {
//                    return null;
//                }
//
//                @Override
//                public PropertyEditorRegistry getPropertyEditorRegistry() {
//                    return null;
//                }
//
//                @Override
//                public String[] resolveMessageCodes(String errorCode) {
//                    return new String[0];
//                }
//
//                @Override
//                public String[] resolveMessageCodes(String errorCode, String field) {
//                    return new String[0];
//                }
//
//                @Override
//                public void addError(ObjectError error) {
//
//                }
//                public boolean hasErrors(){
//                    return false;
//                }
//            };
//
//            Team team = teamService.getTeamById(i);
//
//            TeamInviteForm tf = new TeamInviteForm();
//            tf.setNickname(userService.findByUsername("user2").getNickname());
//            tf.setTeamId(team.getId());
//
//            teamAndProjectService.inviteMember(tf, team.getTeamAdmin() , br);
//        }
//
//        this.categoryService.create("frontEnd");
//        this.categoryService.create("backEnd");
//        this.categoryService.create("dataEngineer");
//        this.categoryService.create("AI");
//
//
//        SiteUser author = this.userService.findByUsername("user1");
//        Category category = this.categoryService.getCategory("frontEnd");
//        for (int i = 1; i <= 10; i++) {
//            String subject = String.format("TestSubject:[%03d]", i);
//            String content = String.format("TestContetn:[%03d] Lorem ipsum dolor sit amet, " +
//                            "consectetur adipisicing elit. Aperiam commodi minima optio placeat quaerat" +
//                            " saepe voluptatum! A animi consectetur ducimus esse facilis molestiae, nesciunt" +
//                            " nihil non perspiciatis provident rem totam!Lorem ipsum dolor sit amet, consectetur" +
//                            " adipisicing elit. Aperiam commodi minima optio placeat quaerat saepe voluptatum! A animi" +
//                            " consectetur ducimus esse facilis molestiae, nesciunt nihil non perspiciatis provident rem totam!"
//                    , i);
//            this.articleService.create(category, subject, content, author);
//        }
//        SiteUser author2 = this.userService.findByUsername("user2");
//        Category category2 = this.categoryService.getCategory("backEnd");
//        for (int i = 1; i <= 10; i++) {
//            String subject = String.format("TestSubject:[%03d]", i);
//            String content = String.format("TestContetn:[%03d] Lorem ipsum dolor sit amet, " +
//                            "consectetur adipisicing elit. Aperiam commodi minima optio placeat quaerat" +
//                            " saepe voluptatum! A animi consectetur ducimus esse facilis molestiae, nesciunt" +
//                            " nihil non perspiciatis provident rem totam!Lorem ipsum dolor sit amet, consectetur" +
//                            " adipisicing elit. Aperiam commodi minima optio placeat quaerat saepe voluptatum! A animi" +
//                            " consectetur ducimus esse facilis molestiae, nesciunt nihil non perspiciatis provident rem totam!"
//                    , i);
//            this.articleService.create(category2, subject, content, author2);
//        }
//        SiteUser author3 = this.userService.findByUsername("user3");
//        Category category3 = this.categoryService.getCategory("dataEngineer");
//        for (int i = 1; i <= 10; i++) {
//            String subject = String.format("TestSubject:[%03d]", i);
//            String content = String.format("TestContetn:[%03d] Lorem ipsum dolor sit amet, " +
//                            "consectetur adipisicing elit. Aperiam commodi minima optio placeat quaerat" +
//                            " saepe voluptatum! A animi consectetur ducimus esse facilis molestiae, nesciunt" +
//                            " nihil non perspiciatis provident rem totam!Lorem ipsum dolor sit amet, consectetur" +
//                            " adipisicing elit. Aperiam commodi minima optio placeat quaerat saepe voluptatum! A animi" +
//                            " consectetur ducimus esse facilis molestiae, nesciunt nihil non perspiciatis provident rem totam!"
//                    , i);
//            this.articleService.create(category3, subject, content, author3);
//        }
//        SiteUser author4 = this.userService.findByUsername("user4");
//        Category category4 = this.categoryService.getCategory("AI");
//        for (int i = 1; i <= 10; i++) {
//            String subject = String.format("TestSubject:[%03d]", i);
//            String content = String.format("TestContetn:[%03d] Lorem ipsum dolor sit amet, " +
//                            "consectetur adipisicing elit. Aperiam commodi minima optio placeat quaerat" +
//                            " saepe voluptatum! A animi consectetur ducimus esse facilis molestiae, nesciunt" +
//                            " nihil non perspiciatis provident rem totam!Lorem ipsum dolor sit amet, consectetur" +
//                            " adipisicing elit. Aperiam commodi minima optio placeat quaerat saepe voluptatum! A animi" +
//                            " consectetur ducimus esse facilis molestiae, nesciunt nihil non perspiciatis provident rem totam!"
//                    , i);
//            this.articleService.create(category4, subject, content, author4);
//        }
//        Article article1 = this.articleService.getArticle(1);
//        this.answerService.create(article1,"testAnswerContent", author);
//    }
//}
