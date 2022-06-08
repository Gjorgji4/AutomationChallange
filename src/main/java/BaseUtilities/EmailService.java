package BaseUtilities;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.mail.Transport;


public class EmailService {

    HelperMethods tools = new HelperMethods();

    public void emailSender() throws Exception {

            // email ID of Recipient.
            String recipient = "automation.learning@yahoo.com";

            // email ID of  Sender.
            String sender = tools.getProperties("mail.Username", "config");
            String password = tools.getProperties("mail.Password", "config");


            Properties prop = System.getProperties();

            // Setting up mail server
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
//        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
            prop.put("mail.smtp.ssl.protocols", "TLSv1.2");

            Session session = Session.getInstance(prop, new Authenticator() {@Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, password);
                }
            });
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(sender));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                message.setSubject("This is Subject");
                message.setText("This is a test mail");
                Transport.send(message);
                System.out.println("Mail successfully sent");
            } catch(MessagingException mex) {
                mex.printStackTrace();
            }
        }
    }
