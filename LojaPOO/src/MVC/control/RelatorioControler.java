/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.control;

import MVC.model.ItensPedido;
import MVC.model.ItensPedidoDAO;
import MVC.model.Pedidos;
import MVC.model.PedidosDAO;
import MVC.model.Usuario;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author joao
 */
public class RelatorioControler {

    private PedidosDAO pedao;
    private ItensPedidoDAO ipdao;

    public RelatorioControler(PedidosDAO pedao, ItensPedidoDAO ipdao) {
        this.pedao = pedao;
        this.ipdao = ipdao;
    }
    
    public boolean pedidoContaComoVenda(Pedidos p) {
        if (p.getStatus().equalsIgnoreCase("Pago") || p.getStatus().equalsIgnoreCase("Enviado") || p.getStatus().equalsIgnoreCase("Entregue"))
            return true;
        else
            return false;
    }

    //  RELATORIO DE VENDAS (CONSOLE)

    public String vendasPorPeriodo(LocalDate inicio, LocalDate fim) {
        StringBuilder sb = new StringBuilder();

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && this.pedidoContaComoVenda(p)) {
                if ((p.getData_criacao().isEqual(inicio) || p.getData_criacao().isAfter(inicio)) &&
                    (p.getData_criacao().isEqual(fim) || p.getData_criacao().isBefore(fim))) {

                    sb.append(p).append("\n");
                }
            }
        }

        if (sb.length() == 0)
            return "\nNenhuma venda encontrada no periodo";

        return sb.toString();
    }

    public String vendasPorCliente(Usuario u) {
        StringBuilder sb = new StringBuilder();

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && p.getUsuario().equals(u) && this.pedidoContaComoVenda(p)) {
                sb.append(p).append("\n");
            }
        }

        if (sb.length() == 0)
            return "\nNenhuma venda encontrada para este cliente";

        return sb.toString();
    }

    public String vendasPorStatus(String status) {
        StringBuilder sb = new StringBuilder();

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && p.getStatus().equalsIgnoreCase(status) && this.pedidoContaComoVenda(p)) {
                sb.append(p).append("\n");
            }
        }

        if (sb.length() == 0)
            return "\nNenhuma venda encontrada com este status";

        return sb.toString();
    }

    //RELATORIO DE FATURAMENTO

    public double faturamentoDiario(LocalDate dia) {
        double total = 0;

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && this.pedidoContaComoVenda(p) && p.getData_criacao().isEqual(dia)) {

                total += p.getValor_total();
            }
        }

        return total;
    }

    public double faturamentoMensal(int mes, int ano) {
        double total = 0;

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && this.pedidoContaComoVenda(p) && p.getData_criacao().getMonthValue() == mes && p.getData_criacao().getYear() == ano) {

                total += p.getValor_total();
            }
        }

        return total;
    }

    public double faturamentoAnual(int ano) {
        double total = 0;

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && this.pedidoContaComoVenda(p) && p.getData_criacao().getYear() == ano) {

                total += p.getValor_total();
            }
        }

        return total;
    }

    //RELATORIO DE PEDIDOS

    public String pedidosPagos() {
        StringBuilder sb = new StringBuilder();

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && this.pedidoContaComoVenda(p)) {
                sb.append(p).append("\n");
            }
        }

        if (sb.length() == 0)
            return "\nNenhum pedido encontrado com este status";

        return sb.toString();
    }
    
    public String pedidosStatus(String status) {
        StringBuilder sb = new StringBuilder();

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && p.getStatus().equals(status)) {
                sb.append(p).append("\n");
            }
        }

        if (sb.length() == 0)
            return "\nNenhum pedido encontrado com este status";

        return sb.toString();
    }

    // ==================== FORMATACAO PARA PDF ====================
    // Os metodos abaixo extraem apenas os dados relevantes de Pedidos, Usuario,
    // Pessoa, Cupom e ItensPedido para montar um conteudo legivel para os PDFs.
    // Nao utilizam toString() dos objetos e nao afetam a saida do console,
    // que continua sendo gerada pelos metodos acima sem nenhuma alteracao.

    private String formatarPedidoPdf(Pedidos p, List<ItensPedido> todosItens) {
        StringBuilder sb = new StringBuilder();

        sb.append("ID Pedido: ").append(p.getId()).append("\n");
        sb.append("Cliente: ").append(p.getUsuario().getPessoa().getNome()).append("\n");
        sb.append("Status: ").append(p.getStatus()).append("\n");
        sb.append("Forma de pagamento: ")
          .append(p.getFormaPagamento() != null ? p.getFormaPagamento() : "Nao informado")
          .append("\n");
        sb.append("Cupom: ")
          .append(p.getCupom() != null ? p.getCupom().getCodigo() : "Nenhum")
          .append("\n");
        sb.append("Valor total: R$").append(String.format("%.2f", p.getValor_total())).append("\n");
        sb.append("Data de criacao: ").append(p.getData_criacao()).append("\n");
        sb.append("Data de modificacao: ").append(p.getData_modificacao()).append("\n");

        sb.append("Itens do pedido:\n");
        boolean temItens = false;
        for (ItensPedido ip : todosItens) {
            if (ip != null && ip.getPedido().getId() == p.getId()) {
                temItens = true;
                sb.append("   ").append(ip.getProduto().getNome())
                  .append(" | Quantidade: ").append(ip.getQuantidade())
                  .append(" | Valor unitario: R$").append(String.format("%.2f", ip.getPreco_unitario()))
                  .append(" | Subtotal: R$").append(String.format("%.2f", ip.getSub_total()))
                  .append("\n");
            }
        }
        if (!temItens)
            sb.append("   Nenhum item encontrado\n");

        return sb.toString();
    }

    public String vendasPorPeriodoPdf(LocalDate inicio, LocalDate fim) {
        StringBuilder sb = new StringBuilder();
        List<ItensPedido> todosItens = ipdao.getItensPedidos();
        boolean encontrou = false;

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && this.pedidoContaComoVenda(p)) {
                if ((p.getData_criacao().isEqual(inicio) || p.getData_criacao().isAfter(inicio)) &&
                    (p.getData_criacao().isEqual(fim) || p.getData_criacao().isBefore(fim))) {

                    sb.append(formatarPedidoPdf(p, todosItens)).append("\n");
                    encontrou = true;
                }
            }
        }

        if (!encontrou)
            return "Nenhuma venda encontrada no periodo";

        return sb.toString();
    }

    public String vendasPorClientePdf(Usuario u) {
        StringBuilder sb = new StringBuilder();
        List<ItensPedido> todosItens = ipdao.getItensPedidos();
        boolean encontrou = false;

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && p.getUsuario().equals(u) && this.pedidoContaComoVenda(p)) {
                sb.append(formatarPedidoPdf(p, todosItens)).append("\n");
                encontrou = true;
            }
        }

        if (!encontrou)
            return "Nenhuma venda encontrada para este cliente";

        return sb.toString();
    }

    public String vendasPorStatusPdf(String status) {
        StringBuilder sb = new StringBuilder();
        List<ItensPedido> todosItens = ipdao.getItensPedidos();
        boolean encontrou = false;

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && p.getStatus().equalsIgnoreCase(status) && this.pedidoContaComoVenda(p)) {
                sb.append(formatarPedidoPdf(p, todosItens)).append("\n");
                encontrou = true;
            }
        }

        if (!encontrou)
            return "Nenhuma venda encontrada com este status";

        return sb.toString();
    }

    public String pedidosPagosPdf() {
        StringBuilder sb = new StringBuilder();
        List<ItensPedido> todosItens = ipdao.getItensPedidos();
        boolean encontrou = false;

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && this.pedidoContaComoVenda(p)) {
                sb.append(formatarPedidoPdf(p, todosItens)).append("\n");
                encontrou = true;
            }
        }

        if (!encontrou)
            return "Nenhum pedido encontrado com este status";

        return sb.toString();
    }

    public String pedidosStatusPdf(String status) {
        StringBuilder sb = new StringBuilder();
        List<ItensPedido> todosItens = ipdao.getItensPedidos();
        boolean encontrou = false;

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && p.getStatus().equals(status)) {
                sb.append(formatarPedidoPdf(p, todosItens)).append("\n");
                encontrou = true;
            }
        }

        if (!encontrou)
            return "Nenhum pedido encontrado com este status";

        return sb.toString();
    }

    public String relatorioPedidosUsuarioPdf(Usuario u) {
        StringBuilder sb = new StringBuilder();
        List<ItensPedido> todosItens = ipdao.getItensPedidos();
        boolean encontrou = false;

        for (Pedidos p : pedao.getPedidos()) {
            if (p != null && (p.getUsuario().getId() == u.getId() || "ADM".equals(u.getLogin()))) {
                sb.append(formatarPedidoPdf(p, todosItens)).append("\n");
                encontrou = true;
            }
        }

        if (!encontrou)
            return "Nenhum pedido encontrado para este usuario";

        return sb.toString();
    }

    // ==================== GERACAO DE PDF ====================

    public void gerarPdf(String titulo, String conteudo, LocalDate dataAtual) {
        String nomeArquivo = titulo.replaceAll("[^a-zA-Z0-9]", "_")
                           + "_"
                           + dataAtual.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                           + ".pdf";

        Document document = new Document(PageSize.A4, 50, 50, 70, 60);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(nomeArquivo));
            document.open();

            // ── Cores ───────────────────────────────────────────
            BaseColor corPrimaria   = new BaseColor(24,  54,  95);   // azul marinho
            BaseColor corSecundaria = new BaseColor(70, 130, 180);   // azul aço
            BaseColor corCinza      = new BaseColor(110, 110, 110);  // cinza médio
            BaseColor corCinzaClaro = new BaseColor(200, 210, 220);  // cinza azulado

            // ── Fontes ──────────────────────────────────────────
            Font fontTitulo  = FontFactory.getFont(FontFactory.HELVETICA_BOLD,   16, corPrimaria);
            Font fontDataGer = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, corCinza);
            Font fontCorpo   = FontFactory.getFont(FontFactory.HELVETICA,        10, BaseColor.BLACK);
            Font fontNegrito = FontFactory.getFont(FontFactory.HELVETICA_BOLD,   10, corPrimaria);
            Font fontSmall   = FontFactory.getFont(FontFactory.HELVETICA,         8, corCinza);

            // ── Cabeçalho ───────────────────────────────────────
            Paragraph pTitulo = new Paragraph(titulo.toUpperCase(), fontTitulo);
            pTitulo.setAlignment(Element.ALIGN_LEFT);
            pTitulo.setSpacingAfter(6);
            document.add(pTitulo);

            LineSeparator linhaTitulo =
                    new LineSeparator(2f, 100f, corSecundaria, Element.ALIGN_LEFT, -2f);
            document.add(new Chunk(linhaTitulo));

            Paragraph pGerado = new Paragraph(
                    "Gerado em: " + dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    fontDataGer);
            pGerado.setAlignment(Element.ALIGN_RIGHT);
            pGerado.setSpacingBefore(5);
            pGerado.setSpacingAfter(18);
            document.add(pGerado);

            // ── Conteúdo ────────────────────────────────────────
            String[] linhas = conteudo.trim().split("\n");
            boolean primeiraSecao = true;

            for (String linha : linhas) {
                if (linha.trim().isEmpty()) {
                    if (!primeiraSecao) {
                        LineSeparator sep =
                                new LineSeparator(0.5f, 100f, corCinzaClaro, Element.ALIGN_LEFT, -1f);
                        Paragraph pSep = new Paragraph();
                        pSep.setSpacingBefore(6);
                        pSep.setSpacingAfter(6);
                        pSep.add(new Chunk(sep));
                        document.add(pSep);
                    }
                    continue;
                }

                primeiraSecao = false;
                String linhaLimpa = linha.trim();

                // Linhas de destaque: campos que identificam o registro ou seus totais
                boolean ehDestaque = linhaLimpa.matches(
                        "(?i)^(id|pedido|produto|cliente|total|valor total|faturamento|data|status|cupom).*:.*"
                );

                Paragraph p = new Paragraph(linhaLimpa, ehDestaque ? fontNegrito : fontCorpo);
                p.setSpacingAfter(2);
                document.add(p);
            }

            // ── Rodapé ──────────────────────────────────────────
            document.add(Chunk.NEWLINE);
            LineSeparator linhaRodape =
                    new LineSeparator(0.5f, 100f, corCinzaClaro, Element.ALIGN_LEFT, -1f);
            document.add(new Chunk(linhaRodape));

            Paragraph pRodape = new Paragraph(
                    "Sistema de Gestao  \u00B7  Relatorio gerado em "
                    + dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    fontSmall);
            pRodape.setAlignment(Element.ALIGN_CENTER);
            pRodape.setSpacingBefore(5);
            document.add(pRodape);

            System.out.println("\nRelatorio PDF gerado: " + nomeArquivo);

        } catch (Exception e) {
            System.out.println("\nErro ao gerar PDF: " + e.getMessage());
        } finally {
            if (document.isOpen())
                document.close();
        }
    }
}
