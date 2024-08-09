package br.com.tbiazin.service.interfaces;

import java.util.List;
import br.com.tbiazin.domain.Venda;

public interface IVendaService {
    Venda createVenda(Venda venda);
    Venda updateVenda(Venda venda);
    void deleteVenda(Long id);
    Venda getVendaById(Long id);
    List<Venda> getAllVendas();
}
