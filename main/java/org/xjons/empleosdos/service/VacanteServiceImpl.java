package org.xjons.empleosdos.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.xjons.empleosdos.model.Vacante;

@Service
public class VacanteServiceImpl implements IVacanteService {

	private List<Vacante> lista = new LinkedList<Vacante>();

	public VacanteServiceImpl() {

		Vacante v1 = new Vacante();
		v1.setId(1);
		v1.setNombre("Ingeniero de Software");
		v1.setDescripcion("Se solicita ingeniero de software con experiencia en Java/Spring");
		v1.setFecha(new Date());
		v1.setSalario(25000.0);
		v1.setDestacado(1);
		v1.setImagen("empresa1.webp");
		v1.setEstatus("Aprobada");
		v1.setDetalles("Los detalles de la vacante...");

		Vacante v2 = new Vacante();
		v2.setId(2);
		v2.setNombre("Ingeniero Civil");
		v2.setDescripcion("Se solicita ingeniero civil para diseño de puente peatonal");
		v2.setFecha(new Date());
		v2.setSalario(35000.0);
		v2.setDestacado(0);
		v2.setImagen("empresa2.webp");
		v2.setEstatus("Creada");
		v2.setDetalles("Los detalles de la vacante...");

		Vacante v3 = new Vacante();
		v3.setId(3);
		v3.setNombre("Contador Público");
		v3.setDescripcion("Se requiere contador con experiencia en sector financiero");
		v3.setFecha(new Date());
		v3.setSalario(30000.0);
		v3.setDestacado(1);
		v3.setImagen("empresa3.webp");
		v3.setEstatus("Aprobada");
		v3.setDetalles("Los detalles de la vacante...");

		Vacante v4 = new Vacante();
		v4.setId(4);
		v4.setNombre("Diseñador Gráfico");
		v4.setDescripcion("Se busca diseñador gráfico para publicidad online");
		v4.setFecha(new Date());
		v4.setSalario(20000.0);
		v4.setDestacado(0);
		v4.setImagen("empresa4.webp");
		v4.setEstatus("Creada");
		v4.setDetalles("Los detalles de la vacante...");

		lista.add(v1);
		lista.add(v2);
		lista.add(v3);
		lista.add(v4);

		System.out.println("lista Ok");

		System.out.println(v3.getDescripcion() + "is null? Test");

	}

	@Override
	public List<Vacante> buscarTodas() {
		for (Vacante vacante : lista) {
			System.out.println("Vacamte pruiena desde buscarTodas: " + vacante.getNombre());
		}
		return lista;
	}

	@Override
	public Vacante buscarPorId(Integer idVacante) {

		for (Vacante vacante : lista) {
			if (vacante.getId() == idVacante) {
				return vacante;
			}
		}

		return null;
	}

	@Override
	public void guardar(Vacante vacante) {
		lista.add(vacante);

	}

	@Override
	public List<Vacante> buscarDestacadas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Integer idVacante) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Vacante> buscarDestacadas(int destacado, String estatus, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
