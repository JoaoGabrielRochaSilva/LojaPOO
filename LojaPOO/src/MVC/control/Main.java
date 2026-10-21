/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.control;

import MVC.model.Login;
import MVC.model.Calendario;
import MVC.model.PessoaDAO;
import MVC.model.UsuarioDAO;
import MVC.model.ProdutoDAO;
import MVC.model.MovimentacaoEstoqueDAO;
import MVC.model.CupomDAO;
import MVC.model.EntregaDAO;
import MVC.model.PedidosDAO;
import MVC.model.ItensPedidoDAO;
import MVC.model.CarrinhoDAO;
import MVC.model.ItensCarrinhoDAO;

import MVC.view.loginView;
import MVC.view.ProgramaAdmView;
import MVC.view.ProgramaUserView;
import MVC.view.usuarioView;

import java.util.Scanner;

/**
 *
 * @author joao
 */
public class Main {
    
    // Utilitarios do programa
    Scanner s = new Scanner(System.in);
    Login log = new Login();
    Calendario cal = new Calendario();
    EventosAutomaticos eventos = new EventosAutomaticos();
    
    // Views pricipais do programa
    loginView lview = new loginView();
    ProgramaAdmView pav = new ProgramaAdmView();
    ProgramaUserView puv = new ProgramaUserView();
    usuarioView uview = new usuarioView();
    
    // DAOs do programa
    PessoaDAO pdao = new PessoaDAO();
    UsuarioDAO udao = new UsuarioDAO();
    ProdutoDAO prodao = new ProdutoDAO();
    MovimentacaoEstoqueDAO mdao = new MovimentacaoEstoqueDAO();
    CupomDAO cudao = new CupomDAO();
    PedidosDAO pedao = new PedidosDAO();
    ItensPedidoDAO ipdao = new ItensPedidoDAO();
    EntregaDAO edao = new EntregaDAO();
    CarrinhoDAO cadao = new CarrinhoDAO();
    ItensCarrinhoDAO icdao = new ItensCarrinhoDAO();
    
    // Controlers do programa
    PedidoControler PeControler;
    AdmControler AdmControler;
    CarrinhoControler carrinhoControler;
    UserControler UserControler;
    RelatorioControler ReControler;
    
    //Menu inicial do programa
    private void Login() {
        String login;
        String senha;
       int opcaoLogin = 0;
       do {
            opcaoLogin = lview.menuLogin();
            
            //o loop do login
            switch (opcaoLogin) {
                case 1:
                    System.out.println("\nTodos os produtos disponiveis:" + prodao.mostrarTodos());
                    break;
                case 2:
                    AdmControler.criarUsuario(uview);
                    break;
                case 3:
                    login = uview.receberLogin();
                    
                    senha = uview.receberSenha();
                    
                    log.setUserLogado(udao.validarUser(login, senha));
                    
                    if (log.getUserLogado() != null) {   
                        System.out.println("Login feito com sucesso ... \n");
                        
                        if (log.getUserLogado().getLogin().equals("ADM"))
                            AdmControler.ProgramaAdm();
                        else
                            UserControler.ProgramaUser();
                    }
                    else
                        System.out.println("Usuairo ou senha incorreto ... \n");
                    break;
                default:
                    break;
                
            }
            
        } while (opcaoLogin != 4);
       
    }
    
    // Inicia o programa
    public Main() {
        this.PeControler = new PedidoControler(s, log, cal, prodao, pedao, ipdao, edao, cudao, mdao);
        this.ReControler = new RelatorioControler(pedao, ipdao);
        this.AdmControler = new AdmControler(s, log, cal, eventos, pav, uview, pdao, udao, prodao, mdao, cudao, pedao, ipdao, edao, cadao, icdao, ReControler);
        this.carrinhoControler = new CarrinhoControler(cal, cadao, icdao);
        this.UserControler = new UserControler(s, log, cal, PeControler, carrinhoControler, ReControler, puv);
        
        Login();
    }
    
    public static void main(String[] args) {
        new Main();
    }
    
}
