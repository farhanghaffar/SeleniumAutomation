package com.innroad.inncenter.tests;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.innroad.inncenter.utils.EmailUtils;
import com.innroad.inncenter.utils.EmailUtils.EmailFolder;

public class Email {

	public static void main(String[] args) throws MessagingException {
		EmailUtils utils = new EmailUtils(EmailFolder.INBOX);
		System.out.println(utils.getNumberOfMessages());
		
	}

}
