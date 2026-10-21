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
public class pessoaView {
    Scanner scanner = new Scanner(System.in);
    
    public int menuPessoa() {
        StringBuilder builder = new StringBuilder("");

        builder.append("Menu de Pessoas\n");
        builder.append("\n1 - Adiciona");
        builder.append("\n2   Remove");
        builder.append("\n3 - Altera");
        builder.append("\n4 - Mostrar Todas");
        builder.append("\n5 - Para sair do programa\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public int menuPessoaAlterar() {
        StringBuilder builder = new StringBuilder("");

        builder.append("Alterar Pessoa\n");
        builder.append("\n1 - Nome");
        builder.append("\n2 - Documento");
        builder.append("\n3 - Nascimento");
        builder.append("\n4 - Para sair do programa\n");
        builder.append("\nQual sua opção ? R: ");

        System.out.print(builder.toString());

        return Integer.parseInt(scanner.nextLine());
    }
    
    public long receberId() {
        long id;
        System.out.println("\nInforme o ID da pessoa...:");
        id = Long.parseLong(scanner.nextLine());
        return id;
    }
    
    public String receberNome() {
        String nome;
        System.out.println("\nInforme o nome da pessoa...:");
        nome = scanner.nextLine();
        return nome;
    }
    
    public String receberDocumento() {
        String doc;
        System.out.println("\nInforme o documento da pessoa...:");
        doc = scanner.nextLine();
        return doc;
    }
    
    public LocalDate receberNascimento() {
        LocalDate nasc;
        System.out.println("\nInforme a data de nascimento...:");
        nasc = LocalDate.parse(scanner.nextLine());
        return nasc;
    }
    
   
}
