	package utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Email {
	String destinatario;
	String asunto;
	String cuerpo; 
	
	public Email(String destinatario, String asunto, String cuerpo) {
		
		this.destinatario = destinatario;
		this.asunto = asunto;
		this.cuerpo = cuerpo;
		
		
	}

	public  void enviarEmail() {
		
	    // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
	    String remitente = "cinexinsl@yahoo.com";  //Para la dirección nomcuenta@gmail.com
	    Properties props = System.getProperties();
	    
	    props.put("mail.smtp.host", "smtp.mail.yahoo.com");  //El servidor SMTP de Yahoo
	    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("cinexinsl", "nfkjmvhidmzjduwy");
			}
		});
		
		session.setDebug(true);


		
	    try {
			MimeMessage message = new MimeMessage(session);

	        message.setFrom(new InternetAddress(remitente));
	        message.addRecipients(Message.RecipientType.TO, destinatario);
	        message.setSubject(asunto);
	        message.setText(cuerpo);
	        Transport.send(message);
	    }
	    catch (MessagingException me) {
	    	System.out.println("Error al enviar email");
	        me.printStackTrace();   //Si se produce un error
	    }
	}	
	
	
}
