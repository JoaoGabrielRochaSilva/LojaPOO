/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model;

/**
 *
 * @author joao
 */
public class Login {
    private Usuario userLogado;
    
    public Login() {
        this.userLogado = null;
    }
    
    public Usuario getUserLogado() {
        return userLogado;
    }

    public void setUserLogado(Usuario userLogado) {
        this.userLogado = userLogado;
    }

    @Override
    public String toString() {
        return "Login{" + "userLogado=" + userLogado + '}';
    }
    
    
}
