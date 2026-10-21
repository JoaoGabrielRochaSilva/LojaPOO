/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model;   

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author joao
 */
public class ConectionFactory {
    
    private static final String url = "jdbc:mysql://localhost:3306/faculdade?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Sao_Paulo";
    private static final String usuario = "seu_usuario";
    private static final String senha = "sua_senha"; 
    
    public static Connection getConection() throws SQLException {
        return DriverManager.getConnection(url, usuario, senha);
    }
}
