package mx.uam.tsis.ejemploBackend.negocio;

import java.util.Optional;

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
	
	public Grupo create(Grupo nuevoGrupo) {
		log.info("estoy en la clase GrupoService creando un nuevo grupo con los datos: " + nuevoGrupo);
		return grupoRepository.save(nuevoGrupo);
	}
	
	public Iterable<Grupo> retrieveAll(){
		 return grupoRepository.findAll(); 
	}
	
	/*
	 * faltan los demas endpoints
	 */
	
	/**
	 * 
	 * @param groupId Llave primaria que identifica un grupo
	 * @param matricula Llave primaria que identifica a un alumno
	 * @return
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
			log.info("No se encontraron matricula: " + matricula+"ni idGrupo: "+groupId);
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
