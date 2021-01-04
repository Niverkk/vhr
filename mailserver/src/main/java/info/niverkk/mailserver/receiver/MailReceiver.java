package info.niverkk.mailserver.receiver;

import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.MailConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * @author JKXAING on 2020/8/27
 */
@Component
public class MailReceiver {
    private static final Logger logger = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    MailProperties mailProperties;
    @Autowired
    TemplateEngine templateEngine;


    @RabbitListener(queues="niverkk.mail.welcome")
    public void handle(Employee emp){
        //复杂邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            //设置信息
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("欢迎入职");
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(emp.getEmail());
            helper.setSentDate(new Date());

            //设置邮件模板变量值
            Context context = new Context();
            context.setVariable("name", emp.getName());
            context.setVariable("posName", emp.getPosition().getName());
            context.setVariable("joblevelName", emp.getJobLevel().getName());
            context.setVariable("departmentName", emp.getDepartment().getName());
            //生成邮件内容
            String mailContent = templateEngine.process("mail.html", context);
            helper.setText(mailContent,true);

            javaMailSender.send(mimeMessage);
            logger.error("发送邮件成功");
        } catch (MessagingException e) {
            logger.error("发送邮件失败",e);
        }

    }

}
