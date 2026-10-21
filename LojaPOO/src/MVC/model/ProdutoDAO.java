/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joao
 */
public class ProdutoDAO {

    // ==================== ADICIONAR ====================

    public boolean adiciona(Produto p) {
        String sql = "INSERT INTO produto (nome, descricao, preco_venda, qtd_estoque, ativo, data_criacao, data_modificacao) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDescricao());
            stmt.setDouble(3, p.getPreco_venda());
            stmt.setInt(4, p.getQtd_estoque());
            stmt.setBoolean(5, p.getAtivo());
            stmt.setDate(6, Date.valueOf(p.getData_criacao()));
            stmt.setDate(7, Date.valueOf(p.getData_modificacao()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                p.setId(rs.getLong(1));

            return true;

        } catch (SQLException e) {
            System.out.println("\nErro ao adicionar produto: " + e.getMessage());
            return false;
        }
    }

    // ==================== ATIVA/DESATIVA ====================

    public boolean AtivaDesativa(long id, LocalDate data) {
        String sql = "UPDATE produto SET ativo = NOT ativo, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(data));
            stmt.setLong(2, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao ativar/desativar produto: " + e.getMessage());
            return false;
        }
    }

    // ==================== ALTERAR ====================

    public boolean alterarNome(long id, String novoNome, LocalDate data) {
        String sql = "UPDATE produto SET nome = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoNome);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar nome do produto: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarDescricao(long id, String novaDesc, LocalDate data) {
        String sql = "UPDATE produto SET descricao = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novaDesc);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar descricao: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarPreco(long id, double novoPreco, LocalDate data) {
        String sql = "UPDATE produto SET preco_venda = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, novoPreco);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar preco: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarQtdEstoque(long id, int novoQtd, LocalDate data) {
        String sql = "UPDATE produto SET qtd_estoque = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, novoQtd);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                vereficarQtd(id, data);
                return true;
            }
            return false;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar estoque: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarAtivo(long id, boolean novoAtivo, LocalDate data) {
        String sql = "UPDATE produto SET ativo = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setBoolean(1, novoAtivo);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar ativo: " + e.getMessage());
            return false;
        }
    }

    // ==================== BUSCAR ====================

    public Produto getProduto(long id) {
        String sql = "SELECT * FROM produto WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildProduto(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar produto: " + e.getMessage());
        }
        return null;
    }

    // ==================== LISTAR ====================

    public List<Produto> getProdutos() {
        String sql = "SELECT * FROM produto";
        List<Produto> lista = new ArrayList<>();

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildProduto(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao listar produtos: " + e.getMessage());
        }
        return lista;
    }

    public String mostrarTodos() {
        StringBuilder builder = new StringBuilder("");
        for (Produto produto : getProdutos())
            builder.append("\n").append(produto);
        return builder.toString();
    }

    // ==================== LÓGICA INTERNA ====================

    private void vereficarQtd(long id, LocalDate data) {
        Produto p = getProduto(id);
        if (p != null) {
            if (p.getQtd_estoque() <= 0)
                this.alterarAtivo(id, false, data);
            else
                this.alterarAtivo(id, true, data);
        }
    }

    // ==================== HELPER ====================

    Produto buildProduto(ResultSet rs) throws SQLException {
        Date dc = rs.getDate("data_criacao");
        Produto p = new Produto(dc != null ? dc.toLocalDate() : LocalDate.now());
        p.setId(rs.getLong("id"));
        p.setNome(rs.getString("nome"));
        p.setDescricao(rs.getString("descricao"));
        p.setPreco_venda(rs.getDouble("preco_venda"));
        p.setQtd_estoque(rs.getInt("qtd_estoque"));
        p.setAtivo(rs.getBoolean("ativo"));
        Date dm = rs.getDate("data_modificacao");
        if (dm != null) p.setData_modificacao(dm.toLocalDate());
        return p;
    }
}
