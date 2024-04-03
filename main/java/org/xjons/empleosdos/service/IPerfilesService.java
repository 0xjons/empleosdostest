package org.xjons.empleosdos.service;

import java.util.List;

import org.xjons.empleosdos.model.Perfil;

public interface IPerfilesService {

 Perfil buscarPorTipo(String tipo);
 List<Perfil> buscarTodos();
}
