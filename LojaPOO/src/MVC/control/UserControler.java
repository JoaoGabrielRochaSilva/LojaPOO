/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.control;

import MVC.model.Calendario;
import MVC.model.ItensCarrinho;
import MVC.model.ItensPedido;
import MVC.model.Login;
import MVC.model.Pedidos;
import MVC.view.ProgramaUserView;
import MVC.view.carrinhoView;
import java.util.Scanner;

/**
 *
 * @author joao
 */
public class UserControler {
    private Scanner s;
    private Login log;
    private Calendario cal;
    
    private PedidoControler pedidoControler;
    private CarrinhoControler carrinhoControler;
    private RelatorioControler reControler;
    private ProgramaUserView puv;
    
    public UserControler(Scanner s, Login log, Calendario cal,
                            PedidoControler pedidoControler,
                            CarrinhoControler carrinhoControler,
                            RelatorioControler reControler,
                            ProgramaUserView puv) {
        
        this.s = s;
        this.log = log;
        this.cal = cal;
        this.pedidoControler = pedidoControler;
        this.carrinhoControler = carrinhoControler;
        this.reControler = reControler;
        this.puv = puv;
    
    }
    
    public void Comprar() {
        int qtd;
        long idProduto;
        String res;
        do {
            System.out.println("\nProdutos cadastrados: \n" + pedidoControler.mostrarProdutos());
            idProduto = puv.receberIdProdutoCompra();
            if (pedidoControler.buscarProduto(idProduto) == null || pedidoControler.buscarProduto(idProduto).getAtivo() == false)
                System.out.println("\nEstoque esgotado ou produto nao existe");
        } while (pedidoControler.buscarProduto(idProduto) == null || pedidoControler.buscarProduto(idProduto).getAtivo() == false);

        do {
            System.out.println("\nInforme a quantidade de produto..: ");
            qtd = Integer.parseInt(s.nextLine());
            if (pedidoControler.buscarProduto(idProduto).getQtd_estoque() < qtd)
                System.out.println("\nQuantidade insuficiente no estoque");
        } while (pedidoControler.buscarProduto(idProduto).getQtd_estoque() < qtd);

        res = puv.receberResposta();
        if (res.equals("s")) {
            if (carrinhoControler.buscarCarrinhoAberto(log.getUserLogado()) != null) {
                if (carrinhoControler.adicionarItem(carrinhoControler.buscarCarrinhoAberto(log.getUserLogado()), pedidoControler.buscarProduto(idProduto), qtd)) 
                    System.out.println("\nProduto adicionado com sucesso ...");
                else
                    System.out.println("\nEspaco cheio ... ");   
            }
            else
                System.out.println("\nVoce nao possui carrinhos abertos crie um ...");
        } 
        else {
            Pedidos p = pedidoControler.criarPedidoUserLogado();
            if (p != null) {
                ItensPedido ip = pedidoControler.criarItensPedido(p, pedidoControler.buscarProduto(idProduto), qtd, pedidoControler.buscarProduto(idProduto).getPreco_venda());

                if (ip != null) {
                    pedidoControler.calcularValorPedido(p);
                    System.out.println("\nValor total do pedido (acesse pedidos para pagar)..: R$" + p.getValor_total());
                    pedidoControler.gerarMovimentacaoSaida(ip.getProduto(), qtd, ip.getPreco_unitario());
                }
                else
                    System.out.println("\nNao foi possivel criar itens pedido espaco cheio...");
            }
            else
                System.out.println("\nPedido cancelado espaço cheio...");
            
        }
    }
    
    public void menuPedidosUser() {
        long idPedido;
        int opcaoPedidos;
        do {
            opcaoPedidos = puv.menuPedidos();
            switch (opcaoPedidos) {
                case 1:
                    this.pagarPedidoUsuario();
                    break;
                case 2:
                    this.cancelarPedido();
                    break;
                case 3:
                    String resPedidosUser = pedidoControler.mostrarPedidosUsuario(log.getUserLogado());
                    System.out.println("\nTodos os seus Pedidos:\n" + resPedidosUser);
                    String resPedidosUserPdf = reControler.relatorioPedidosUsuarioPdf(log.getUserLogado());
                    reControler.gerarPdf("Pedidos_" + log.getUserLogado().getLogin(), resPedidosUserPdf, cal.getDia());
                    break;
                case 4:
                    do {
                        System.out.println("\nTodos os seus pedidos:\n" + pedidoControler.mostrarPedidosUsuario(log.getUserLogado()));
                        idPedido = puv.receberIdPedido();
                        if (pedidoControler.buscarPedido(idPedido) == null)
                            System.out.println("\nPedido nao existe");
                    } while (pedidoControler.buscarPedido(idPedido) == null);
                    System.out.println("\nTodos os Itens do Pedido:\n" + pedidoControler.mostrarItensPedido(pedidoControler.buscarPedido(idPedido)));
                    break;
                default:
                    break;

            }
        } while (opcaoPedidos != 5);
    }
    
