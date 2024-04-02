package org.xjons.empleosdos.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitudes")
public class Solicitud {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Date fecha;
	private String comentarios;
	private String archivo;

	@OneToOne
	@JoinColumn(name = "idVacante") // fk en la taabla solicitudes
	private Vacante vacante;

	@OneToOne
	@JoinColumn(name = "idUsuario") // fk en la taabla solicitudes
	private Usuario usuario;

	
	
	public Solicitud() {
		// TODO Auto-generated constructor stub
	}



	public Solicitud(Date fecha) {
		super();
		this.fecha = fecha;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Date getFecha() {
		return fecha;
	}



	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}



	public String getComentarios() {
		return comentarios;
	}



	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}



	public String getArchivo() {
		return archivo;
	}



	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}



	public Vacante getVacante() {
		return vacante;
	}



	public void setVacante(Vacante vacante) {
		this.vacante = vacante;
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	@Override
	public String toString() {
		return "Solicitud [id=" + id + ", fecha=" + fecha + ", comentarios=" + comentarios + ", archivo=" + archivo
				+ ", vacante=" + vacante + ", usuario=" + usuario + "]";
	}
	
	

}
