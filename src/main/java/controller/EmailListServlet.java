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
        String emailStatus = "";

        // Lấy action từ form (mặc định là "join")
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";
        }

        if (action.equals("join")) {
            url = "/emailList.jsp";
        } else if (action.equals("add")) {
            // Lấy tham số từ request
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            // Tạo đối tượng User
            User user = new User(firstName, lastName, email);

            // Kiểm tra email đã tồn tại trong PostgreSQL chưa
            if (UserDB.emailExists(user.getEmail())) {
                message = "This email address already exists.<br>Please enter another email address.";
                url = "/emailList.jsp";
            } else {
                // 1. Lưu vào PostgreSQL
                int rows = UserDB.insert(user);
                if (rows > 0) {
                    // 2. Gửi email xác nhận
                    String mailResult = MailUtil.sendWelcomeEmailSync(user.getEmail(), user.getFirstName());
                    if ("SUCCESS".equals(mailResult)) {
                        emailStatus = "SENT";
                    } else {
                        emailStatus = "FAILED: " + mailResult;
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
