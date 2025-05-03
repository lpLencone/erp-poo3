<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, erp.dao.ProductDAO, erp.model.Product, erp.dao.CategoryDAO, erp.model.Category" %>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Gerenciamento de Produtos</title>
    <style>
        table {
            width: 80%;
            border-collapse: collapse;
            margin: 20px auto;
        }
        th, td {
            border: 1px solid #888;
            padding: 10px;
            text-align: center;
        }
        .message {
            color: green;
            text-align: center;
        }
        .error {
            color: red;
            text-align: center;
        }
        .actions form {
            display: inline;
        }
        .low-stock {
		    background-color: #ffe5e5; /* vermelho claro */
		    font-weight: bold;
		}
		.low-stock-indicator {
		    color: red;
		    font-size: 0.9em;
		    margin-left: 5px;
		}       
    </style>
</head>
<body>

<h1 style="text-align:center;">Gerenciamento de Produtos</h1>

<%
    String message = (String) session.getAttribute("message");
    if (message != null) {
%>
    <p class="message"><%= message %></p>
<%
        session.removeAttribute("message");
    }

    ProductDAO productDAO = new ProductDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

    List<Product> products = productDAO.getAllProducts();
    List<Category> categories = categoryDAO.getAllCategories();

    Map<Integer, String> categoryMap = new HashMap<>();
    for (Category c : categories) {
        categoryMap.put(c.id, c.name);
    }
%>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Preço</th>
            <th>Categoria</th>
            <th>Estoque</th>
            <th>Ações</th>
        </tr>
    </thead>
    <tbody>
    <%
    for (Product p : products) {
        boolean isLowStock = p.stock < 10;
	%>
	    <tr class="<%= isLowStock ? "low-stock" : "" %>">
	        <td><%= p.id %></td>
	        <td><%= p.name %></td>
	        <td>R$ <%= String.format("%.2f", p.price) %></td>
	        <td><%= categoryMap.getOrDefault(p.categoryId, "Desconhecida") %></td>
	        <td>
	            <%= p.stock %>
	            <% if (isLowStock) { %>
	                <span class="low-stock-indicator">(Baixo!)</span>
	            <% } %>
	        </td>
	        <td class="actions">
	            <a href="editProduct.jsp?id=<%= p.id %>">Editar</a>
	            <form action="DeleteProductServlet" method="post" onsubmit="return confirm('Deseja remover este produto?');">
	                <input type="hidden" name="id" value="<%= p.id %>">
	                <button type="submit">Remover</button>
	            </form>
	        </td>
	    </tr>
	<%
	    }
	%>
    </tbody>
</table>

<div style="text-align:center;">
    <p><a href="addProduct.jsp">Adicionar novo produto</a></p>
    <p><a href="adminPanel.jsp">Voltar ao painel administrativo</a></p>
</div>

</body>
</html>
