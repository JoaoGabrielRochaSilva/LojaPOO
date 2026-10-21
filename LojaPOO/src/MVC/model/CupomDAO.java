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
public class CupomDAO {

    // ==================== ADICIONAR ====================

    public boolean adiciona(Cupom c) {
        String sql = "INSERT INTO cupom (codigo, tipo_desconto, valor_desconto, valor_minimo, "
                   + "data_validade, ativo, data_criacao, data_modificacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, c.getCodigo());
            stmt.setString(2, c.getTipo_desconto());
            stmt.setDouble(3, c.getValor_desconto());
            stmt.setDouble(4, c.getValor_minimo());
            stmt.setDate(5, Date.valueOf(c.getData_validade()));
            stmt.setBoolean(6, c.getAtivo());
            stmt.setDate(7, Date.valueOf(c.getData_criacao()));
            stmt.setDate(8, Date.valueOf(c.getData_modificacao()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                c.setId(rs.getLong(1));

            return true;

        } catch (SQLException e) {
            System.out.println("\nErro ao adicionar cupom: " + e.getMessage());
            return false;
        }
    }

    // ==================== ATIVA/DESATIVA ====================

    public boolean AtivaDesativa(long id, LocalDate data) {
        String sql = "UPDATE cupom SET ativo = NOT ativo, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(data));
            stmt.setLong(2, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao ativar/desativar cupom: " + e.getMessage());
            return false;
        }
    }

    // ==================== ALTERAR ====================

    public boolean alterarCodigo(long id, int novoCod, LocalDate data) {
        // Garante que nao existe outro cupom com o mesmo codigo
        if (ExisteCod(novoCod))
            return false;

        String sql = "UPDATE cupom SET codigo = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, novoCod);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar codigo do cupom: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarTipoDesconto(long id, String novoTipo, LocalDate data) {
        String sql = "UPDATE cupom SET tipo_desconto = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoTipo);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar tipo de desconto: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarValorDesconto(long id, double novoValor, LocalDate data) {
        String sql = "UPDATE cupom SET valor_desconto = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, novoValor);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar valor de desconto: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarDataValidade(long id, LocalDate novaValidade, LocalDate data) {
        String sql = "UPDATE cupom SET data_validade = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(novaValidade));
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar data de validade: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarValorMinimo(long id, double novoValorMin, LocalDate data) {
        String sql = "UPDATE cupom SET valor_minimo = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, novoValorMin);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar valor minimo: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarAtivo(long id, boolean novoAtivo, LocalDate data) {
        String sql = "UPDATE cupom SET ativo = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setBoolean(1, novoAtivo);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar ativo do cupom: " + e.getMessage());
            return false;
        }
    }

    // ==================== BUSCAR ====================

    public Cupom getCupom(int codigo) {
        String sql = "SELECT * FROM cupom WHERE codigo = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildCupom(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar cupom: " + e.getMessage());
        }
        return null;
    }

    // ==================== LISTAR ====================

    // Retorna List<Cupom> para uso no EventosAutomaticos (Collections Framework)
    public List<Cupom> getCupoms() {
        String sql = "SELECT * FROM cupom";
        List<Cupom> lista = new ArrayList<>();

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildCupom(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao listar cupons: " + e.getMessage());
        }
        return lista;
    }

    public String mostrarTodos() {
        StringBuilder builder = new StringBuilder("");
        for (Cupom Cupom : getCupoms())
            builder.append("\n").append(Cupom);
        return builder.toString();
    }

    // ==================== LÓGICA INTERNA ====================

    private boolean ExisteCod(int codigo) {
        String sql = "SELECT COUNT(*) FROM cupom WHERE codigo = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao verificar codigo: " + e.getMessage());
        }
        return false;
    }

    // ==================== HELPER ====================

    Cupom buildCupom(ResultSet rs) throws SQLException {
        Date dc = rs.getDate("data_criacao");
        Cupom c = new Cupom(dc != null ? dc.toLocalDate() : LocalDate.now());
        c.setId(rs.getLong("id"));
        c.setCodigo(rs.getInt("codigo"));
        c.setTipo_desconto(rs.getString("tipo_desconto"));
        c.setValor_desconto(rs.getDouble("valor_desconto"));
        c.setValor_minimo(rs.getDouble("valor_minimo"));
        Date dv = rs.getDate("data_validade");
        if (dv != null) c.setData_validade(dv.toLocalDate());
        c.setAtivo(rs.getBoolean("ativo"));
        Date dm = rs.getDate("data_modificacao");
        if (dm != null) c.setData_modificacao(dm.toLocalDate());
        return c;
    }
}
