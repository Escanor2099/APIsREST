package mx.uam.tsis.ejemploBackend.negocio;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemploBackend.datos.GrupoRepository;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Grupo;

/**
 * clase para manejar las reglas de negocio
 * @author aaron
 *
 */
@Service
@Slf4j
public class GrupoService {
	
	/**
	 * @Autowired conecta servicio con repository
	 */
	@Autowired
	//inicializar una variable de tipo GrupoRepository para conectar con GrupoRepository
	private GrupoRepository grupoRepository;
	
	//Conectar con AlumnoService
	@Autowired
	private AlumnoService alumnoService;
	
	/**
	 * CREATE
	 * Metodo que nos permite crearun nuevo grupo mediante el paso de el objeto Grupo
	 * @param nuevoGrupo Recibe por parametro el objeto grupo que quiere ser creado (almacenado en BD)
	 * @return 
	 */
	public Grupo create(Grupo nuevoGrupo) {
		log.info("estoy en la clase GrupoService persistiendo un nuevo grupo con los datos: " + nuevoGrupo);
		//log.info("Lo que se regresa es esto: " + grupoRepository.save(nuevoGrupo));
		//Grupo grupoReturn = grupoRepository.save(nuevoGrupo);
		Grupo grupoReturn = grupoRepository.save(nuevoGrupo);
		log.info("Lo que se va a retornar es: " + grupoReturn);
		return grupoReturn;
	}
	
	/**
	 * RETRIEVE ALL
	 * Metodo que nos permite regresar a todos los grupos que se encuentren en la BD
	 * @return Regresa todos los objetos almacenados en BD
	 */
	public Iterable<Grupo> retrieveAll(){
		 return grupoRepository.findAll(); 
	}
	
	/**
	 * RETRIEVE BY ID
	 * Metodo que nos permite buscar a un grupo en especifico, pasandole su id de Grupo
	 * @param id Recibe un id de grupo para que pueda ser buscado
	 * @return Se regresa 
	 */
	public Grupo retrieveById(Integer id) {
		Optional<Grupo> optGrupo = grupoRepository.findById(id);
		log.info("alumnoOpt es: " + optGrupo);
		
		if(optGrupo.isPresent()) {
			
			log.info("Se encontro al grupo con Id: " + optGrupo.get());
			return optGrupo.get();
			
			
		}else {
			return null;
		}
	}
	
	/**
	 * UPDATE
	 * Metodo de la clase GrupoService que permite actualizar un grupo, especificamente su clave.
	 * @param idGrupo Recibe el id del grupo que se quiere actualizar
	 * @param grupoActualizado Se recibe lo que se le quiere cambiar
	 * @return Regresa el grupo actualizado
	 */
	@Transactional
	public Grupo update(Integer idGrupo, Grupo grupoActualizado) {
		//primero buscamos a ver si esta el alumno
		log.info("Antes de actualizar verifico que el alumno con matricula: " + idGrupo + " existe");
		Optional<Grupo> optGrupo = grupoRepository.findById(idGrupo);
		log.info("Esto es el grupo que se regresa con el id del grupo que se quiere buscar, el valor de optGroup es: "+optGrupo);
		
		if (optGrupo.isPresent()) {
			Grupo grupoReturn = grupoRepository.save(grupoActualizado);
			log.info("Si existe lo procedemos a aactualizar con los datos: " + grupoActualizado);
			log.info("Esto es lo que se regresa al actualizar en la BD " + grupoReturn);
			return grupoReturn;
		}else {
			log.info("Si no existe entonces, null");
			return null;
		}
	}
	
	/**
	 * DELETE
	 * Método en la clase GrupoService que nos permite acceder a la base de datos (respetando logica de negocios)
	 * y nos permite eliminar un grupo que fue buscado previamente por su id.
	 * @param id, Se le pas por parametro el id del grupo que se quiere eliminar
	 * @return Regresa un booleano, true si se borró, false si no exisistia ese grupo con dicho id
	 */
	public boolean delete(Integer id) {
		log.info("Buscando el grupo con id: " + id + " para borrarlo");
		Optional<Grupo> optGrupo = grupoRepository.findById(id);
		
		if(optGrupo.isPresent()) {
			log.info("Grupo con id: " + optGrupo + " ...procediendo a borrar");
			grupoRepository.deleteById(id);
			return true;
		} else {
			log.info("No se encontró el grupo a borrar");
			return false;
		}
	}
	
	/**
	 * ADD STUDENT TO GROUP
	 * Método que nos permite agregar un alumno a un Grupo mediante la matricula del alumno
	 * y el id del Grupo al cual se desea agregar
	 * @param groupId Llave primaria que identifica un grupo
	 * @param matricula Llave primaria que identifica a un alumno
	 * @return Regresa un true si el borrado se hizo, false de lo contrario
	 */
	public boolean addStudentToGroup(Integer groupId, Integer matricula) {
		log.info("En el metodo addToStudentToGroup de la clase GrupoService");
		
		//recuperar estas dos llaves
		
		//Recuperar primer al alumno
		Alumno alumno = alumnoService.retrieveByMatricula(matricula);
		
		//optional puede regresar nullo si no esta o el grupo si esta
		Optional<Grupo> optGrupo = grupoRepository.findById(groupId);
		
		//si no se encuentra nada en optGrupo y si allumno no existe entonces
		if(!optGrupo.isPresent() || alumno == null) {
			log.info("No se encontró alumno o grupo");
			return false;
		}
			
		//agregar el alumno al grupo
		Grupo  grupo = optGrupo.get();
		
		//conetar al alumno al grupo	
		grupo.addALumno(alumno);
			
		//Persistir el cambio
		grupoRepository.save(grupo);
			
		return true;
	}
}
