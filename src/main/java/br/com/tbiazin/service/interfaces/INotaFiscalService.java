package br.com.tbiazin.service.interfaces;

import br.com.tbiazin.domain.NotaFiscal;

import java.util.List;

public interface INotaFiscalService {
    NotaFiscal emitirNotaFiscal(Long clienteId, List<Long> produtoIds, List<Integer> quantidades, String token);
    NotaFiscal buscarNotaFiscalPorId(Long id);
    List<NotaFiscal> listarNotasFiscais();
}
