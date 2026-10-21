/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.view;

import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author joao
 */
public class relatorioView {
    Scanner scanner = new Scanner(System.in);
    
    public int menuRelatorios() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nMenu de Relatorios\n");
        builder.append("\n1 - Relatorio de vendas");
        builder.append("\n2 - Relatorio de faturamento");
        builder.append("\n3 - Relatorio de pedidos");
        builder.append("\n4 - Para voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public int menuRelatoriosVendas() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nRelatorios de Vendas\n");
        builder.append("\n1 - Por periodo");
        builder.append("\n2 - Por cliente");
        builder.append("\n3 - Por status");
        builder.append("\n4 - Para voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
     
    public int menuRelatoriosFaturamento() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nRelatorios de Faturamento\n");
        builder.append("\n1 - Diario");
        builder.append("\n2 - Mensal");
        builder.append("\n3 - Anual");
        builder.append("\n4 - Para voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public int menuRelatoriosPedidos() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nRelatorios de Pedidos\n");
        builder.append("\n1 - Abertos");
        builder.append("\n2 - Pagos");
        builder.append("\n3 - Cancelados");
        builder.append("\n4 - Para voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public LocalDate receberDia() {
        LocalDate data;
        System.out.println("\nInforme a data...:");
        data = LocalDate.parse(scanner.nextLine());
        return data;
                
    }
    
    public LocalDate receberDataIni() {
        LocalDate data;
        System.out.println("\nInforme a data inicial do periodo...:");
        data = LocalDate.parse(scanner.nextLine());
        return data;
                
    }
    
     public LocalDate receberDataFim() {
        LocalDate data;
        System.out.println("\nInforme a data final do periodo...:");
        data = LocalDate.parse(scanner.nextLine());
        return data;
                
    }
    
    public int receberMes() {
        int mes;
        System.out.println("\nInforme o mes...:");
        mes = Integer.parseInt(scanner.nextLine());
        return mes;
    }
    
    public int receberAno() {
        int ano;
        System.out.println("\nInforme o Ano...:");
        ano = Integer.parseInt(scanner.nextLine());
        return ano;
    }
}
