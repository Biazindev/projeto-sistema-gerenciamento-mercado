package br.com.tbiazin.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.tbiazin.domain.Cliente;
import br.com.tbiazin.domain.NotaFiscal;
import br.com.tbiazin.domain.Produto;
import br.com.tbiazin.domain.ProdutoNotaFiscal;
import br.com.tbiazin.repository.IClienteRepository;
import br.com.tbiazin.repository.INotaFiscalRepository;
import br.com.tbiazin.repository.IProdutoNotaFiscalRepository;
import br.com.tbiazin.repository.IProdutoRepository;
import br.com.tbiazin.service.interfaces.INotaFiscalService;

@Service
public class NotaFiscalService implements INotaFiscalService{

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private INotaFiscalRepository notaFiscalRepository;

    @Autowired
    private IProdutoRepository produtoRepository;

    @Autowired
    private IProdutoNotaFiscalRepository produtoNotaFiscalRepository;

    @Transactional
    @Override
    public NotaFiscal emitirNotaFiscal(Long clienteId, List<Long> produtoIds, List<Integer> quantidades, String token) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        double valorTotal = 0;
        NotaFiscal notaFiscal = NotaFiscal.builder()
                .cliente(cliente)
                .dataEmissao(LocalDate.now())
                .status("PENDENTE")
                .naturezaOperacao("Venda de Produtos")
                .formaPagamento("0")
                .tipoDocumento("1")
                .finalidadeEmissao("1")
                .cnpjEmitente("12345678000123")
                .nomeEmitente("Nome da Empresa Emitente")
                .nomeFantasiaEmitente("Nome Fantasia Emitente")
                .logradouroEmitente("Rua do Emitente")
                .numeroEmitente("1000")
                .bairroEmitente("Bairro Emitente")
                .municipioEmitente("Cidade Emitente")
                .ufEmitente("SP")
                .cepEmitente("01001000")
                .telefoneEmitente("11999999999")
                .inscricaoEstadualEmitente("123456789")
                .valorFrete(0.0)
                .valorSeguro(0.0)
                .valorDesconto(0.0)
                .valorIpi(0.0)
                .modalidadeFrete("1")
                .icmsBaseCalculo(0.0)
                .icmsValorTotal(0.0)
                .icmsBaseCalculoSt(0.0)
                .icmsValorTotalSt(0.0)
                .icmsModalidadeBaseCalculo("0")
                .icmsValor(0.0)
                .pisValorTotal(0.0)
                .cofinsValorTotal(0.0)
                .valorOutrasDespesas(0.0)
                .valorTotalTributos(0.0)
                .chaveAcesso("chave_de_acesso")
                .protocoloAutorizacao("protocolo_de_autorizacao")
                .produtosNotaFiscal(new ArrayList<>()) // Inicializando a lista aqui
                .build();

        notaFiscal = notaFiscalRepository.save(notaFiscal);

