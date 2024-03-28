package org.xjons.empleosdos.service;

import java.util.List;

import org.xjons.empleosdos.model.Categoria;

public interface ICategoriasService {
	void guardar(Categoria categoria);
	List<Categoria> buscarTodas();
	Categoria buscarPorId(Integer idCategoria);	
	
	void eliminar(Integer idCat);
}