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
        String erro = request.getParameter("erro");
        if ("1".equals(erro)) {
    %>
        <p style="color:red;">Email ou senha incorretos.</p>
    <% 
        } 
    %>

    <form action="/erp/LoginServlet" method="post">
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="senha">Senha:</label><br>
        <input type="password" id="password" name="password" required><br><br>

        <input type="submit" value="Entrar">
    </form>

    <p><a href="/erp/cadastroCliente.jsp">Cadastrar Cliente</a></p>

</body>
</html>
