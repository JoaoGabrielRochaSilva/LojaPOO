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
public class loginView {
    Scanner scanner = new Scanner(System.in);
    
    public int menuLogin() {
        StringBuilder builder = new StringBuilder("");

        builder.append("SEJA BEM VINDO A LOJA\n");
        builder.append("\n1 - Produtos disponiveis");
        builder.append("\n2 - Cadastrar usuario");
        builder.append("\n3 - Fazer login");
        builder.append("\n4 - Para sair do programa\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    
}
