package br.com.tbiazin.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import br.com.tbiazin.Util.TipoDePagamentoEnum;
import br.com.tbiazin.domain.ProdutoVenda;
import br.com.tbiazin.domain.Venda;
import br.com.tbiazin.service.interfaces.ICupomFiscalService;

@Service
public class CupomFiscalServices implements ICupomFiscalService {

	
	

	
    @Override
    public String gerarCupomFiscal(Venda venda) {
        StringBuilder cupom = new StringBuilder();
        
        TipoDePagamentoEnum tipoPagamento = venda.getTipoPagamento(); 


        cupom.append("-------- CUPOM FISCAL --------\n");
        cupom.append("ID da Venda: ").append(venda.getId()).append("\n");
    	String dataVendaFormatada = venda.getDataVenda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    	String horaAtual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        cupom.append("Data da Venda: ").append(venda.getDataVenda().format(DateTimeFormatter.ofPattern(dataVendaFormatada + " " + horaAtual))).append("\n");
        cupom.append("Cliente: ").append(venda.getCliente().getNome()).append("\n");
        cupom.append("PDV: ").append(venda.getPdv().getNome()).append("\n");
        cupom.append("-----------------------------\n");
        cupom.append("Produtos:\n");

        DecimalFormat df = new DecimalFormat("#.00"); 

        BigDecimal total = BigDecimal.ZERO; 

        for (ProdutoVenda produtoVenda : venda.getProdutosVenda()) {
            BigDecimal preco = produtoVenda.getProduto().getPreco();
            int quantidade = produtoVenda.getQuantidade();
            BigDecimal quantidadeBigDecimal = BigDecimal.valueOf(quantidade);
            BigDecimal subtotal = preco.multiply(quantidadeBigDecimal);

            total = total.add(subtotal);

            cupom.append(produtoVenda.getProduto().getNome())
                    .append(" - Qtd: ").append(quantidade)
                    .append(" - Preço: R$").append(df.format(preco))
                    .append(" - Subtotal: R$").append(df.format(subtotal))
                    .append("\n");
        }

        cupom.append("-----------------------------\n");
        cupom.append("Total: R$").append(df.format(total)).append("\n");
        cupom.append("Forma de Pagamento: ").append(tipoPagamento.name()).append("\n");
        cupom.append("-----------------------------\n");
        cupom.append("Obrigado e volte sempre!\n");

        return cupom.toString();
    }
}
