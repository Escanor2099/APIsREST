package mx.uam.tsis.ejemploBackend.servicio;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

//import javax.print.attribute.standard.Media;
//import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemploBackend.negocio.AlumnoService;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;

/**
 * Controlador para el API rest
 * 
 * @author aaron
 *
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/v1")
@Slf4j
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService; 
	
	
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody @Valid Alumno nuevoAlumno) {
		
		log.info("REcibi llamada a create(claseAlumnoControllr) con" + nuevoAlumno + "se los paso al metodo creat()de la clase ALumnoService");
		
		Alumno alumno = alumnoService.create(nuevoAlumno);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumno);

		}else {
			// build para construir el objeto
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear alumno porque ya existe");

		}
		
	
		 
	}
	
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retieveAll() {
		List <Alumno> result = alumnoService.retrieveAll();
		//log.info("Mostrando todos los objetos alumno" + alumnoRespository.values() );
		//en el cuerpo de la invocacion (body) regresame la lista alumnos diciendole al hasmap dame tus valores 
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}
	
	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("matricula") Integer matricula) {
		log.info("Buscando al alumno con matricula "+matricula + "Le mando la orden a alumnoService");
		
		Alumno alumno = alumnoService.retrieveByMatricula(matricula);
		
		if(alumno != null) {
			log.info("Alumno con la matricula: " + matricula + "se encontro en la clase AlumnoController");
			return ResponseEntity.status(HttpStatus.OK).body(alumno);
		} else {
			log.info("Alumno no encotrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
		}
		
		
	}
	
	/**
	 * 
	 * @param matricula
	 * @param alumnoActualizado
	 * @return una respuesta con status OK si se actualizó el alumno.
	 * @return CONFLICT si no se pudo actualizar
	 */

	@PutMapping(path = "/alumnos/{matricula}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("matricula") Integer matricula,@RequestBody Alumno alumnoActualizado){
		log.info("Le digo a AlumnoService que haga una actualizacion con estos nuevos datos: " + alumnoActualizado + " con la matricula: " + matricula);
		Alumno alumno = alumnoService.update(matricula, alumnoActualizado);
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).build(); 
		}else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	/**
	 * 
	 * @param matricula
	 * @return un OK si se eliminó el alumno
	 * @return un NOT_FOUND si es que no se encontraba
	 */
	@DeleteMapping(path = "/alumnos/{matricula}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable("matricula") Integer matricula) {
		log.info("Borrando al alumno con matricula "+matricula + "Le paso la orden a AlumnoService");
		//Alumno alumno = alumnoRespository.get(matricula);
		Alumno alumno = alumnoService.delete(matricula);
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno con la matricula: " + matricula + " no encontrado ");
		}
	}

}
