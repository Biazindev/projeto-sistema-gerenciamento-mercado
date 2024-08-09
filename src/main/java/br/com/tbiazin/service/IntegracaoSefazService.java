package br.com.tbiazin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.tbiazin.domain.NotaFiscal;
import br.com.tbiazin.domain.ProdutoNotaFiscal;

@Service
public class IntegracaoSefazService {

    public void enviarNotaFiscal(NotaFiscal notaFiscal, String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.nuvemfiscal.com.br/v2/nfe";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> infNFe = criarInfNFe(notaFiscal);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("infNFe", infNFe);
        requestBody.put("ambiente", "producao");
        requestBody.put("referencia", notaFiscal.getId().toString());

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Nota Fiscal enviada com sucesso: " + response.getBody());
        } else {
            System.err.println("Erro ao enviar Nota Fiscal: " + response.getStatusCode());
        }
    }

    private Map<String, Object> criarInfNFe(NotaFiscal notaFiscal) {
        Map<String, Object> infNFe = new HashMap<>();
        
        infNFe.put("versao", "4.00");
        infNFe.put("Id", "NFe" + notaFiscal.getChaveAcesso());
        
        // Ide section
        Map<String, Object> ide = new HashMap<>();
        ide.put("cUF", "35"); // Código da UF do emitente do Documento Fiscal
        ide.put("cNF", "12345678"); // Código Numérico que compõe a Chave de Acesso
        ide.put("natOp", notaFiscal.getNaturezaOperacao());
        ide.put("mod", 55); // Código do modelo do Documento Fiscal
        ide.put("serie", 1); // Série do Documento Fiscal
        ide.put("nNF", notaFiscal.getId());
        ide.put("dhEmi", notaFiscal.getDataEmissao().toString());
        ide.put("tpNF", 1); // Tipo de Operação (0 - entrada, 1 - saída)
        ide.put("idDest", 1); // Identificador de destino da operação
        ide.put("cMunFG", "3550308"); // Código do Município de Ocorrência do Fato Gerador
        ide.put("tpImp", 1); // Formato de Impressão do DANFE
        ide.put("tpEmis", 1); // Tipo de Emissão da NF-e
        ide.put("cDV", 9); // Dígito Verificador da Chave de Acesso da NF-e
        ide.put("tpAmb", 2); // Identificação do Ambiente (1 - Produção, 2 - Homologação)
        ide.put("finNFe", 1); // Finalidade de emissão da NF-e
        ide.put("indFinal", 1); // Indicador de operação com consumidor final
        ide.put("indPres", 1); // Indicador de presença do comprador no estabelecimento comercial
        ide.put("procEmi", 0); // Processo de emissão utilizado com a NF-e
        ide.put("verProc", "1.0"); // Versão do processo de emissão da NF-e
        
        infNFe.put("ide", ide);
        
        // Emit section
        Map<String, Object> emit = new HashMap<>();
        emit.put("CNPJ", notaFiscal.getCnpjEmitente());
        emit.put("xNome", notaFiscal.getNomeEmitente());
        emit.put("xFant", notaFiscal.getNomeFantasiaEmitente());
        
        Map<String, Object> enderEmit = new HashMap<>();
        enderEmit.put("xLgr", notaFiscal.getLogradouroEmitente());
        enderEmit.put("nro", notaFiscal.getNumeroEmitente());
        enderEmit.put("xBairro", notaFiscal.getBairroEmitente());
        enderEmit.put("cMun", "3550308"); // Código do Município do Emitente
        enderEmit.put("xMun", notaFiscal.getMunicipioEmitente());
        enderEmit.put("UF", notaFiscal.getUfEmitente());
        enderEmit.put("CEP", notaFiscal.getCepEmitente());
        enderEmit.put("cPais", "1058"); // Código do País do Emitente
        enderEmit.put("xPais", "BRASIL");
        enderEmit.put("fone", notaFiscal.getTelefoneEmitente());
        
        emit.put("enderEmit", enderEmit);
        emit.put("IE", notaFiscal.getInscricaoEstadualEmitente());
        emit.put("CRT", 3); // Código de Regime Tributário
        
        infNFe.put("emit", emit);
        
        // Dest section
        Map<String, Object> dest = new HashMap<>();
        dest.put("CNPJ", notaFiscal.getCliente().getCpf());
        dest.put("xNome", notaFiscal.getCliente().getNome());
        
        Map<String, Object> enderDest = new HashMap<>();
        enderDest.put("xLgr", notaFiscal.getCliente().getRua());
        enderDest.put("nro", String.valueOf(notaFiscal.getCliente().getNumero()));
        enderDest.put("xBairro", notaFiscal.getCliente().getBairro());
        enderDest.put("cMun", "3550308"); // Código do Município do Destinatário
        enderDest.put("xMun", notaFiscal.getCliente().getCidade());
        enderDest.put("UF", notaFiscal.getCliente().getUf());
        enderDest.put("CEP", notaFiscal.getCliente().getCep());
        enderDest.put("cPais", "1058"); // Código do País do Destinatário
        enderDest.put("xPais", "BRASIL");
        enderDest.put("fone", notaFiscal.getCliente().getTelefone());
        
        dest.put("enderDest", enderDest);
        dest.put("indIEDest", 9); // Indicador da IE do Destinatário
        
        infNFe.put("dest", dest);
        
        // Det section
        List<Map<String, Object>> detList = new ArrayList<>();
        for (ProdutoNotaFiscal produtoNotaFiscal : notaFiscal.getProdutosNotaFiscal()) {
            Map<String, Object> det = new HashMap<>();
            det.put("nItem", produtoNotaFiscal.getId());
            
            Map<String, Object> prod = new HashMap<>();
            prod.put("cProd", produtoNotaFiscal.getProduto().getId().toString());
            prod.put("xProd", produtoNotaFiscal.getProduto().getNome());
            prod.put("NCM", "61091000"); // Código NCM do Produto
            prod.put("CFOP", "5102"); // Código Fiscal de Operações e Prestações
            prod.put("uCom", "UN"); // Unidade Comercial
            prod.put("qCom", produtoNotaFiscal.getQuantidade());
            prod.put("vUnCom", produtoNotaFiscal.getPrecoUnitario());
            prod.put("vProd", produtoNotaFiscal.getPrecoUnitario().multiply(new BigDecimal(produtoNotaFiscal.getQuantidade())));
            prod.put("uTrib", "UN"); // Unidade Tributável
            prod.put("qTrib", produtoNotaFiscal.getQuantidade());
            prod.put("vUnTrib", produtoNotaFiscal.getPrecoUnitario());
            prod.put("indTot", 1); // Indicador de Totalização do ICMS
            
            det.put("prod", prod);
            
            Map<String, Object> imposto = new HashMap<>();
            Map<String, Object> ICMS = new HashMap<>();
            Map<String, Object> ICMS00 = new HashMap<>();
            ICMS00.put("orig", 0); // Origem da Mercadoria
            ICMS00.put("CST", "00"); // Código da Situação Tributária
            ICMS00.put("modBC", 3); // Modalidade de Determinação da BC do ICMS
            ICMS00.put("vBC", produtoNotaFiscal.getPrecoUnitario().multiply(new BigDecimal(produtoNotaFiscal.getQuantidade()))); // Valor da BC do ICMS
            ICMS00.put("pICMS", new BigDecimal("18.00")); // Alíquota do ICMS
            ICMS00.put("vICMS", produtoNotaFiscal.getPrecoUnitario().multiply(new BigDecimal(produtoNotaFiscal.getQuantidade())).multiply(new BigDecimal("0.18"))); // Valor do ICMS
            
            ICMS.put("ICMS00", ICMS00);
            imposto.put("ICMS", ICMS);
            
            det.put("imposto", imposto);
            
            detList.add(det);
        }
        
        infNFe.put("det", detList);
        
        // Total section
        Map<String, Object> total = new HashMap<>();
        Map<String, Object> ICMSTot = new HashMap<>();
        ICMSTot.put("vBC", new BigDecimal(notaFiscal.getValorTotal()));
        ICMSTot.put("vICMS", new BigDecimal(notaFiscal.getValorTotal()).multiply(new BigDecimal("0.18")));
        ICMSTot.put("vICMSDeson", new BigDecimal("0.0"));
        ICMSTot.put("vFCPUFDest", new BigDecimal("0.0"));
        ICMSTot.put("vICMSUFDest", new BigDecimal("0.0"));
        ICMSTot.put("vICMSUFRemet", new BigDecimal("0.0"));
        ICMSTot.put("vFCP", new BigDecimal("0.0"));
        ICMSTot.put("vBCST", new BigDecimal("0.0"));
        ICMSTot.put("vST", new BigDecimal("0.0"));
        ICMSTot.put("vFCPST", new BigDecimal("0.0"));
        ICMSTot.put("vFCPSTRet", new BigDecimal("0.0"));
        ICMSTot.put("vProd", new BigDecimal(notaFiscal.getValorTotal()));
        ICMSTot.put("vFrete", new BigDecimal(notaFiscal.getValorFrete()));
        ICMSTot.put("vSeg", new BigDecimal(notaFiscal.getValorSeguro()));
        ICMSTot.put("vDesc", new BigDecimal(notaFiscal.getValorDesconto()));
        ICMSTot.put("vII", new BigDecimal("0.0"));
        ICMSTot.put("vIPI", new BigDecimal(notaFiscal.getValorIpi()));
        ICMSTot.put("vIPIDevol", new BigDecimal("0.0"));
        ICMSTot.put("vPIS", new BigDecimal(notaFiscal.getPisValorTotal()));
        ICMSTot.put("vCOFINS", new BigDecimal(notaFiscal.getCofinsValorTotal()));
        ICMSTot.put("vOutro", new BigDecimal(notaFiscal.getValorOutrasDespesas()));
        ICMSTot.put("vNF", new BigDecimal(notaFiscal.getValorTotal()));
        ICMSTot.put("vTotTrib", new BigDecimal(notaFiscal.getValorTotalTributos()));
        
        total.put("ICMSTot", ICMSTot);
        infNFe.put("total", total);
        
        // Transp section
        Map<String, Object> transp = new HashMap<>();
        transp.put("modFrete", notaFiscal.getModalidadeFrete());
        
        infNFe.put("transp", transp);
        
        return infNFe;
    }

}
