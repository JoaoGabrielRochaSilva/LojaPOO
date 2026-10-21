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
public class CarrinhoDAO {

    // ==================== ADICIONAR ====================

    public boolean adiciona(Carrinho c) {
        String sql = "INSERT INTO carrinho (id_usuario, status, data_criacao, data_modificacao) VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, c.getUsuario().getId());
            stmt.setString(2, c.getStatus());
            stmt.setDate(3, Date.valueOf(c.getData_criacao()));
            stmt.setDate(4, Date.valueOf(c.getData_modificacao()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                c.setId(rs.getLong(1));

            return true;

        } catch (SQLException e) {
            System.out.println("\nErro ao adicionar carrinho: " + e.getMessage());
            return false;
        }
    }

    // ==================== REMOVER ====================

    public boolean remove(long id) {
        String sql = "DELETE FROM carrinho WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao remover carrinho: " + e.getMessage());
            return false;
        }
    }

    // ==================== ALTERAR ====================

    public boolean alterarStatus(long id, String novoStatus, LocalDate data) {
        String sql = "UPDATE carrinho SET status = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar status do carrinho: " + e.getMessage());
            return false;
        }
    }

    // ==================== BUSCAR ====================

    public Carrinho getCarrinho(long id) {
        String sql = buildSelectSql() + " WHERE ca.id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildCarrinho(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar carrinho: " + e.getMessage());
        }
        return null;
    }

    public Carrinho getCarrinhoAberto(Usuario u) {
        String sql = buildSelectSql() + " WHERE ca.id_usuario = ? AND ca.status = 'Aberto' LIMIT 1";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, u.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildCarrinho(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar carrinho aberto: " + e.getMessage());
        }
        return null;
    }

    // ==================== LISTAR ====================

    // Retorna List<Carrinho> para uso no EventosAutomaticos (Collections Framework)
    public List<Carrinho> getCarrinhos() {
        String sql = buildSelectSql();
        List<Carrinho> lista = new ArrayList<>();

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildCarrinho(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao listar carrinhos: " + e.getMessage());
        }
        return lista;
    }

    public String mostrarTodos(Usuario u) {
        String sql;
        List<Carrinho> lista = new ArrayList<>();

        if ("ADM".equals(u.getLogin())) {
            sql = buildSelectSql();
        } else {
            sql = buildSelectSql() + " WHERE ca.id_usuario = ?";
        }

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (!"ADM".equals(u.getLogin()))
                stmt.setLong(1, u.getId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildCarrinho(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao mostrar carrinhos: " + e.getMessage());
        }

        StringBuilder builder = new StringBuilder("");
        for (Carrinho Carrinho : lista)
            builder.append("\n").append(Carrinho);
        return builder.toString();
    }

    // ==================== HELPERS ====================

    private String buildSelectSql() {
        return "SELECT ca.id AS ca_id, ca.status AS ca_status, "
             + "ca.data_criacao AS ca_dc, ca.data_modificacao AS ca_dm, "
             + "u.id AS u_id, u.login, u.senha, "
             + "u.data_criacao AS u_dc, u.data_modificacao AS u_dm, "
             + "p.id AS p_id, p.nome, p.documento, p.nascimento, "
             + "p.data_criacao AS p_dc, p.data_modificacao AS p_dm "
             + "FROM carrinho ca "
             + "JOIN usuario u ON ca.id_usuario = u.id "
             + "JOIN pessoa p ON u.id_pessoa = p.id";
    }

    private Carrinho buildCarrinho(ResultSet rs) throws SQLException {
        // Monta a Pessoa
        Date pDc = rs.getDate("p_dc");
        Pessoa p = new Pessoa(pDc != null ? pDc.toLocalDate() : LocalDate.now());
        p.setId(rs.getLong("p_id"));
        p.setNome(rs.getString("nome"));
        p.setDocumento(rs.getString("documento"));
        Date nasc = rs.getDate("nascimento");
        if (nasc != null) p.setNascimento(nasc.toLocalDate());
        Date pDm = rs.getDate("p_dm");
        if (pDm != null) p.setData_modificacao(pDm.toLocalDate());

        // Monta o Usuario
        Date uDc = rs.getDate("u_dc");
        Usuario u = new Usuario(uDc != null ? uDc.toLocalDate() : LocalDate.now());
        u.setId(rs.getLong("u_id"));
        u.setPessoa(p);
        u.setLogin(rs.getString("login"));
        u.setSenha(rs.getString("senha"));
        Date uDm = rs.getDate("u_dm");
        if (uDm != null) u.setData_modificacao(uDm.toLocalDate());

        // Monta o Carrinho
        Date caDc = rs.getDate("ca_dc");
        Carrinho c = new Carrinho(caDc != null ? caDc.toLocalDate() : LocalDate.now());
        c.setId(rs.getLong("ca_id"));
        c.setUsuario(u);
        c.setStatus(rs.getString("ca_status"));
        Date caDm = rs.getDate("ca_dm");
        if (caDm != null) c.setData_modificacao(caDm.toLocalDate());

        return c;
    }
}
