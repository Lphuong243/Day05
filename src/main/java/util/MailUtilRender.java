package util;

import jakarta.mail.MessagingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MailUtilRender {

    // Điền API Key của Brevo tại đây (hoặc cấu hình biến môi trường BREVO_API_KEY trên Render)
    // Lấy API Key miễn phí tại: https://app.brevo.com/settings/keys/api
    public static final String DEFAULT_BREVO_API_KEY = "xkeysib-dien_api_key_cua_ban_o_day";

    // Email người gửi (phải là email bạn dùng để đăng ký tài khoản Brevo)
    public static final String DEFAULT_SENDER_EMAIL = "your_registered_email@gmail.com";

    public static void sendMail(String to, String from,
                                String subject, String body, boolean bodyIsHTML)
            throws MessagingException {

        try {
            // 1. Đọc API Key (ưu tiên đọc biến môi trường nếu có, không thì lấy DEFAULT_BREVO_API_KEY)
            String apiKey = System.getenv("BREVO_API_KEY");
            if (apiKey == null || apiKey.trim().isEmpty()) {
                apiKey = DEFAULT_BREVO_API_KEY;
            }

            if (apiKey == null || apiKey.contains("dien_api_key")) {
                throw new MessagingException("Bạn chưa điền BREVO_API_KEY vào file MailUtilRender.java!");
            }

            // Nếu from rỗng hoặc là mặc định thì dùng DEFAULT_SENDER_EMAIL
            String sender = (from != null && !from.trim().isEmpty() && !from.contains("your_registered")) 
                            ? from.trim() : DEFAULT_SENDER_EMAIL;

            // 2. Làm sạch dữ liệu truyền vào tránh lỗi JSON
            String cleanFrom = sender.trim();
            String cleanTo = to.trim();
            String cleanSubject = subject.replace("\"", "\\\"");
            String cleanBody = body.replace("\\", "\\\\")
                                   .replace("\"", "\\\"")
                                   .replace("\n", "\\n")
                                   .replace("\r", "");

            // 3. Đóng gói dữ liệu dạng JSON
            String jsonPayload = "{"
                    + "\"sender\":{\"email\":\"" + cleanFrom + "\"},"
                    + "\"to\":[{\"email\":\"" + cleanTo + "\"}],"
                    + "\"subject\":\"" + cleanSubject + "\","
                    + (bodyIsHTML ? "\"htmlContent\":\"" : "\"textContent\":\"") + cleanBody + "\""
                    + "}";

            // 4. Gọi API Brevo qua Cổng 443 (HTTPS)
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("accept", "application/json")
                    .header("api-key", apiKey)
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // 5. Gửi request và kiểm tra phản hồi
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                throw new MessagingException("Lỗi Brevo API (Mã " + response.statusCode() + "): " + response.body());
            }

            System.out.println(">> Đã gửi email thành công qua Brevo API tới: " + cleanTo);

        } catch (Exception e) {
            throw new MessagingException("Không thể gửi mail qua HTTP API: " + e.getMessage(), e);
        }
    }
}
