package Client.rmi;

import Services.LoginService;
import Services.QueryService;
import java.rmi.Remote;

public class RemoteHelper {
	private Remote remote;
	private static RemoteHelper remoteHelper = new RemoteHelper();
	public static RemoteHelper getInstance(){
		return remoteHelper;
	}
	
	private RemoteHelper() {}
	
	public void setRemote(Remote remote){
		this.remote = remote;
	}

	public QueryService getQueryService() {
		return (QueryService)remote;
	}
	public LoginService getLoginService() { return (LoginService)remote; }
}
