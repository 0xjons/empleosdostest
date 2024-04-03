package org.xjons.empleosdos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xjons.empleosdos.model.Perfil;
import org.xjons.empleosdos.repository.PerfilesRepository;
import org.xjons.empleosdos.service.IPerfilesService;

@Service
public class PerfilesServiceJpa implements IPerfilesService {
	
	@Autowired
	private PerfilesRepository pr;

	public PerfilesServiceJpa() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Perfil buscarPorTipo(String tipo) {
	    return pr.findByPerfil(tipo).orElse(null);
	}


	@Override
 public List<Perfil> buscarTodos() {
  return pr.findAll();
}

}
