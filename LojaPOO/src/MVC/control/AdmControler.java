/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.control;

import MVC.model.Calendario;
import MVC.model.CarrinhoDAO;
import MVC.model.Cupom;
import MVC.model.CupomDAO;
import MVC.model.EntregaDAO;
import MVC.model.ItensCarrinhoDAO;
import MVC.model.ItensPedidoDAO;
import MVC.model.Login;
import MVC.model.MovimentacaoEstoque;
import MVC.model.MovimentacaoEstoqueDAO;
import MVC.model.PedidosDAO;
import MVC.model.Pessoa;
import MVC.model.PessoaDAO;
import MVC.model.Produto;
import MVC.model.ProdutoDAO;
import MVC.model.Usuario;
import MVC.model.UsuarioDAO;
import MVC.view.ProgramaAdmView;
import MVC.view.cupomView;
import MVC.view.entregaView;
import MVC.view.movimentacaoEstoqueView;
import MVC.view.pessoaView;
import MVC.view.produtoView;
import MVC.view.usuarioView;
import java.time.LocalDate;
import java.util.Scanner;
import MVC.view.relatorioView;

/**
 *
 * @author joao
 */
public class AdmControler {
    private Scanner s;
    private Login log;
    private Calendario cal;
    private EventosAutomaticos eventos;

    private ProgramaAdmView pav;
    private usuarioView uview;

    private PessoaDAO pdao;
    private UsuarioDAO udao;
    private ProdutoDAO prodao;
    private MovimentacaoEstoqueDAO mdao;
    private CupomDAO cudao;
    private PedidosDAO pedao;
    private ItensPedidoDAO ipdao;
    private EntregaDAO edao;
    private CarrinhoDAO cadao;
    private ItensCarrinhoDAO icdao;
    private RelatorioControler relatorioControler;

    public AdmControler(Scanner s, Login log, Calendario cal, EventosAutomaticos eventos,
                         ProgramaAdmView pav, usuarioView uview,
                         PessoaDAO pdao, UsuarioDAO udao, ProdutoDAO prodao,
                         MovimentacaoEstoqueDAO mdao, CupomDAO cudao,
                         PedidosDAO pedao, ItensPedidoDAO ipdao,
                         EntregaDAO edao, CarrinhoDAO cadao, ItensCarrinhoDAO icdao, RelatorioControler relatorioControler) {

        this.s = s;
        this.log = log;
        this.cal = cal;
        this.eventos = eventos;
        this.pav = pav;
        this.uview = uview;

        this.pdao = pdao;
        this.udao = udao;
        this.prodao = prodao;
        this.mdao = mdao;
        this.cudao = cudao;
        this.pedao = pedao;
        this.ipdao = ipdao;
        this.edao = edao;
        this.cadao = cadao;
        this.icdao = icdao;
        this.relatorioControler = relatorioControler;
    }
    
    public void criarUsuario(usuarioView uview) {
        long id;
        System.out.println("Informe o id da pessoa...: ");
        id = Long.parseLong(s.nextLine());
        Pessoa p = pdao.getPessoa(id);
        
        Usuario user = new Usuario(cal.getDia());
        user.setPessoa(p);
        user.setLogin(uview.receberLogin());
        user.setSenha(uview.receberSenha());

        if (p != null) {
            if (udao.adiciona(user))
                System.out.println("\nUsuario adicionado com sucesso");
            else
                System.out.println("\nEspaço cheio ...");
        }
        else
            System.out.println("Pessoa nao encontrada");
    }
    
