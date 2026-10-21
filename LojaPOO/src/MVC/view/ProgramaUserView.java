/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.view;

import java.util.Scanner;

/**
 *
 * @author joao
 */
public class ProgramaUserView {
    Scanner scanner = new Scanner(System.in);
    
    public int menuUser() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nBem vindo ao programa do Usuario\n");
        builder.append("\n1 - Ir as compras");
        builder.append("\n2 - Pedidos");
        builder.append("\n3 - Carrinho");
        builder.append("\n4 - Entregas");
        builder.append("\n5 - Para sair do Usuario\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public int menuPedidos() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nMenu Pedidos\n");
        builder.append("\n1 - Pagar");
        builder.append("\n2 - Cancelar");
        builder.append("\n3 - Relatorio de pedidos");
        builder.append("\n4 - Mostrar Todos Itens");
        builder.append("\n5 - Para Voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public int menuEntregas() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nMenu Entregasn\n");
        builder.append("\n1 - Rastrear");
        builder.append("\n2 - Mostrar Todas as Entregas");
        builder.append("\n3 - Para Voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public long receberIdProdutoCompra() {
        long id;
        System.out.println("\nInforme o id do produto que deseja comprar..:");
        id = Long.parseLong(scanner.nextLine());
        return id;
    }
    
    public String receberResposta() {
        String res;
        System.out.println("\nDeseja adicionar ao carrinho? (s ou n)...:");
        res = scanner.nextLine();
        return res;
    }
    
    public long receberIdPedido() {
        long id;
        System.out.println("\nInforme o id do pedido..:");
        id = Long.parseLong(scanner.nextLine());
        return id;
    }
    
}
