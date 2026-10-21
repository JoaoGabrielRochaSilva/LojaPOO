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
public class EntregaDAO {

    // ==================== ADICIONAR ====================

    public boolean adiciona(Entrega p) {
        String sql = "INSERT INTO entrega (id_pedido, codigo_rastreio, transportadora, status, "
                   + "data_envio, data_entrega, data_criacao, data_modificacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, p.getPedido().getId());
            stmt.setInt(2, p.getCodigo_rastreio());
            stmt.setString(3, p.getTransportadora());
            stmt.setString(4, p.getStatus());
            stmt.setDate(5, p.getData_envio() != null ? Date.valueOf(p.getData_envio()) : null);
            stmt.setDate(6, p.getData_entrega() != null ? Date.valueOf(p.getData_entrega()) : null);
            stmt.setDate(7, Date.valueOf(p.getData_criacao()));
            stmt.setDate(8, Date.valueOf(p.getData_modificacao()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                p.setId(rs.getLong(1));

            return true;

        } catch (SQLException e) {
            System.out.println("\nErro ao adicionar entrega: " + e.getMessage());
            return false;
        }
    }

    // ==================== REMOVER ====================

    public boolean remove(long id) {
        String sql = "DELETE FROM entrega WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao remover entrega: " + e.getMessage());
            return false;
        }
    }

    // ==================== ALTERAR ====================

    public boolean alterarPedido(long id, Pedidos novoPedido, LocalDate data) {
        String sql = "UPDATE entrega SET id_pedido = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, novoPedido.getId());
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar pedido da entrega: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarCod(long id, int novoCodigo, LocalDate data) {
        if (ExisteCod(novoCodigo))
            return false;

        String sql = "UPDATE entrega SET codigo_rastreio = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, novoCodigo);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar codigo de rastreio: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarTransportadora(long id, String novoTransport, LocalDate data) {
        String sql = "UPDATE entrega SET transportadora = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoTransport);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar transportadora: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarStatus(long id, String novoStatus, LocalDate data) {
        String sql = "UPDATE entrega SET status = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar status da entrega: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarDataEnvio(long id, LocalDate novaData, LocalDate data) {
        String sql = "UPDATE entrega SET data_envio = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(novaData));
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar data de envio: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarDataEntrega(long id, LocalDate novaData, LocalDate data) {
        String sql = "UPDATE entrega SET data_entrega = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(novaData));
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar data de entrega: " + e.getMessage());
            return false;
        }
    }

    // ==================== BUSCAR ====================

    public Entrega getEntrega(Pedidos p) {
        String sql = buildSelectSql() + " WHERE e.id_pedido = ? LIMIT 1";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, p.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildEntrega(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar entrega por pedido: " + e.getMessage());
        }
        return null;
    }

    public Entrega getEntregaCod(int codigo) {
        String sql = buildSelectSql() + " WHERE e.codigo_rastreio = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildEntrega(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar entrega por codigo: " + e.getMessage());
        }
        return null;
    }

    public Entrega getEntregaId(long id) {
        String sql = buildSelectSql() + " WHERE e.id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildEntrega(rs);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar entrega por id: " + e.getMessage());
        }
        return null;
    }

    public boolean ExistePedido(Pedidos pedido) {
        String sql = "SELECT COUNT(*) FROM entrega WHERE id_pedido = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, pedido.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao verificar pedido na entrega: " + e.getMessage());
        }
        return false;
    }

    public int getProximoCodRastreio() {
        String sql = "SELECT COALESCE(MAX(codigo_rastreio), 99) + 1 FROM entrega";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1);

        } catch (SQLException e) {
            System.out.println("\nErro ao obter proximo codigo de rastreio: " + e.getMessage());
        }
        return 100;
    }

    // ==================== LISTAR ====================

    public List<Entrega> getEntregas() {
        String sql = buildSelectSql();
        List<Entrega> lista = new ArrayList<>();

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildEntrega(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao listar entregas: " + e.getMessage());
        }
        return lista;
    }

    public String mostrarTodos(Usuario u) {
        String sql;
        List<Entrega> lista = new ArrayList<>();

        if ("ADM".equals(u.getLogin())) {
            sql = buildSelectSql();
        } else {
            // Filtra entregas cujo pedido pertence ao usuario logado
            sql = buildSelectSql() + " WHERE ped.id_usuario = ?";
        }

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (!"ADM".equals(u.getLogin()))
                stmt.setLong(1, u.getId());

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildEntrega(rs));

        } catch (SQLException e) {
            System.out.println("\nErro ao mostrar entregas: " + e.getMessage());
        }

        StringBuilder builder = new StringBuilder("");
        for (Entrega Entrega : lista)
            builder.append("\n").append(Entrega);
        return builder.toString();
    }

    // ==================== LÓGICA INTERNA ====================

    private boolean ExisteCod(int codigo) {
        String sql = "SELECT COUNT(*) FROM entrega WHERE codigo_rastreio = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao verificar codigo de rastreio: " + e.getMessage());
        }
        return false;
    }

    // ==================== HELPERS ====================

    private String buildSelectSql() {
        return "SELECT e.id AS e_id, e.codigo_rastreio, e.transportadora, e.status AS e_status, "
             + "e.data_envio, e.data_entrega, e.data_criacao AS e_dc, e.data_modificacao AS e_dm, "
             + "ped.id AS ped_id, ped.status AS ped_status, ped.forma_pagamento, ped.valor_total, "
             + "ped.data_criacao AS ped_dc, ped.data_modificacao AS ped_dm, "
             + "u.id AS u_id, u.login, u.senha, "
             + "u.data_criacao AS u_dc, u.data_modificacao AS u_dm, "
             + "pes.id AS pes_id, pes.nome, pes.documento, pes.nascimento, "
             + "pes.data_criacao AS pes_dc, pes.data_modificacao AS pes_dm, "
             + "c.id AS c_id, c.codigo AS c_codigo, c.tipo_desconto, c.valor_desconto, "
             + "c.valor_minimo, c.data_validade, c.ativo AS c_ativo, "
             + "c.data_criacao AS c_dc, c.data_modificacao AS c_dm "
             + "FROM entrega e "
             + "JOIN pedidos ped ON e.id_pedido = ped.id "
             + "JOIN usuario u ON ped.id_usuario = u.id "
             + "JOIN pessoa pes ON u.id_pessoa = pes.id "
             + "LEFT JOIN cupom c ON ped.id_cupom = c.id";
    }

    private Entrega buildEntrega(ResultSet rs) throws SQLException {
        // Monta a Pessoa
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

        // Monta o Cupom (LEFT JOIN - pode ser null)
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
        Pedidos ped = new Pedidos(pedDc != null ? pedDc.toLocalDate() : LocalDate.now());
        ped.setId(rs.getLong("ped_id"));
        ped.setUsuario(u);
        ped.setCupom(cupom);
        ped.setStatus(rs.getString("ped_status"));
        ped.setFormaPagamento(rs.getString("forma_pagamento"));
        ped.setValor_total(rs.getDouble("valor_total"));
        Date pedDm = rs.getDate("ped_dm");
        if (pedDm != null) ped.setData_modificacao(pedDm.toLocalDate());

        // Monta a Entrega
        Date eDc = rs.getDate("e_dc");
        Entrega e = new Entrega(eDc != null ? eDc.toLocalDate() : LocalDate.now());
        e.setId(rs.getLong("e_id"));
        e.setPedido(ped);
        e.setCodigo_rastreio(rs.getInt("codigo_rastreio"));
        e.setTransportadora(rs.getString("transportadora"));
        e.setStatus(rs.getString("e_status"));
        Date de = rs.getDate("data_envio");
        if (de != null) e.setData_envio(de.toLocalDate());
        Date det = rs.getDate("data_entrega");
        if (det != null) e.setData_entrega(det.toLocalDate());
        Date eDm = rs.getDate("e_dm");
        if (eDm != null) e.setData_modificacao(eDm.toLocalDate());

        return e;
    }
}
