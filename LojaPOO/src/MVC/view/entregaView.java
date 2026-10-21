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
public class entregaView {
     Scanner scanner = new Scanner(System.in);
    
    public int menuEntrega() {
        StringBuilder builder = new StringBuilder("");

        builder.append("Menu de Entrega\n");
        builder.append("\n1 - Modificar");
        builder.append("\n2   cancelar");
        builder.append("\n3 - Mostrar Entrega");
        builder.append("\n4 - Para sair do programa\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public int menuModificarEntrega() {
        StringBuilder builder = new StringBuilder("");

        builder.append("Menu de Modificar\n");
        builder.append("\n1 - Codigo Rastreio");
        builder.append("\n2   Transportadora");
        builder.append("\n3- Para sair do programa\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public int menuMostrarEntrega() {
        StringBuilder builder = new StringBuilder("");

        builder.append("Menu de Entrega\n");
        builder.append("\n1 - Codigo Rastreio");
        builder.append("\n2   Mostrar Todas");
        builder.append("\n3- Para sair do programa\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public long receberId() {
        long id;
        System.out.println("\nInforme o ID da entrega...:");
        id = Long.parseLong(scanner.nextLine());
        return id;
    }
    
    public int receberCodigo() {
        int cod;
        System.out.println("\nInforme o codigo de rastreio da entrega...:");
        cod = Integer.parseInt(scanner.nextLine());
        return cod;
    }
    
    public String receberTransportadora() {
        String transport;
        System.out.println("\nInforme a transportadora...:");
        transport = scanner.nextLine();
        return transport;
    }
}
