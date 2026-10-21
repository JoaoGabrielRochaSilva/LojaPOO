/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model;

import java.time.LocalDate;

/**
 *
 * @author joao
 */
public class Carrinho {
    private long id;
    private Usuario usuario;
    private String status;
    private LocalDate data_criacao;
    private LocalDate data_modificacao;
    
    public Carrinho(LocalDate data) {
        this.data_criacao = data;
        this.data_modificacao = data;
    }

    public long getId() {
        return id;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData_modificacao(LocalDate data_modificacao) {
        this.data_modificacao = data_modificacao;
    }

    public LocalDate getData_criacao() {
        return data_criacao;
    }

    public LocalDate getData_modificacao() {
        return data_modificacao;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setData_criacao(LocalDate data_criacao) {
        this.data_criacao = data_criacao;
    }
    
    

    @Override
    public String toString() {
        return "Carrinho{" + "id=" + id + ", usuario=" + usuario + ", status=" + status + ", data_criacao=" + data_criacao + ", data_modificacao=" + data_modificacao + '}';
    }
    
    
}
