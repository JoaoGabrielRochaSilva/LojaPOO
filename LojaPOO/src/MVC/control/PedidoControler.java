/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.control;

import MVC.model.Calendario;
import MVC.model.CupomDAO;
import MVC.model.Entrega;
import MVC.model.EntregaDAO;
import MVC.model.ItensPedido;
import MVC.model.ItensPedidoDAO;
import MVC.model.Login;
import MVC.model.MovimentacaoEstoque;
import MVC.model.MovimentacaoEstoqueDAO;
import MVC.model.Pedidos;
import MVC.model.PedidosDAO;
import MVC.model.Produto;
import MVC.model.ProdutoDAO;
import MVC.model.Usuario;
import java.util.List;   // NOVO: Collections Framework
import java.util.Scanner;

/**
 *
 * @author joao
 */
public class PedidoControler {
    
    private Scanner s;
    private Login log;
    private Calendario cal;
    
    private ProdutoDAO prodao;
    private PedidosDAO pedao;
    private ItensPedidoDAO ipdao;
    private EntregaDAO edao;
    private CupomDAO cudao;
    private MovimentacaoEstoqueDAO mdao;
    
    public PedidoControler(Scanner s, Login log, Calendario cal,
                          ProdutoDAO prodao,
                          PedidosDAO pedao,
                          ItensPedidoDAO ipdao,
                          EntregaDAO edao,
                          CupomDAO cudao,
                          MovimentacaoEstoqueDAO mdao) {
    
        this.s = s;
        this.log = log;
        this.cal = cal;
        
        this.prodao = prodao;
        this.pedao = pedao;
        this.ipdao = ipdao;
        this.edao = edao;
        this.cudao = cudao;
        this.mdao = mdao;
    }
   
    
    public void gerarMovimentacaoSaida(Produto p, int qtd, double valor) {
        MovimentacaoEstoque novaMe = new MovimentacaoEstoque(cal.getDia());
        novaMe.setProduto(p);
        novaMe.setQuantidade(qtd);
        novaMe.setTipo("Saida");
        novaMe.setValor_unitario(valor);
        mdao.adiciona(novaMe);
        int calc = p.getQtd_estoque() - qtd;
        prodao.alterarQtdEstoque(p.getId(), calc, cal.getDia());
    }
    
    public void gerarMovimentacaoEntrada(Produto p, int qtd, double valor) {
        MovimentacaoEstoque novaMe = new MovimentacaoEstoque(cal.getDia());
        novaMe.setProduto(p);
        novaMe.setQuantidade(qtd);
        novaMe.setTipo("Entrada");
        novaMe.setValor_unitario(valor);
        mdao.adiciona(novaMe);
        int calc = p.getQtd_estoque() + qtd;
        prodao.alterarQtdEstoque(p.getId(), calc, cal.getDia());
    }
    
    public void aplicarCupom(Pedidos p, double valor) {
        String res;
        int codCupom;
        System.out.println("\nDeseja aplicar algun cupom? (s ou n)");
        res = s.nextLine();  
        if (res.equals("s")) {
            do {
                  System.out.println("\nTodos os cupons cadastrados:\n" + cudao.mostrarTodos());
                  System.out.println("\n Informe codigo do cupom que deseja aplicar");
                  codCupom = Integer.parseInt(s.nextLine());
                  if (cudao.getCupom(codCupom) == null)
                      System.out.println("\nCupom nao existe ou expirado");
                  else if (cudao.getCupom(codCupom).getAtivo() == false)
                      System.out.println("\nCupom expirado...");
                  else if (cudao.getCupom(codCupom).getValor_minimo() > valor)
                      System.out.println("\nValor minimo insuficiente");

            } while (codCupom != 0 && cudao.getCupom(codCupom) == null || (cudao.getCupom(codCupom) != null && cudao.getCupom(codCupom).getAtivo() == false) || (cudao.getCupom(codCupom) != null &&  cudao.getCupom(codCupom).getValor_minimo() > valor));
            if (codCupom != 0)
              p.setCupom(cudao.getCupom(codCupom));
            else
                System.out.println("\nAplicar cupom cancelado...");
        }
    }
    
    public Pedidos criarPedidoUserLogado() {
        Pedidos p = new Pedidos(cal.getDia());
        p.setUsuario(log.getUserLogado());
        if(pedao.adiciona(p))
            return p;
        else
            return null;
    }
    
    public ItensPedido criarItensPedido(Pedidos pe, Produto pro, int qtd, double valor) {
        ItensPedido ip = new ItensPedido(cal.getDia());
        ip.setPedido(pe);
        ip.setProduto(pro);
        ip.setQuantidade(qtd);
        ip.setPreco_unitario(valor);
        ip.setSub_total(valor * qtd);
        
        if (ipdao.adiciona(ip))
            return ip;
        else
            return null;
    }

    // Serviços de produtos
    public String mostrarProdutos() {
        return prodao.mostrarTodos();
    }

    public Produto buscarProduto(long id) {
        return prodao.getProduto(id);
    }
    
    // Serviços de Pedidos 
    public String mostrarPedidosUsuario(Usuario u) {
        return pedao.mostrarTodos(u);
    }

    public Pedidos buscarPedido(long id) {
        return pedao.getPedido(id);
    }

    public void calcularValorPedido(Pedidos p) {
        // ipdao.getItensPedidos() agora retorna List<ItensPedido> (Collections Framework)
        pedao.calcVltotal(p, cal.getDia(), ipdao.getItensPedidos(), log.getUserLogado());
    }

    public void pagarPedido(Pedidos p) {
        pedao.alterarStatus(p.getId(), "Pago", cal.getDia());
    }

    public void alterarFormaPagamento(Pedidos p) {
        pedao.alterarForPagamento(p.getId(), p.getFormaPagamento(), cal.getDia());
    }

    public void cancelarPedido(Pedidos p) {
        pedao.alterarStatus(p.getId(), "Cancelado", cal.getDia());
    }
    
    // Serviços de Itens Pedido
    public String mostrarItensPedido(Pedidos p) {
        return ipdao.mostrarTodos(p);
    }

    // ALTERADO: retorna List<ItensPedido> em vez de ItensPedido[] (Collections Framework)
    public List<ItensPedido> listarTodosItensPedido() {
        return ipdao.getItensPedidos();
    }
    
    // Serviços de Entregas
    public Entrega buscarEntregaPorCodigo(int cod) {
        return edao.getEntregaCod(cod);
    }

    public boolean existeEntregaPedido(Pedidos p) {
        return edao.ExistePedido(p);
    }

    public Entrega buscarEntregaPedido(Pedidos p) {
        return edao.getEntrega(p);
    }

    public void cancelarEntrega(Entrega e) {
        edao.alterarStatus(e.getId(), "Cancelada", cal.getDia());
    }

    public String mostrarEntregasUsuario(Usuario u) {
        return edao.mostrarTodos(u);
    }
    
    // Cancelar Pedido e Entrega
    public void cancelarPedidoCompleto(long idPedido) {
        Pedidos p = buscarPedido(idPedido);

        cancelarPedido(p);

        if (existeEntregaPedido(p))
            cancelarEntrega(buscarEntregaPedido(p));

        // ALTERADO: ipdao.getItensPedidos() retorna List<ItensPedido>
        // Comparacao por id para funcionar corretamente com objetos carregados do banco
        for (ItensPedido ip : ipdao.getItensPedidos()) {
            if (ip != null && ip.getPedido().getId() == p.getId())
                gerarMovimentacaoEntrada(ip.getProduto(), ip.getQuantidade(), ip.getPreco_unitario());
        }
    }
}
