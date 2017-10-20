package Exemple;

import java.net.Socket;



public class affichetClient extends Thread{
    private  boolean fin = true;
    private Socket socket ;
	public boolean isFin() {
		return fin;
	}
	public void setSocket(Socket socket){
		this.socket=socket;
	}
	public void setFin(boolean t){
    	fin=t;
    }
	
	public void run(){
		while (fin) {
			TestClient.chargerStrams(socket);
			TestClient.afficher();	
		}
	}
	
}
