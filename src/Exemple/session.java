package Exemple;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import Exemple.TestClient.sendListener;


public class session extends Thread {
	private Socket socket ;
	private int auth;
	private  Utilisateur user;
	private Boolean still=true,able = false;
	private  ObjectInputStream ObjectIn=null;
    private    User utilisateur = new UserDaoImp();
    private   Admistrateur admin = new UserDaoImp();

	
	public Boolean getStill() {
		return still;
	}

	public void setStill(Boolean still) {
		this.still = still;
	}

	public Boolean getAble() {
		return able;
	}

	public void setAble(Boolean able) {
		this.able = able;
	}

	public session(Socket socket,int auth){
		 this.socket = socket;
		 this.auth=auth;
	 }
	 
    public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	public void setable(boolean able){
    	this.able=able;
    }
	public void setstill(Boolean sti){
		still=sti;
	}
	public Utilisateur getUser() {
		return user;
	}
	
	public void run(){
	   while (true) {
		try {
		if(able){
		    Serveur.chargerStrams(socket);
	        Serveur.afficher(auth,user.getNickname(),user.getEmail(),socket);
	        if(!still){
	        	break;}
		}else{
			Auth(socket,auth);
			int k  =0;
			if(Serveur.espaceSession.size()>2)
				for (int j = 0; j < Serveur.espaceSession.size(); j++) 
				{if(Serveur.espaceSession.get(j).getUser()!=null)
					if(Serveur.espaceSession.get(j).getUser()
		         .getEmail().equalsIgnoreCase(user.getEmail()))
					{k++;}
				}
			if(k==2){
				user=null;
				Serveur.SendUtilisateur(null,socket);
			    Serveur.Send(auth, "some one using this account now :p");}
			 if(!user.getMessage().equals("SUCCES"))
			    { 
				  Serveur.Send(auth, "INSCRIPTION FAIL SORRY THIS EMAIL IS USED BEFORE ! :p");
				  Serveur.SendUtilisateur(null,socket);
                  user =null;
		    	break;
			    }
		    if (user!=null) 
		    {   Serveur.SendUtilisateur(user,socket);
			    Serveur.Send(getAuth(), "SUCCES");
		    	setable(true);}
		    else{break;}
		   
        }
		}catch(Exception e){
			   break;
		}
	   }
      }
	
	   public  void Auth(Socket socket,int ath)throws Exception{
	    	try {
				ObjectIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				short i = ObjectIn.readShort();
		    	try {
		    		if(i==1){
		    	    int k =0;
		    	    Auth auth = new Auth(); 
					auth = (Auth) ObjectIn.readObject();	
					user = utilisateur.authentifié(auth);	
					Serveur.espaceSession.add(this);
					Serveur.names.add(user.getNom());
		    		}
		    		if(i==2){
	                user =(Utilisateur) ObjectIn.readObject();	
	                user =admin.Inscription(user);        	
					Serveur.espaceSession.add(this);
					Serveur.names.add(user.getNom());
					}
		    		
				} catch (ClassNotFoundException e) {
                   	 throw new Exception();	
				}
	    	} catch (IOException e) {
              	 throw new Exception();	

	    	}
	    }
}
