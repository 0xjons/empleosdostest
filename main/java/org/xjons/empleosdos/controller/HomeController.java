package org.xjons.empleosdos.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xjons.empleosdos.model.Usuario;
import org.xjons.empleosdos.model.Vacante;
import org.xjons.empleosdos.service.ICategoriasService;
import org.xjons.empleosdos.service.IUsuariosService;
import org.xjons.empleosdos.service.IVacanteService;

import jakarta.servlet.http.HttpSession;

@Controller
public final class HomeController {

	@Autowired
	private IVacanteService vs;

	@Autowired
	private IUsuariosService us;

	@Autowired
	private ICategoriasService cs;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String mostrarHome(Model model) {
		return "index";
	}

	@GetMapping("/home")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		String username = auth.getName();
		System.out.println("Nombre del usuario: " + username);

		for (GrantedAuthority rol : auth.getAuthorities()) {
			System.out.println("ROL: " + rol.getAuthority());
		}

		if (session.getAttribute("usuario") == null) {
			Usuario usuario = us.buscarPorUsername(username);
			usuario.setPassword(null); //no almacenamos el pass en la sesion
			session.setAttribute("usuario", usuario);
		}

		return "redirect:/";
	}

	@GetMapping("/signup")
	public String registrarse(Usuario usuario) {
		return "formRegistro";
	}

	@PostMapping("/signup")
	public String guardarRegistro(@ModelAttribute Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		// Primero verifica si el nombre de usuario ya existe
		Usuario existente = us.buscarPorUsername(usuario.getUsername());
		
		String pwdPlano = usuario.getPassword();
		String pwdEncriptado = passwordEncoder.encode(pwdPlano);
		
		if (existente != null) {
			// Agrega el error en el BindingResult para que se muestre en el formulario
			result.rejectValue("username", "error.usuario", "El nombre de usuario ya está en uso");
			// No redireccionas, sino que vuelves a mostrar el formulario con el error
			return "formRegistro";
		}

		// Establece el estatus activo y la fecha de registro
		usuario.setEstatus(1); // Asumiendo que '1' significa activo en tu dominio
		usuario.setFechaRegistro(new Date()); // Fecha actual
		usuario.setPassword(pwdEncriptado);

		// Intenta guardar el nuevo usuario
		try {
			us.guardar(usuario);
			attributes.addFlashAttribute("msg", "El usuario ha sido guardado con éxito");
		} catch (DataIntegrityViolationException e) {
			attributes.addFlashAttribute("msgError", "Ha ocurrido un error al guardar el usuario.");
		}
		return "redirect:/";
	}

	@GetMapping("/search")
	public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {
		System.out.println("Buscando por : " + vacante);

		// para que no se use el = en el select, sino un 'like' %?%
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("descripcion",
				ExampleMatcher.GenericPropertyMatchers.contains());

		Example<Vacante> example = Example.of(vacante, matcher);
		List<Vacante> lista = vs.buscarByExample(example);

		model.addAttribute("vacantes", lista);
		return "index";
	}

	@ModelAttribute
	public void setGenericos(Model model) {
		Vacante vacanteSearch = new Vacante();

		model.addAttribute("vacantes", vs.buscarDestacadas());
		model.addAttribute("categorias", cs.buscarTodas());
		model.addAttribute("search", vacanteSearch);
	}

	/*
	 * InitBinder para String si los detecta vacios en el Data Binding los settea a
	 * NULL
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder wdb) {
		wdb.registerCustomEditor(String.class, new StringTrimmerEditor(true));
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
