package org.xjons.empleosdos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xjons.empleosdos.model.Categoria;
import org.xjons.empleosdos.repository.CategoriasRepository;
import org.xjons.empleosdos.service.ICategoriasService;

@Service
@Primary
public final class CategoriasServiceJpa implements ICategoriasService {

	@Autowired
	private CategoriasRepository catRepo;

	public CategoriasServiceJpa() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void guardar(Categoria categoria) {
		catRepo.save(categoria);
	}

	@Override
	public List<Categoria> buscarTodas() {
		// TODO Auto-generated method stub
		return catRepo.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
Optional<Categoria> opt =catRepo.findById(idCategoria);
		if(opt.isPresent()) {
			System.out.println();
			return opt.get();
		}
		return null;
	}

	@Override
	public void eliminar(Integer idCat) {
		catRepo.deleteById(idCat);
		
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return catRepo.findAll(page);
	}

}
