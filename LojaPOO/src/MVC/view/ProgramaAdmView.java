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
public class ProgramaAdmView {
    Scanner scanner = new Scanner(System.in);
    
    public int menuAdm() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nBem vindo ao programa do ADM\n\n");
        builder.append("\n1 - Produto");
        builder.append("\n2 - Cupom");
        builder.append("\n3 - Estoque");
        builder.append("\n4 - Calendario");
        builder.append("\n5 - Entregas");
        builder.append("\n6 - Relatorios");
        builder.append("\n7 - Cadastrar pessoa");
        builder.append("\n8 - Cadastrar usuario");
        builder.append("\n9 - Para sair do usuario\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public int calendario() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nCalendario\n\n");
        builder.append("\n1 - Mostrar Data");
        builder.append("\n2 - Passar Dia");
        builder.append("\n3 - Voltar\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
}
