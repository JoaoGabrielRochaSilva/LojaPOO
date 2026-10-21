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
public class UsuarioDAO {

    // ==================== ADICIONAR ====================

    public boolean adiciona(Usuario p) {
        String sql = "INSERT INTO usuario (id_pessoa, login, senha, data_criacao, data_modificacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, p.getPessoa().getId());
            stmt.setString(2, p.getLogin());
            stmt.setString(3, p.getSenha());
            stmt.setDate(4, Date.valueOf(p.getData_criacao()));
            stmt.setDate(5, Date.valueOf(p.getData_modificacao()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                p.setId(rs.getLong(1));

            return true;

        } catch (SQLException e) {
            System.out.println("\nErro ao adicionar usuario: " + e.getMessage());
            return false;
        }
    }

    // ==================== REMOVER ====================

    public boolean remove(long id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao remover usuario: " + e.getMessage());
            return false;
        }
    }

    // ==================== ALTERAR ====================

    public boolean alterarLogin(long id, String novoNome, LocalDate data) {
        String sql = "UPDATE usuario SET login = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoNome);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar login: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarSenha(long id, String novaSenha, LocalDate data) {
        String sql = "UPDATE usuario SET senha = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novaSenha);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar senha: " + e.getMessage());
            return false;
        }
    }

    // ==================== BUSCAR ====================

    public Usuario getUsuario(long id) {
        // JOIN para carregar Usuario e sua Pessoa em uma unica consulta
        String sql = "SELECT u.id, u.login, u.senha, "
                   + "u.data_criacao AS u_dc, u.data_modificacao AS u_dm, "
                   + "p.id AS p_id, p.nome, p.documento, p.nascimento, "
                   + "p.data_criacao AS p_dc, p.data_modificacao AS p_dm "
                   + "FROM usuario u "
                   + "JOIN pessoa p ON u.id_pessoa = p.id "
                   + "WHERE u.id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildUsuario(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar usuario: " + e.getMessage());
        }
        return null;
    }

    public Usuario validarUser(String login, String senha) {
        String sql = "SELECT u.id, u.login, u.senha, "
                   + "u.data_criacao AS u_dc, u.data_modificacao AS u_dm, "
                   + "p.id AS p_id, p.nome, p.documento, p.nascimento, "
                   + "p.data_criacao AS p_dc, p.data_modificacao AS p_dm "
                   + "FROM usuario u "
                   + "JOIN pessoa p ON u.id_pessoa = p.id "
                   + "WHERE u.login = ? AND u.senha = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildUsuario(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao validar usuario: " + e.getMessage());
        }
        return null;
    }

    // ==================== LISTAR ====================

    public List<Usuario> getUsuarios() {
        String sql = "SELECT u.id, u.login, u.senha, "
                   + "u.data_criacao AS u_dc, u.data_modificacao AS u_dm, "
                   + "p.id AS p_id, p.nome, p.documento, p.nascimento, "
                   + "p.data_criacao AS p_dc, p.data_modificacao AS p_dm "
                   + "FROM usuario u "
                   + "JOIN pessoa p ON u.id_pessoa = p.id";

        List<Usuario> lista = new ArrayList<>();

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildUsuario(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    public String mostrarTodos() {
        StringBuilder builder = new StringBuilder("");
        for (Usuario usuario : getUsuarios())
            builder.append("\n").append(usuario);
        return builder.toString();
    }

    // ==================== HELPER ====================

    // Constroi um Usuario (com Pessoa embutida) a partir de um ResultSet com aliases
    Usuario buildUsuario(ResultSet rs) throws SQLException {
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
        u.setId(rs.getLong("id"));
        u.setPessoa(p);
        u.setLogin(rs.getString("login"));
        u.setSenha(rs.getString("senha"));
        Date uDm = rs.getDate("u_dm");
        if (uDm != null) u.setData_modificacao(uDm.toLocalDate());

        return u;
    }
}
