package org.xjons.empleosdos.service;

import java.util.List;

import org.xjons.empleosdos.model.Usuario;

public interface IUsuariosService {
	
	void guardar(Usuario usuario);
	
	void eliminar(Integer idUsuario);
	
	List<Usuario> buscarTodos();
	
	Usuario buscarPorId(Integer idUsuario);
	
	Usuario buscarPorUsername(String username);

}
