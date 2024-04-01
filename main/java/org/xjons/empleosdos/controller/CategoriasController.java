package org.xjons.empleosdos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xjons.empleosdos.model.Categoria;
import org.xjons.empleosdos.service.ICategoriasService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {
	
	@Autowired
   	private ICategoriasService serviceCategorias;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String mostrarIndex(Model model, Pageable page) {
		Page<Categoria> lista = serviceCategorias.buscarTodas(page);
    	model.addAttribute("categorias", lista);
		return "categorias/listCategorias";		
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String crear(Categoria categoria) {
		return "categorias/formCategorias";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String guardar(Categoria categoria, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()){		
			System.out.println("Existieron errores");
			return "categorias/formCategorias";
		}	
		
		// Guadamos el objeto categoria en la bd
		serviceCategorias.guardar(categoria);
		attributes.addFlashAttribute("msg", "Los datos de la categoría fueron guardados!");		
		return "redirect:/categorias/index";
	}

	
	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idCat, RedirectAttributes attributes) {
	    try {
	        serviceCategorias.eliminar(idCat);
	        attributes.addFlashAttribute("msg", "La categoría fue eliminada con éxito");
	    } catch (DataIntegrityViolationException e) {
	        attributes.addFlashAttribute("msgError", "No se puede eliminar la categoría porque tiene vacantes asociadas");
	    }
	    return "redirect:/categorias/index";
	}
	
	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idCat, Model model) {

		Categoria cat = serviceCategorias.buscarPorId(idCat);
		model.addAttribute("categoria", cat);

		return "categorias/formCategorias";
	}
	
	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
	}
}
	