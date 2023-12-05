package com.oszero.deliver.server.client;

import cn.hutool.json.JSONUtil;
import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.message.param.mail.MailParam;
import com.oszero.deliver.server.model.app.MailApp;
import com.oszero.deliver.server.model.dto.SendTaskDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 渠道-邮箱工具类
 *
 * @author oszero
 * @version 1.0.0
 */
@Slf4j
@Component
public class MailClient {

    /**
     * 发送邮件
     *
     * @param mailApp     邮件应用
     * @param sendTaskDto dto
     */
    public void sendMail(MailApp mailApp, SendTaskDto sendTaskDto) {
        try {
            String paramJson = sendTaskDto.getParamJson();
            MailParam mailParam = JSONUtil.toBean(paramJson, MailParam.class);

            //1. 创建 JavaMailSender
            JavaMailSenderImpl javaMailSender = getJavaMailSender(mailApp);

            //2. 创建 message 对象
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailApp.getUsername());
            helper.setTo(sendTaskDto.getUsers().toArray(new String[0]));
            helper.setCc(mailParam.getToCC().toArray(new String[0]));
            helper.setBcc(mailParam.getToBCC().toArray(new String[0]));
            helper.setSubject(mailParam.getTitle());
            helper.setText(mailParam.getContent(), mailParam.isHtmlFlag());
            // todo：增加邮件附件
//        helper.addAttachment("", new File(""));

            // 3. 发送邮件
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MessageException("发送邮件失败，" + e.getMessage());
        }
        log.info("邮件消息发送成功，from {}，to {}", mailApp.getUsername(), sendTaskDto.getUsers());
    }

    /**
     * 获取 JavaMailSender
     *
     * @param mailApp 邮件应用
     * @return JavaMailSender
     */
    private static JavaMailSenderImpl getJavaMailSender(MailApp mailApp) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(mailApp.getUsername());
        javaMailSender.setPassword(mailApp.getPassword());
        javaMailSender.setHost(mailApp.getHost());
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setProtocol("smtp");
        Properties pro = new Properties();
        pro.put("mail.smtp.auth", mailApp.getAuth());
        pro.put("mail.smtp.ssl.enable", mailApp.getSslEnable());
        pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailSender.setJavaMailProperties(pro);
        return javaMailSender;
    }

}
