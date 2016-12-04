package Client.runner;

import Client.ClientService.RemoteHelper;
import Client.ui.MainFrame;

public class ClientRunner {
	private RemoteHelper remoteHelper;
	
	public ClientRunner() {
		initGUI();
	}
	
	private void initGUI() {
		MainFrame mainFrame = new MainFrame();
	}

	
	public static void main(String[] args){
		ClientRunner cr = new ClientRunner();
	}
}
