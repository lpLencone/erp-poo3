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

    <form action="/erp/CadastrarProdutoServlet" method="post">
        <label for="nome">Nome do Produto:</label><br>
        <input type="text" id="nome" name="nome" required><br><br>

        <label for="descricao">Descrição:</label><br>
        <textarea id="descricao" name="descricao" rows="4" cols="30"></textarea><br><br>

        <label for="preco">Preço:</label><br>
        <input type="number" id="preco" name="preco" step="0.01" required><br><br>

        <label for="categoria">Categoria:</label><br>
        <select id="categoria" name="categoria_id" required>
            <%
                try (Connection conn = DatabaseConnection.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT id, name FROM categories ORDER BY name")) {

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        out.println("<option value=\"" + id + "\">" + name + "</option>");
                    }

                } catch (Exception e) {
                    out.println("<option disabled>Erro ao carregar categorias</option>");
                    e.printStackTrace(new java.io.PrintWriter(out));
                }
            %>
        </select><br><br>

        <label for="fornecedor">Fornecedor:</label><br>
        <select id="fornecedor" name="fornecedor_id" required>
            <%
                try (Connection conn = DatabaseConnection.getConnection();
                     Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT id, name FROM suppliers ORDER BY name")) {

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        out.println("<option value=\"" + id + "\">" + name + "</option>");
                    }

                } catch (Exception e) {
                    out.println("<option disabled>Erro ao carregar fornecedores</option>");
                    e.printStackTrace(new java.io.PrintWriter(out));
                }
            %>
        </select><br><br>

        <input type="submit" value="Cadastrar Produto">
    </form>

    <p><a href="/erp/painel.html">Voltar para o Painel</a></p>
</body>
</html>
