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
public class Entrega {
    private long id;
    private Pedidos pedido;
    private String status;
    private String transportadora;
    private int codigo_rastreio;
    private LocalDate data_envio;
    private LocalDate data_entrega;
    private LocalDate data_criacao;
    private LocalDate data_modificacao;

    public Entrega(LocalDate data) {
        this.data_criacao = data;
        this.data_modificacao = data;
    }


    public long getId() {
        return id;
    }

    public Pedidos getPedido() {
        return pedido;
    }

    public void setPedido(Pedidos pedido) {
        this.pedido = pedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(String transportadora) {
        this.transportadora = transportadora;
    }

    public int getCodigo_rastreio() {
        return codigo_rastreio;
    }

    public void setCodigo_rastreio(int codigo_rastreio) {
        this.codigo_rastreio = codigo_rastreio;
    }

    public LocalDate getData_envio() {
        return data_envio;
    }

    public void setData_envio(LocalDate data_envio) {
        this.data_envio = data_envio;
    }

    public LocalDate getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(LocalDate data_entrega) {
        this.data_entrega = data_entrega;
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
        return "Entrega{" + "id=" + id + ", pedido=" + pedido + ", status=" + status + ", transportadora=" + transportadora + ", codigo_rastreio=" + codigo_rastreio + ", data_envio=" + data_envio + ", data_entrega=" + data_entrega + ", data_criacao=" + data_criacao + ", data_modificacao=" + data_modificacao + '}';
    }
    
}
