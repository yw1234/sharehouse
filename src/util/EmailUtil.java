package util;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import model.Email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class EmailUtil {

	private JavaMailSender mailSender;
	private Configuration cfg = new Configuration();
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void send(Email email,Map root){                 
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = null;
		cfg.setClassForTemplateLoading(getClass(),"/util/mailFtl"); 
		try{
			messageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
			messageHelper.setFrom(email.getFrom(),"ShareHouse");
			messageHelper.setTo(email.getTo());
			messageHelper.setSubject(email.getSubject());
			messageHelper.setSentDate(email.getSendDate());
			Template t = cfg.getTemplate(root.get("fileName").toString(),"UTF-8");   
		    StringWriter writer = new StringWriter();
		    t.process(root, writer);                    		//把模版内容写入邮件中     
			messageHelper.setText(writer.toString(),true);
		}catch(Exception e){
			e.printStackTrace();
		}
		JavaMailSenderImpl sender = (JavaMailSenderImpl) mailSender;    
		sender.send(mimeMessage);
	}
}
