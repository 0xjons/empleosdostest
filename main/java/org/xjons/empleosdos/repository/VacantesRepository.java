package org.xjons.empleosdos.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.xjons.empleosdos.model.Vacante;


public interface VacantesRepository extends JpaRepository<Vacante, Integer> {

	List<Vacante> findByEstatus(String estatus);
	
	
	List<Vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado, String estatus	);
	
 Page<Vacante> findByDestacadoAndEstatusOrderByIdDesc(int destacado, String estatus, Pageable pageable);

}
