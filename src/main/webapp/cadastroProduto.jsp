<%@ page import="java.sql.*" %>
<%@ page import="erp.util.DatabaseConnection" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Produto</title>
</head>
<body>
    <h2>Cadastro de Produto</h2>

    <!-- Exibe a mensagem de sucesso ou erro, se houver -->
    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
            out.println("<h3>" + message + "</h3>");
        }
    %>

    <form action="/erp/CadastrarProdutoServlet" method="post">
        <label for="name">Nome do Produto:</label><br>
        <input type="text" id="name" name="name" required><br><br>

        <label for="description">Descrição:</label><br>
        <textarea id="description" name="description" rows="4" cols="30"></textarea><br><br>

        <label for="price">Preço:</label><br>
        <input type="number" id="price" name="price" step="0.01" required><br><br>

        <label for="category">Categoria:</label><br>
        <select id="category" name="category_id" required>
            <%
                try (Connection conn = DatabaseConnection.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT id, name FROM categories ORDER BY name")) {

                    while (rs.next()) {
                        int categoryId = rs.getInt("id");
                        String categoryName = rs.getString("name");
                        out.println("<option value=\"" + categoryId + "\">" + categoryName + "</option>");
                    }

                } catch (Exception e) {
                    out.println("<option disabled>Erro ao carregar categorias</option>");
                    e.printStackTrace(new java.io.PrintWriter(out));
                }
            %>
        </select><br><br>

        <label for="supplier">Fornecedor:</label><br>
        <select id="supplier" name="supplier_id" required>
            <%
                try (Connection conn = DatabaseConnection.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT id, name FROM suppliers ORDER BY name")) {

                    while (rs.next()) {
                        int supplierId = rs.getInt("id");
                        String supplierName = rs.getString("name");
                        out.println("<option value=\"" + supplierId + "\">" + supplierName + "</option>");
                    }

                } catch (Exception e) {
                    out.println("<option disabled>Erro ao carregar fornecedores</option>");
                    e.printStackTrace(new java.io.PrintWriter(out));
                }
            %>
        </select><br><br>

        <label for="stock">Estoque:</label><br>
        <input type="number" id="stock" name="stock" required><br><br>

        <input type="submit" value="Cadastrar Produto">
    </form>

    <p><a href="/erp/painel.html">Voltar para o Painel</a></p>
</body>
</html>
