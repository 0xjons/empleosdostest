package org.xjons.empleosdos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.xjons.empleosdos.model.Categoria;

public interface ICategoriasService {
	void guardar(Categoria categoria);
	List<Categoria> buscarTodas();
	Page<Categoria> buscarTodas(Pageable page);
	
	Categoria buscarPorId(Integer idCategoria);	
	
	void eliminar(Integer idCat);
}