    public void menuProdutosAdm() {
        long id;
        int opcaoProduto;
        produtoView proview = new produtoView();
        // loop da opcao produto
        do {
            opcaoProduto = proview.menuProduto();
            switch (opcaoProduto) {
                case 1:
                    Produto p = new Produto(cal.getDia());
                    p.setNome(proview.receberNome());
                    p.setDescricao(proview.receberDescricao());
                    p.setPreco_venda(proview.receberPreco());
                    
                    if (prodao.adiciona(p))
                        System.out.println("\nProduto adicionado com sucesso");
                    else
                        System.out.println("\nEspaço cheio ...");
                    break;
                case 2: 
                    id = proview.receberId();
                    if (prodao.AtivaDesativa(id, cal.getDia()))
                        System.out.println("\nProduto alterado com sucesso");
                    else
                        System.out.println("\nProduto nao encontrado");
                    break;
                case 3:
                    int opcaoAlteraProduto;
                    // loop da opcao Alterar Produto
                    do {
                        opcaoAlteraProduto = proview.menuProdutoAlterar();
                        switch (opcaoAlteraProduto) {
                            case 1:
                                String novoNome;
                                id = proview.receberId();

                                System.out.println("\nInforme o novo nome...: ");
                                novoNome = s.nextLine();

                                if (prodao.alterarNome(id, novoNome, cal.getDia()))
                                    System.out.println("\nNome alterado com sucesso");
                                else
                                    System.out.println("\nProduto nao encontrado");
                                break;
                            case 2:
                                String novaDesc;
                                id = proview.receberId();

                                System.out.println("\nInforme a nova Descricao...: ");
                                novaDesc = s.nextLine();

                                if (prodao.alterarDescricao(id, novaDesc, cal.getDia()))
                                    System.out.println("\nDescricao alterada com sucesso");
                                else
                                    System.out.println("\nProduto nao encontrado");
                                break;
                            case 3:
                                double novoPreco;
                                id = proview.receberId();

                                System.out.println("\nInforme o novo Preco...: ");
                                novoPreco = Double.parseDouble(s.nextLine());

                                if (prodao.alterarPreco(id, novoPreco, cal.getDia()))
                                    System.out.println("\nPreco  alterado com sucesso");
                                else
                                    System.out.println("\nProduto nao encontrado");
                                break;

                        }
                    } while (opcaoAlteraProduto != 4);
                    break;
                case 4:
                    System.out.println("\n\nTodas os produtos cadastrados\n\n" + prodao.mostrarTodos());
                    break;
                default:
                    break;

            }
        } while (opcaoProduto != 5);
    }
    
    public void menuCuponsAdm() {
        long id;
        int opcaoCupom;
        cupomView cuview = new cupomView();
        // loop da opcao produto
        do {
            opcaoCupom = cuview.menuCupom();
            switch (opcaoCupom) {
                case 1:
                    Cupom cup = new Cupom(cal.getDia());
                    cup.setCodigo(cuview.receberCodigo());
                    cup.setTipo_desconto(cuview.receberTipo());
                    cup.setData_validade(cuview.receberDataValidade());
                    cup.setValor_desconto(cuview.receberVlDesconto());
                    cup.setValor_minimo(cuview.receberVlMinimo());
                    
                    if (cudao.adiciona(cup))
                        System.out.println("\nCupom adicionado com sucesso");
                    else
                        System.out.println("\nEspaço cheio ...");
                    break;
                case 2:
                    id = cuview.receberId();

                    if (cudao.AtivaDesativa(id, cal.getDia()))
                        System.out.println("\nCupom alterado");
                    else
                        System.out.println("\nCupom nao encontrado");
                    break;
                case 3:
                    int opcaoAlteraCupom;
                    // loop da opcao Alterar Produto
                    do {
                        opcaoAlteraCupom = cuview.menuCupomAlterar();
                        switch (opcaoAlteraCupom) {
                            case 1:
                                int novoCod;
                                id = cuview.receberId();

                                System.out.println("Informe o novo codigo...: ");
                                novoCod = Integer.parseInt(s.nextLine());

                                if (cudao.alterarCodigo(id, novoCod, cal.getDia()))
                                    System.out.println("\nCodigo alterado com sucesso");
                                else
                                    System.out.println("\nCupom nao encontrado ou ja existe um com esse codigo");
                                break;
                            case 2:
                                String novoTipo;
                                id = cuview.receberId();

                                System.out.println("Informe o novo tipo (percentual ou fixo)...: ");
                                novoTipo = s.nextLine();

                                if (cudao.alterarTipoDesconto(id, novoTipo, cal.getDia()))
                                    System.out.println("\nTipo alterado com sucesso");
                                else
                                    System.out.println("\nCupom nao encontrado");
                                break;
                            case 3:
                                double novoValor;
                                id = cuview.receberId();

                                System.out.println("\nInforme o novo valor de desconto...: ");
                                novoValor = Double.parseDouble(s.nextLine());

                                if (cudao.alterarValorDesconto(id, novoValor, cal.getDia()))
                                    System.out.println("\nValor  alterado com sucesso");
                                else
                                    System.out.println("\nCupom nao encontrado");
                                break;
                            case 4:
                                double novoValorMin;
                                id = cuview.receberId();

                                System.out.println("\nInforme o novo valor minimo...: ");
                                novoValorMin = Double.parseDouble(s.nextLine());

                                if (cudao.alterarValorMinimo(id, novoValorMin, cal.getDia()))
                                    System.out.println("\nValor minimo alterado com sucesso");
                                else
                                    System.out.println("\nCupom nao encontrado");
                                break;
                            case 5:
                                LocalDate novaData;
                                id = cuview.receberId();

                                System.out.println("\nInforme a nova data de validade...: ");
                                novaData = LocalDate.parse(s.nextLine());

                                if (cudao.alterarDataValidade(id, novaData, cal.getDia()))
                                    System.out.println("\nData de validade alterada com sucesso");
                                else
                                    System.out.println("\nCupom nao encontrado");
                                break;
                        }
                    } while (opcaoAlteraCupom != 6);
                    break;
                case 4:
                    System.out.println("\n\nTodos os cupoms cadastrados\n\n" + cudao.mostrarTodos());
                    break;
                default:
                    break;

            }
        } while (opcaoCupom != 5);
    }
    
