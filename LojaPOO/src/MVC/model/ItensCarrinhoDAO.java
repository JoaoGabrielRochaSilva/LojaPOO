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
public class ItensCarrinhoDAO {

    // ==================== ADICIONAR ====================

    public boolean adiciona(ItensCarrinho c) {
        String sql = "INSERT INTO itens_carrinho (id_carrinho, id_produto, quantidade, preco_unitario, data_criacao, data_modificacao) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, c.getCarrinho().getId());
            stmt.setLong(2, c.getProduto().getId());
            stmt.setInt(3, c.getQuantidade());
            stmt.setDouble(4, c.getPreco_unitario());
            stmt.setDate(5, Date.valueOf(c.getData_criacao()));
            stmt.setDate(6, Date.valueOf(c.getData_modificacao()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                c.setId(rs.getLong(1));

            return true;

        } catch (SQLException e) {
            System.out.println("\nErro ao adicionar item de carrinho: " + e.getMessage());
            return false;
        }
    }

    // ==================== REMOVER ====================

    public boolean remove(long id) {
        String sql = "DELETE FROM itens_carrinho WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao remover item de carrinho: " + e.getMessage());
            return false;
        }
    }

    // ==================== ALTERAR ====================

    public boolean alterarProduto(long id, Produto novoProduto, LocalDate data) {
        String sql = "UPDATE itens_carrinho SET id_produto = ?, preco_unitario = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, novoProduto.getId());
            stmt.setDouble(2, novoProduto.getPreco_venda());
            stmt.setDate(3, Date.valueOf(data));
            stmt.setLong(4, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar produto do item de carrinho: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarQuantidade(long id, int novaQuantidade, LocalDate data) {
        String sql = "UPDATE itens_carrinho SET quantidade = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, novaQuantidade);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar quantidade do item de carrinho: " + e.getMessage());
            return false;
        }
    }

    private boolean alterarPrecoUnitario(long id, double novoValor, LocalDate data) {
        String sql = "UPDATE itens_carrinho SET preco_unitario = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, novoValor);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar preco unitario do item de carrinho: " + e.getMessage());
            return false;
        }
    }

    // ==================== BUSCAR ====================

    public ItensCarrinho getItensCarrinho(long id) {
        String sql = buildSelectSql() + " WHERE ic.id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildItensCarrinho(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar item de carrinho: " + e.getMessage());
        }
        return null;
    }

    // ==================== LISTAR ====================

    // Retorna List<ItensCarrinho> para uso no CarrinhoControler (Collections Framework)
    public List<ItensCarrinho> getItensCarrinhos() {
        String sql = buildSelectSql();
        List<ItensCarrinho> lista = new ArrayList<>();

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildItensCarrinho(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao listar itens de carrinho: " + e.getMessage());
        }
        return lista;
    }

    public String mostrarTodos(Carrinho c) {
        String sql = buildSelectSql() + " WHERE ic.id_carrinho = ?";
        StringBuilder builder = new StringBuilder("");

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, c.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
                builder.append("\n").append(buildItensCarrinho(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao mostrar itens de carrinho: " + e.getMessage());
        }
        return builder.toString();
    }

    // ==================== VALIDAÇÕES ====================

    public boolean verificarCarrinhoVazio(Usuario u, Carrinho c) {
        String sql = "SELECT COUNT(*) FROM itens_carrinho ic "
                   + "JOIN carrinho ca ON ic.id_carrinho = ca.id "
                   + "WHERE ic.id_carrinho = ? AND ca.id_usuario = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, c.getId());
            stmt.setLong(2, u.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao verificar carrinho vazio: " + e.getMessage());
        }
        return false;
    }

    public boolean ExisteEstoque(Carrinho carrinho) {
        // Verifica se todos os itens do carrinho possuem estoque suficiente
        String sql = "SELECT COUNT(*) FROM itens_carrinho ic "
                   + "JOIN produto p ON ic.id_produto = p.id "
                   + "WHERE ic.id_carrinho = ? AND ic.quantidade <= p.qtd_estoque";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, carrinho.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao verificar estoque do carrinho: " + e.getMessage());
        }
        return false;
    }

    // ==================== HELPERS ====================

    private String buildSelectSql() {
        return "SELECT ic.id AS ic_id, ic.quantidade, ic.preco_unitario, "
             + "ic.data_criacao AS ic_dc, ic.data_modificacao AS ic_dm, "
             + "ca.id AS ca_id, ca.status AS ca_status, "
             + "ca.data_criacao AS ca_dc, ca.data_modificacao AS ca_dm, "
             + "u.id AS u_id, u.login, u.senha, "
             + "u.data_criacao AS u_dc, u.data_modificacao AS u_dm, "
             + "p.id AS p_id, p.nome AS pessoa_nome, p.documento, p.nascimento, "
             + "p.data_criacao AS p_dc, p.data_modificacao AS p_dm, "
             + "prod.id AS prod_id, prod.nome AS prod_nome, prod.descricao, prod.preco_venda, "
             + "prod.qtd_estoque, prod.ativo, "
             + "prod.data_criacao AS prod_dc, prod.data_modificacao AS prod_dm "
             + "FROM itens_carrinho ic "
             + "JOIN carrinho ca ON ic.id_carrinho = ca.id "
             + "JOIN usuario u ON ca.id_usuario = u.id "
             + "JOIN pessoa p ON u.id_pessoa = p.id "
             + "JOIN produto prod ON ic.id_produto = prod.id";
    }

    private ItensCarrinho buildItensCarrinho(ResultSet rs) throws SQLException {
        // Monta a Pessoa
        Date pDc = rs.getDate("p_dc");
        Pessoa pes = new Pessoa(pDc != null ? pDc.toLocalDate() : LocalDate.now());
        pes.setId(rs.getLong("p_id"));
        pes.setNome(rs.getString("pessoa_nome"));
        pes.setDocumento(rs.getString("documento"));
        Date nasc = rs.getDate("nascimento");
        if (nasc != null) pes.setNascimento(nasc.toLocalDate());
        Date pDm = rs.getDate("p_dm");
        if (pDm != null) pes.setData_modificacao(pDm.toLocalDate());

        // Monta o Usuario
        Date uDc = rs.getDate("u_dc");
        Usuario u = new Usuario(uDc != null ? uDc.toLocalDate() : LocalDate.now());
        u.setId(rs.getLong("u_id"));
        u.setPessoa(pes);
        u.setLogin(rs.getString("login"));
        u.setSenha(rs.getString("senha"));
        Date uDm = rs.getDate("u_dm");
        if (uDm != null) u.setData_modificacao(uDm.toLocalDate());

        // Monta o Carrinho
        Date caDc = rs.getDate("ca_dc");
        Carrinho ca = new Carrinho(caDc != null ? caDc.toLocalDate() : LocalDate.now());
        ca.setId(rs.getLong("ca_id"));
        ca.setUsuario(u);
        ca.setStatus(rs.getString("ca_status"));
        Date caDm = rs.getDate("ca_dm");
        if (caDm != null) ca.setData_modificacao(caDm.toLocalDate());

        // Monta o Produto
        Date prodDc = rs.getDate("prod_dc");
        Produto prod = new Produto(prodDc != null ? prodDc.toLocalDate() : LocalDate.now());
        prod.setId(rs.getLong("prod_id"));
        prod.setNome(rs.getString("prod_nome"));
        prod.setDescricao(rs.getString("descricao"));
        prod.setPreco_venda(rs.getDouble("preco_venda"));
        prod.setQtd_estoque(rs.getInt("qtd_estoque"));
        prod.setAtivo(rs.getBoolean("ativo"));
        Date prodDm = rs.getDate("prod_dm");
        if (prodDm != null) prod.setData_modificacao(prodDm.toLocalDate());

        // Monta o ItensCarrinho
        Date icDc = rs.getDate("ic_dc");
        ItensCarrinho ic = new ItensCarrinho(icDc != null ? icDc.toLocalDate() : LocalDate.now());
        ic.setId(rs.getLong("ic_id"));
        ic.setCarrinho(ca);
        ic.setProduto(prod);
        ic.setQuantidade(rs.getInt("quantidade"));
        ic.setPreco_unitario(rs.getDouble("preco_unitario"));
        Date icDm = rs.getDate("ic_dm");
        if (icDm != null) ic.setData_modificacao(icDm.toLocalDate());

        return ic;
    }
}
