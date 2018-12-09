package user;

//File Name SendEmail.java



import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import admin.Config;
import alert.ErrorAlert;
import alert.SuccessAlert;
import student.ads.Actions;

public class SendEmail {
	public ErrorAlert alert =  new ErrorAlert();
	public SuccessAlert success =  new SuccessAlert();
	public Actions actions = new Actions();
	Config config = null;

public  void send(String sendTo,  String subject, String content) {    
	String myEmail;
	String password;
	if(config != null){
		config = actions.getConfig();
		 myEmail = config.getEmail();
		 password = config.getPassword();
		
	}else{
		 myEmail = "studentsellingmaterial@gmail.com";
		 password = "NewPassword2018";
	}
//	String sendTo="215119630@mycput.ac.za";
	Properties pro=new Properties();
	pro.put("mail.smtp.host", "smtp.gmail.com");
	pro.put("mail.smtp.starttls.enable","true");
	pro.put("mail.smtp.auth","true");
	pro.put("mail.smtp.port","587");
	
	    Session ss=Session.getInstance(pro, new javax.mail.Authenticator(){
	    	@Override
	        protected PasswordAuthentication getPasswordAuthentication(){
	                return new PasswordAuthentication(myEmail,password );
	         	}
	    	});
	    
	    try
	    {
	        Message msg=new MimeMessage(ss);
	        msg.setFrom(new InternetAddress(myEmail));
	        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
	        		sendTo));
	        msg.setSubject(subject);
		    msg.setText(content);
		    Transport trans = ss.getTransport("smtp"); 
		    trans.connect(myEmail,password);
		    Transport.send(msg);
		    trans.close();		    
//		    Transport t = session.getTransport("smtp");
//	        t.connect(userName, password);
//	        t.sendMessage(msg, msg.getAllRecipients());
//	        t.close();
		    
		    System.out.println("message sent");
	}
	catch(Exception e)
	{
		alert.displayError("Email not sent",
				"Failed to send your email", 
				"There was an error while trying to send your email\n"
				+ "Possible reason is : "+ e.getClass().getCanonicalName());
//	  e.printStackTrace();
	}
	    
	
}
}