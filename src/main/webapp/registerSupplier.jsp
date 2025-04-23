<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Fornecedor</title>
</head>
<body>
    <h2>Cadastro de Fornecedor</h2>

    <%-- Mensagem de sucesso/erro opcional --%>
    <%
        String mensagem = request.getParameter("mensagem");
        if (mensagem != null) {
    %>
        <p style="color: green;"><%= mensagem %></p>
    <%
        }
    %>

    <form action="CadastrarFornecedorServlet" method="post">
        <label for="nome">Nome do Fornecedor:</label><br>
        <input type="text" id="nome" name="nome" required><br><br>

        <label for="contato">Informações de Contato:</label><br>
        <textarea id="contato" name="contato" rows="4" cols="40"></textarea><br><br>

        <input type="submit" value="Cadastrar">
    </form>

    <p><a href="adminPanel.jsp">Voltar ao Painel</a></p>
</body>
</html>
