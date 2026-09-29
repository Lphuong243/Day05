<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css">
</head>
<body>
    <div class="container">
        <div class="nav-links">
            <a href="sqlGateway">➔ The SQL Gateway</a>
            <a href="emailList">➔ Join Email List</a>
        </div>
        <h1 class="title">The SQL Gateway</h1>
        <p class="instruction">Enter an SQL statement and click the Execute button.</p>

        <form action="sqlGateway" method="post">
            <div class="form-group">
                <label for="sqlStatement" class="label">SQL statement:</label>
                <textarea id="sqlStatement" name="sqlStatement" class="sql-input">${sqlStatement}</textarea>
            </div>
            <button type="submit" class="btn-execute">Execute</button>
        </form>

        <section class="result-section">
            <span class="label">SQL result:</span>
            <div class="result-content">
                ${sqlResult}
            </div>
        </section>
    </div>
</body>
</html>
