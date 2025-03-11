/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.oszero.deliver.platformclient.client.mail;

import com.oszero.deliver.platformclient.exception.ClientException;
import com.oszero.deliver.platformclient.model.app.MailApp;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author oszero
 * @version 1.0.0
 */
public class MailClient {

    public void sendMail(MailApp mailApp, Map<String, Object> paramMap, List<String> users) {
        try {
            String title = paramMap.get("title").toString();
            String content = paramMap.get("content").toString();
            List<String> toCC = (List<String>) paramMap.getOrDefault("toCC", List.of());
            List<String> toBCC = (List<String>) paramMap.getOrDefault("toBCC", List.of());
            Boolean htmlFlag = (Boolean) paramMap.get("htmlFlag");
            //1. 创建 JavaMailSender
            JavaMailSenderImpl javaMailSender = getJavaMailSender(mailApp);
            //2. 创建 message 对象
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailApp.getUsername());
            helper.setTo(users.toArray(new String[0]));
            helper.setCc(toCC.toArray(new String[0]));
            helper.setBcc(toBCC.toArray(new String[0]));
            helper.setSubject(title);
            helper.setText(content, htmlFlag);
            // 3. 发送邮件
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new ClientException("发送邮件失败，" + e.getMessage());
        }
    }

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