        for (int i = 0; i < produtoIds.size(); i++) {
            Long produtoId = produtoIds.get(i);
            int quantidade = quantidades.get(i);
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + produtoId));

            ProdutoNotaFiscal produtoNotaFiscal = ProdutoNotaFiscal.builder()
                    .notaFiscal(notaFiscal)
                    .produto(produto)
                    .quantidade(quantidade)
                    .precoUnitario(produto.getPreco())
                    .build();

            produtoNotaFiscalRepository.save(produtoNotaFiscal);
            notaFiscal.getProdutosNotaFiscal().add(produtoNotaFiscal);
            valorTotal += produto.getPreco().doubleValue() * quantidade;
        }

        notaFiscal.setValorTotal(valorTotal);
        notaFiscalRepository.save(notaFiscal);

        // Enviando para Nuvem Fiscal
        enviarNotaFiscalParaNuvemFiscal(notaFiscal, token);

        return notaFiscal;
    }


    private void enviarNotaFiscalParaNuvemFiscal(NotaFiscal notaFiscal, String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> infNFe = criarInfNFe(notaFiscal);
        Map<String, Object> payload = new HashMap<>();
        payload.put("infNFe", infNFe);
        payload.put("ambiente", "homologacao");
        payload.put("referencia", "string");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "https://api.sandbox.nuvemfiscal.com.br/nfe/emitir",
                HttpMethod.POST,
                requestEntity,
                String.class
            );
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Nota fiscal enviada com sucesso: " + response.getBody());
            } else {
                System.out.println("Falha ao enviar a nota fiscal: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            System.err.println("Erro ao enviar a nota fiscal: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        }
    }


    public List<NotaFiscal> listarNotasFiscais() {
        return notaFiscalRepository.findAll();
    }
    
    private Map<String, Object> criarInfNFe(NotaFiscal notaFiscal) {
        Map<String, Object> infNFe = new HashMap<>();

        // Adicionando informações básicas
        infNFe.put("versao", "4.00");
        infNFe.put("Id", "NFe" + notaFiscal.getChaveAcesso());

        // Adicionando informações de identificação
        Map<String, Object> ide = new HashMap<>();
        ide.put("cUF", 35); // Código da UF de SP
        ide.put("cNF", "12345678"); // Código numérico
        ide.put("natOp", notaFiscal.getNaturezaOperacao());
        ide.put("mod", 55); // Modelo da NFe
        ide.put("serie", 1); // Série da NFe
        ide.put("nNF", 1); // Número da NFe
        ide.put("dhEmi", notaFiscal.getDataEmissao().toString());
        ide.put("tpNF", 1); // Tipo de operação (1 = Saída)
        ide.put("idDest", 1); // Identificador de destino da operação (1 = Operação interna)
        ide.put("cMunFG", "3550308"); // Código do município de SP
        ide.put("tpImp", 1); // Formato de impressão (1 = Retrato)
        ide.put("tpEmis", 1); // Tipo de emissão (1 = Normal)
        ide.put("cDV", "1"); // Dígito verificador
        ide.put("tpAmb", 2); // Ambiente (2 = Homologação)
        ide.put("finNFe", notaFiscal.getFinalidadeEmissao());
        ide.put("indFinal", 1); // Indicador de operação final (1 = Consumidor final)
        ide.put("indPres", 1); // Indicador de presença (1 = Operação presencial)
        ide.put("procEmi", 0); // Processo de emissão (0 = Emissão de NF-e com aplicativo do contribuinte)
        ide.put("verProc", "1.0"); // Versão do aplicativo de emissão

        infNFe.put("ide", ide);

        // Adicionando informações do emitente
        Map<String, Object> emit = new HashMap<>();
        emit.put("CNPJ", notaFiscal.getCnpjEmitente());
        emit.put("xNome", notaFiscal.getNomeEmitente());
        emit.put("xFant", notaFiscal.getNomeFantasiaEmitente());
        Map<String, Object> enderEmit = new HashMap<>();
        enderEmit.put("xLgr", notaFiscal.getLogradouroEmitente());
        enderEmit.put("nro", notaFiscal.getNumeroEmitente());
        enderEmit.put("xBairro", notaFiscal.getBairroEmitente());
        enderEmit.put("cMun", "3550308");
        enderEmit.put("xMun", notaFiscal.getMunicipioEmitente());
        enderEmit.put("UF", notaFiscal.getUfEmitente());
        enderEmit.put("CEP", notaFiscal.getCepEmitente());
        enderEmit.put("cPais", "1058");
        enderEmit.put("xPais", "Brasil");
        enderEmit.put("fone", notaFiscal.getTelefoneEmitente());
        emit.put("enderEmit", enderEmit);
        emit.put("IE", notaFiscal.getInscricaoEstadualEmitente());

        infNFe.put("emit", emit);

        // Adicionando informações dos produtos
        List<Map<String, Object>> detList = new ArrayList<>();
        for (ProdutoNotaFiscal produtoNotaFiscal : notaFiscal.getProdutosNotaFiscal()) {
            Map<String, Object> det = new HashMap<>();
            det.put("nItem", produtoNotaFiscal.getId());

            Map<String, Object> prod = new HashMap<>();
            prod.put("cProd", produtoNotaFiscal.getProduto().getId().toString());
            prod.put("xProd", produtoNotaFiscal.getProduto().getNome());
            prod.put("qCom", produtoNotaFiscal.getQuantidade());
            prod.put("vUnCom", produtoNotaFiscal.getPrecoUnitario());
            prod.put("vProd", produtoNotaFiscal.getQuantidade() * produtoNotaFiscal.getPrecoUnitario().doubleValue());
            prod.put("uCom", "UN");

            det.put("prod", prod);

            // Adicionando impostos (exemplo de ICMS)
            Map<String, Object> imposto = new HashMap<>();
            Map<String, Object> ICMS = new HashMap<>();
            Map<String, Object> ICMS00 = new HashMap<>();
            ICMS00.put("orig", 0);
            ICMS00.put("CST", "00");
            ICMS00.put("modBC", 0);
            ICMS00.put("vBC", produtoNotaFiscal.getPrecoUnitario());
            ICMS00.put("pICMS", 18);
            ICMS00.put("vICMS", produtoNotaFiscal.getPrecoUnitario().multiply(BigDecimal.valueOf(0.18)));
            ICMS.put("ICMS00", ICMS00);
            imposto.put("ICMS", ICMS);

            det.put("imposto", imposto);

            detList.add(det);
        }
        infNFe.put("det", detList);

        // Adicionando totais
        Map<String, Object> total = new HashMap<>();
        Map<String, Object> ICMSTot = new HashMap<>();
        ICMSTot.put("vBC", notaFiscal.getIcmsBaseCalculo());
        ICMSTot.put("vICMS", notaFiscal.getIcmsValorTotal());
        ICMSTot.put("vProd", notaFiscal.getValorTotal());
        ICMSTot.put("vNF", notaFiscal.getValorTotal());

        total.put("ICMSTot", ICMSTot);
        infNFe.put("total", total);

        return infNFe;
    }


	@Override
	public NotaFiscal buscarNotaFiscalPorId(Long id) {
		return null;
	}

}
