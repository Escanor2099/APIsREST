 package mx.uam.tsis.ejemploBackend.servicio;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import mx.uam.tsis.ejemploBackend.negocio.GrupoService;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Grupo;

/**
 * 
 * @author aaron
 *
 */
 @RestController
 @RequestMapping("/v4") // Versionamiento
 @Slf4j //logging
public class GrupoController {
	 
	 @Autowired
	 private GrupoService grupoService;
	 
	 @ApiOperation(value = "Nos permite crear un nuevo grupo")// Documentación del api
	 @PostMapping(path = "/grupos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<?> create(@RequestBody @Valid Grupo nuevoGrupo) {   //validaciones
			
			log.info("REcibi llamada a create(clase GrupoController) con" + nuevoGrupo + "se los paso al metodo creat()de la clase GrupoService");
			
			Grupo grupo = grupoService.create(nuevoGrupo);
			
			if(grupo != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(grupo);

			}else {
				// build para construir el objeto
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear el grupo porque ya existe");

			}
		}
		
		/**
		 * RETRIEVE ALL
		 * MEtodo que me regresa a todos los grupos persistidos en la BD
		 * @return todos los grupos en la BD junto con su status
		 */
		@ApiOperation(
				value = "Nos permite obtener todos los grupos")// Documentación del api
		@GetMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> retieveAll() {
			Iterable <Grupo> result = grupoService.retrieveAll();
			//log.info("Mostrando todos los objetos alumno" + alumnoRespository.values() );
			//en el cuerpo de la invocacion (body) regresame la lista alumnos diciendole al hasmap dame tus valores 
			return ResponseEntity.status(HttpStatus.OK).body(result);	
		}
		
		/**
		 * RETRIEVEBYID
		 * Metodo que nos regresa el grupo que se quiere buscar mediante su id de grupo
		 * @param idGrupo
		 * @return
		 */
		@ApiOperation(
				value = "Nos permite obtener un nuevo alumno mediante su matricula",
				notes = "La matricula nos permitirá buscar al alumno"
				)// Documentación del api
		@GetMapping(path = "/grupos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity <?> retrieve(@PathVariable ("id")  @Valid Integer id) {
			log.info("Buscando el grupo con id "+ id + "Le mando la orden a alumnoService");
			
			Grupo grupo = grupoService.retrieveById(id);
			
			if(grupo != null) {
				log.info("Este es el Grupo con el id: " + id + " que se encontró");
				return ResponseEntity.status(HttpStatus.OK).body(grupo);
			} else {
				log.info("Grupo no encotrado");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		}
		
		/**
		 * UPDATE
		 * Método que nos permite actualizar la clave de un Grupo
		 * @param id
		 * @param grupoActualizado
		 * @return una respuesta con status OK si se actualizó el Grupo.
		 * @return CONFLICT si no se pudo actualizar
		 */
		@ApiOperation(
				value = "Nos permite actualizar un grupo con clave nueva",
				notes = "Al actualizar grupo no se puede modificar el id"
				)// Documentación del api
		@PutMapping(path = "/grupos/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> update(@PathVariable("id") @Valid Integer id, @RequestBody @Valid Grupo grupoActualizado){
			log.info("Le digo a AlumnoService que haga una actualizacion con estos nuevos datos: " + grupoActualizado + " con el id: " + id);
			Grupo grupo = grupoService.update(id, grupoActualizado);
			if(grupo != null) {
				return ResponseEntity.status(HttpStatus.OK).build(); 
			}else {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		}
		
		/**
		 * DELETE
		 * Este metodo nos permite eliminar un Grupo de la BD mediante su parametro id
		 * @param id de grupo
		 * @return un OK si se eliminó el grupo
		 * @return un NOT_FOUND si es que no se encontraba el grupo en la BD
		 */
		@ApiOperation(value = "Nos permite eliminar un grupo", notes = "Mediante su id")// Documentación del api
		@DeleteMapping(path = "/grupos/{id}")
		public ResponseEntity<?> delete(@PathVariable("id") @Valid Integer id) {
			log.info("Borrando el grupo con id: "+ id + "Le paso la orden a AlumnoService");
			boolean retval;
			retval = grupoService.delete(id);
			if(retval==true) {
				return ResponseEntity.status(HttpStatus.OK).body("Se borró grupo" + id);
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grupo con id: " + id + " no encontrado ");
			}
		}
		
		/**
		 * EL parametro El parametro id de grupo lo agarramos como @PathVariable
		 * y se lo asignamos a  a id
		 * @param id
		 * @param matricula Lo recibimos como un parametro
		 * @return
		 * 
		 * Probar este para la tarea
		 * 
		 * POST /grupos/{id}/alumnos
		 */
		@ApiOperation(value = "Nos permite agregar un alumno a un Grupo", notes = "Mediante el id del grupo y la matricula del alumno")// Documentación del api
		@PostMapping(path = "/grupos/{id}/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> addStudentToGroup(@PathVariable("id") Integer id, 
				@RequestParam("matricula") Integer matricula){
			
			log.info("Se hizo una invocacion al metodo addStudentToGroup enla clase GrupoService");
			boolean result = grupoService.addStudentToGroup(id, matricula);
			log.info("Results es verdadero si tiene algo, de lo contrario será false: " + result);
			
			if(result) {
				return ResponseEntity.status(HttpStatus.OK).build();
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		}
}
