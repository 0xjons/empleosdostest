package org.xjons.empleosdos.service;

import java.util.List;

import org.xjons.empleosdos.model.Vacante;

public interface IVacanteService {

	List<Vacante> buscarTodas();
	
	Vacante buscarPorId(Integer idVacante);
	
	void guardar(Vacante vacante);
	
	void eliminar(Integer idVacante);
	
	List<Vacante> buscarDestacadas();
}
