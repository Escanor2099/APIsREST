package mx.uam.tsis.ejemploBackend.servicio;


//import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

//import javax.print.attribute.standard.Media;
//import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemploBackend.negocio.AlumnoService;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;

/**
 * Controlador para el API rest
 * 
 * @author aaron
 *
 */

@RestController
@RequestMapping("/v4") // Versionamiento
@Slf4j //logging
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService; 
	
	/**
	 * 
	 * @param nuevoAlumno Este nuevo alumno es un objeto json
	 * @return si el alumno se creo regresa un status y al alumno creado
	 * @return si el alumno no se puede crear se regresa un status de bad_request y un mensaje advirtiendo que no se puede
	 */
	@ApiOperation(
			value = "Nos permite crear un nuevo alumno",
			notes = "Al crear al nuevo alumno la matricula debe ser única"
			)// Documentación del api
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody @Valid Alumno nuevoAlumno) {   //validaciones
		
		log.info("REcibi llamada a create(claseAlumnoControllr) con" + nuevoAlumno + "se los paso al metodo creat()de la clase ALumnoService");
		
		Alumno alumno = alumnoService.create(nuevoAlumno);
		
		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumno);

		}else {
			// build para construir el objeto
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear alumno porque ya existe");

		}
		
	
		 
	}
	
	/**
	 * 
	 * @return todos los alumnos en la BD junto con su status
	 */
	@ApiOperation(
			value = "Nos permite obtener todos los alumos")// Documentación del api
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retieveAll() {
		Iterable <Alumno> result = alumnoService.retrieveAll();
		//log.info("Mostrando todos los objetos alumno" + alumnoRespository.values() );
		//en el cuerpo de la invocacion (body) regresame la lista alumnos diciendole al hasmap dame tus valores 
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}
	
	@ApiOperation(
			value = "Nos permite obtener un nuevo alumno mediante su matricula",
			notes = "La matricula nos permitirá buscar al alumno"
			)// Documentación del api
	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable ("matricula")  @Valid Integer matricula) {
		log.info("Buscando al alumno con matricula "+matricula + "Le mando la orden a alumnoService");
		
		Alumno alumno = alumnoService.retrieveByMatricula(matricula);
		
		if(alumno != null) {
			log.info("Este es el Alumno con la matricula: " + matricula + " que se encontró");
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
	@ApiOperation(
			value = "Nos permite actualizar un nuevo alumno",
			notes = "Al actualizar alumno no se debe modificar la matricula"
			)// Documentación del api
	@PutMapping(path = "/alumnos/{matricula}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable("matricula") @Valid Integer matricula, @RequestBody @Valid Alumno alumnoActualizado){
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
	
	@ApiOperation(
			value = "Nos permite eliminar a un alumno",
			notes = "Mediante su matricula"
			)// Documentación del api
	@DeleteMapping(path = "/alumnos/{matricula}")
	public ResponseEntity<?> delete(@PathVariable("matricula") @Valid Integer matricula) {
		log.info("Borrando al alumno con matricula "+matricula + "Le paso la orden a AlumnoService");
		boolean retval;
		//Alumno alumno = alumnoRespository.get(matricula);
		retval = alumnoService.delete(matricula);
		if(retval==true) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno con la matricula: " + matricula + " no encontrado ");
		}
	}

}
