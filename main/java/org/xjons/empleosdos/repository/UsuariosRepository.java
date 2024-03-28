package org.xjons.empleosdos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xjons.empleosdos.model.Usuario;


public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

	
	Usuario findByUsername(String username);
}
