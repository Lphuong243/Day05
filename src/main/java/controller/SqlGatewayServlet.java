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
        request.getRequestDispatcher("/sqlGateway.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String sqlStatement = request.getParameter("sqlStatement");
        QueryResult result = sqlService.processSql(sqlStatement);

        request.setAttribute("sqlStatement", sqlStatement != null ? sqlStatement : "");
        request.setAttribute("sqlResult", result.toHtml());

        request.getRequestDispatcher("/sqlGateway.jsp").forward(request, response);
    }
}
