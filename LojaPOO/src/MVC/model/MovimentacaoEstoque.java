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
public class MovimentacaoEstoque {
    private long id;
    private Produto produto;
    private int quantidade;
    private String tipo;
    private double valor_unitario;
    private LocalDate data_criacao;
    private LocalDate data_modificacao;

    public MovimentacaoEstoque(LocalDate data) {
        this.data_criacao = data;
        this.data_modificacao = data;
    }

    public long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(double valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public LocalDate getData_modificacao() {
        return data_modificacao;
    }

    public void setData_modificacao(LocalDate data_modificacao) {
        this.data_modificacao = data_modificacao;
    }

    public LocalDate getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(LocalDate data_criacao) {
        this.data_criacao = data_criacao;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    
    
    @Override
    public String toString() {
        return "MovimentacaoEstoque{" + "id=" + id + ", produto=" + produto + ", quantidade=" + quantidade + ", tipo=" + tipo + ", valor_unitario=" + valor_unitario + ", data_criacao=" + data_criacao + ", data_modificacao=" + data_modificacao + '}';
    }
    
}
