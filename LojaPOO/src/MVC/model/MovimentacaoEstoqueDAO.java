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
public class MovimentacaoEstoqueDAO {

    // ==================== ADICIONAR ====================

    public boolean adiciona(MovimentacaoEstoque m) {
        String sql = "INSERT INTO movimentacao_estoque (id_produto, tipo, quantidade, valor_unitario, data_criacao, data_modificacao) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, m.getProduto().getId());
            stmt.setString(2, m.getTipo());
            stmt.setInt(3, m.getQuantidade());
            stmt.setDouble(4, m.getValor_unitario());
            stmt.setDate(5, Date.valueOf(m.getData_criacao()));
            stmt.setDate(6, Date.valueOf(m.getData_modificacao()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                m.setId(rs.getLong(1));

            return true;

        } catch (SQLException e) {
            System.out.println("\nErro ao adicionar movimentacao: " + e.getMessage());
            return false;
        }
    }

    // ==================== REMOVER ====================

    public boolean remove(long id, LocalDate data) {
        MovimentacaoEstoque m = getMov(id);
        if (m == null)
            return false;

        // Reverte o efeito da movimentacao no estoque antes de remover
        this.reverterMov(m, data);

        String sql = "DELETE FROM movimentacao_estoque WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao remover movimentacao: " + e.getMessage());
            return false;
        }
    }

    // ==================== ALTERAR ====================

    public boolean alterarTipo(long id, String novoTipo, LocalDate data) {
        MovimentacaoEstoque m = getMov(id);
        if (m == null)
            return false;

        this.reverterMov(m, data);

        String sql = "UPDATE movimentacao_estoque SET tipo = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoTipo);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar tipo: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarProduto(long id, Produto novoProduto, LocalDate data) {
        MovimentacaoEstoque m = getMov(id);
        if (m == null)
            return false;

        this.reverterMov(m, data);

        String sql = "UPDATE movimentacao_estoque SET id_produto = ?, valor_unitario = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, novoProduto.getId());
            stmt.setDouble(2, novoProduto.getPreco_venda());
            stmt.setDate(3, Date.valueOf(data));
            stmt.setLong(4, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar produto da movimentacao: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarQuantidade(long id, int novaQtd, LocalDate data) {
        MovimentacaoEstoque m = getMov(id);
        if (m == null)
            return false;

        this.reverterMov(m, data);

        String sql = "UPDATE movimentacao_estoque SET quantidade = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, novaQtd);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar quantidade: " + e.getMessage());
            return false;
        }
    }

    // ==================== BUSCAR ====================

    public MovimentacaoEstoque getMov(long id) {
        // JOIN para carregar a Movimentacao com seu Produto
        String sql = "SELECT me.id, me.tipo, me.quantidade, me.valor_unitario, "
                   + "me.data_criacao AS me_dc, me.data_modificacao AS me_dm, "
                   + "p.id AS p_id, p.nome, p.descricao, p.preco_venda, "
                   + "p.qtd_estoque, p.ativo, "
                   + "p.data_criacao AS p_dc, p.data_modificacao AS p_dm "
                   + "FROM movimentacao_estoque me "
                   + "JOIN produto p ON me.id_produto = p.id "
                   + "WHERE me.id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildMovimentacao(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar movimentacao: " + e.getMessage());
        }
        return null;
    }

    // ==================== LISTAR ====================

    public List<MovimentacaoEstoque> getMovimentacoes() {
        String sql = "SELECT me.id, me.tipo, me.quantidade, me.valor_unitario, "
                   + "me.data_criacao AS me_dc, me.data_modificacao AS me_dm, "
                   + "p.id AS p_id, p.nome, p.descricao, p.preco_venda, "
                   + "p.qtd_estoque, p.ativo, "
                   + "p.data_criacao AS p_dc, p.data_modificacao AS p_dm "
                   + "FROM movimentacao_estoque me "
                   + "JOIN produto p ON me.id_produto = p.id";

        List<MovimentacaoEstoque> lista = new ArrayList<>();

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildMovimentacao(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao listar movimentacoes: " + e.getMessage());
        }
        return lista;
    }

    public String mostrarTodos() {
        StringBuilder builder = new StringBuilder("");
        for (MovimentacaoEstoque movimentacao : getMovimentacoes())
            builder.append("\n").append(movimentacao);
        return builder.toString();
    }

    // ==================== LÓGICA INTERNA ====================

    // Reverte o efeito da movimentacao no estoque do produto (UPDATE direto no banco)
    private void reverterMov(MovimentacaoEstoque m, LocalDate data) {
        String sqlQtd;
        if ("Entrada".equals(m.getTipo())) {
            // Entrada foi somada ao estoque -> subtrai ao reverter
            sqlQtd = "UPDATE produto SET qtd_estoque = qtd_estoque - ?, data_modificacao = ? WHERE id = ?";
        } else if ("Saida".equals(m.getTipo())) {
            // Saida foi subtraida do estoque -> soma ao reverter
            sqlQtd = "UPDATE produto SET qtd_estoque = qtd_estoque + ?, data_modificacao = ? WHERE id = ?";
        } else {
            return; // Ajuste nao e revertido
        }

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sqlQtd)) {

            stmt.setInt(1, m.getQuantidade());
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, m.getProduto().getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("\nErro ao reverter movimentacao: " + e.getMessage());
        }

        // Atualiza o campo 'ativo' conforme o estoque resultante
        String sqlAtivo = "UPDATE produto SET ativo = (qtd_estoque > 0), data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sqlAtivo)) {

            stmt.setDate(1, Date.valueOf(data));
            stmt.setLong(2, m.getProduto().getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("\nErro ao atualizar ativo apos reversao: " + e.getMessage());
        }
    }

    // ==================== HELPER ====================

    private MovimentacaoEstoque buildMovimentacao(ResultSet rs) throws SQLException {
        // Monta o Produto
        Date pDc = rs.getDate("p_dc");
        Produto p = new Produto(pDc != null ? pDc.toLocalDate() : LocalDate.now());
        p.setId(rs.getLong("p_id"));
        p.setNome(rs.getString("nome"));
        p.setDescricao(rs.getString("descricao"));
        p.setPreco_venda(rs.getDouble("preco_venda"));
        p.setQtd_estoque(rs.getInt("qtd_estoque"));
        p.setAtivo(rs.getBoolean("ativo"));
        Date pDm = rs.getDate("p_dm");
        if (pDm != null) p.setData_modificacao(pDm.toLocalDate());

        // Monta a MovimentacaoEstoque
        Date meDc = rs.getDate("me_dc");
        MovimentacaoEstoque m = new MovimentacaoEstoque(meDc != null ? meDc.toLocalDate() : LocalDate.now());
        m.setId(rs.getLong("id"));
        m.setProduto(p);
        m.setTipo(rs.getString("tipo"));
        m.setQuantidade(rs.getInt("quantidade"));
        m.setValor_unitario(rs.getDouble("valor_unitario"));
        Date meDm = rs.getDate("me_dm");
        if (meDm != null) m.setData_modificacao(meDm.toLocalDate());

        return m;
    }
}
