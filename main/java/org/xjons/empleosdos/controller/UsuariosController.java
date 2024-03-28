package org.xjons.empleosdos.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
		// Asegúrate de que 'usuario' se agregue al modelo, ya que esto es lo que espera
		// la vista 'formRegistro'.
		model.addAttribute("usuario", usuario);
		return "formRegistro";
	}

//Método para guardar o actualizar un usuario
	@PostMapping("/save")
	public String guardar(@ModelAttribute Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		// Si el usuario tiene un ID, es una edición, de lo contrario es una creación
		if (usuario.getId() != null) {
			Usuario usuarioExistente = us.buscarPorId(usuario.getId());
			usuario.setPassword(usuarioExistente.getPassword());
			if (usuarioExistente != null) {
				// Actualiza los datos que se permiten cambiar
				usuarioExistente.setNombre(usuario.getNombre());
				usuarioExistente.setEmail(usuario.getEmail());
				// Agrega otros campos que desees actualizar
				usuario = usuarioExistente;
			}
		} else {
			// Para un nuevo usuario, establece el estatus y la fecha de registro
			usuario.setEstatus(1); // Activo
			usuario.setFechaRegistro(new Date());
		}

		// Intenta guardar el usuario
		try {
			us.guardar(usuario);
			attributes.addFlashAttribute("msg", "Los cambios fueron guardados con éxito");
		} catch (DataIntegrityViolationException e) {
			attributes.addFlashAttribute("msgError", "Error al guardar el usuario");
		}

		return "redirect:/usuarios/index";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idUsuario, Model model) {
		Usuario usuario = us.buscarPorId(idUsuario);
		model.addAttribute("usuario", usuario);
		return "formRegistro";
	}

	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("usuarios", us.buscarTodos());
	}

}
