package util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailUtil {

    // CẤU HÌNH TÀI KHOẢN GMAIL GỬI ĐI:
    // Bạn có thể điền Gmail của bạn và Mật khẩu ứng dụng (16 chữ cái) vào đây khi muốn gửi thật:
    public static final String SENDER_EMAIL = "thilanphuong2403@gmail.com";
    public static final String SENDER_APP_PASSWORD = "xljj ycku zbhi ixyg";

    public static void sendMail(String to, String from, String subject, String body, boolean bodyIsHTML) 
            throws MessagingException {

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_APP_PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setSubject(subject);

        if (bodyIsHTML) {
            message.setContent(body, "text/html; charset=UTF-8");
        } else {
            message.setText(body);
        }

        Address fromAddress = new InternetAddress(from != null ? from : SENDER_EMAIL);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        Transport.send(message);
        System.out.println(">> Đã gửi email thành công tới: " + to);
    }

    /**
     * Gửi email chào mừng trong luồng ngầm (Background Thread)
     * Giúp trang web phản hồi tức thì, không bị lag hay gián đoạn.
     */
    public static void sendWelcomeEmail(String toEmail, String firstName) {
        new Thread(() -> {
            try {
                String subject = "Welcome to our email list!";
                String body = "Dear " + firstName + ",\n\n"
                        + "Thanks for joining our email list. We'll make sure to send you updates regularly.\n\n"
                        + "Have a great day!\n"
                        + "The Murach Team";

                sendMail(toEmail, SENDER_EMAIL, subject, body, false);
            } catch (Exception e) {
                System.err.println(">> [JavaMail] Chưa thể gửi mail ra ngoài (cần cấu hình Gmail thật trong MailUtil.java): " + e.getMessage());
            }
        }).start();
    }
}
