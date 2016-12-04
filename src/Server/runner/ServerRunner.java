package Server.runner;
import Server.rmi.RemoteHelper;

public class ServerRunner {
	
	public ServerRunner() {
		new RemoteHelper();
	}
	
	public static void main(String[] args) {
		new ServerRunner();
	}
}
