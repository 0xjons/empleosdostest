package org.xjons.empleosdos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.xjons.empleosdos.model.Usuario;
import org.xjons.empleosdos.repository.UsuariosRepository;
import org.xjons.empleosdos.service.IUsuariosService;

@Service
@Primary
public class UsuarioServiceJpa implements IUsuariosService {

	@Autowired
	private UsuariosRepository usersRepo;
	
	public UsuarioServiceJpa() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void guardar(Usuario usuario) {
		usersRepo.save(usuario);
	}

	@Override
	public void eliminar(Integer idUsuario) {
		usersRepo.deleteById(idUsuario);
	}

	@Override
	public List<Usuario> buscarTodos() {
		// TODO Auto-generated method stub
		return usersRepo.findAll();
	}

	@Override
	public Usuario buscarPorId(Integer idUsuario) {
		Optional<Usuario> opt = usersRepo.findById(idUsuario);
		if (opt.isPresent()) {
			System.out.println();
			return opt.get();
		}
		return null;
	}

	@Override
	public Usuario buscarPorUsername(String username) {
		// TODO Auto-generated method stub
		return usersRepo.findByUsername(username);
	}

}
