package server;

public class ServerStartUpThread extends Thread{
	
	private Server server;
	
//	public ServerStartUpThread(Server server){
//		this.server = server;
//	}
	
	public void run(){
		ServerGUI.server.run();
	}
}
