package org.xjons.empleosdos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.xjons.empleosdos.model.Solicitud;

public interface ISolicitudesService {

	void guardar(Solicitud sol);
	void eliminar(Integer idSol);
	
	List<Solicitud> buscarTodas();
	
	Solicitud buscarPorId(Integer idSol);
	Page<Solicitud> buscarTodas(Pageable page);
}