    public void menuEstoqueAdm() {
        long id;
        int opcaoEstoque;
        movimentacaoEstoqueView mview = new movimentacaoEstoqueView();
        // loop da opcao produto
        do {
            opcaoEstoque = mview.menuEstoque();
            switch (opcaoEstoque) {
                case 1:
                    System.out.println("Informe o id do produto que foi movimentado");
                    id = Long.parseLong(s.nextLine());
                    Produto p = prodao.getProduto(id);

                    if (p != null) {
                        MovimentacaoEstoque meAdd = new MovimentacaoEstoque(cal.getDia());
                        meAdd.setProduto(p);
                        meAdd.setTipo(mview.receberTipo());
                        meAdd.setQuantidade(mview.receberQtdProduto());
                        meAdd.setValor_unitario(p.getPreco_venda());

                        if (meAdd.getTipo().equals("Saida")) {

                            if (prodao.getProduto(id).getQtd_estoque() >= meAdd.getQuantidade()) {
                                int calculo = prodao.getProduto(id).getQtd_estoque() - meAdd.getQuantidade();

                                prodao.alterarQtdEstoque(id, calculo, cal.getDia());

                                if (mdao.adiciona(meAdd))
                                    System.out.println("\nMovimentacao adicionada com sucesso");
                                else
                                    System.out.println("\nEspaço cheio ...");
                            }
                            else
                                System.out.println("\nQuantidade em estoque insuficiente ...");
                        }
                        else if (meAdd.getTipo().equals("Entrada")) {
                            int calculo = prodao.getProduto(id).getQtd_estoque() + meAdd.getQuantidade();
                            prodao.alterarQtdEstoque(id, calculo, cal.getDia());

                            if (mdao.adiciona(meAdd))
                                System.out.println("\nMovimentacao adicionada com sucesso");
                            else
                                System.out.println("\nEspaço cheio ...");
                        }
                        else if (meAdd.getTipo().equals("Ajuste")) {
                            int calculo = meAdd.getQuantidade() - prodao.getProduto(id).getQtd_estoque();

                            prodao.alterarQtdEstoque(id, meAdd.getQuantidade(), cal.getDia());
                            meAdd.setQuantidade(calculo);

                            if (mdao.adiciona(meAdd))
                                System.out.println("\nMovimentacao adicionada com sucesso");
                            else
                                System.out.println("\nEspaço cheio ...");
                        }
                    }
                    else
                        System.out.println("Produto nao encontrado");

                    break;
                case 2:
                    id = mview.receberId();

                    if (mdao.getMov(id) != null && !mdao.getMov(id).getTipo().equals("Ajuste") && mdao.remove(id, cal.getDia()))
                        System.out.println("\nMovimentacao Removida");
                    else
                        System.out.println("\nMovimentacao nao encontrada ou eh um ajuste");
                    break;
                case 3:
                    System.out.println("\n\nTodas as movimentacoes cadastradas\n\n" + mdao.mostrarTodos());
                    break;
                default:
                    break;

            }
        } while (opcaoEstoque != 4);
    }
    
