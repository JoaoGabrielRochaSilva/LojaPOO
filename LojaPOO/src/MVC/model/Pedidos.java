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
public class Pedidos {
    private long id;
    private Usuario usuario;
    private Cupom cupom;
    private String status;
    private double valor_total;
    private String formaPagamento;
    private LocalDate data_criacao;
    private LocalDate data_modificacao;
    
    public Pedidos(LocalDate data) {
        this.setStatus("Criado");
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

    public Cupom getCupom() {
        return cupom;
    }

    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
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
        return "Pedidos{" + "id=" + id + ", usuario=" + usuario + ", cupom=" + cupom + ", status=" + status + ", valor_total=" + valor_total + ", formaPagamento=" + formaPagamento + ", data_criacao=" + data_criacao + ", data_modificacao=" + data_modificacao + '}';
    }
    
}
