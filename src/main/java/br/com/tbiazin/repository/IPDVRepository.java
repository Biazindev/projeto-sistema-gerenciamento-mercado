package br.com.tbiazin.repository;

import br.com.tbiazin.domain.PDV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPDVRepository extends JpaRepository<PDV, Long> {
    // Métodos personalizados, se houver
}
