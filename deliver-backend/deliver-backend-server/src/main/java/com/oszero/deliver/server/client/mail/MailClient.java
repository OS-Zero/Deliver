package com.oszero.deliver.server.client.mail;

import com.oszero.deliver.server.exception.MessageException;
import com.oszero.deliver.server.model.app.MailApp;
import com.oszero.deliver.server.model.dto.common.SendTaskDto;
import com.oszero.deliver.server.util.MessageLinkTraceUtils;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 渠道-邮箱工具类
 *
 * @author oszero
 * @version 1.0.0
 */
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
            Map<String, Object> paramMap = sendTaskDto.getParamMap();
            String title = paramMap.get("title").toString();
            String content = paramMap.get("content").toString();
            List<String> toCC = (List<String>) paramMap.get("toCC");
            List<String> toBCC = (List<String>) paramMap.get("toBCC");
            Boolean htmlFlag = (Boolean) paramMap.get("htmlFlag");

            //1. 创建 JavaMailSender
            JavaMailSenderImpl javaMailSender = getJavaMailSender(mailApp);

            //2. 创建 message 对象
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailApp.getUsername());
            helper.setTo(sendTaskDto.getUsers().toArray(new String[0]));
            helper.setCc(toCC.toArray(new String[0]));
            helper.setBcc(toBCC.toArray(new String[0]));
            helper.setSubject(title);
            helper.setText(content, htmlFlag);
            // TODO：增加邮件附件
            // helper.addAttachment("", new File(""));

            // 3. 发送邮件
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MessageException(sendTaskDto, "发送邮件失败，" + e.getMessage());
        }
        MessageLinkTraceUtils.recordMessageLifecycleInfoLog(sendTaskDto, "邮件消息发送成功");
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
