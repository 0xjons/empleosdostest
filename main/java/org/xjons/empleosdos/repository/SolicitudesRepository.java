package org.xjons.empleosdos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xjons.empleosdos.model.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {

}
