<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Murach's Java Servlets and JSP - Thanks</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    <style>
        .user-info {
            margin: 15px 0 20px 0;
        }
        .user-info table {
            border-collapse: collapse;
        }
        .user-info td {
            padding: 6px 12px 6px 0;
        }
        .user-info .label {
            font-weight: bold;
            width: 100px;
        }
        .btn-return {
            background-color: #efefef;
            color: #000;
            border: 1px solid #767676;
            padding: 5px 15px;
            font-size: 13px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .btn-return:hover {
            background-color: #e5e5e5;
        }
        .email-notice {
            padding: 10px 14px;
            border-radius: 4px;
            margin-bottom: 18px;
            font-size: 13px;
            line-height: 1.5;
        }
        .email-success {
            background-color: #e6f4ea;
            border: 1px solid #34a853;
            color: #137333;
        }
        .email-warning {
            background-color: #fef7e0;
            border: 1px solid #f9ab00;
            color: #b06000;
        }
    </style>
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
            <strong>✔ Đã gửi email thành công!</strong> Một thư chào mừng đã được gửi tới hộp thư <i>${user.email}</i>.
        </div>
    <% } else if (emailStatus != null && emailStatus.startsWith("FAILED")) { %>
        <div class="email-notice email-warning">
            <strong>⚠️ Chưa thể gửi email tới ${user.email}!</strong><br>
            Chi tiết: <%= emailStatus.replace("FAILED: ", "") %><br>
            <i>(Hãy mở file <code>MailUtil.java</code> để điền Gmail của bạn và Mật khẩu ứng dụng 16 ký tự).</i>
        </div>
    <% } %>

    <p style="margin-bottom: 15px;">To enter another email address, click on the Return button below.</p>
    <a href="emailList" class="btn-return">Return</a>
    &nbsp;&nbsp;
    <a href="sqlGateway" class="btn-return">Go to The SQL Gateway</a>
</div>
</body>
</html>
