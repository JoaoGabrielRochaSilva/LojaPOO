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
public class Cupom {
    private long id;
    private int codigo;
    private String tipo_desconto;
    private double valor_desconto;
    private double valor_minimo;
    private LocalDate data_validade;
    private boolean ativo;
    private LocalDate data_criacao;
    private LocalDate data_modificacao;

    public Cupom(LocalDate data) {
        this.ativo = true;
        this.data_criacao = data;
        this.data_modificacao = data;
    }


    public long getId() {
        return id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipo_desconto() {
        return tipo_desconto;
    }

    public void setTipo_desconto(String tipo_desconto) {
        this.tipo_desconto = tipo_desconto;
    }

    public double getValor_desconto() {
        return valor_desconto;
    }

    public void setValor_desconto(double valor_desconto) {
        this.valor_desconto = valor_desconto;
    }

    public double getValor_minimo() {
        return valor_minimo;
    }

    public void setValor_minimo(double valor_minimo) {
        this.valor_minimo = valor_minimo;
    }

    public LocalDate getData_validade() {
        return data_validade;
    }

    public void setData_validade(LocalDate data_validade) {
        this.data_validade = data_validade;
    }

    public boolean getAtivo() {
        return this.ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
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
        return "Cupom{" + "id=" + id + ", codigo=" + codigo + ", tipo_desconto=" + tipo_desconto + ", valor_desconto=" + valor_desconto + ", valor_minimo=" + valor_minimo + ", data_validade=" + data_validade + ", ativo=" + ativo + ", data_criacao=" + data_criacao + ", data_modificacao=" + data_modificacao + '}';
    }
    
    
}
