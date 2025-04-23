<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirmação</title>
</head>
<body>
    <h2><%= request.getAttribute("mensagem") %></h2>
    <a href="listarProdutos.jsp">Voltar à loja</a>
</body>
</html>
