package cn.springboot.blog.service.impl;


import cn.springboot.blog.dao.UserCodeMapper;
import cn.springboot.blog.entity.UserCode;
import cn.springboot.blog.util.Result;
import cn.springboot.blog.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件服务
 * @author cc
 * @date 2021-12-07 10:37
 */
@Service
public class MailServiceImpl {
    private final JavaMailSender mailSender;

    private String SENDER;

    @Autowired
    UserCodeMapper userCodeMapper;
    public MailServiceImpl(JavaMailSender mailSender) throws IOException {
        this.mailSender = mailSender;
        InputStream resourceAsStream = MailServiceImpl.class.getResourceAsStream("/application.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        SENDER = properties.getProperty("spring.mail.username");
        System.out.println(SENDER);
    }

    // 发送普通邮件
    public void sendSimpleMailMessage(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(SENDER);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    // 发送html邮件
    public void sendMimeMessage(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();;
        // true表示需要创建一个multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(SENDER);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    // 发送带附件的邮件
    public void sendMimeMessage(String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();;
        // true表示需要创建一个multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(SENDER);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();
        helper.addAttachment(fileName, file);
        mailSender.send(message);
    }

    // 发送带静态文件的邮件
    public void sendMimeMessage(String to, String subject, String content, Map<String,String> rscIdMap) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        // true表示需要创建一个multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(SENDER);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        for (Map.Entry<String, String> entry : rscIdMap.entrySet()) {
            FileSystemResource file = new FileSystemResource(new File(entry.getValue()));
            helper.addInline(entry.getKey(), file);
        }
        mailSender.send(message);
    }

    public Result checkCode(String code, Integer uid){

        UserCode userCode = userCodeMapper.selectByPrimaryKey(uid);
        if(userCode==null){
            return ResultGenerator.genFailResult( "请先获取验证码");
        }
        return code.equals(userCode.getCode())?ResultGenerator.genSuccessResult("验证通过"):ResultGenerator.genFailResult("验证码错误");
    }
}


