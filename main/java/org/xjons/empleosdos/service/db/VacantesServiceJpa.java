package org.xjons.empleosdos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xjons.empleosdos.model.Vacante;
import org.xjons.empleosdos.repository.VacantesRepository;
import org.xjons.empleosdos.service.IVacanteService;

@Service
@Primary
public final class VacantesServiceJpa implements IVacanteService {

	@Autowired
	private VacantesRepository vacantesRepo;

	public VacantesServiceJpa() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Vacante> buscarTodas() {
		// TODO Auto-generated method stub
		return vacantesRepo.findAll();
	}

	@Override
	public Vacante buscarPorId(Integer idVacante) {
		// TODO Auto-generated method stub
		Optional<Vacante> opt = vacantesRepo.findById(idVacante);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	@Override
	public void guardar(Vacante vacante) {
		// TODO Auto-generated method stub
		vacantesRepo.save(vacante);
	}

	@Override
	public List<Vacante> buscarDestacadas() {
		// TODO Auto-generated method stub
		return vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
	}

	@Override
	public void eliminar(Integer idVacante) {
		vacantesRepo.deleteById(idVacante);

	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		// TODO Auto-generated method stub
		return vacantesRepo.findAll(example);
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return vacantesRepo.findAll(page);
	}
	
 @Override
 public Page<Vacante> buscarDestacadas(int destacado, String estatus, Pageable pageable) {
     return vacantesRepo.findByDestacadoAndEstatusOrderByIdDesc(destacado, estatus, pageable);
 }

}