    public void menuCalendarioAdm() {
        int opcaoCal;
        int qtd;
        do {
            opcaoCal = pav.calendario();
            switch (opcaoCal) {
                case 1:
                    System.out.println("\n\nData atual...: " + cal.getDia() + "\n\n");
                    break;
                case 2:
                    System.out.println("Informe a quantidade de dias que quer passar...:");
                    qtd = Integer.parseInt(s.nextLine());
                    if (qtd > 0) {
                        for (int n=1; n <= qtd; n++) {
                            cal.passarDia();
                            eventos.verificarCarrinhos(cal.getDia(), cadao.getCarrinhos(), cadao);
                            eventos.verificarPedidos(cal.getDia(), pedao.getPedidos(), edao, pedao);
                            eventos.verificarEntregas(cal.getDia(), edao.getEntregas(), edao);
                            eventos.verificarCupoms(cal.getDia(), cudao.getCupoms(), cudao);
                        }
                    }
                    else
                        System.out.println("\nQuantidade invalida ... ");

                    System.out.println("\n\nPassou "+ qtd +" dias ...\n\n");
                    break;
                default:
                    break;
            }
        }while(opcaoCal != 3);
    }
    
    public void menuEntregasAdm() {
        long id;
        int opcaoEntrega;
        entregaView eview = new entregaView();
        do {
           opcaoEntrega = eview.menuEntrega();
            switch (opcaoEntrega) {
                case 1:
                    int opcaoMod;
                    do {
                       opcaoMod = eview.menuModificarEntrega();
                        switch (opcaoMod) {
                            case 1:
                                id = eview.receberId();
                                if (edao.getEntregaId(id) != null && !edao.getEntregaId(id).getStatus().equals("Entregue") && !edao.getEntregaId(id).getStatus().equals("Cancelada")) {
                                    int cod = eview.receberCodigo();
                                    if (edao.alterarCod(id, cod, cal.getDia()))
                                        System.out.println("\nCodigo alterado com sucesso");
                                    else
                                        System.out.println("\nJa existe uma entrega com este codigo");
                                }
                                else
                                    System.out.println("\nEssa entrega nao existe ou ja foi entregue");
                                break;
                            case 2:
                                id = eview.receberId();
                                if (edao.getEntregaId(id) != null && !edao.getEntregaId(id).getStatus().equals("Entregue") && !edao.getEntregaId(id).getStatus().equals("Cancelada")) {
                                    String novaT = eview.receberTransportadora();
                                    if (edao.alterarTransportadora(id, novaT, cal.getDia()))
                                        System.out.println("\nTransportadora alterada com sucesso");
                                    else
                                        System.out.println("\nNao foi possivel alterar");
                                }
                                else
                                    System.out.println("\nEssa entrega nao existe ou ja foi entregue");
                                break;
                            default:
                                break;
                        }
                    } while(opcaoMod != 3);
                    break;
                case 2:
                    id = eview.receberId();
                    if (edao.getEntregaId(id) != null && !edao.getEntregaId(id).getStatus().equals("Entregue") && !edao.getEntregaId(id).getStatus().equals("Cancelada")) {
                        if (edao.alterarStatus(id, "Cancelada", cal.getDia())) {
                            pedao.alterarStatus(edao.getEntregaId(id).getPedido().getId(), "Cancelado", cal.getDia());
                            System.out.println("\nEntrega Cancelada com sucesso");
                        }
                        else
                            System.out.println("\nNao foi possivel cancelar");
                    }
                    else
                        System.out.println("\nEssa entrega nao existe ou ja foi entregue");
                    break;
                case 3:
                    int opcaoMostrar;
                    do {
                       opcaoMostrar = eview.menuMostrarEntrega();
                        switch (opcaoMostrar) {
                            case 1:
                                int cod = eview.receberCodigo();
                                if (edao.getEntregaCod(cod) != null)
                                    System.out.println("\nEntrega encontrada:\n" + edao.getEntregaCod(cod));
                                else
                                    System.out.println("\nNao existe uma entrega com este codigo");
                                break;
                            case 2:
                                System.out.println("\nTodas as entregas:\n" + edao.mostrarTodos(log.getUserLogado()));
                                break;
                            default:
                                break;
                        }
                    } while(opcaoMostrar != 3);
                    break;
                default:
                    break;
            }
        } while(opcaoEntrega != 4);
    }
    
