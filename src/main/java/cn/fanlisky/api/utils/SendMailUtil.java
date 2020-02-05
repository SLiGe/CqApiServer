package cn.fanlisky.api.utils;

import cn.fanlisky.api.model.request.SendMailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author Gary
 * @description: 发送邮件工具类
 * @since 2018/12/5 12:53
 */
@Configuration
public class SendMailUtil {

    @Value(value = "${app.niurenqu.mail.account}")
    private String account;
    @Value(value = "${app.niurenqu.mail.pass}")
    private String pass;

    /**
     * @author: LiGe
     * @description:创建邮件 方法
     * @params: [session, sendMail, receiveMail, sendMailRequest]
     * @date: 12:55 2018/12/5
     * @return: javax.mail.internet.MimeMessage
     **/
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, SendMailRequest sendMailRequest) throws Exception {

        //1.创建一封邮件
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(sendMail, sendMailRequest.getEmailTitle(), "utf-8"));
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "xx", "UTF-8"));
        msg.setSubject(sendMailRequest.getEmailTitle(), "utf-8");
        msg.setContent(sendMailRequest.getSendContent(), "text/html;charset=UTF-8");
        msg.setSentDate(new Date());
        msg.saveChanges();
        return msg;
    }

    /**
     * @author: LiGe
     * @description: 发送邮件方法
     * @params: [sendMailRequest]
     * @date: 12:55 2018/12/5
     * @return: void
     **/
    public void sendMailFun(SendMailRequest sendMailRequest) throws Exception {
        String receiveEmail = sendMailRequest.getUserMail();
        Properties props = new Properties();
        // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.transport.protocol", "smtp");
        // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.host", "smtp.mxhichina.com");
        // 需要请求认证
        props.setProperty("mail.smtp.auth", "true");
        // 设置是否使用ssl安全连接 ---一般都使用
        props.put("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.port", "465");

        Session session = Session.getInstance(props);
        session.setDebug(true);
        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, account, receiveEmail, sendMailRequest);
        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(account, pass);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();

    }
}
