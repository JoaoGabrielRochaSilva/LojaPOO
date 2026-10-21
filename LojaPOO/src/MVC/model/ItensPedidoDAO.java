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
public class ItensPedidoDAO {

    // ==================== ADICIONAR ====================

    public boolean adiciona(ItensPedido p) {
        String sql = "INSERT INTO itens_pedido (id_pedido, id_produto, quantidade, preco_unitario, sub_total, data_criacao, data_modificacao) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, p.getPedido().getId());
            stmt.setLong(2, p.getProduto().getId());
            stmt.setInt(3, p.getQuantidade());
            stmt.setDouble(4, p.getPreco_unitario());
            stmt.setDouble(5, p.getSub_total());
            stmt.setDate(6, Date.valueOf(p.getData_criacao()));
            stmt.setDate(7, Date.valueOf(p.getData_modificacao()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                p.setId(rs.getLong(1));

            return true;

        } catch (SQLException e) {
            System.out.println("\nErro ao adicionar item de pedido: " + e.getMessage());
            return false;
        }
    }

    // ==================== REMOVER ====================

    public boolean remove(long id) {
        String sql = "DELETE FROM itens_pedido WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao remover item de pedido: " + e.getMessage());
            return false;
        }
    }

    // ==================== ALTERAR ====================

    public boolean alterarPedido(long id, Pedidos novoPedido, LocalDate data) {
        String sql = "UPDATE itens_pedido SET id_pedido = ?, data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, novoPedido.getId());
            stmt.setDate(2, Date.valueOf(data));
            stmt.setLong(3, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar pedido do item: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarProduto(long id, Produto novoProduto, LocalDate data) {
        String sql = "UPDATE itens_pedido SET id_produto = ?, preco_unitario = ?, "
                   + "sub_total = (quantidade * ?), data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, novoProduto.getId());
            stmt.setDouble(2, novoProduto.getPreco_venda());
            stmt.setDouble(3, novoProduto.getPreco_venda());
            stmt.setDate(4, Date.valueOf(data));
            stmt.setLong(5, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar produto do item: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarQuantidade(long id, int novaQuantidade, LocalDate data) {
        String sql = "UPDATE itens_pedido SET quantidade = ?, "
                   + "sub_total = (preco_unitario * ?), data_modificacao = ? WHERE id = ?";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, novaQuantidade);
            stmt.setDate(3, Date.valueOf(data));
            stmt.setLong(4, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nErro ao alterar quantidade do item: " + e.getMessage());
            return false;
        }
    }

    // ==================== BUSCAR ====================

    public ItensPedido getItensPedido(Pedidos p) {
        String sql = buildSelectSql() + " WHERE ip.id_pedido = ? LIMIT 1";

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, p.getId());
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                return buildItensPedido(rs, p);

        } catch (SQLException e) {
            System.out.println("\nErro ao buscar item de pedido: " + e.getMessage());
        }
        return null;
    }

    // ==================== LISTAR ====================

    // Retorna List<ItensPedido> para uso no PedidoControler (Collections Framework)
    public List<ItensPedido> getItensPedidos() {
        String sql = buildSelectSql();
        List<ItensPedido> lista = new ArrayList<>();

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                lista.add(buildItensPedido(rs, null));

        } catch (SQLException e) {
            System.out.println("\nErro ao listar itens de pedido: " + e.getMessage());
        }
        return lista;
    }

    public String mostrarTodos(Pedidos p) {
        String sql = buildSelectSql() + " WHERE ip.id_pedido = ?";
        StringBuilder builder = new StringBuilder("");

        try (Connection conexao = ConectionFactory.getConection();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, p.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
                builder.append("\n").append(buildItensPedido(rs, p));

        } catch (SQLException e) {
            System.out.println("\nErro ao mostrar itens de pedido: " + e.getMessage());
        }
        return builder.toString();
    }

    // ==================== HELPERS ====================

    private String buildSelectSql() {
        return "SELECT ip.id AS ip_id, ip.quantidade, ip.preco_unitario, ip.sub_total, "
             + "ip.data_criacao AS ip_dc, ip.data_modificacao AS ip_dm, "
             + "ip.id_pedido, ip.id_produto, "
             + "prod.id AS prod_id, prod.nome, prod.descricao, prod.preco_venda, "
             + "prod.qtd_estoque, prod.ativo, "
             + "prod.data_criacao AS prod_dc, prod.data_modificacao AS prod_dm "
             + "FROM itens_pedido ip "
             + "JOIN produto prod ON ip.id_produto = prod.id";
    }

    // pedidoRef: se nao for null, reutiliza o objeto passado (evita SELECT extra)
    private ItensPedido buildItensPedido(ResultSet rs, Pedidos pedidoRef) throws SQLException {
        // Monta o Produto
        Date prodDc = rs.getDate("prod_dc");
        Produto prod = new Produto(prodDc != null ? prodDc.toLocalDate() : LocalDate.now());
        prod.setId(rs.getLong("prod_id"));
        prod.setNome(rs.getString("nome"));
        prod.setDescricao(rs.getString("descricao"));
        prod.setPreco_venda(rs.getDouble("preco_venda"));
        prod.setQtd_estoque(rs.getInt("qtd_estoque"));
        prod.setAtivo(rs.getBoolean("ativo"));
        Date prodDm = rs.getDate("prod_dm");
        if (prodDm != null) prod.setData_modificacao(prodDm.toLocalDate());

        // Recupera o Pedido: usa o objeto passado ou faz consulta ao banco
        Pedidos pedido = pedidoRef;
        if (pedido == null) {
            long idPedido = rs.getLong("id_pedido");
            pedido = new PedidosDAO().getPedido(idPedido);
        }

        // Monta o ItensPedido
        Date ipDc = rs.getDate("ip_dc");
        ItensPedido ip = new ItensPedido(ipDc != null ? ipDc.toLocalDate() : LocalDate.now());
        ip.setId(rs.getLong("ip_id"));
        ip.setPedido(pedido);
        ip.setProduto(prod);
        ip.setQuantidade(rs.getInt("quantidade"));
        ip.setPreco_unitario(rs.getDouble("preco_unitario"));
        ip.setSub_total(rs.getDouble("sub_total"));
        Date ipDm = rs.getDate("ip_dm");
        if (ipDm != null) ip.setData_modificacao(ipDm.toLocalDate());

        return ip;
    }
}
