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
public class ItensCarrinho {
    private long id;
    private Carrinho carrinho;
    private Produto produto;
    private int quantidade;
    private double preco_unitario;
    private LocalDate data_criacao;
    private LocalDate data_modificacao;
    
    public ItensCarrinho(LocalDate data) {
        this.data_criacao = data;
        this.data_modificacao = data;
    }

    public long getId() {
        return id;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
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

    public double getPreco_unitario() {
        return preco_unitario;
    }

    public void setPreco_unitario(double preco_unitario) {
        this.preco_unitario = preco_unitario;
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
        return "ItensCarrinho{" + "id=" + id + ", carrinho=" + carrinho + ", produto=" + produto + ", quantidade=" + quantidade + ", preco_unitario=" + preco_unitario + ", data_criacao=" + data_criacao + ", data_modificacao=" + data_modificacao + '}';
    }
    
}
