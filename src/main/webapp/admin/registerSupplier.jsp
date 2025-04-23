<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Fornecedor</title>
</head>
<body>
    <h2>Cadastro de Fornecedor</h2>

<%
    String mensagem = request.getParameter("mensagem");
    if (mensagem != null) {
        boolean sucesso = mensagem.toLowerCase().contains("sucesso");
        String cor = sucesso ? "green" : "red";
%>
    <p style="color: <%= cor %>;"><%= mensagem %></p>
<%
    }
%>

    <form action="RegisterSupplierServlet" method="post">
        <label for="name">Nome do Fornecedor:</label><br>
        <input type="text" id="name" name="name" required><br><br>

        <input type="submit" value="Cadastrar">
    </form>

    <p><a href="/erp/employee/adminPanel.jsp">Voltar ao Painel</a></p>
</body>
</html>
