package util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailUtil {

    // =========================================================================
    // CẤU HÌNH TÀI KHOẢN GMAIL DÙNG ĐỂ GỬI ĐI (SENDER):
    // BẮT BUỘC: Bạn phải thay bằng Gmail của bạn và "Mật khẩu ứng dụng" (16 chữ cái)
    // =========================================================================
    public static final String SENDER_EMAIL = "thilanphuong2403@gmail.com";
    public static final String SENDER_APP_PASSWORD = "ekfi tnzr jcxh lsco";

    /**
     * Gửi email sử dụng giao thức SMTP qua máy chủ Gmail
     */
    public static void sendMail(String to, String from, String subject, String body, boolean bodyIsHTML) 
            throws MessagingException {

        if (SENDER_EMAIL.equals("your_email@gmail.com") || SENDER_APP_PASSWORD.contains("xxxx")) {
            throw new MessagingException("Bạn chưa cấu hình SENDER_EMAIL và SENDER_APP_PASSWORD (16 ký tự) trong file MailUtil.java!");
        }

        // 1. Thiết lập các thông số SMTP cho Gmail
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // 2. Tạo phiên làm việc (Session) có xác thực tài khoản
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_APP_PASSWORD);
            }
        });

        // 3. Tạo thông điệp (Message)
        Message message = new MimeMessage(session);
        message.setSubject(subject);

        if (bodyIsHTML) {
            message.setContent(body, "text/html; charset=UTF-8");
        } else {
            message.setText(body);
        }

        // 4. Thiết lập địa chỉ người gửi và người nhận
        Address fromAddress = new InternetAddress(from != null ? from : SENDER_EMAIL);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // 5. Gửi thư đi
        Transport.send(message);
        System.out.println(">> Đã gửi email thành công tới: " + to);
    }

    /**
     * Gửi email chào mừng và trả về kết quả (thành công hoặc thông báo lỗi)
     */
    public static String sendWelcomeEmailSync(String toEmail, String firstName) {
        try {
            String subject = "Chào mừng bạn gia nhập Email List!";
            String body = "Xin chào " + firstName + ",\n\n"
                    + "Cảm ơn bạn đã đăng ký tham gia danh sách nhận email của chúng tôi.\n"
                    + "Thông tin của bạn đã được lưu thành công vào cơ sở dữ liệu PostgreSQL!\n\n"
                    + "Trân trọng,\n"
                    + "Đội ngũ Phát triển Web";

            sendMail(toEmail, SENDER_EMAIL, subject, body, false);
            return "SUCCESS";
        } catch (Exception e) {
            System.err.println(">> Lỗi gửi mail: " + e.getMessage());
            return e.getMessage();
        }
    }
}
