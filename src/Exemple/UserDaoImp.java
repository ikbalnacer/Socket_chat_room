package Exemple;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class UserDaoImp implements Admistrateur {
	
	UserFactory usefac;
    PreparedStatement prep ;
    PreparedStatement prep1 ;
    PreparedStatement prep2 ;
    PreparedStatement prep3 ;
    PreparedStatement prep4 ;
    PreparedStatement prep5 ;
    PreparedStatement prep6 ;
    PreparedStatement prep7 ;
    PreparedStatement prep8 ;
    PreparedStatement prep9 ;
    
    public UserDaoImp(){
    	usefac = UserFactory.getInstance("chat_room","ikbal","ikol1945");
    	usefac.getConnection();
    	prep = usefac.prepare("select *from user where email=? and mot_de_pass=? ;");
    	prep1 = usefac.prepare("insert into user values(?,?,?,?,?,?);");
    	prep2 = usefac.prepare("update user set nom=? ,prenom=?,nickname=? where email=?");
    	prep3 = usefac.prepare("update user set nom=? ,prenom=? where email=?");
    	prep4 = usefac.prepare("update user set nom=?,nickname=? where email=?");
    	prep5 = usefac.prepare("update user set prenom=?,nickname=? where email=?");
    	prep6 = usefac.prepare("update user set nom=?  where email=?");
    	prep7 = usefac.prepare("update user set nickname=? where email=?");
    	prep8 = usefac.prepare("update user set prenom=? where email=?");
    	prep9 = usefac.prepare("delete from user where email=?");
    }
    		
    @Override
	public Utilisateur authentifié(Auth auth) {
    	ResultSet result=null;
    	try {
			prep.setString(1,auth.getEmail());
			prep.setString(2,auth.getMot_de_pass());
	    	result= usefac.executeQuery(prep);	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	Utilisateur user=null;
    	try {
			while (result.next()) {
				user = new Utilisateur(result.getInt("id"), result.getString("mot_de_pass"),result.getString("email"),
						result.getString("nom"), result.getString("prenom"),result.getString("nickname"),"SUCCES");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Utilisateur Inscription(Utilisateur user) {
		try {
			prep1.setInt(1,user.getId());
			prep1.setString(3,user.getMotDepass());
			prep1.setString(2,user.getEmail());
			prep1.setString(4,user.getNom());
			prep1.setString(5,user.getPrenom());
			prep1.setString(6,user.getNickname());
			int  i=usefac.executeUpdate(prep1);
			if(i!=0)
			user.setMessage("SUCCES");
			else{
	        user.setMessage("FAIL THIS EMAIL EXIST");
			}
		} catch (SQLException e) {
			System.out.println("nononononononno");
            user.setMessage("FAIL THIS EMAIL EXIST");
            return user;
		}
		return user;
	}

	@Override
	public void Modifier(Utilisateur user) {
		if (user.getNom()!=null&&user.getPrenom()!=null&&user.getNickname()!=null) {
			try {
				prep2.setString(1,user.getNom() );
				prep2.setString(2,user.getPrenom() );
				prep2.setString(3,user.getNickname());
				prep2.setString(4,user.getEmail() );
				usefac.executeUpdate(prep2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			if(user.getNom()!=null&&user.getPrenom()!=null){
				try {
				    prep3.setString(1,user.getNom() );
					prep3.setString(2,user.getPrenom() );
					prep3.setString(3,user.getEmail() );
					usefac.executeUpdate(prep3);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				if(user.getNom()!=null&&user.getNickname()!=null){
					try {
					    prep4.setString(1,user.getNom() );
						prep4.setString(2,user.getPrenom() );
						prep4.setString(3,user.getEmail() );
						usefac.executeUpdate(prep4);
					} catch (SQLException e) {
                        
						e.printStackTrace();
					}
				}else{
					if (user.getNickname()!=null&&user.getPrenom()!=null) {
						try {
						    prep5.setString(1,user.getPrenom() );
							prep5.setString(2,user.getNickname());
							prep5.setString(3,user.getEmail() );
							usefac.executeUpdate(prep5);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}else{
						if(user.getNom()!=null){
							try {
							    prep6.setString(1,user.getNom() );
								prep6.setString(2,user.getEmail() );
								usefac.executeUpdate(prep6);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}else{
							if(user.getPrenom()!=null){
								try {
								    prep7.setString(1,user.getPrenom() );
									prep7.setString(2,user.getEmail() );
									usefac.executeUpdate(prep7);	
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}else{
								if(user.getNickname()!=null){
									try {
										prep8.setString(1,user.getNickname());
										prep8.setString(2,user.getEmail() );
										usefac.executeUpdate(prep8);
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void supprimer(String  email) {
      try {
		prep9.setString(1, email);
		usefac.executeUpdate(prep9);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}
    
}
