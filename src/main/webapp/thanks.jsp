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
        .email-confirmation {
            color: #006666;
            font-weight: bold;
            font-size: 14px;
            margin: 15px 0;
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

    <p class="email-confirmation">✉ A confirmation email has been sent to <i>${user.email}</i>.</p>

    <p style="margin-bottom: 15px;">To enter another email address, click on the Return button below.</p>
    <a href="emailList" class="btn-return">Return</a>
    &nbsp;&nbsp;
    <a href="sqlGateway" class="btn-return">Go to The SQL Gateway</a>
</div>
</body>
</html>
