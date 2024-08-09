package br.com.tbiazin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tbiazin.domain.PDV;
import br.com.tbiazin.repository.IPDVRepository;
import br.com.tbiazin.service.interfaces.IPDVService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PDVServices implements IPDVService {

	@Autowired
	private final IPDVRepository pdvRepository;

	public PDVServices(IPDVRepository pdvRepository) {
		this.pdvRepository = pdvRepository;
	}

	@Override
	public PDV createPDV(PDV pdv) {
		return pdvRepository.save(pdv);
	}

	@Override
	public PDV updatePDV(PDV pdv) {
		return pdvRepository.save(pdv);
	}

	@Override
	public void deletePDV(Long id) {
		pdvRepository.deleteById(id);
	}

	@Override
	public List<PDV> getAllPDVs() {
		return pdvRepository.findAll();
	}

	@Override
	public PDV getPDVById(Long id) {
	    Optional<PDV> optionalPdv = pdvRepository.findById(id);
	    return optionalPdv.orElseThrow(() -> new EntityNotFoundException("PDV não encontrado com o ID: " + id));
	}
}