    public void menuCarrinhoUser() {
        long idCarrinho;
        long idProduto;
        int qtd;
        carrinhoView caview = new carrinhoView();
        int opcaoCarriho;
        do {
            opcaoCarriho = caview.menuCarrinho();
            switch (opcaoCarriho) {
                case 1:
                    this.finalizarCarrinho();
                    break;
                case 2:
                    if (carrinhoControler.buscarCarrinhoAberto(log.getUserLogado()) == null) {
                        
                        if (carrinhoControler.criarCarrinho(log.getUserLogado()))
                            System.out.println("\nCarrinho criado com sucesso ... ");
                        else
                            System.out.println("\nEspaco cheio ... ");
                    }
                    else
                        System.out.println("\nVoce ja tem um carrinho aberto ... ");
                    break;
                case 3:
                    if (carrinhoControler.buscarCarrinhoAberto(log.getUserLogado()) != null) {
                        if (carrinhoControler.cancelarCarrinho(carrinhoControler.buscarCarrinhoAberto(log.getUserLogado())))
                            System.out.println("\nCarrinho cancelado com sucesso ... ");
                        else
                            System.out.println("\nNao foi possivel cancelar");
                    }
                    else
                        System.out.println("\nVoce nao tem carrinho aberto para cancelar ... ");
                    break;
                case 4:
                    int opcaoItensCarrinho;
                    long idItem;
                    do {
                        opcaoItensCarrinho = caview.menuItensCarrinho();
                        switch (opcaoItensCarrinho) {
                            case 1:
                                if (carrinhoControler.buscarCarrinhoAberto(log.getUserLogado()) != null) {
                                    System.out.println("\nTodos os itens do carrinho:\n" + carrinhoControler.mostrarItensCarrinho(carrinhoControler.buscarCarrinhoAberto(log.getUserLogado())));
                                    idItem = caview.receberIdItens();
                                    if (carrinhoControler.removerItem(idItem))
                                        System.out.println("\nItem removido do carrinho ... ");
                                    else
                                        System.out.println("\nEsse item nao existe ... ");
                                }
                                else
                                    System.out.println("\nVoce nao tem carrinho aberto para alterar");
                                break;
                            case 2:
                                if (carrinhoControler.buscarCarrinhoAberto(log.getUserLogado()) != null) {
                                    System.out.println("\nTodos os itens do carrinho:\n" + carrinhoControler.mostrarItensCarrinho(carrinhoControler.buscarCarrinhoAberto(log.getUserLogado())));
           
                                    idItem = caview.receberIdItens();
                                    if (carrinhoControler.buscarItem(idItem)!= null) {
                                        do {
                                            System.out.println("\nProdutos cadastrados: \n" + pedidoControler.mostrarProdutos());
                                    
                                            idProduto = puv.receberIdProdutoCompra();
                                            if (pedidoControler.buscarProduto(idProduto) == null || pedidoControler.buscarProduto(idProduto).getAtivo() == false || carrinhoControler.buscarItem(idItem).getQuantidade() > pedidoControler.buscarProduto(idProduto).getQtd_estoque())
                                                System.out.println("\nEstoque esgotado ou produto nao existe");
                                        } while (pedidoControler.buscarProduto(idProduto) == null || pedidoControler.buscarProduto(idProduto).getAtivo() == false || carrinhoControler.buscarItem(idItem).getQuantidade() > pedidoControler.buscarProduto(idProduto).getQtd_estoque());

                                        if (carrinhoControler.alterarProdutoItem(idItem, pedidoControler.buscarProduto(idProduto)))
                                            System.out.println("\nItem alterado com sucesso ... ");
                                        else
                                            System.out.println("\nNao foi possivel fazer a alteracao");
                                    }  
                                    else
                                        System.out.println("\nEsse item nao existe ... ");
                                }
                                else
                                    System.out.println("\nVoce nao tem carrinho aberto para alterar");
                                break;
                            case 3:
                                if (carrinhoControler.buscarCarrinhoAberto(log.getUserLogado()) != null) {
                                    System.out.println("\nTodos os itens do carrinho:\n" + carrinhoControler.mostrarItensCarrinho(carrinhoControler.buscarCarrinhoAberto(log.getUserLogado())));
     
                                    idItem = caview.receberIdItens();
                                    if (carrinhoControler.buscarItem(idItem)!= null) {
                                        do {
                                            System.out.println("\nInforme a quantidade de produto..: ");
                                            qtd = Integer.parseInt(s.nextLine());
                                            if (carrinhoControler.buscarItem(idItem).getProduto().getQtd_estoque() < qtd)
                                                System.out.println("\nQuantidade insuficiente no estoque");
                                        } while (carrinhoControler.buscarItem(idItem).getProduto().getQtd_estoque() < qtd);

                                        if (carrinhoControler.alterarQuantidadeItem(idItem, qtd))
                                            System.out.println("\nItem alterado com sucesso ... ");
                                        else
                                            System.out.println("\nNao foi possivel fazer a alteracao");
                                    }  
                                    else
                                        System.out.println("\nEsse item nao existe ... ");
                                }
                                else
                                    System.out.println("\nVoce nao tem carrinho aberto para alterar");
                                break;
                            case 4:
                                System.out.println("\nTodos os seus Carrinhos:\n" + carrinhoControler.mostrarCarrinhosUsuario(log.getUserLogado()));
                                idCarrinho = caview.receberId();
                                if(carrinhoControler.buscarCarrinho(idCarrinho) != null)
                                    System.out.println("\nTodos os itens deste carrinho:\n" + carrinhoControler.mostrarItensCarrinho(carrinhoControler.buscarCarrinho(idCarrinho)));
                                else
                                    System.out.println("\nEsse carrinho nao existe");
                                break;
                            default:
                                break;
                        }
                    } while (opcaoItensCarrinho != 5);
                    break;
                case 5:
                    System.out.println("\nTodos os seus Carrinhos:\n" + carrinhoControler.mostrarCarrinhosUsuario(log.getUserLogado()));
                    break;
                default:
                    break;
            }
        } while(opcaoCarriho != 6);
    }
    
