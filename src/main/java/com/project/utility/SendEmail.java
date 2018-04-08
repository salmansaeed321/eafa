package com.project.utility;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * @author Salman Saeed
 * @email salmansaeed321@hotmail.com
 * @version 3.1
 **/

public class SendEmail {
    public static void main(String[] args) {
    	SendEmail demo = new SendEmail();
        demo.sendEmail();
    }
    
    public static void sendEmail() {
        // Defines the E-Mail information.
        String from = "salmansaeed321@gmail.com";
        String to = "salmansaeed321@gmail.com";
        String subject = "Important Message";
        String bodyText = "This is an important message with attachment.";

        // The attachment file name.
        String attachmentName = "D:/Hello_Attachment.txt";

        // Creates a Session with the following properties.
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        Session session = Session.getDefaultInstance(props);

        try {
            InternetAddress fromAddress = new InternetAddress(from);
            InternetAddress toAddress = new InternetAddress(to);

            // Create an Internet mail msg.
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(fromAddress);
            msg.setRecipient(Message.RecipientType.TO, toAddress);
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // Set the email msg text.
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(bodyText);

            // Set the email attachment file
            FileDataSource fileDataSource = new FileDataSource(attachmentName);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.setDataHandler(new DataHandler(fileDataSource));
            attachmentPart.setFileName(fileDataSource.getName());

            // Create Multipart E-Mail.
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messagePart);
            multipart.addBodyPart(attachmentPart);

            msg.setContent(multipart);

            // Send the msg. Don't forget to set the username and password
            // to authenticate to the mail server.
            Transport.send(msg, "salmansaeed321@gmail.com", "*********");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}