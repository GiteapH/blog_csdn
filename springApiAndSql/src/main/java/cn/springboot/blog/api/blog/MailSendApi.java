package cn.springboot.blog.api.blog;

import cn.springboot.blog.api.blog.param.MailParams;
import cn.springboot.blog.api.blog.vo.codeV0;
import cn.springboot.blog.service.impl.MailServiceImpl;
import cn.springboot.blog.util.MailUtil;
import cn.springboot.blog.util.Result;
import cn.springboot.blog.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@Api(value = "v1", tags = "邮箱发送接口")
@RequestMapping("/api/v1")
public class MailSendApi {
    private MailServiceImpl  mailService;

    {
        try {
            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setPassword("WRYOQMSZUPCYTGTX");
            javaMailSender.setHost("smtp.163.com");
            javaMailSender.setUsername("lyp057403@163.com");
            javaMailSender.setPort(465);
            javaMailSender.setDefaultEncoding("UTF-8");
            javaMailSender.setProtocol("smtps");
            mailService = new MailServiceImpl(javaMailSender);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/user/sendMail")
    @ApiOperation(value = "发送邮件接口")
    public Result<String> send(@RequestBody @Valid MailParams mailParams) {
        String code = MailUtil.get6Code();
        System.out.println(code);
        String htmlContent = MailUtil.getHtmlContent(code, mailParams.getAction(), mailParams.getUsername());
        try {
            mailService.sendMimeMessage(mailParams.getToMail(),mailParams.getAction(),htmlContent);
            ResultGenerator.genSuccessResult(code);
        } catch (MessagingException e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
        codeV0 userCheckParam = new codeV0();
        userCheckParam.setCode(code);

        return ResultGenerator.genSuccessResult(userCheckParam);
    }

    @PostMapping("/user/checkCode/{code}/{uid}")
    @ApiOperation(value = "验证接口")
    public Result<String> send(@PathVariable(value = "code",required = true) String code,@PathVariable("uid") String uid) {

        Result<String> result = mailService.checkCode(code, Integer.parseInt(uid));
        return result;
    }
}
