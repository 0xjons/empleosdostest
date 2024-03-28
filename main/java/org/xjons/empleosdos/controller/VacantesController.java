package org.xjons.empleosdos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.xjons.empleosdos.model.Categoria;
import org.xjons.empleosdos.model.Vacante;
import org.xjons.empleosdos.service.ICategoriasService;
import org.xjons.empleosdos.service.IVacanteService;
import org.xjons.empleosdos.utils.UtilesArchivos;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {

	@Value("${empleosdos.ruta.imagenes}")
	private String ruta;

	@Autowired
	private IVacanteService vs;
	@Autowired
	private ICategoriasService cs;

	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante> lista = vs.buscarTodas();
		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";
	}

	@GetMapping("/create")
	public String crear(Vacante vacante, Model model) {
		return "vacantes/formVacante";
	}

	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes ra,
			@RequestParam("archivoImagen") MultipartFile multiPart, Model model) {

		if (vacante.getCategoria() == null || vacante.getCategoria().getId() == null
				|| "none".equals(vacante.getCategoria().getId().toString())) {
			result.rejectValue("categoria.id", "required", "La categoría es obligatoria");
			ra.addFlashAttribute("errorCategoria", "La categoría es obligatoria");
		}

		if (vacante.getSalario() != null) {
			String salarioStr = vacante.getSalario().toString();
			if (!salarioStr.matches("\\d+(\\.\\d{1,2})?")) {
				result.rejectValue("salario", "Invalid.salario", "El salario debe ser un número válido con hasta dos decimales.");
				ra.addFlashAttribute("errorSalarioFormato", "El salario debe ser un número válido con hasta dos decimales.");
			}
		} else {
			result.rejectValue("salario", "NotNull.vacante.salario", "El salario no puede ser nulo.");
			ra.addFlashAttribute("errorSalario", "El salario no puede ser nulo.");
		}

		if (result.hasErrors()) {
			model.addAttribute("categorias", cs.buscarTodas());
			ra.addFlashAttribute("errors", result.getAllErrors());
			return "vacantes/formVacante"; // Asumiendo que "nueva" es la ruta al formulario de vacantes
		}

		if (!multiPart.isEmpty()) { //
			// String ruta = "c:/tmp_empleosapp/img-vacantes/"; // Linux/MAC String ruta =
			// "c:/tmp_empleosapp/img-vacantes/"; // Windows
			// Verifica si el tamaño del archivo es mayor a 1200 KB
			if (multiPart.getSize() > 1200 * 1024) {
				ra.addFlashAttribute("msg", "El tamaño del archivo excede el máximo permitido (1200 KB)");
				return "vacantes/formVacante";
			}

			try {
				String nombreImagen = UtilesArchivos.guardarArchivo(multiPart, ruta);
				if (nombreImagen != null) { // La imagen si se subio
					vacante.setImagen(nombreImagen);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ra.addFlashAttribute("msg", "Error al guardar la imagen");
				return "vacantes/formVacante";
			}
		} else {
			// Si no se carga una nueva imagen y estamos editando (id existe), conserva la
			// imagen actual
			if (vacante.getId() != null && vacante.getId() > 0) {
				Vacante vacanteExistente = vs.buscarPorId(vacante.getId());
				vacante.setImagen(vacanteExistente.getImagen()); // Conserva la imagen existente
			}
		}

		vs.guardar(vacante);
		ra.addFlashAttribute("msg", "Registro Guardado");
		System.out.println("Guardando la siguiente información: ");
		System.out.println(vacante.mostrarInfoVacante());

		return "redirect:/vacantes/index";
	}

	@GetMapping("/delete/{id}")
	public String eliminar(@PathVariable("id") int idVacante, Model model) {
		System.out.println("Borrando vacante con id: " + idVacante);

		// eliminar de la base de datos
		vs.eliminar(idVacante);

		return "redirect:/vacantes/index";
	}

	@GetMapping("/edit/{id}")
	public String editar(@PathVariable("id") int idVacante, Model model) {

		Vacante vacante = vs.buscarPorId(idVacante);
		model.addAttribute("vacante", vacante);

		return "vacantes/formVacante";
	}

	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante, Model model) {

		Vacante v = vs.buscarPorId(idVacante);

		System.out.println("Vacante: " + v);
		model.addAttribute("vacante", v);
		return "detalle";
	}

	@ModelAttribute
	public void setGenericos(Model model) {
		model.addAttribute("categorias", cs.buscarTodas());
	}

	@InitBinder
	public void initBinder(WebDataBinder wdb) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		wdb.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
	}
}
