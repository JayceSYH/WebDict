package Client.ClientService;

import Services.LoginService;
import Utils.GetUtil;
import Utils.JsonUtil;
import org.apache.struts2.json.JSONUtil;

import java.util.Map;

import static org.jboss.logging.MDC.getMap;

/**
 * Created by Sun YuHao on 2016/12/4.
 */
public class LoginServiceClient implements LoginService {
    @Override
    public String login(String username, String password) {
        String ret = GetUtil.sendGet(GetUtil.url + "/login", "username=" +username + "&password=" + password);
        JsonUtil jsonUtil = new JsonUtil(ret);
        Map map = jsonUtil.getMap();

        return (String)map.get("session");
    }

    @Override
    public String register(String username, String password) {
        String ret = GetUtil.sendGet(GetUtil.url + "/register", "username=" +username + "&password=" + password);
        JsonUtil jsonUtil = new JsonUtil(ret);
        Map map = jsonUtil.getMap();

        return (String)map.get("session");
    }
}
