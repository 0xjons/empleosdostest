package org.xjons.empleosdos.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xjons.empleosdos.model.Usuario;
import org.xjons.empleosdos.model.Vacante;
import org.xjons.empleosdos.service.IUsuariosService;
import org.xjons.empleosdos.service.IVacanteService;

@Controller
public final class HomeController {

	@Autowired
	private IVacanteService vs;

	@Autowired
	private IUsuariosService us;

	@GetMapping("/")
	public String mostrarHome(Model model) {
		return "index";
	}

	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "formRegistro";
	}
	
	@PostMapping("/signup")
	public String guardarRegistro(@ModelAttribute Usuario usuario, BindingResult result, RedirectAttributes attributes) {
	    // Primero verifica si el nombre de usuario ya existe
	    Usuario existente = us.buscarPorUsername(usuario.getUsername());
	    if (existente != null) {
	        // Agrega el error en el BindingResult para que se muestre en el formulario
	        result.rejectValue("username", "error.usuario", "El nombre de usuario ya está en uso");
	        // No redireccionas, sino que vuelves a mostrar el formulario con el error
	        return "formRegistro"; 
	    }
	    
	    // Establece el estatus activo y la fecha de registro
	    usuario.setEstatus(1); // Asumiendo que '1' significa activo en tu dominio
	    usuario.setFechaRegistro(new Date()); // Fecha actual
	    
	    // Intenta guardar el nuevo usuario
	    try {
	        us.guardar(usuario);
	        attributes.addFlashAttribute("msg", "El usuario ha sido guardado con éxito");
	    } catch (DataIntegrityViolationException e) {
	        attributes.addFlashAttribute("msgError", "Ha ocurrido un error al guardar el usuario.");
	    }
	    return "redirect:/usuarios/index";
	}


	/*
	 * @PostMapping("/signup") public String guardarRegistro(Usuario usuario,
	 * RedirectAttributes attributes) { usuario.setEstatus(1); // Activado por
	 * defecto usuario.setFechaRegistro(new Date()); // Fecha de Registro, la fecha
	 * actual del servidor
	 * 
	 * // Creamos el Perfil que le asignaremos al usuario nuevo Perfil perfil = new
	 * Perfil(); perfil.setId(3); // Perfil USUARIO usuario.agregar(perfil);
	 * 
	 *//**
				 * Guardamos el usuario en la base de datos. El Perfil se guarda automaticamente
				 *//*
							 * us.guardar(usuario);
							 * 
							 * attributes.addFlashAttribute("msg",
							 * "El registro fue guardado correctamente!");
							 * 
							 * return "redirect:/usuarios/index"; }
							 */

	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("vacantes", vs.buscarDestacadas());
	}

	@GetMapping("/detalle")
	public String mostrarDetalle(Model model) {
		Vacante vacante = new Vacante();
		vacante.setId(1);
		vacante.setNombre("Operador de procesos");
		vacante.setDescripcion("Se solicita ingeniero para dar soporte de IT");
		vacante.setFecha(new Date()); // Usando java.util.Date en lugar de LocalDateTime
		vacante.setSalario(15430.55);

		model.addAttribute("vacante", vacante);

		return "detalle";
	}
}
