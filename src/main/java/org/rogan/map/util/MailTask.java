package org.rogan.map.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.rogan.map.base.ConfigConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author Rogan
 * 2017年5月3日15:01:08
 * 发送邮件帮助类
 */
public class MailTask implements Runnable{
	
	private String mail_to;
	private String nickname;
	private String subject;
	private String pwd;
	
	Logger logger = LoggerFactory.getLogger(MailTask.class);
	
	public MailTask() {
	}
	public MailTask(String mail_to, String nickname, String subject) {
		this.mail_to = mail_to;
		this.nickname = nickname;
		this.subject = subject;
		System.out.println("MailTask [mail_to=" + mail_to + ", nickname=" + nickname + ", subject=" + subject + ", logger=" + logger
				+ "]");
	}
	
	public MailTask(String mail_to, String nickname, String subject, String pwd) {
		this.mail_to = mail_to;
		this.nickname = nickname;
		this.subject = subject;
		this.pwd = pwd;
	}
	
	
	
	@Override
	public String toString() {
		return "MailTask [mail_to=" + mail_to + ", nickname=" + nickname + ", subject=" + subject + ", logger=" + logger
				+ "]";
	}
	public static void main(String[] args) {
		try {
			MailTask task = new MailTask();
			task.sendMail();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public String getMail_to() {
		return mail_to;
	}
	public void setMail_to(String mail_to) {
		this.mail_to = mail_to;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public  void sendMail() throws MessagingException, IOException{
		Properties props = System.getProperties();
		InputStream is = MailTask.class.getClassLoader().getResourceAsStream("mail.properties");
		props.load(is);
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				//发信人和授权码
				return new PasswordAuthentication(ConfigConsts.MAIL_FROM, ConfigConsts.AUTHORIZE_CODE);
			}
		};
		Session session = Session.getInstance(props, auth);
		session.setDebug(true);
		MimeMessage mm = new MimeMessage(session);
		mm.setFrom(new InternetAddress(ConfigConsts.MAIL_FROM));
		mm.setRecipient(RecipientType.TO, new InternetAddress(mail_to));
		mm.setSubject(ConfigConsts.SUBJECT_MAP.get(subject));
		String path = "mailTemplate/%s.template";
		path = String.format(path, subject);
		String content = CommonUtils.readMailTemplate(MailTask.class, path, nickname, pwd);
		mm.setContent(content, ConfigConsts.CONTENT_TYPE);
		Transport.send(mm);
		
	}

	public void run() {
		try {
			//6s之后发送电子邮件
			Thread.sleep(6000);
			sendMail();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
}

