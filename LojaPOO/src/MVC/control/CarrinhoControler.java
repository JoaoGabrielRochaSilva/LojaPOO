/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.control;

import MVC.model.Calendario;
import MVC.model.Carrinho;
import MVC.model.CarrinhoDAO;
import MVC.model.ItensCarrinho;
import MVC.model.ItensCarrinhoDAO;
import MVC.model.Produto;
import MVC.model.Usuario;
import java.util.List;   // NOVO: Collections Framework

/**
 *
 * @author joao
 */
public class CarrinhoControler {

    private Calendario cal;

    private CarrinhoDAO cadao;
    private ItensCarrinhoDAO icdao;

    public CarrinhoControler(Calendario cal,
                             CarrinhoDAO cadao,
                             ItensCarrinhoDAO icdao) {

        this.cal = cal;
        this.cadao = cadao;
        this.icdao = icdao;
    }

    // ==================== CARRINHO ====================

    public Carrinho buscarCarrinhoAberto(Usuario u) {
        return cadao.getCarrinhoAberto(u);
    }

    public Carrinho buscarCarrinho(long id) {
        return cadao.getCarrinho(id);
    }

    public String mostrarCarrinhosUsuario(Usuario u) {
        return cadao.mostrarTodos(u);
    }

    public boolean criarCarrinho(Usuario u) {
        Carrinho c = new Carrinho(cal.getDia());
        c.setUsuario(u);
        c.setStatus("Aberto");

        return cadao.adiciona(c);
    }

    public boolean cancelarCarrinho(Carrinho c) {
        return cadao.alterarStatus(c.getId(), "Cancelado", cal.getDia());
    }

    public boolean fecharCarrinho(Carrinho c) {
        return cadao.alterarStatus(c.getId(), "Fechado", cal.getDia());
    }

    // ==================== ITENS ====================

    public boolean adicionarItem(Carrinho c, Produto p, int qtd) {
        ItensCarrinho ic = new ItensCarrinho(cal.getDia());
        ic.setCarrinho(c);
        ic.setProduto(p);
        ic.setQuantidade(qtd);
        ic.setPreco_unitario(p.getPreco_venda());

        return icdao.adiciona(ic);
    }

    public boolean removerItem(long idItem) {
        return icdao.remove(idItem);
    }

    public ItensCarrinho buscarItem(long idItem) {
        return icdao.getItensCarrinho(idItem);
    }

    public boolean alterarProdutoItem(long idItem, Produto p) {
        return icdao.alterarProduto(idItem, p, cal.getDia());
    }

    public boolean alterarQuantidadeItem(long idItem, int qtd) {
        return icdao.alterarQuantidade(idItem, qtd, cal.getDia());
    }

    public String mostrarItensCarrinho(Carrinho c) {
        return icdao.mostrarTodos(c);
    }

    // ALTERADO: retorna List<ItensCarrinho> em vez de ItensCarrinho[] (Collections Framework)
    public List<ItensCarrinho> listarTodosItensCarrinho() {
        return icdao.getItensCarrinhos();
    }

    // ==================== VALIDAÇÕES ====================

    public boolean carrinhoTemItens(Usuario u, Carrinho c) {
        return icdao.verificarCarrinhoVazio(u, c);
    }

    public boolean carrinhoTemEstoque(Carrinho c) {
        return icdao.ExisteEstoque(c);
    }
}
