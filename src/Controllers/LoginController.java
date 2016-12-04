package Controllers;

import Server.serviceImpl.LoginServiceImpl;
import Server.serviceImpl.QueryServiceImpl;
import Services.LoginService;
import Services.QueryService;
import org.springframework.stereotype.Controller;

/**
 * Created by Sun YuHao on 2016/12/4.
 */
@Controller
public class LoginController extends JSONAction {
    LoginService loginService;

    public LoginController() {
        loginService = new LoginServiceImpl();
    }

    public String login() {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        setResult(new MSession(loginService.login(username, password)));

        return SUCCESS;
    }

    public String register() {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        setResult(new MSession(loginService.register(username, password)));

        return SUCCESS;
    }

    public class MSession {
        public MSession(String session) {
            this.session = session;
        }

        public String session;

        public String getSession() {
            return session;
        }

        public void setSession(String session) {
            this.session = session;
        }
    }
}