    public void menuPessoasAdm() {
        long id;
        int opcaoPessoa;
        pessoaView pview = new pessoaView();
        // loop da opcao pessoa
        do {
            opcaoPessoa = pview.menuPessoa();
            switch (opcaoPessoa) {
                case 1:
                    Pessoa p = new Pessoa(cal.getDia());
                    p.setNome(pview.receberNome());
                    p.setDocumento(pview.receberDocumento());
                    p.setNascimento(pview.receberNascimento());
                    
                    if (pdao.adiciona(p))
                        System.out.println("\nPessoa adicionada com sucesso");
                    else
                        System.out.println("\nEspaço cheio ...");
                    break;
                case 2:
                    id = pview.receberId();

                    if (pdao.remove(id))
                        System.out.println("\nPessoa Removida");
                    else
                        System.out.println("\nPessoa nao encontrada");
                    break;
                case 3:
                    int opcaoAlteraPessoa;
                    // loop da opcao Alterar Pessoa
                    do {
                        opcaoAlteraPessoa = pview.menuPessoaAlterar();
                        switch (opcaoAlteraPessoa) {
                            case 1:
                                String novoNome;
                                id = pview.receberId();

                                System.out.println("\nInforme o novo nome...: ");
                                novoNome = s.nextLine();

                                if (pdao.alterarNome(id, novoNome, cal.getDia()))
                                    System.out.println("\nNome alterado com sucesso");
                                else
                                    System.out.println("\nPessoa nao encontrada");
                                break;
                            case 2:
                                String novoDoc;
                                id = pview.receberId();

                                System.out.println("\nInforme o novo Documento...: ");
                                novoDoc = s.nextLine();

                                if (pdao.alterarDocumento(id, novoDoc, cal.getDia()))
                                    System.out.println("\nDocumento alterado com sucesso");
                                else
                                    System.out.println("\nPessoa nao encontrada");
                                break;
                            case 3:
                                LocalDate novoNas;
                                id = pview.receberId();

                                System.out.println("\nInforme o novo Nacimento...: ");
                                novoNas = LocalDate.parse(s.nextLine());

                                if (pdao.alterarNascimento(id, novoNas, cal.getDia()))
                                    System.out.println("\nNascimento  alterado com sucesso");
                                else
                                    System.out.println("\nPessoa nao encontrada");
                                break;
                            default:
                                break;

                        }
                    } while (opcaoAlteraPessoa != 4);
                    break;
                case 4:
                    System.out.println("\n\nTodas as pessoas cadastradas\n\n" + pdao.mostrarTodos());
                    break;
                default:
                    break;

            }
        } while (opcaoPessoa != 5);
    }
    
