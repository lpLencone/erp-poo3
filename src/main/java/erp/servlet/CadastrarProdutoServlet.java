package erp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CadastrarProdutoServlet")
public class CadastrarProdutoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final String url = "jdbc:postgresql://localhost:5432/erp";
    private final String user = "postgres";
    private final String password = "admin";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        double preco = Double.parseDouble(request.getParameter("preco"));
        int categoriaId = Integer.parseInt(request.getParameter("categoria_id"));
        int fornecedorId = Integer.parseInt(request.getParameter("fornecedor_id"));

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);

            // Inserir produto
            String sqlProduto = "INSERT INTO products (name, description, price, category_id) VALUES (?, ?, ?, ?) RETURNING id";
            PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto);
            stmtProduto.setString(1, nome);
            stmtProduto.setString(2, descricao);
            stmtProduto.setDouble(3, preco);
            stmtProduto.setInt(4, categoriaId);

            ResultSet rs = stmtProduto.executeQuery();
            if (rs.next()) {
                int produtoId = rs.getInt("id");

                // Relacionar com fornecedor na tabela supplier_products
                String sqlRelacao = "INSERT INTO supplier_products (supplier_id, product_id) VALUES (?, ?)";
                PreparedStatement stmtRelacao = conn.prepareStatement(sqlRelacao);
                stmtRelacao.setInt(1, fornecedorId);
                stmtRelacao.setInt(2, produtoId);
                stmtRelacao.executeUpdate();
                stmtRelacao.close();

                out.println("<h3>Produto cadastrado com sucesso!</h3>");
            } else {
                out.println("<h3>Erro ao inserir o produto.</h3>");
            }

            rs.close();
            stmtProduto.close();
            conn.close();

        } catch (Exception e) {
            out.println("<h3>Erro ao cadastrar produto: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }

        out.println("<br><a href=\"/erp/cadastroProduto.jsp\">Voltar</a>");
    }
}