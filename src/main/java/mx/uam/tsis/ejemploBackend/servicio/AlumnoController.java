package mx.uam.tsis.ejemploBackend.servicio;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.Media;
import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;

/**
 * Controlador para el API rest
 * 
 * @author aaron
 *
 */
@SuppressWarnings("unused")
@RestController
@Slf4j
public class AlumnoController {
	
	
	//base de datos en memoria
	private Map <Integer, Alumno> alumnoRespository = new HashMap<>();
	
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody Alumno nuevoAlumno) {
		
		log.info("REcibi llamada a create con" + nuevoAlumno);
		
		//put para insertar al alumno a la base de datos
		alumnoRespository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
		
		// build para construir el objeto
		return ResponseEntity.status(HttpStatus.CREATED).build();
	
		 
	}
	
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retieveAll() {
		//Alumno alumno = alumnoRespository.get(key)
		log.info("Mostrando todos los objetos alumno" + alumnoRespository.values() );
		//en el cuerpo de la invocacion (body) regresame la lista alumnos diciendole al hasmap dame tus valores 
		return ResponseEntity.status(HttpStatus.OK).body(alumnoRespository.values());
		
	}
	
	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("matricula") Integer matricula) {
		log.info("Buscando al alumno con matricula "+matricula);
		
		Alumno alumno = alumnoRespository.get(matricula);
		
		if(alumno != null) {
			log.info("Alumno se actualizo");
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			log.info("Alumno no encotrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		}
		
		
	}
	
	/*
	 * en la anotacion @PutMapping le coloqué que tanto consumia como producia json
	 * ya que al actualizar con put se cambia todo el objeto json que ahi es donde
	 * produce , y a la vez se le tiene que pasar su identificador, en este caso 
	 * matricula ya que se le tiene que pasar para saber a cual se le debe de cambiar
	 * y ahi es donde consume. 
	 * Espero haberme expresado bien.
	 */

	@PutMapping(path = "/alumnos/{matricula}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatePut(@RequestBody Alumno nuevoAlumno,@PathVariable("matricula") Integer matricula) {
	//public ResponseEntity<?> updatePut(@PathParam("/alumnos/matricula") Integer matricula, Alumno nuevoAlumno) {	
		
		log.info("Actualizando al alumno con matricula "+matricula);
		Alumno alumno = alumnoRespository.remove(matricula);
		alumnoRespository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
		//nuevoAlumno = alumnoRespository.replace(matricula, nuevoAlumno);
		log.info("El nuevo objeto json alumno es" + "Nombre: "+nuevoAlumno.getNombre() + "Carrera: "+ nuevoAlumno.getCarrera()+  "Matricula: "+ nuevoAlumno.getMatricula());
		if(nuevoAlumno.getMatricula() != null) {
		return ResponseEntity.status(HttpStatus.OK).build(); 
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}

	
	
	@DeleteMapping(path = "/alumnos/{matricula}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable("matricula") Integer matricula) {
		log.info("Borrando al alumno con matricula "+matricula);
		//Alumno alumno = alumnoRespository.get(matricula);
		Alumno alumno = alumnoRespository.remove(matricula);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