    public void finalizarCarrinho() {
        if (carrinhoControler.buscarCarrinhoAberto(log.getUserLogado()) != null && carrinhoControler.carrinhoTemItens(log.getUserLogado(), carrinhoControler.buscarCarrinhoAberto(log.getUserLogado()))) {
            if (carrinhoControler.carrinhoTemEstoque(carrinhoControler.buscarCarrinhoAberto(log.getUserLogado()))) {
                Pedidos p = pedidoControler.criarPedidoUserLogado();

                for (ItensCarrinho itensCarrinho : carrinhoControler.listarTodosItensCarrinho()) {
                    if (itensCarrinho != null && itensCarrinho.getCarrinho().getId() == carrinhoControler.buscarCarrinhoAberto(log.getUserLogado()).getId()) {
                        if (itensCarrinho.getProduto().getAtivo() == true && itensCarrinho.getProduto().getQtd_estoque() >= itensCarrinho.getQuantidade()) {
                            ItensPedido ip = pedidoControler.criarItensPedido(p, itensCarrinho.getProduto(), itensCarrinho.getQuantidade(), itensCarrinho.getPreco_unitario());
                            
                            pedidoControler.gerarMovimentacaoSaida(ip.getProduto(), ip.getQuantidade(), ip.getPreco_unitario());
                        }
                        else
                            System.out.println("\nProduto " + itensCarrinho.getProduto().getNome() + " sem quantidade suficiente");
                    }
                }

                pedidoControler.calcularValorPedido(p);
                
                carrinhoControler.fecharCarrinho(carrinhoControler.buscarCarrinhoAberto(log.getUserLogado()));
                System.out.println("\nValor total do pedido (acesse pedidos para pagar)..: R$" + p.getValor_total());
            }
            else
                System.out.println("\nProdutos sem estoque o suficiente...");
        }
        else
            System.out.println("Voce nao possui carrinho aberto ou o carrinho esta vazio ... ");
    }
    
