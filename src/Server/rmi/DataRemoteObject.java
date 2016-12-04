package Server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Server.serviceImpl.LoginServiceImpl;
import Server.serviceImpl.QueryServiceImpl;
import Services.LoginService;
import Services.QueryService;

public class DataRemoteObject extends UnicastRemoteObject implements QueryService, LoginService {
	private static final long serialVersionUID = 4029039744279087114L;
	private QueryService queryService;
	private LoginService loginService;

	public DataRemoteObject() throws RemoteException {
		queryService = new QueryServiceImpl();
		loginService = new LoginServiceImpl();
	}

	@Override
	public String[] queryWord(String word) throws RemoteException {
		return queryService.queryWord(word);
	}

	@Override
	public void toggleApprovTranslation(String session, String word, String translator) throws RemoteException {
		queryService.toggleApprovTranslation(session, word, translator);
	}

	@Override
	public int getApprovNum(String word, String translator) throws RemoteException {
		return queryService.getApprovNum(word, translator);
	}

	@Override
	public String login(String username, String password) throws RemoteException {
		return loginService.login(username, password);
	}

	@Override
	public String register(String username, String password) throws RemoteException {
		return loginService.register(username, password);
	}
}
