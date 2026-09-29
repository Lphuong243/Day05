<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Murach's Java Servlets and JSP - Email List</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    <style>
        .form-row {
            margin-bottom: 12px;
        }
        .form-row label {
            display: inline-block;
            width: 110px;
            font-weight: bold;
        }
        .form-row input[type="text"],
        .form-row input[type="email"] {
            width: 250px;
            padding: 5px 8px;
            border: 1px solid #777;
            font-size: 14px;
        }
        .btn-submit {
            background-color: #efefef;
            color: #000;
            border: 1px solid #767676;
            padding: 4px 15px;
            font-size: 13px;
            cursor: pointer;
            margin-left: 115px;
            margin-top: 5px;
        }
        .btn-submit:hover {
            background-color: #e5e5e5;
        }
        .nav-links {
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #ddd;
        }
        .nav-links a {
            color: #006666;
            text-decoration: none;
            font-weight: bold;
            margin-right: 15px;
        }
        .nav-links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <nav class="nav-links">
        <a href="sqlGateway">➔ Go to The SQL Gateway</a>
        <a href="emailList">➔ Join Email List</a>
    </nav>

    <h1 class="title">Join our email list</h1>
    <p class="instruction">To join our email list, enter your name and email address below.</p>
    
    <% if (request.getAttribute("message") != null && !((String)request.getAttribute("message")).isEmpty()) { %>
        <p class="error-msg">${message}</p>
    <% } %>

    <form action="emailList" method="post">
        <input type="hidden" name="action" value="add">

        <div class="form-row">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${user.email}" required>
        </div>

        <div class="form-row">
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" value="${user.firstName}" required>
        </div>

        <div class="form-row">
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" value="${user.lastName}" required>
        </div>

        <div>
            <button type="submit" class="btn-submit">Join Now</button>
        </div>
    </form>
</div>
</body>
</html>
