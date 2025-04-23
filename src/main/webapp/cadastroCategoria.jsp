<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Categoria - ERP</title>
</head>
<body>
    <h2>Cadastrar Nova Categoria</h2>

    <form action="CadastrarCategoriaServlet" method="post">
        <label for="name">Nome da Categoria:</label>
        <input type="text" id="name" name="name" required>

        <label for="description">Descrição:</label>
        <input type="text" id="description" name="description" required>

        <input type="submit" value="Cadastrar">
    </form>

    <br>
    <a href="index.html">Voltar para o índice</a>
</body>
</html>
