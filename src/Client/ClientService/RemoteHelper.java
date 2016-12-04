package Client.ClientService;

import Services.LoginService;
import Services.QueryService;

/**
 * Created by Sun YuHao on 2016/12/4.
 */
public class RemoteHelper {
    private QueryService queryService;
    private LoginService loginService;
    static private RemoteHelper remoteHelper = new RemoteHelper();

    static public RemoteHelper getInstance() {
        return remoteHelper;
    }

    public RemoteHelper() {
        queryService = new QueryServiceClient();
        loginService = new LoginServiceClient();
    }

    public QueryService getQueryService() {
        return queryService;
    }

    public LoginService getLoginService() {
        return loginService;
    }
}
