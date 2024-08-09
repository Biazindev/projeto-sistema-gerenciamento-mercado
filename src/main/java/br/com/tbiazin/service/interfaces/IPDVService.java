package br.com.tbiazin.service.interfaces;

import java.util.List;

import br.com.tbiazin.domain.PDV;

public interface IPDVService {
    PDV createPDV(PDV pdv);
    PDV updatePDV(PDV pdv);
    void deletePDV(Long id);
    PDV getPDVById(Long id);
    List<PDV> getAllPDVs();
}
