package org.xjons.empleosdos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xjons.empleosdos.model.Solicitud;
import org.xjons.empleosdos.repository.SolicitudesRepository;
import org.xjons.empleosdos.service.ISolicitudesService;

@Service
public class SolicitudesServiceJpa implements ISolicitudesService {

	@Autowired
	private SolicitudesRepository sr;

	public SolicitudesServiceJpa() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void guardar(Solicitud sol) {
		sr.save(sol);
	}

	@Override
	public void eliminar(Integer idSol) {
		sr.deleteById(idSol);
	}

	@Override
	public List<Solicitud> buscarTodas() {
		// TODO Auto-generated method stub
		return sr.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSol) {
		Optional<Solicitud> opt = sr.findById(idSol);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {

		return sr.findAll(page);
	}

}
