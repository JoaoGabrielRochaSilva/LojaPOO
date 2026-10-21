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
public class usuarioView {
    Scanner scanner = new Scanner(System.in);
    
    public int menuUsuario() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nMenu de Usuario\n");
        builder.append("\n1 - Adiciona");
        builder.append("\n2   Remove");
        builder.append("\n3 - Altera");
        builder.append("\n4 - Mostrar Todos");
        builder.append("\n5 - Para sair do programa\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public int menuUsuarioAlterar() {
        StringBuilder builder = new StringBuilder("");

        builder.append("\n\nAlterar Usuario\n");
        builder.append("\n1 - Login");
        builder.append("\n2 - Senha");
        builder.append("\n3 - Para sair do programa\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public long receberId() {
        long id;
        System.out.println("\nInforme o ID do usuario...:");
        id = Long.parseLong(scanner.nextLine());
        return id;
    }
    
    public String receberLogin() {
        String login;
        System.out.println("Login...: ");
        login = scanner.nextLine();
        return login;
    }
    
    public String receberSenha() {
        String senha;
        System.out.println("Senha...: ");
        senha = scanner.nextLine();
        return senha;
    }
    
}
