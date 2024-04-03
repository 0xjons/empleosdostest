package org.xjons.empleosdos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xjons.empleosdos.model.Perfil;


public interface PerfilesRepository extends JpaRepository<Perfil, Integer> {

	Optional<Perfil> findByPerfil(String tipo);

}
