<%@ page import="java.util.List" %>
<%@ page import="erp.dao.SupplierDAO" %>
<%@ page import="erp.model.Supplier" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastro e Gerenciamento de Fornecedores</title>
</head>
<body>
    <h2>Cadastro de Fornecedor</h2>

<%
    String message = request.getParameter("message");
    if (message != null) {
        boolean sucesso = message.toLowerCase().contains("sucesso");
        String cor = sucesso ? "green" : "red";
%>
    <p style="color: <%= cor %>;"><%= message %></p>
<%
    }
%>

    <!-- Formulário de Cadastro -->
    <form action="RegisterSupplierServlet" method="post">
        <label for="name">Nome do Fornecedor:</label><br>
        <input type="text" id="name" name="name" required><br><br>

        <input type="submit" value="Cadastrar">
    </form>

    <h2>Fornecedores Cadastrados</h2>

<%
    SupplierDAO supplierDAO = new SupplierDAO();
    List<Supplier> fornecedores = supplierDAO.getAllSuppliers();
    if (fornecedores.isEmpty()) {
%>
    <p>Nenhum fornecedor cadastrado.</p>
<%
    } else {
%>
    <table border="1">
        <tr>
            <th>Nome</th>
            <th>Ações</th>
        </tr>
    <% for (Supplier fornecedor : fornecedores) { %>
        <tr>
            <td><%= fornecedor.name %></td>
            <td>
                <form action="DeleteSupplierServlet" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= fornecedor.id %>">
                    <input type="submit" value="Excluir" onclick="return confirm('Deseja realmente excluir este fornecedor?');">
                </form>
            </td>
        </tr>
    <% } %>
    </table>
<%
    }
%>

    <p><a href="/erp/employee/adminPanel.jsp">Voltar ao Painel</a></p>
</body>
</html>
