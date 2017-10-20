package Exemple;

import java.io.Serializable;

import javax.swing.ImageIcon;


public class Utilisateur implements Serializable {
private int id ;
private String motDepass;
private String Email;
private String Nom;
private String prenom;
private String nickname;
private String Message;
private transient ImageIcon image ;

public String getMessage() {
	return Message;
}
public void setMessage(String message) {
	Message = message;
}
public ImageIcon getImage() {
	return image;
}
public void setImage(ImageIcon image) {
	this.image = image;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNickname() {
	return nickname;
}
public void setNickname(String nickname) {
	this.nickname = nickname;
}
public Utilisateur(int id, String motDepass, String email, String nom,
		String prenom,String nickname,String message) {
	this.id = id;
	this.motDepass = motDepass;
	this.Email = email;
	this.Nom = nom;
	this.prenom = prenom;
	this.nickname=nickname;
	this.Message=message;
    }
public String getPrenom() {
	return prenom;
}
public void setPrenom(String prenom) {
	this.prenom = prenom;
}

public String getMotDepass() {
	return motDepass;
}
public void setMotDepass(String motDepass) {
	this.motDepass = motDepass;
}
public String getEmail() {
	return Email;
}
public void setEmail(String Email) {
	this.Email = Email;
}
public String getNom() {
	return Nom;
}
public void setNom(String nom) {
	Nom = nom;
}
}
