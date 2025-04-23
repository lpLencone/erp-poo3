<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Cliente</title>
</head>
<body>
    <h2>Cadastro de Cliente</h2>

    <form action="/erp/RegisterServlet" method="post">
        <label for="nome">Nome:</label><br>
        <input type="text" id="nome" name="nome" required><br><br>

        <label for="email">E-mail:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="senha">Senha:</label><br>
        <input type="password" id="senha" name="senha" required><br><br>

        <input type="submit" value="Cadastrar Cliente">
    </form>

    <p><a href="/erp/login.jsp">← Voltar para Login</a></p>
</body>
</html>
