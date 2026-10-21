/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.control;

import MVC.model.Carrinho;
import MVC.model.CarrinhoDAO;    
import MVC.model.Cupom;
import MVC.model.CupomDAO;       
import MVC.model.Entrega;
import MVC.model.EntregaDAO;
import MVC.model.Pedidos;
import MVC.model.PedidosDAO;     

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;          

/**
 *
 * @author joao
 */
public class EventosAutomaticos {

    public EventosAutomaticos() {
    }
    
    public void verificarCarrinhos(LocalDate dataAtual, List<Carrinho> carrinhos, CarrinhoDAO cadao) {
        for (Carrinho carrinho : carrinhos) {
            if (carrinho != null && carrinho.getStatus().equals("Aberto")) {
                long dias = ChronoUnit.DAYS.between(carrinho.getData_criacao(), dataAtual);
                
                if (dias >= 1) {
                    carrinho.setStatus("Expirado");
                    carrinho.setData_modificacao(dataAtual);
                    // NOVO: persiste a alteracao de status no banco
                    cadao.alterarStatus(carrinho.getId(), "Expirado", dataAtual);
                }
            }
        }
    }
    
    private Entrega CriarEntregas(LocalDate dataAtual, Pedidos pedido, EntregaDAO edao) {
        Entrega novaEntrega = new Entrega(dataAtual);
        novaEntrega.setPedido(pedido);
        novaEntrega.setStatus("Preparando");
        novaEntrega.setTransportadora("Uberaba Transporte");
        novaEntrega.setCodigo_rastreio(edao.getProximoCodRastreio());

        return novaEntrega;
    }
    
    public void verificarPedidos(LocalDate dataAtual, List<Pedidos> pedidos, EntregaDAO edao, PedidosDAO pedao) {
        for (Pedidos pedido : pedidos) {
            if (pedido != null && !pedido.getStatus().equals("Entregue") && !pedido.getStatus().equals("Cancelado") && !pedido.getStatus().equals("Criado")) {

                long dias = ChronoUnit.DAYS.between(pedido.getData_criacao(), dataAtual);

                if (dias >= 4) {
                    pedido.setStatus("Entregue");
                }
                else if (dias >= 2) {
                    pedido.setStatus("Enviado");
                }
                else if (dias >= 1 && pedido.getStatus().equals("Pago")) {
                    pedido.setStatus("Separacao");

                    if (!edao.ExistePedido(pedido)) {
                        edao.adiciona(CriarEntregas(dataAtual, pedido, edao));
                    }
                }

                pedido.setData_modificacao(dataAtual);
                pedao.alterarStatus(pedido.getId(), pedido.getStatus(), dataAtual);
            }
        }
    }   
    
    public void verificarEntregas(LocalDate dataAtual, List<Entrega> entregas, EntregaDAO edao) {
        for (Entrega entrega : entregas) {
            if (entrega != null && !entrega.getStatus().equals("Entregue") && !entrega.getStatus().equals("Cancelada")) {

                long dias = ChronoUnit.DAYS.between(entrega.getData_criacao(), dataAtual);
                System.out.println("\n\nDias..: " + dias);

                if (dias >= 3) {
                    entrega.setStatus("Entregue");
                    if (entrega.getData_entrega() == null) {
                        entrega.setData_entrega(dataAtual);
                        // persiste a data de entrega no banco
                        edao.alterarDataEntrega(entrega.getId(), dataAtual, dataAtual);
                    }
                }
                else if (dias >= 2) {
                    entrega.setStatus("Em transito");
                }
                else if (dias >= 1) {
                    entrega.setStatus("Enviado");
                    if (entrega.getData_envio() == null) {
                        entrega.setData_envio(dataAtual);
                        // persiste a data de envio no banco
                        edao.alterarDataEnvio(entrega.getId(), dataAtual, dataAtual);
                    }
                }

                entrega.setData_modificacao(dataAtual);
                // persiste o status final e a data_modificacao no banco
                edao.alterarStatus(entrega.getId(), entrega.getStatus(), dataAtual);
            }
        }
    }

    public void verificarCupoms(LocalDate dataAtual, List<Cupom> cupoms, CupomDAO cudao) {
        for (Cupom cupom : cupoms) {
            if(cupom != null && !dataAtual.isBefore(cupom.getData_validade())) {
                cupom.setAtivo(false);
                cupom.setData_modificacao(dataAtual);
                // persiste a desativacao do cupom no banco
                cudao.alterarAtivo(cupom.getId(), false, dataAtual);
            }
        }
    }
}
