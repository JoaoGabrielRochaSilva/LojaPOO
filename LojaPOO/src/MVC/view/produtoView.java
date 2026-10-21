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
public class produtoView {
     Scanner scanner = new Scanner(System.in);
    
    public int menuProduto() {
        StringBuilder builder = new StringBuilder("");

        builder.append("Menu de Produtos\n");
        builder.append("\n1 - Adiciona");
        builder.append("\n2   Ativa/Desativa");
        builder.append("\n3 - Altera");
        builder.append("\n4 - Mostrar Todos");
        builder.append("\n5 - Para voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public int menuProdutoAlterar() {
        StringBuilder builder = new StringBuilder("");

        builder.append("Alterar Produto\n");
        builder.append("\n1 - Nome");
        builder.append("\n2 - Descricao");
        builder.append("\n3 - Preco Venda");
        builder.append("\n4 - Para voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public long receberId() {
        long id;
        System.out.println("\nInforme o ID do produto...:");
        id = Long.parseLong(scanner.nextLine());
        return id;
    }
    
    public String receberNome() {
        String nome;
        System.out.println("\nNome...:");
        nome = scanner.nextLine();
        return nome;
    }
    
    public String receberDescricao() {
        String desc;
        System.out.println("\nDescricao...:");
        desc = scanner.nextLine();
        return desc;
    }
    
    public double receberPreco() {
        double preco;
        System.out.println("\nPreco...:");
        preco = Double.parseDouble(scanner.nextLine());
        return preco;
    }
    
}
