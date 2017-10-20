package Exemple;

import java.net.Socket;

public class TreadReceive extends Thread{
    Socket socket1;
	boolean fin=true ;
	public void setFin(boolean fin){
		this.fin=fin;
	}
	public void setSocket(Socket socket){
		this.socket1=socket;
	}
	public void run(){
		while(fin){
			TestClient.chargerStrams(socket1);
			TestClient.rececive();
		}
	}
	
}
