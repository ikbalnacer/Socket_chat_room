package Exemple;

import java.awt.Image;
import java.io.Serializable;

public class Auth implements Serializable{

	private String Email;
	private String mot_de_pass;

 	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getMot_de_pass() {
		return mot_de_pass;
	}
	public void setMot_de_pass(String mot_de_pass) {
		this.mot_de_pass = mot_de_pass;
	}
	
	
	
}
