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
public class PessoaDAO {

    // ==================== ADICIONAR ====================

    public boolean adiciona(Pessoa p) {
        String sql = "INSERT INTO pessoa (nome, documento, nascimento, data_criacao, data_modificacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDocumento());
            stmt.setDate(3, p.getNascimento() != null ? Date.valueOf(p.getNascimento()) : null);
            stmt.setDate(4, Date.valueOf(p.getData_criacao()));
            stmt.setDate(5, Date.valueOf(p.getData_modificacao()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                p.setId(rs.getLong(1));

            return true;

        } catch (SQLException e) {
            System.out.println("\nErro ao adicionar pessoa: " + e.getMessage());
            return false;
        }
    }

    // ==================== REMOVER ====================

    public boolean remove(long id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao remover pessoa: " + e.getMessage());
            return false;
        }
    }

    // ==================== ALTERAR ====================

    public boolean alterarNome(long id, String novoNome, LocalDate data) {
        String sql = "UPDATE pessoa SET nome = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoNome);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar nome: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarDocumento(long id, String novoDoc, LocalDate data) {
        String sql = "UPDATE pessoa SET documento = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoDoc);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar documento: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarNascimento(long id, LocalDate novoNasc, LocalDate data) {
        String sql = "UPDATE pessoa SET nascimento = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(novoNasc));
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar nascimento: " + e.getMessage());
            return false;
        }
    }

    // ==================== BUSCAR ====================

    public Pessoa getPessoa(long id) {
        String sql = "SELECT * FROM pessoa WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildPessoa(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar pessoa: " + e.getMessage());
        }
        return null;
    }

    // ==================== LISTAR ====================

    public List<Pessoa> getPessoas() {
        String sql = "SELECT * FROM pessoa";
        List<Pessoa> lista = new ArrayList<>();

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildPessoa(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao listar pessoas: " + e.getMessage());
        }
        return lista;
    }

    public String mostrarTodos() {
        StringBuilder builder = new StringBuilder("");
        for (Pessoa pessoa : getPessoas())
            builder.append("\n").append(pessoa);
        return builder.toString();
    }

    // ==================== HELPER ====================

    Pessoa buildPessoa(ResultSet rs) throws SQLException {
        Date dc = rs.getDate("data_criacao");
        Pessoa p = new Pessoa(dc != null ? dc.toLocalDate() : LocalDate.now());
        p.setId(rs.getLong("id"));
        p.setNome(rs.getString("nome"));
        p.setDocumento(rs.getString("documento"));
        Date nasc = rs.getDate("nascimento");
        if (nasc != null) p.setNascimento(nasc.toLocalDate());
        Date dm = rs.getDate("data_modificacao");
        if (dm != null) p.setData_modificacao(dm.toLocalDate());
        return p;
    }
}