    public void menuUsuarioAdm() {
        String login;
        String senha;
        long id;
        int opcaoUsuario;
        // loop da opcao usuario
        do {
            opcaoUsuario = uview.menuUsuario();
            switch (opcaoUsuario) {
                case 1:
                    this.criarUsuario(uview);
                    break;
                case 2:
                    id = uview.receberId();

                    login = uview.receberLogin();

                    senha = uview.receberSenha();

                    if (udao.validarUser(login, senha) != null) {
                        if (udao.remove(id))
                            System.out.println("\nUsuario Removido");
                        else
                            System.out.println("\nUsuario nao encontrado");
                    }
                    else
                        System.out.println("\nLogin ou senha incorreto ... ");
                    break;
                case 3:
                    int opcaoAlteraUsuario;
                    // loop da opcao Alterar usuario
                    do {
                        opcaoAlteraUsuario = uview.menuUsuarioAlterar();
                        switch (opcaoAlteraUsuario) {
                            case 1:
                                String novologin;
                                id = uview.receberId();

                                System.out.println("\nInforme o novo Login...: ");
                                novologin = s.nextLine();

                                login = uview.receberLogin();

                                senha = uview.receberSenha();

                                if (udao.validarUser(login, senha) != null) {
                                    if (udao.alterarLogin(id, novologin, cal.getDia()))
                                        System.out.println("\nLogin alterado com sucesso");
                                    else
                                        System.out.println("\nUsuario nao encontrada");
                                }
                                else
                                    System.out.println("\nLogin ou senha incorreto ... ");

                                break;
                            case 2:
                                String novasenha;
                                id = uview.receberId();

                                System.out.println("\nInforme a nova senha...: ");
                                novasenha = s.nextLine();

                                login = uview.receberLogin();

                                senha = uview.receberSenha();

                                if (udao.validarUser(login, senha) != null) {
                                    if (udao.alterarSenha(id, novasenha, cal.getDia()))
                                        System.out.println("\nUsuario alterado com sucesso");
                                    else
                                        System.out.println("\nUsuario nao encontrada");
                                }
                                else
                                    System.out.println("\nLogin ou senha incorreto ... ");
                                break;
                            default:
                                break;

                        }
                    } while (opcaoAlteraUsuario != 3);
                    break;
                case 4:
                    System.out.println("\n\nTodos os Usuairos cadastrados\n\n" + udao.mostrarTodos());
                    break;
                default:
                    break;

            }
        } while (opcaoUsuario != 5);
    }
    