    public void pagarPedidoUsuario() {
        long idPedido;
        String forPagamento;

        do {
            System.out.println("\nTodos os seus pedidos:\n" + pedidoControler.mostrarPedidosUsuario(log.getUserLogado()));
 
            idPedido = puv.receberIdPedido();

            if (pedidoControler.buscarPedido(idPedido) == null || !pedidoControler.buscarPedido(idPedido).getStatus().equals("Criado"))
                System.out.println("\nPedido nao existe ou ja esta pago");

        } while (idPedido != 0 && pedidoControler.buscarPedido(idPedido) == null || (pedidoControler.buscarPedido(idPedido) != null && !pedidoControler.buscarPedido(idPedido).getStatus().equals("Criado")));
        
        if (idPedido != 0) {
            Pedidos pedido = pedidoControler.buscarPedido(idPedido);

            System.out.println("\nInforme a forma de pagamento...:");
            forPagamento = s.nextLine();
            pedido.setFormaPagamento(forPagamento);

            pedidoControler.aplicarCupom(pedido, pedido.getValor_total());
            pedidoControler.calcularValorPedido(pedido);
            pedidoControler.alterarFormaPagamento(pedido);
            pedidoControler.pagarPedido(pedido);

            System.out.println("\nPedido pago com sucesso...: R$" + pedido.getValor_total());
        }
        else
            System.out.println("\nPagamento cancelado...");
}
    
    public void cancelarPedido() {
        long idPedido;
        do {
            System.out.println("\nTodos os seus pedidos:\n" + pedidoControler.mostrarPedidosUsuario(log.getUserLogado()));
            idPedido = puv.receberIdPedido();
            if (idPedido != 0 && pedidoControler.buscarPedido(idPedido) == null || (pedidoControler.buscarPedido(idPedido) != null && "Entregue".equals(pedidoControler.buscarPedido(idPedido).getStatus())) || (pedidoControler.buscarPedido(idPedido) != null && "Cancelado".equals(pedidoControler.buscarPedido(idPedido).getStatus())))
                System.out.println("\nPedido nao existe ou ja foi entregue/cancelado");
        } while (idPedido != 0 && pedidoControler.buscarPedido(idPedido) == null || (pedidoControler.buscarPedido(idPedido) != null && "Entregue".equals(pedidoControler.buscarPedido(idPedido).getStatus())) || (pedidoControler.buscarPedido(idPedido) != null && "Cancelado".equals(pedidoControler.buscarPedido(idPedido).getStatus())));
        if (idPedido != 0) {
            pedidoControler.cancelarPedidoCompleto(idPedido);
            System.out.println("\nPedido cancelado com sucesso...");
        }           
    }
    
    public void menuEntregasUser() {
        int opcaoEntrega;
        do {
            opcaoEntrega = puv.menuEntregas();
            switch (opcaoEntrega) {
                case 1:
                    int cod;
                    System.out.println("Informe o codigo de rastreio...:");
                    cod = Integer.parseInt(s.nextLine());
                    if (pedidoControler.buscarEntregaPorCodigo(cod) != null)
                        System.out.println("\nEntrega Encontrada:\n" + pedidoControler.buscarEntregaPorCodigo(cod));
                    else
                        System.out.println("\nEssa entrega nao existe ou codigo errado ...");
                    break;
                case 2:
                    System.out.println("\nSuas entregas:\n" + pedidoControler.mostrarEntregasUsuario(log.getUserLogado()));
                default:
                    break;
            }
        } while (opcaoEntrega != 3);
    }
    
    public void ProgramaUser() {
        int opcaoProUser = 0;
        do{
            opcaoProUser = puv.menuUser();
            switch (opcaoProUser) {
                case 1:
                    this.Comprar();
                    break;
                case 2:
                    this.menuPedidosUser();
                    break;
                case 3:
                    this.menuCarrinhoUser();
                    break;
                case 4:
                    this.menuEntregasUser();
                    break;
                default:
                    break;
            }
        } while(opcaoProUser != 5);
    }
}
