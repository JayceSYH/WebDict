package Client.ClientService;

import Exception.*;

/**
 * Created by Sun YuHao on 2016/12/3.
 */
public class UserManager {
    static private UserInfo userInfo;

    static public void Login(UserInfo userInfo) {
        UserManager.userInfo = userInfo;
    }

    static public void logout() {
        userInfo = null;
    }

    static public UserInfo getUserInfo() throws NotLoginException {
        return userInfo;
    }

    static public boolean isLogin() {
        return userInfo != null;
    }
}
