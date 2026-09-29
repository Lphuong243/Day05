package controller;

import business.User;
import data.UserDB;
import util.MailUtil;
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

            // Kiểm tra email đã tồn tại trong PostgreSQL chưa
            if (UserDB.emailExists(user.getEmail())) {
                message = "This email address already exists.<br>Please enter another email address.";
                url = "/emailList.jsp";
            } else {
                // 1. Lưu vào cơ sở dữ liệu PostgreSQL
                int rows = UserDB.insert(user);
                if (rows > 0) {
                    // 2. Kích hoạt gửi email xác nhận qua JavaMail
                    MailUtil.sendWelcomeEmail(user.getEmail(), user.getFirstName());

                    message = "";
                    url = "/thanks.jsp";
                } else {
                    String errDetail = UserDB.lastError;
                    if (errDetail == null || errDetail.isEmpty()) {
                        errDetail = "An error occurred in database.";
                    }
                    message = "Could not add user. " + errDetail;
                    url = "/emailList.jsp";
                }
            }

            request.setAttribute("user", user);
            request.setAttribute("message", message);
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
}
