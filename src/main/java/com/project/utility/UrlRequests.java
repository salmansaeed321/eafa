package com.project.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;

/**
 * @author Salman Saeed
 * @email salmansaeed321@hotmail.com
 * @version 3.1
 **/

public class UrlRequests {

		public static String getOtp(String phonenumber){
			String otp = null;

		try {
			String webPage = "http://staging.project.com/mobapp_vp/index.php?wid=d3d9446802a44259755d38e6d163e820&m=api&a=getpin&username=" + phonenumber;
			final String name = "username";
			final String password = "password";

			String authString = name + ":" + password;
//			System.out.println("auth string: " + authString);
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
//			System.out.println("Base64 encoded auth string: " + authStringEnc);

			Authenticator.setDefault(new Authenticator() {
				 @Override
				        protected PasswordAuthentication getPasswordAuthentication() {
				         return new PasswordAuthentication(
				   name, password.toCharArray());
				        }
				});
			
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();

//			System.out.println(result);
			String otp1 = result == null || result.length() < 5 ? result : result.substring(result.length() - 5);
			otp = otp1.replaceAll("\\D+", "");
//			System.out.println(otp);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return otp;
	}
}