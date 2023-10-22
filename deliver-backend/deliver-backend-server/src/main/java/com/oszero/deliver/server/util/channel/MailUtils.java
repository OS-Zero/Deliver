package com.oszero.deliver.server.util.channel;

import com.oszero.deliver.server.message.param.mail.MailParam;
import com.oszero.deliver.server.model.app.MailApp;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Component
public class MailUtils {
    private static final Properties pro;

    static {
        pro = System.getProperties(); // 下面各项缺一不可
        pro.put("mail.smtp.auth", "true");
        pro.put("mail.smtp.ssl.enable", "true");
        pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    @SneakyThrows
    public void sendMail(MailApp mailApp, MailParam mailParam) {
        // 创建
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(mailApp.getUsername());
        javaMailSender.setPassword(mailApp.getPassword());
        javaMailSender.setHost(mailApp.getHost());
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setProtocol("smtp");
        javaMailSender.setJavaMailProperties(pro);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailParam.getFrom());
        helper.setTo(mailParam.getTo().toArray(new String[0]));
        helper.setCc(mailParam.getToCC().toArray(new String[0]));
        helper.setBcc(mailParam.getToBCC().toArray(new String[0]));
        helper.setSubject(mailParam.getTitle());
        helper.setText(mailParam.getContent(), mailParam.isHtmlFlag());
        log.info("[email request] subject={} content={} to={}", mailParam.getTitle(), mailParam.getContent(), mailParam.getTo());
        javaMailSender.send(message);
    }

}
