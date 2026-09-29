package controller;

import business.QueryResult;
import business.SqlService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sqlGateway")
public class SqlGatewayServlet extends HttpServlet {
    private SqlService sqlService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.sqlService = new SqlService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mặc định chuyển đến trang JSP
        request.getRequestDispatcher("/sqlGateway.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thiết lập mã hóa UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String sqlStatement = request.getParameter("sqlStatement");

        // Gọi Business Layer xử lý
        QueryResult result = sqlService.processSql(sqlStatement);

        // Lưu dữ liệu vào request attributes
        request.setAttribute("sqlStatement", sqlStatement != null ? sqlStatement : "");
        request.setAttribute("sqlResult", result.toHtml());

        // Forward về lại trang sqlGateway.jsp để hiển thị kết quả
        request.getRequestDispatcher("/sqlGateway.jsp").forward(request, response);
    }
}
