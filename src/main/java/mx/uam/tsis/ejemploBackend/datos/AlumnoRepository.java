package mx.uam.tsis.ejemploBackend.datos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

//import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;

/**
 * Se encarga de alamacenar y recuperar alumno
 * @author aaron
 *
 */
@Component
@Slf4j
public class AlumnoRepository {
	
	//base de datos en memoria
	private Map <Integer, Alumno> alumnoRespository = new HashMap<>();
	
	/**
	 * Guarda la base de datos
	 * @param alumno
	 */
	public Alumno save(Alumno nuevoAlumno) {
		//put para insertar al alumno a la base de datos
		alumnoRespository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
		return nuevoAlumno;		
	}
	
	public Alumno findByMatricula(Integer matricula) {
		return alumnoRespository.get(matricula);
	}
	
	public List<Alumno> find() {
		return new ArrayList<>(alumnoRespository.values());
	}
	
	/**
	 * 
	 * @param matricula
	 * @return Encuentra el alumno y lo regresa
	 */
	public Alumno retrieveByMatricula(Integer matricula) {
		Alumno alumno = alumnoRespository.get(matricula);
		return alumno;
	}
	
	/**
	 * 
	 * @param matricula
	 * @param alumnoActualizado
	 * @return al alumno actualizado
	 */
	//update
	public Alumno update(Integer matricula, Alumno alumnoActualizado) {
		
		Alumno alumno = alumnoRespository.get(matricula);
		log.info("Si existe el alumno con matricula: " + alumno.getMatricula() + "lo catualizamos y reemplazamos");
		
		alumno.setCarrera(alumnoActualizado.getCarrera());
		log.info("El alumno con carrera: " + alumno.getCarrera() + "será actualizado por: " + alumnoActualizado.getCarrera());
		
		alumno.setNombre(alumnoActualizado.getNombre());
		log.info("El alumno con nombre: " + alumno.getNombre() + "será actualizado por: " + alumnoActualizado.getCarrera());
		
		alumnoRespository.replace(alumnoActualizado.getMatricula(), alumno);
		log.info("El alumno se actualizó con: " + alumnoActualizado) ;
		return alumnoActualizado;
	}
	
	
	//delete
	public Alumno delete(Integer matricula) {
		Alumno alumno = alumnoRespository.get(matricula);
		return alumnoRespository.remove(alumno.getMatricula());
		
	}
}
