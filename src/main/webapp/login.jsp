<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - ERP</title>
</head>
<body>
    <h2>Login</h2>

    <% 
        String error = request.getParameter("error");
        if ("1".equals(error)) {
    %>
        <p style="color:red;">Email ou senha incorretos.</p>
    <% 
        } 
    %>

    <form action="LoginServlet" method="post">
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="password">Senha:</label><br>
        <input type="password" id="password" name="password" required><br><br>

        <input type="submit" value="Entrar">
    </form>

    <p><a href="client/register.jsp">Cadastrar</a></p>

</body>
</html>
