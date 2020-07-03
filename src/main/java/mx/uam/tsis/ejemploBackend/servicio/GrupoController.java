 package mx.uam.tsis.ejemploBackend.servicio;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
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
	 
	 @ApiOperation(
				value = "Nos permite crear un nuevo grupo")// Documentación del api
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
		 * 
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
		 * EL parametro El parametro id de grupo lo agarramos como @PathVariable
		 * y se lo asignamos a  a id
		 * @param id
		 * @param matricula Lo recibimos como un parametro
		 * @return
		 */
		@PostMapping(path = "/grupos/{id}/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<?> addStudentToGroup(@PathVariable("id") Integer id, 
				@PathVariable("matricula") Integer matricula){
			
			log.info("Se hizo una invocacion al metodo addStudentToGroup enla clase GrupoService");
			boolean result = grupoService.addStudentToGroup(id, matricula);
			log.info("Results es verdadero si tiene algo, de lo contrario será false: ");
			
			if(result) {
				return ResponseEntity.status(HttpStatus.OK).build();
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		}
}
