package controller;

import business.User;
import data.UserDB;
import util.MailUtilRender;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String url = "/emailList.jsp";
        String message = "";
        String emailStatus = "";

        String action = request.getParameter("action");
        if (action == null) {
            action = "join";
        }

        if (action.equals("join")) {
            url = "/emailList.jsp";
        } else if (action.equals("add")) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            User user = new User(firstName, lastName, email);

            if (UserDB.emailExists(user.getEmail())) {
                message = "This email address already exists.<br>Please enter another email address.";
                url = "/emailList.jsp";
            } else {
                // 1. Lưu vào cơ sở dữ liệu PostgreSQL
                int rows = UserDB.insert(user);
                if (rows > 0) {
                    // 2. Gửi email xác nhận qua Brevo HTTP API (chuẩn như bạn Lộc chia sẻ)
                    try {
                        String subject = "Chào mừng bạn gia nhập Email List!";
                        String body = "Xin chào " + user.getFirstName() + ",\n\n"
                                + "Cảm ơn bạn đã tham gia Email List của chúng tôi.\n"
                                + "Dữ liệu của bạn đã được lưu thành công vào PostgreSQL!\n\n"
                                + "Trân trọng,\nĐội ngũ WebPro";

                        MailUtilRender.sendMail(user.getEmail(), MailUtilRender.DEFAULT_SENDER_EMAIL, subject, body, false);
                        emailStatus = "SENT";
                    } catch (Exception e) {
                        System.err.println(">> Lỗi gửi mail Brevo: " + e.getMessage());
                        emailStatus = "FAILED: " + e.getMessage();
                    }

                    message = "";
                    url = "/thanks.jsp";
                } else {
                    message = "Could not add user. An error occurred in database.";
                    url = "/emailList.jsp";
                }
            }

            request.setAttribute("user", user);
            request.setAttribute("message", message);
            request.setAttribute("emailStatus", emailStatus);
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
}
