<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Categoria - ERP</title>
</head>
<body>
    <h2>Cadastrar Nova Categoria</h2>

    <!-- Mostra a mensagem, se existir -->
    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
        <p style="color: <%= message.startsWith("Erro") ? "red" : "green" %>;"><%= message %></p>
    <%
        }
    %>

    <form action="RegisterCategoryServlet" method="post">
        <label for="name">Nome da Categoria:</label>
        <input type="text" id="name" name="name" required>

        <input type="submit" value="Cadastrar">
    </form>

    <br>
    <p><a href="/erp/adminPanel.jsp">Voltar para o painel administrativo</a></p>
</body>
</html>
