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
public class PedidosDAO {

    // ==================== ADICIONAR ====================

    public boolean adiciona(Pedidos p) {
        String sql = "INSERT INTO pedidos (id_usuario, id_cupom, status, forma_pagamento, valor_total, data_criacao, data_modificacao) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, p.getUsuario().getId());
            if (p.getCupom() != null)
                stmt.setLong(2, p.getCupom().getId());
            else
                stmt.setNull(2, java.sql.Types.BIGINT);
            stmt.setString(3, p.getStatus());
            stmt.setString(4, p.getFormaPagamento());
            stmt.setDouble(5, p.getValor_total());
            stmt.setDate(6, Date.valueOf(p.getData_criacao()));
            stmt.setDate(7, Date.valueOf(p.getData_modificacao()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                p.setId(rs.getLong(1));

            return true;

        } catch (SQLException e) {
            System.out.println("\nErro ao adicionar pedido: " + e.getMessage());
            return false;
        }
    }

    // ==================== REMOVER ====================

    public boolean remove(long id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao remover pedido: " + e.getMessage());
            return false;
        }
    }

    // ==================== ALTERAR ====================

    public boolean alterarCupom(long id, Cupom novoCupom, LocalDate data) {
        String sql = "UPDATE pedidos SET id_cupom = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (novoCupom != null)
                stmt.setLong(1, novoCupom.getId());
            else
                stmt.setNull(1, java.sql.Types.BIGINT);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar cupom do pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarStatus(long id, String novoStatus, LocalDate data) {
        String sql = "UPDATE pedidos SET status = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar status do pedido: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarForPagamento(long id, String novaForma, LocalDate data) {
        String sql = "UPDATE pedidos SET forma_pagamento = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novaForma);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar forma de pagamento: " + e.getMessage());
            return false;
        }
    }

    // ==================== BUSCAR ====================

    public Pedidos getPedido(long id) {
        String sql = buildSelectSql() + " WHERE ped.id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildPedidos(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar pedido: " + e.getMessage());
        }
        return null;
    }

    // ==================== LISTAR ====================

    // Retorna List<Pedidos> para uso no RelatorioControler e EventosAutomaticos (Collections Framework)
    public List<Pedidos> getPedidos() {
        String sql = buildSelectSql();
        List<Pedidos> lista = new ArrayList<>();

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildPedidos(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao listar pedidos: " + e.getMessage());
        }
        return lista;
    }

    public String mostrarTodos(Usuario u) {
        String sql;
        List<Pedidos> lista = new ArrayList<>();

        if ("ADM".equals(u.getLogin())) {
            sql = buildSelectSql();
        } else {
            sql = buildSelectSql() + " WHERE ped.id_usuario = ?";
        }

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (!"ADM".equals(u.getLogin()))
                stmt.setLong(1, u.getId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildPedidos(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao listar pedidos por usuario: " + e.getMessage());
        }

        StringBuilder builder = new StringBuilder("");
        for (Pedidos Pedido : lista)
            builder.append("\n").append(Pedido);
        return builder.toString();
    }

    // ==================== CALCULO DE VALOR ====================

    // Recebe List<ItensPedido> em vez de array (Collections Framework)
    public void calcVltotal(Pedidos p, LocalDate data, List<ItensPedido> ip, Usuario u) {
        double soma = 0;
        for (ItensPedido itensPedido : ip) {
            // Comparacao por id para funcionar corretamente com objetos carregados do banco
            if (itensPedido != null
                    && itensPedido.getPedido().getId() == p.getId()
                    && (p.getUsuario().getId() == u.getId() || "ADM".equals(u.getLogin())))
                soma += itensPedido.getSub_total();
        }

        if (p.getCupom() != null) {
            double valor;
            if ("Percentual".equals(p.getCupom().getTipo_desconto())) {
                valor = (p.getCupom().getValor_desconto() / 100) * soma;
                p.setValor_total(soma - valor);
            } else {
                p.setValor_total(soma - p.getCupom().getValor_desconto());
            }
        } else {
            p.setValor_total(soma);
        }

        // Persiste o valor total calculado e o cupom aplicado no banco
        String sql = "UPDATE pedidos SET valor_total = ?, id_cupom = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, p.getValor_total());
            if (p.getCupom() != null)
                stmt.setLong(2, p.getCupom().getId());
            else
                stmt.setNull(2, java.sql.Types.BIGINT);
            stmt.setDate(3, Date.valueOf(data));
            stmt.setLong(4, p.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("\nErro ao persistir valor total: " + e.getMessage());
        }
    }

    // ==================== HELPERS ====================

    // Query base reutilizavel com todos os JOINs necessarios
    private String buildSelectSql() {
        return "SELECT ped.id AS ped_id, ped.status, ped.forma_pagamento, ped.valor_total, "
             + "ped.data_criacao AS ped_dc, ped.data_modificacao AS ped_dm, "
             + "u.id AS u_id, u.login, u.senha, "
             + "u.data_criacao AS u_dc, u.data_modificacao AS u_dm, "
             + "pes.id AS pes_id, pes.nome, pes.documento, pes.nascimento, "
             + "pes.data_criacao AS pes_dc, pes.data_modificacao AS pes_dm, "
             + "c.id AS c_id, c.codigo AS c_codigo, c.tipo_desconto, c.valor_desconto, "
             + "c.valor_minimo, c.data_validade, c.ativo AS c_ativo, "
             + "c.data_criacao AS c_dc, c.data_modificacao AS c_dm "
             + "FROM pedidos ped "
             + "JOIN usuario u ON ped.id_usuario = u.id "
             + "JOIN pessoa pes ON u.id_pessoa = pes.id "
             + "LEFT JOIN cupom c ON ped.id_cupom = c.id";
    }

    Pedidos buildPedidos(ResultSet rs) throws SQLException {
        // Monta a Pessoa do usuario
        Date pesDc = rs.getDate("pes_dc");
        Pessoa pes = new Pessoa(pesDc != null ? pesDc.toLocalDate() : LocalDate.now());
        pes.setId(rs.getLong("pes_id"));
        pes.setNome(rs.getString("nome"));
        pes.setDocumento(rs.getString("documento"));
        Date nasc = rs.getDate("nascimento");
        if (nasc != null) pes.setNascimento(nasc.toLocalDate());
        Date pesDm = rs.getDate("pes_dm");
        if (pesDm != null) pes.setData_modificacao(pesDm.toLocalDate());

        // Monta o Usuario
        Date uDc = rs.getDate("u_dc");
        Usuario u = new Usuario(uDc != null ? uDc.toLocalDate() : LocalDate.now());
        u.setId(rs.getLong("u_id"));
        u.setPessoa(pes);
        u.setLogin(rs.getString("login"));
        u.setSenha(rs.getString("senha"));
        Date uDm = rs.getDate("u_dm");
        if (uDm != null) u.setData_modificacao(uDm.toLocalDate());

        // Monta o Cupom (pode ser null - LEFT JOIN)
        Cupom cupom = null;
        long cupomId = rs.getLong("c_id");
        if (!rs.wasNull()) {
            Date cDc = rs.getDate("c_dc");
            cupom = new Cupom(cDc != null ? cDc.toLocalDate() : LocalDate.now());
            cupom.setId(cupomId);
            cupom.setCodigo(rs.getInt("c_codigo"));
            cupom.setTipo_desconto(rs.getString("tipo_desconto"));
            cupom.setValor_desconto(rs.getDouble("valor_desconto"));
            cupom.setValor_minimo(rs.getDouble("valor_minimo"));
            Date dv = rs.getDate("data_validade");
            if (dv != null) cupom.setData_validade(dv.toLocalDate());
            cupom.setAtivo(rs.getBoolean("c_ativo"));
            Date cDm = rs.getDate("c_dm");
            if (cDm != null) cupom.setData_modificacao(cDm.toLocalDate());
        }

        // Monta o Pedido
        Date pedDc = rs.getDate("ped_dc");
        Pedidos p = new Pedidos(pedDc != null ? pedDc.toLocalDate() : LocalDate.now());
        p.setId(rs.getLong("ped_id"));
        p.setUsuario(u);
        p.setCupom(cupom);
        p.setStatus(rs.getString("status"));
        p.setFormaPagamento(rs.getString("forma_pagamento"));
        p.setValor_total(rs.getDouble("valor_total"));
        Date pedDm = rs.getDate("ped_dm");
        if (pedDm != null) p.setData_modificacao(pedDm.toLocalDate());

        return p;
    }
}
