package devcom.main.global.rq;

import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.lang.reflect.Member;
import java.security.Principal;

@Component("rq")
@RequiredArgsConstructor
public class  Rq {

    private final UserService userService;
    private static SiteUser loginedUser;

    @RequestScope
    public void doLogin(SiteUser siteUser) {
        if(siteUser != null){
            loginedUser = siteUser;
        }
    }

    @RequestScope
    public void doLogout() {
        loginedUser = null;
    }

    public SiteUser getLoginedUser() {
        return loginedUser;
    }
}
