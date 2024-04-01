package org.xjons.empleosdos.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xjons.empleosdos.model.Usuario;
import org.xjons.empleosdos.service.IUsuariosService;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private IUsuariosService us;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		return "usuarios/listUsuarios";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {

		try {
			us.eliminar(idUsuario);
			attributes.addFlashAttribute("msg", "El usuario fue eliminado con éxito");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/usuarios/index";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String crear(Usuario usuario, Model model) {
		return "formRegistro";
	}

//Método para guardar o actualizar un usuario
	@PostMapping("/save")
	public String guardar(@ModelAttribute Usuario usuario, BindingResult result, RedirectAttributes attributes) {
	    if (result.hasErrors()) {
	        return "formRegistro";
	    }
	    
	  		String pwdPlano = usuario.getPassword();
	  		String pwdEncriptado = passwordEncoder.encode(pwdPlano);

	    try {
	        if (usuario.getId() != null) {
	            // Es una actualización
	            Usuario usuarioExistente = us.buscarPorId(usuario.getId());
	            if (usuarioExistente != null) {
	                usuarioExistente.setNombre(usuario.getNombre());
	                usuarioExistente.setEmail(usuario.getEmail());
	                usuarioExistente.setUsername(usuario.getUsername()); // Asegúrate de que esto se maneje correctamente
	                usuarioExistente.setPassword(usuarioExistente.getPassword()); // No cambies la contraseña aquí

	                // Mantén el estatus existente si no se está modificando en el formulario
	                if (usuario.getEstatus() == null) {
	                    usuario.setEstatus(usuarioExistente.getEstatus());
	                } else {
	                    usuarioExistente.setEstatus(usuario.getEstatus());
	                }

	                // Guarda el usuario existente actualizado
	                us.guardar(usuarioExistente);
	            }
	        } else {
	            // Es un nuevo usuario
	            usuario.setFechaRegistro(new Date());
	            usuario.setEstatus(1); // Asegúrate de establecer un estatus por defecto
	            usuario.setPassword(pwdEncriptado);
	            us.guardar(usuario);
	        }
	        attributes.addFlashAttribute("msg", "Los cambios fueron guardados con éxito");
	    } catch (DataIntegrityViolationException e) {
	        attributes.addFlashAttribute("msgError", "Error al guardar el usuario: " + e.getMessage());
	    }

	    return "redirect:/";
	}

	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idUsuario, Model model) {
		Usuario usuario = us.buscarPorId(idUsuario);
		model.addAttribute("usuario", usuario);
		return "formRegistro";
	}
	
	@GetMapping("/updateStatus/{id}/{status}")
	public String actualizarEstado(@PathVariable("id") int idUsuario, @PathVariable("status") int nuevoEstado, RedirectAttributes attributes) {
	    try {
	        Usuario usuario = us.buscarPorId(idUsuario);
	        if (usuario != null) {
	            usuario.setEstatus(nuevoEstado);
	            us.guardar(usuario);
	            attributes.addFlashAttribute("msg", "El estado del usuario ha sido actualizado con éxito");
	        } else {
	            attributes.addFlashAttribute("msgError", "Usuario no encontrado");
	        }
	    } catch (Exception e) {
	        attributes.addFlashAttribute("msgError", "Error al actualizar el estado del usuario");
	    }
	    return "redirect:/usuarios/index";
	}


	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("usuarios", us.buscarTodos());
	}

}
