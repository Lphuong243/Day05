<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Murach's Java Servlets and JSP - Thanks</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
<div class="container">
    <h1 class="title">Thanks for joining our email list!</h1>
    <p class="instruction">Here is the information that you entered and saved to PostgreSQL:</p>

    <div class="user-info">
        <table>
            <tr>
                <td class="label">Email:</td>
                <td>${user.email}</td>
            </tr>
            <tr>
                <td class="label">First Name:</td>
                <td>${user.firstName}</td>
            </tr>
            <tr>
                <td class="label">Last Name:</td>
                <td>${user.lastName}</td>
            </tr>
        </table>
    </div>

    <% 
        String emailStatus = (String) request.getAttribute("emailStatus");
        if ("SENT".equals(emailStatus)) { 
    %>
        <div class="email-notice email-success">
            <strong>✔ Đã gửi email thành công!</strong> Một thư chào mừng đã được gửi tới hộp thư <i>${user.email}</i> qua Brevo API.
        </div>
    <% } else if (emailStatus != null && emailStatus.startsWith("FAILED")) { %>
        <div class="email-notice email-warning">
            <strong>⚠️ Chưa thể gửi email tới ${user.email}!</strong><br>
            Chi tiết: <%= emailStatus.replace("FAILED: ", "") %><br>
            <i>(Hãy mở file <code>MailUtilRender.java</code> để cấu hình Brevo API Key và email người gửi đã đăng ký trên Brevo).</i>
        </div>
    <% } %>

    <p style="margin-bottom: 15px;">To enter another email address, click on the Return button below.</p>
    <a href="emailList" class="btn-return">Return</a>
    &nbsp;&nbsp;
    <a href="sqlGateway" class="btn-return">Go to The SQL Gateway</a>
</div>
</body>
</html>
