package Client.ClientService;

/**
 * Created by Sun YuHao on 2016/12/4.
 */
public class UserInfo {
    private String username;
    private String session;

    public UserInfo(String username, String session) {
        this.username = username;
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
