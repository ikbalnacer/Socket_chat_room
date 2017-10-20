package Exemple;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TestClient extends JFrame{
	 static CardLayout card = new CardLayout();
     static String [] tabCard = {"auth","inscrip","act"};
     static JPanel containerManager = new JPanel();
     //Attribute Welcome with Menu
     JLabel Email = new JLabel("Email       :   ");
     JTextField emailtxt = new JTextField();
     JLabel Mot_de_pass = new JLabel("mot de pass  : ");
     JPasswordField wordtxt = new JPasswordField();
     JPanel pancenter = new JPanel(),container = new JPanel();
     JLabel label = new JLabel(new ImageIcon("D:\\welcome_to_chat.png")),label1=new JLabel(new ImageIcon("D:\\just.png"));
     JPanel panG =new JPanel(),panD= new JPanel();
     JButton connexion = new JButton(new ImageIcon("D:\\Conn.png"));
     JMenuBar menuB = new JMenuBar();
     JButton insc = new JButton(new ImageIcon("D:\\Inscr.png"));
     JMenu menu = new JMenu(" Chat "),menu1 = new JMenu(" ? ");  
     JMenuItem Exit = new JMenuItem("Exit");
     // Attribute Inscription
     JPanel Inscrption_container = new JPanel(),pan_image = new JPanel(),pan_Perso= new JPanel(),
    		 pan_auth= new JPanel(),pan_fomulaire=new JPanel();
     JLabel inscription = new JLabel(new ImageIcon("D:\\Incription_chat_room.png")),nom = new JLabel("nom          :   ")
     ,prenom=new JLabel(" prenom   :   "), email=new JLabel("     Email           :  "),mot_de_pass2=new JLabel(" mot de pass   :  ")
     ,mot_de_pass_re=new JLabel(" re_mot de pass"),nickname=new JLabel("NickName : ");
     JTextField nomtxt = new JTextField(),prenomtxt = new JTextField(),emailtxt2 = new JTextField()
     ,nicknametxt = new JTextField();
     JPasswordField mot_de_passre=new JPasswordField(),mot_de_pass_txt=new JPasswordField();
     
     JButton insc2 = new JButton(new ImageIcon("D:\\Register.png"));
     JButton backB = new JButton(new ImageIcon("D:\\back.png"));
	static // reacting
	 JTextArea affichage = new JTextArea();
	JTextField ouOnEcrire = new JTextField();
	JPanel  container1 = new JPanel();
	JButton button = new JButton(new ImageIcon("D:\\SEnd1.png"));
	static JPanel panReacting = new JPanel();
   JPanel panInformation = new JPanel();
	static JLabel name_I = new JLabel("Nom     : ");
	static JLabel name_Inf = new JLabel();
	static JLabel prenom_I = new JLabel("Prenom     : ");
	static 	JLabel prenom_Inf = new JLabel();
	static  JLabel nickname_I = new JLabel("Nickname      : ");
	static  JLabel nickname_Inf = new JLabel();
	static	JLabel email_I = new JLabel("Email    : ");
	static JLabel email_Inf = new JLabel();
	static JLabel image_label = new JLabel();
	
	
	//list _ pan 
	static JPanel Collect_Both = new JPanel();
	static JPanel Image_pan = new JPanel();
	static JPanel list_pan = new JPanel();
	static JPanel pan_User_Online = new JPanel();
		JLabel nameL = new JLabel("Nom : "),prenomL = new JLabel("prenom : "),nicknameL=new JLabel(" nickname : "),emailL=new JLabel("Email : ");
	    JLabel nameR_L = new JLabel(),prenomR_L = new JLabel(),nicknamR_l = new JLabel(),email_R_l=new JLabel() ;
	static	boolean pass = false;
    static int type = 0;
	// connexion
	static Socket socket =null ;
	static BufferedReader in=null;
	static PrintStream out=null;
	String str_to_send=null;
	static ObjectOutputStream ObjectOut ;
	static ObjectInputStream ObjectIn;
	//static DataOutputStream dataout=null;
	static Utilisateur user=null;
	static	String [] Titles ={"Online Email :"};
	static	JList<String> table = new JList<String>(Titles);
	static ArrayList<String> names = new ArrayList<>();
	static DataInputStream indata=null;
	static ArrayList<String> tab = new ArrayList<String>() ;
	static affichetClient thread =null;

	public TestClient(){
		this.setTitle("Client");
	 	 this.setSize(470,370);
    	 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 this.setLocationRelativeTo(null); 
    	 intGraph();
    	 this.setVisible(true);
	} 
	
	public static void main(String [] args){
		tab.add("Online email :");
	try {
		TestClient t = new TestClient();
		socket = new Socket(InetAddress.getLocalHost(),1999);
		while(true){
		while(!pass)
			try {
				attendAuth();
			} catch (Exception e){
				e.printStackTrace();
			}
		chargerStrams(socket);
		thread = new affichetClient();
		thread.setSocket(socket);
		thread.start();
		if(pass){
		chargerStrams(socket);
        try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		}
		}	} catch (IOException e) {
		e.printStackTrace();
	}finally{
		try {
			socket.close();
			if(thread!=null)
			thread.setFin(false);
		} catch (IOException e) {		
			e.printStackTrace();
		}
	}  
    }
	
/*	public static void rececive(){
		 String str ="";
		try {
	     str =in.readLine();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    int j=0;
	    tab = new ArrayList<>();
	    tab.add("Online names");
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i)==';') {
				tab.add(str.substring(j,i));
				System.out.println(str.substring(j,i));
				j=i+1;
			}
		}
		Titles = new String [tab.size()];
		for (int i = 0; i < tab.size(); i++) {
			Titles[i]=tab.get(i);
		}
		    table = new JList<String>(Titles);
		    pan_User_Online.setPreferredSize(new Dimension(80,90));
	        table.setVisibleRowCount(5); 
	        pan_User_Online.revalidate();
	        Collect_Both.revalidate();  
	        panReacting.revalidate();
	}
	*/
	public static Utilisateur attendAuth()throws Exception{	
		String Str=null;
			try {
				
				while(!pass){
				ObjectIn = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				user = (Utilisateur) ObjectIn.readObject();
				chargerStrams(socket);
				Str=in.readLine();
				if(user!=null&&Str.equals("SUCCES")){

					pass=true;
					name_Inf.setText(user.getNom());
					prenom_Inf.setText(user.getPrenom());
					nickname_Inf.setText(user.getNickname());
					email_Inf.setText(user.getEmail());
					card.show(containerManager, tabCard[2]);
					System.out.println(user.getMessage());
				
				}else{
					if(type==1)
					{JOptionPane.showConfirmDialog(null,"THIS EMAIL IS USED BEFORE !!", "INFORMATION", JOptionPane.OK_OPTION);
					System.exit(1);
					}
					else{
					JOptionPane.showConfirmDialog(null,"SOMEONE IS USING A SESSION WITH THIS EMAIL  NOW !!", "INFORMATION", JOptionPane.OK_OPTION);
					System.exit(1);}
				}
				}
				return user;
			} catch (Exception e) {
				
	
				throw new Exception();

				}
			
	}
	
	public static void auth(Auth user){
		try {
			ObjectOut= new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			ObjectOut.writeShort(1);
			ObjectOut.writeObject(user);
			ObjectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void auth(Utilisateur user){
		try {
			ObjectOut= new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			ObjectOut.writeShort(2);
			ObjectOut.writeObject(user);
			ObjectOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void afficher(){
		 String str=null;
			try {
				
				    short choix=0;
					str = in.readLine();
					affichage.setText(affichage.getText()+"\n"+str);
		
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    
	     public static void chargerStrams(Socket socket){
		 try {
			 in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			 out = new PrintStream(socket.getOutputStream());
			} catch (IOException e) {
			e.printStackTrace();
			}
	     }
	     
	    
	    class sendListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				str_to_send =ouOnEcrire.getText();
				ouOnEcrire.setText("");
				Send();
			}
		}

	    public void Send (){	    
		affichage.setText(affichage.getText()+"\n"+"moi : "+str_to_send);
		 try {
			out = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			    out.println(str_to_send);
				out.flush();	
	    }
	    public void Send(String str){
			try {
				out = new PrintStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 out.println(str);
			 out.flush();	
	    }
	    
	    
	    public void intGraph(){
	    	 containerManager.setLayout(card);
	    	/// JMenu
	    	 menu.add(Exit);
	    	 menuB.add(menu);
             Exit.addActionListener(new OUT_Listenr());
	    	 menuB.add(menu1);
	    	 //Welcome page 
	    	 pancenter.add(Email);
	    	 emailtxt.setPreferredSize(new  Dimension(220,25));
	    	 pancenter.add(emailtxt);
	    	 pancenter.add(Mot_de_pass);
	    	 wordtxt.setPreferredSize(new Dimension(220,25));
	    	 pancenter.add(wordtxt);
	    	 connexion.setPreferredSize(new Dimension(88,30));
	    	 pancenter.add(connexion);
	    	 insc.setPreferredSize(new Dimension(88,30));
	    	 pancenter.add(insc);
	    	 pancenter.setPreferredSize(new Dimension(320,100));
	    	 container.setBackground(Color.WHITE);
	    	 pancenter.setBackground(Color.WHITE);
	    	 panD.add(label);
	    	 panG.add(label1); 
	    	 panD.setBackground(Color.WHITE);
	    	 panG.setBackground(Color.WHITE);
	         container.add(panD,BorderLayout.NORTH);
	         container.add(pancenter,BorderLayout.CENTER);
	         container.add(panG,BorderLayout.SOUTH);
	        containerManager.add(container,tabCard[0]);
	         // Inscription page 
	         pan_image.add(inscription);
	         pan_Perso.add(nom);
	         nomtxt.setPreferredSize(new Dimension(100,25));
	         pan_Perso.add(nomtxt);
	         pan_Perso.add(prenom);
	         prenomtxt.setPreferredSize(new Dimension(100,25));
	         pan_Perso.add(prenomtxt);
	         pan_Perso.add(nickname);
	         pan_Perso.setBorder(BorderFactory.createTitledBorder(" Espace Personel... "));
	         pan_Perso.setPreferredSize(new Dimension(220,120));
	         nicknametxt.setPreferredSize(new  Dimension(100,25));
	         pan_Perso.add(nicknametxt);
	         pan_auth.add(email);
	         emailtxt2.setPreferredSize(new Dimension(100,25));
	         pan_auth.add(emailtxt2);
	         pan_auth.add(mot_de_pass2);
	         mot_de_pass_txt.setPreferredSize(new Dimension(100,25));
	         pan_auth.add(mot_de_pass_txt);     
	         pan_auth.add(mot_de_pass_re);
	         mot_de_passre.setPreferredSize(new Dimension(100,25));
	         pan_auth.add(mot_de_passre);
	         pan_auth.setBorder(BorderFactory.createTitledBorder(" Authdentifie... "));
	         pan_auth.setPreferredSize(new Dimension(220,120));
	         
	        // list pan
	         list_pan.setPreferredSize(new Dimension(220,150));
	 	    list_pan.add(nameL);
	 	    list_pan.add(nameR_L);
	 	    nameR_L.setPreferredSize(new Dimension(80,25));
	 	    list_pan.add(prenomL);
	 	    list_pan.add(prenomR_L);
	 	    prenomR_L.setPreferredSize(new Dimension(80,25));
	 	    list_pan.add(nicknameL);
	 	    list_pan.add(nicknamR_l);
	 	    nicknamR_l.setPreferredSize(new Dimension(80,25));
	 	    list_pan.add(emailL);
	 	    list_pan.add(email_R_l);
	 	    email_R_l.setPreferredSize(new Dimension(80,25));
	         
	 	    
	         pan_fomulaire.add(pan_Perso);
	         pan_fomulaire.add(pan_auth);
	         insc2.setPreferredSize(new Dimension(90,30));
	         backB.setPreferredSize(new Dimension(83,30));
	         pan_fomulaire.add(insc2);
	         pan_fomulaire.add(backB);
	         pan_fomulaire.setPreferredSize(new Dimension(450,180));
	         
	         Inscrption_container.add(pan_image,BorderLayout.NORTH);
	         Inscrption_container.add(pan_fomulaire,BorderLayout.CENTER);
	         containerManager.add(Inscrption_container,tabCard[1]);
	       
	         // reacting implement 
	        
	         affichage.setPreferredSize(new Dimension(240,400));
			 affichage.setEditable(false);
			 affichage.setBorder(BorderFactory.createTitledBorder("Messages "));
			 ouOnEcrire.setPreferredSize(new Dimension(200,50));
			 panInformation.setPreferredSize(new Dimension(130,100));
			 image_label.setPreferredSize(new Dimension(30,30));
			 pan_User_Online.setPreferredSize(new Dimension(80,90));
	         table.setVisibleRowCount(5);
	         
	         pan_User_Online.add(new JScrollPane(table),BorderLayout.EAST);
			 Image_pan.add(image_label);
	         
			 panInformation.add(name_I);
			 panInformation.add(name_Inf);
			 panInformation.add(prenom_I);
			 panInformation.add(prenom_Inf);
			 panInformation.add(nickname_I);
			 panInformation.add(nickname_Inf);
			 panInformation.add(email_I);
			 panInformation.add(email_Inf);
			 Collect_Both.add(Image_pan,BorderLayout.EAST);
			 Collect_Both.add(pan_User_Online,BorderLayout.WEST);
             
			 panReacting.add(Collect_Both,BorderLayout.NORTH);
             
			 panReacting.add(panInformation,BorderLayout.CENTER);
			 panReacting.add(ouOnEcrire,BorderLayout.CENTER);
			 button.setPreferredSize(new Dimension(60,25));
			 panReacting.add(button,BorderLayout.SOUTH);
			 panReacting.setPreferredSize(new Dimension(200,400));
		     button.addActionListener(new sendListener());
		     container1.add(panReacting,BorderLayout.EAST);
		     container1.add(affichage,BorderLayout.WEST);
	         containerManager.add(container1,tabCard[2] );         
	         
	         // ajouter les Listener 
	         backB.addActionListener(new Welcome_page_Listener());
	         insc.addActionListener(new inscription_page_Listener());
	         connexion.addActionListener(new Connexion_Listener());
	         insc2.addActionListener(new Register_Listener());
	         
	         this.getContentPane().add(menuB,BorderLayout.NORTH);
	    	 this.getContentPane().add(containerManager,BorderLayout.CENTER);
	     }
	 
	    class inscription_page_Listener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
			card.show(containerManager, tabCard[1]);
			type=1;
			}
		}
	    
		class Welcome_page_Listener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				card.show(containerManager, tabCard[0]);
				type=0;
			}
			
		}
		
	    class Connexion_Listener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
			Auth auth = new Auth();
			auth.setEmail(emailtxt.getText());
			auth.setMot_de_pass(wordtxt.getText());
			emailtxt.setText("");
			wordtxt.setText("");
		       System.out.println("awe saye");
			auth(auth);
			}	
	    }
	    
	    class Register_Listener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(mot_de_pass_txt.getText().equals(mot_de_passre.getText()))
				{ user = new Utilisateur(0,mot_de_pass_txt.getText(),emailtxt2.getText(),
					nomtxt.getText(),prenomtxt.getText(),nicknametxt.getText(),"");
			       auth(user);
				}
			}    	
	    }
	    class OUT_Listenr implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
             Send("DECDECODECODEOCODECODEOC194521547jeua1457tokyoNEWYORK_LASvegas");				
			if(thread !=null)
			 thread.setFin(true);
			 System.exit(1);
             card.show(containerManager, tabCard[0]);
			}
	    	
	    }
	    
}
