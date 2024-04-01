package org.xjons.empleosdos.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.xjons.empleosdos.model.Vacante;

public interface IVacanteService {

	List<Vacante> buscarTodas();
	
	Vacante buscarPorId(Integer idVacante);
	
	void guardar(Vacante vacante);
	
	void eliminar(Integer idVacante);
	
	List<Vacante> buscarDestacadas();
	
	List<Vacante> buscarByExample(Example<Vacante> example);
	
	Page<Vacante> buscarTodas(Pageable page);
}
