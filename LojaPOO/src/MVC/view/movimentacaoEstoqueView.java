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
public class movimentacaoEstoqueView {
     Scanner scanner = new Scanner(System.in);
    
    public int menuEstoque() {
        StringBuilder builder = new StringBuilder("");

        builder.append("Menu de Estoque\n");
        builder.append("\n1 - Adiciona");
        builder.append("\n2   Remove");
        builder.append("\n3 - Mostrar Todos");
        builder.append("\n4 - Para voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public long receberId() {
        long id;
        System.out.println("\nInforme o ID da movimentacao...:");
        id = Long.parseLong(scanner.nextLine());
        return id;
    }
    
    public String receberTipo() {
        String tipo;
        System.out.println("\nInforme o tipo da movimentacao (Entrada, Saida, Ajuste)...:");
        tipo = scanner.nextLine();
        return tipo;
    }
    
    public int receberQtdProduto() {
        int qtd;
        System.out.println("\nInforme a quantidade de produto movimentado...:");
        qtd = Integer.parseInt(scanner.nextLine());
        return qtd;  
    }
    
   
}
