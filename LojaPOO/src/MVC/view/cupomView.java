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
public class cupomView {
     Scanner scanner = new Scanner(System.in);
    
    public int menuCupom() {
        StringBuilder builder = new StringBuilder("");

        builder.append("Menu de Cupom\n");
        builder.append("\n1 - Adiciona");
        builder.append("\n2   Ativar/Desativar");
        builder.append("\n3 - Altera");
        builder.append("\n4 - Mostrar Todos");
        builder.append("\n5 - Para voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public int menuCupomAlterar() {
        StringBuilder builder = new StringBuilder("");

        builder.append("Alterar Cupom\n");
        builder.append("\n1 - Codigo");
        builder.append("\n2 - Tipo");
        builder.append("\n3 - Valor Desconto");
        builder.append("\n4 - Valor Minimo");
        builder.append("\n5 - Data de Validade");
        builder.append("\n6 - Para voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public long receberId() {
        long id;
        System.out.println("\nInforme o ID do cupom...:");
        id = Long.parseLong(scanner.nextLine());
        return id;
    }
    
    public int receberCodigo() {
        int cod;
        System.out.println("\nInforme o codigo...:");
        cod = Integer.parseInt(scanner.nextLine());
        return cod;
    }
    
    public String receberTipo() {
        String tipo;
        System.out.println("\nInforme o tipo (Percentual ou Fixo)...:");
        tipo = scanner.nextLine();
        return tipo;
    }
    
    public double receberVlDesconto() {
        double valor;
        System.out.println("\nInforme o valor de desconto...:");
        valor = Double.parseDouble(scanner.nextLine());
        return valor;
    }
    
     public double receberVlMinimo() {
        double valor;
        System.out.println("\nInforme o valor minimo...:");
        valor = Double.parseDouble(scanner.nextLine());
        return valor;
    }
     
     public LocalDate receberDataValidade() {
         LocalDate data;
         System.out.println("\nInforme a data de validade...:");
         data = LocalDate.parse(scanner.nextLine());
         return data;
     }
    
    
}
