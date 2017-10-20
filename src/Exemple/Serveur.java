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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Serveur extends JFrame {
	CardLayout card = new CardLayout();
	String [] tab = {"footer","modifie","consult"};
	/// graphics
	static JTextArea affichage = new JTextArea();
		JPanel  container = new JPanel(),footer = new JPanel();
		static JTextField Email = new JTextField();
		JButton delete = new JButton(new ImageIcon("D:\\Send.png"));
		JButton Modifie = new JButton(new ImageIcon("D:\\Modifie.png"));
		JButton outB = new JButton(new ImageIcon("D:\\out.png"));
		JLabel label = new JLabel("Email : ");
	static	String [] Titles ={"Online  Names    : "};
	static	JList<String> table = new JList<String>(Titles);
	     JButton backB = new JButton(new ImageIcon("D:\\back.png")),backB1 = new JButton(new ImageIcon("D:\\back.png")),modifie2 = new JButton(new ImageIcon("D:\\Modifie.png"))
	     ,referech = new JButton(new ImageIcon("D:\\Referech.png")),get_Information=new JButton(new ImageIcon("D:\\consult.png")),show_inf =new JButton(new ImageIcon("D:\\consult.png"));
		JLabel name = new JLabel("Nom : "),prenom = new JLabel("prenom : "),nickname=new JLabel(" nickname : "),email=new JLabel("Email : ");
		JTextField nametxt = new JTextField(),prenomtxt = new JTextField(),nicknametxt = new JTextField(),
				emailtxt = new JTextField(),write_nickname = new JTextField();
	static JPanel pan = new JPanel(),modifie_pan = new JPanel(),container2 = new JPanel();
	static	ArrayList<session> espaceSession = new ArrayList<session>();
    static  Admistrateur admin = new UserDaoImp();
    static  User utilisateur = new UserDaoImp();
    
    static JPanel list_pan = new JPanel();
	JLabel nameL = new JLabel("Nom : "),prenomL = new JLabel("prenom : "),nicknameL=new JLabel(" nickname : "),emailL=new JLabel("Email : ");
    JLabel nameR_L = new JLabel(),prenomR_L = new JLabel(),nicknamR_l = new JLabel(),email_R_l=new JLabel() ;
    static Message m =new Message();
	/// connexion
	static ServerSocket serveur =null;
	static Socket socket =null ;
	static BufferedReader in=null;
	 
	static PrintStream out=null;
	//static BufferedReader br =null;
	static String str_to_send =null;
	
	static int co=0;
	static Auth auth ;
//	static DataInputStream inData =null;
	//static DataOutputStream outData = null;
	static Utilisateur user;

	static ObjectOutputStream ObjectOut=null;
	static ArrayList<Integer> table_Auth_Out= new ArrayList<Integer>();
	static ArrayList<String> names = new ArrayList<String>();
	public Serveur(){
		this.setTitle("Serveur");
		this.setSize(450,350);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setBackground(Color.WHITE);
		intServeur(); 
		this.setVisible(true);
	}
	
    public static void main(String [] args){
	  try {
		Serveur s = new Serveur();
		serveur = new ServerSocket(1999);
		while(true){
		socket = serveur.accept();
		session sess = new session(socket,co++);
		sess.start();
		}
     	} catch (IOException e) {
     		System.out.println("exception");
		e.printStackTrace();
	    }finally{
		try {
		socket.close();
		serveur.close();
		}catch (IOException e) {
		e.printStackTrace();
		 }
	    }
       }
    
    public static void SendUtilisateur(Utilisateur user,Socket socket){
    	try {
			ObjectOut= new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			ObjectOut.writeObject(user);
			ObjectOut.flush();
		} catch (IOException e) {
		}
    }
    
    public static void SendD(int auth,String str){	
        for (int i = 0; i < espaceSession.size(); i++) {
        if (!(espaceSession.get(i).getAuth()==auth)) {
        chargerStrams(espaceSession.get(i).getSocket());           
        out.println(str);
      	out.flush();
      	}
      	} 
    }
    
    public static void Send (int auth,String str){	
        for (int i = 0; i < espaceSession.size(); i++) {
        if ((espaceSession.get(i).getAuth()==auth)) {
        chargerStrams(espaceSession.get(i).getSocket());
        out.println(str);
        out.flush();		
      	}
      	} 
    }
    
    public static  void afficher(int auth,String  nickname,String email,Socket socket){		
          String str = null;
		try {
			for (int i = 0; i < espaceSession.size(); i++) {
				System.out.println(espaceSession.get(i).getUser().getNom());
			}
			str =in.readLine();
			if(str.contains("DECDECODECODEOCODECODEOC194521547jeua1457tokyoNEWYORK_LASvegas"))
			{  OUT(email);
		   	affichage.setText(affichage.getText()+"\n"+ email+": "+ "IS OUT");
			}else{
		   	affichage.setText(affichage.getText()+"\n"+ email+": "+ ""+str);
			SendD(auth, nickname+" : "+str);
			}
		} catch (IOException e) {
		}	
	}
   
    public static void chargerStrams(Socket socket){
	 try {
		 out = new PrintStream(socket.getOutputStream());
		 in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	} catch (IOException e) {
		}
    }

    public void intServeur(){
    	container2.setLayout(card);
	    affichage.setPreferredSize(new Dimension(300,200));
	    affichage.setEditable(false);
	    
	    affichage.setBorder(BorderFactory.createTitledBorder("Affichage des msgs..."));
	    Email.setPreferredSize(new Dimension(200,30));
	    container2.setPreferredSize(new Dimension(320,100));
	   
	    table.setVisibleRowCount(11);
	    pan.add(new JScrollPane(table));
	    pan.setPreferredSize(new Dimension(100,220));
	    pan.add(referech);
	    referech.setPreferredSize(new Dimension(75,30));
	    referech.addActionListener(new referech_Listener());
	    
	    footer.add(label);
	    footer.add(Email);
	    footer.add(Modifie);
	    Modifie.setPreferredSize(new Dimension(75,27));
	    modifie2.setPreferredSize(new Dimension(75,27));
	    delete.setPreferredSize(new Dimension(70,27));
	    outB.setPreferredSize(new Dimension(55,27));
	    show_inf.setPreferredSize(new Dimension(75,27));
	    
	    Modifie.addActionListener(new Modifie_listener());
	    footer.add(delete);
	    delete.addActionListener(new delete_Listener());
	    footer.add(outB);
	    outB.addActionListener(new outB_Listener());
	    footer.add(show_inf);
	    show_inf.addActionListener(new show_inf_listener());
	    footer.setBorder(BorderFactory.createTitledBorder("..."));
	    container2.add(footer,tab[0]);
        
	    modifie_pan.add(name);
	    nametxt.setPreferredSize(new Dimension(100,25));
	    modifie_pan.add(nametxt);
	    modifie_pan.add(prenom);
	    prenomtxt.setPreferredSize(new Dimension(100,25));
	    modifie_pan.add(prenomtxt);
	    modifie_pan.add(nickname);
	    nicknametxt.setPreferredSize(new Dimension(80, 25));
	    modifie_pan.add(nicknametxt);
	    modifie_pan.add(email);
	    modifie_pan.add(emailtxt);
	    emailtxt.setPreferredSize(new Dimension(80,25));
	    modifie_pan.add(modifie2);
	    modifie2.addActionListener(new modifie2_listener());
	    modifie_pan.add(backB1);
	    backB1.setPreferredSize(new Dimension(80,25));
	    backB1.addActionListener(new backListener());
	    container2.add(modifie_pan,tab[1]);
	    
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

	    backB.setPreferredSize(new Dimension(80,25));
	    write_nickname.setPreferredSize(new Dimension(80,25));
	    list_pan.add(write_nickname);
	    list_pan.add(get_Information);
	    list_pan.add(backB);
	    backB.setPreferredSize(new Dimension(80,25));
	    backB.addActionListener(new backListener());
	    container2.add(list_pan,tab[2]);
	    get_Information.setPreferredSize(new Dimension(77,25));
	    get_Information.addActionListener(new get_inf_listenr());
	    container.add(affichage,BorderLayout.NORTH);
	    container.add(container2,BorderLayout.SOUTH);
	  
		this.getContentPane().add(container,BorderLayout.CENTER);
		this.getContentPane().add(pan,BorderLayout.WEST);
	}
     
      
     class Modifie_listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			card.show(container2, tab[1]);
		}  	 
     }
     
     class delete_Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(Email.getText().length()>7)
			{admin.supprimer(Email.getText());
			  Email.setText("");
			}
		}   	 
     }
     
     class outB_Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
	        OUT(Email.getText());
		}
     }
     
     class backListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			nameR_L.setText("");
			prenomR_L.setText("");
			email_R_l.setText("");
			nicknamR_l.setText("");
			write_nickname.setText("");
        	card.show(container2, tab[0]);
		}	 
     }
     
     class modifie2_listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
	        Utilisateur user = new Utilisateur(0,"",emailtxt.getText(),nametxt.getText(),prenomtxt.getText(),nicknametxt.getText(),"");	
	        admin.Modifier(user);
		}
     }
     public static void OUT(String str){
   	  for (int i = 0; i < espaceSession.size(); i++) {
 			if(espaceSession.get(i).getUser().getEmail().equalsIgnoreCase(str)){
 				Send (espaceSession.get(i).getAuth(),"1u've been pushed out by the Adminstrator ,all your messages \n will not be seen by any one  ");
 				espaceSession.get(i).setstill(false);
 				espaceSession.remove(i);
 				Email.setText("");
 			}}
           }
     class referech_Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Titles = new String[names.size()+1];
	    	Titles[0] =" online name : ";
	    	for (int i = 1; i < espaceSession.size()+1; i++) {
			Titles[i]=espaceSession.get(i-1).getUser().getNom();
			}
		    pan.setPreferredSize(new Dimension(100,220));
	    	table = new JList<String>(Titles);
	    	pan.removeAll(); 	
	    	table.setVisibleRowCount(11);
	    	pan.add(new JScrollPane(table));
	    	pan.add(referech);
	    	pan.revalidate();
          }
     }
     
     class show_inf_listener implements ActionListener{
			@Override
	 public void actionPerformed(ActionEvent arg0) {
			
			card.show(container2, tab[2]);
			}
	  }
     class get_inf_listenr implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			for (int i = 0; i < espaceSession.size(); i++) {
				if(espaceSession.get(i).getUser().getEmail().equalsIgnoreCase(write_nickname.getText())){
					Utilisateur user= espaceSession.get(i).getUser();
					nameR_L.setText(user.getNom());
					prenomR_L.setText(user.getPrenom());
					email_R_l.setText(user.getEmail());
					nicknamR_l.setText(user.getNickname());
				}}
		}
     }
     
}
