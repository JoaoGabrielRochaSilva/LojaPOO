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
public class carrinhoView {
    Scanner scanner = new Scanner(System.in);
    
    public int menuCarrinho() {
        StringBuilder builder = new StringBuilder("");

        builder.append("Menu de Carrinho\n");
        builder.append("\n1 - Finalizar");
        builder.append("\n2 - Criar");
        builder.append("\n3 - Cancelar");
        builder.append("\n4 - Itens Carrinho");
        builder.append("\n5 - Mostrar Todos");
        builder.append("\n6 - Para voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public int menuItensCarrinho() {
        StringBuilder builder = new StringBuilder("");

        builder.append("Menu de Itens Carrinho\n");
        builder.append("\n1 - Remover");
        builder.append("\n2 - Alterar Produto");
        builder.append("\n3 - Alterar Quantidade");
        builder.append("\n4 - Mostrar Todos");
        builder.append("\n5 - Para voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public long receberId() {
        long id;
        System.out.println("\nInforme o ID do carrinho...:");
        id = Long.parseLong(scanner.nextLine());
        return id;
    }
    
    public long receberIdItens() {
        long id;
        System.out.println("\nInforme o ID do Item...:");
        id = Long.parseLong(scanner.nextLine());
        return id;
    }
}
