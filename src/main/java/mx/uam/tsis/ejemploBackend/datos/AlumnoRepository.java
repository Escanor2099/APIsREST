package mx.uam.tsis.ejemploBackend.datos;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;

/**
 * Se encarga de alamacenar y recuperar alumno
 * @author aaron
 *
 */
@Component
public interface AlumnoRepository extends CrudRepository<Alumno, Integer> { //entidad, tipo de la llave primaria 
	
	
}