    public void menuRelatoriosAdm() {
        int opcaoRelatorios;
        relatorioView rview = new relatorioView();
        do {
            opcaoRelatorios = rview.menuRelatorios();
            switch (opcaoRelatorios) {
                case 1:
                    int opcaoVenda;
                    do {
                        opcaoVenda = rview.menuRelatoriosVendas();
                        switch (opcaoVenda) {
                            case 1:
                                LocalDate dataInicio;
                                LocalDate dataFim;
                                dataInicio = rview.receberDataIni();
                                dataFim = rview.receberDataFim();
                                String resVendasPeriodo = relatorioControler.vendasPorPeriodo(dataInicio, dataFim);
                                System.out.println("\nTodas as vendas de " + dataInicio + " a " + dataFim + "\n" + resVendasPeriodo);
                                String resVendasPeriodoPdf = relatorioControler.vendasPorPeriodoPdf(dataInicio, dataFim);
                                relatorioControler.gerarPdf("Vendas por Periodo " + dataInicio + " a " + dataFim, resVendasPeriodoPdf, cal.getDia());
                                break;
                            case 2:
                                long idUser;
                                System.out.println("\nTodos os usuarios:" + udao.mostrarTodos());
                                idUser = uview.receberId();
                                if (udao.getUsuario(idUser) != null) {
                                    String resVendasCliente = relatorioControler.vendasPorCliente(udao.getUsuario(idUser));
                                    System.out.println("\nTodas as compras deste usuario:\n" + resVendasCliente);
                                    String resVendasClientePdf = relatorioControler.vendasPorClientePdf(udao.getUsuario(idUser));
                                    relatorioControler.gerarPdf("Vendas por Cliente ID " + idUser, resVendasClientePdf, cal.getDia());
                                }
                                else
                                    System.out.println("\nUsuario nao encontrado");
                                break;
                            case 3:
                                String status;
                                System.out.println("\nInforme o status (Pago, Enviado, Entregue)");
                                status = s.nextLine();
                                String resVendasStatus = relatorioControler.vendasPorStatus(status);
                                System.out.println("\nTodas as vendas com o status " + status + "\n" + resVendasStatus);
                                String resVendasStatusPdf = relatorioControler.vendasPorStatusPdf(status);
                                relatorioControler.gerarPdf("Vendas por Status " + status, resVendasStatusPdf, cal.getDia());
                                break;
                            default:
                                break;
                        }
                    } while (opcaoVenda != 4);
                    break;
                case 2:
                    int opcaoFaturamento;
                    do {
                        opcaoFaturamento = rview.menuRelatoriosFaturamento();
                        switch (opcaoFaturamento) {
                            case 1:
                                LocalDate data;
                                data = rview.receberDia();
                                double fatDiario = relatorioControler.faturamentoDiario(data);
                                System.out.println("\nEm " + data + " teve um faturamento de R$" + fatDiario);
                                relatorioControler.gerarPdf("Faturamento Diario " + data,
                                    "Em " + data + " teve um faturamento de R$" + fatDiario, cal.getDia());
                                break;
                            case 2:
                                int mes;
                                int ano;
                                mes = rview.receberMes();
                                ano = rview.receberAno();
                                double fatMensal = relatorioControler.faturamentoMensal(mes, ano);
                                System.out.println("\nNo mes " + mes + " do ano " + ano + " teve um faturamento de R$" + fatMensal);
                                relatorioControler.gerarPdf("Faturamento Mensal " + mes + "-" + ano,
                                    "No mes " + mes + " do ano " + ano + " teve um faturamento de R$" + fatMensal, cal.getDia());
                                break;
                            case 3:
                                int anoFaturamento;
                                anoFaturamento = rview.receberAno();
                                double fatAnual = relatorioControler.faturamentoAnual(anoFaturamento);
                                System.out.println("\nNo ano " + anoFaturamento + " teve um faturamento de R$" + fatAnual);
                                relatorioControler.gerarPdf("Faturamento Anual " + anoFaturamento,
                                    "No ano " + anoFaturamento + " teve um faturamento de R$" + fatAnual, cal.getDia());
                                break;
                            default:
                                break;
                        }
                    } while(opcaoFaturamento != 4);
                    break;
                case 3:
                    int opcaoPedido;
                    do {
                        opcaoPedido = rview.menuRelatoriosPedidos();
                        switch (opcaoPedido) {
                            case 1:
                                String resNaoPagos = relatorioControler.pedidosStatus("Criado");
                                System.out.println("\nTodos os pedidos nao pagos:\n" + resNaoPagos);
                                String resNaoPagosPdf = relatorioControler.pedidosStatusPdf("Criado");
                                relatorioControler.gerarPdf("Pedidos Nao Pagos", resNaoPagosPdf, cal.getDia());
                                break;
                            case 2:
                                String resPagos = relatorioControler.pedidosPagos();
                                System.out.println("\nTodos os pedidos pagos:\n" + resPagos);
                                String resPagosPdf = relatorioControler.pedidosPagosPdf();
                                relatorioControler.gerarPdf("Pedidos Pagos", resPagosPdf, cal.getDia());
                                break;
                            case 3:
                                String resCancelados = relatorioControler.pedidosStatus("Cancelado");
                                System.out.println("\nTodos os pedidos cancelados:\n" + resCancelados);
                                String resCanceladosPdf = relatorioControler.pedidosStatusPdf("Cancelado");
                                relatorioControler.gerarPdf("Pedidos Cancelados", resCanceladosPdf, cal.getDia());
                                break;
                            default:
                                break;
                        }
                    } while (opcaoPedido != 4);
                    break;
                default:
                    break;
            }
        } while(opcaoRelatorios != 4);
    }
    
    public void ProgramaAdm() {
        int opcaoProAdm = 0;
        do{
            opcaoProAdm = pav.menuAdm();
            switch (opcaoProAdm) {
                case 1:
                    this.menuProdutosAdm();
                    break;
                case 2:
                    this.menuCuponsAdm();
                    break;
                case 3:
                    this.menuEstoqueAdm();
                    break;
                case 4:
                    this.menuCalendarioAdm();
                    break;
                case 5:
                    this.menuEntregasAdm();
                    break;
                case 6:
                    this.menuRelatoriosAdm();
                    break;
                case 7:
                    this.menuPessoasAdm();
                    break;
                case 8:
                    this.menuUsuarioAdm();
                    break;
                default:
                    break;
            }
        } while(opcaoProAdm != 9);
    }
}
