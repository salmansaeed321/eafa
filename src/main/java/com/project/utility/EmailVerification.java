package com.project.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.SubjectTerm;

/**
 * @author Salman Saeed
 * @email salmansaeed321@hotmail.com
 * @version 3.1
 **/

public class EmailVerification {

	public static String getEmail(String tokenOrOtp, String email, String password, String emailSubject) throws Exception {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");

		Session session = Session.getDefaultInstance(props, null);
		Store store = session.getStore("imaps");
		store.connect("imap.gmail.com", email, password);

		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);

//		System.out.println("Total Message: " + folder.getMessageCount());
//		System.out.println("Unread Message: " + folder.getUnreadMessageCount());

		Message[] messages = null;
		boolean isMailFound = false;
		Message mailFromServer = null;

		// Search for mail from noreply.domain
		for (int i = 0; i <= 5; i++) {
			messages = folder.search(new SubjectTerm(
					emailSubject), folder.getMessages());
			// Wait for 10 seconds
			if (messages.length == 0) {
				Thread.sleep(10000);
			}
		}

		// Search for unread mail
		// This is to avoid using the mail for which
		// Registration is already done
		for (Message mail : messages) {
			if (!mail.isSet(Flags.Flag.SEEN)) {
				mailFromServer = mail;
//				System.out.println("Message Count is: " + mailFromServer.getMessageNumber());
				isMailFound = true;
			}
		}

		// Test fails if no unread mail was found
		if (!isMailFound) {
			throw new Exception("Could not find the new mail :-(");

			// Read the content of mail and launch registration URL
		} else {
			String line;
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(mailFromServer.getInputStream()));
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (tokenOrOtp.equals("token")) {
//				System.out.println("Token email: " + buffer);

				List<String> containedUrls = new ArrayList<String>();
				String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
				Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
				Matcher urlMatcher = pattern.matcher(buffer);

				while (urlMatcher.find()) {
					containedUrls.add(buffer.substring(urlMatcher.start(0), urlMatcher.end(0)));
				}

				String i = containedUrls.toString();

				int indexOfOpenBracket = i.indexOf("[");
				int indexOfLastBracket = i.lastIndexOf("]");

//				System.out.println(i.substring(indexOfOpenBracket + 1, indexOfLastBracket));
				tokenOrOtp = i.substring(indexOfOpenBracket + 1, indexOfLastBracket);

			} else if(tokenOrOtp.equals("otp")){
				String emailBody = buffer.toString();
				tokenOrOtp = emailBody.replaceAll("\\D+", "");
//				System.out.println(tokenOrOtp);
			}
			return tokenOrOtp;
		}
	}
}