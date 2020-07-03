package mx.uam.tsis.ejemploBackend.negocio;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemploBackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;

/**
 * 
 * @author aaron
 *
 */
@Service
@Slf4j
public class AlumnoService {
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * 
	 * @param nuevoAlumno
	 * @return el aluo que sse acaba de crear si la creacion es exitosa
	 * null de lo contrario
	 */
	//recibe un nuevo alumno
	public Alumno create(Alumno nuevoAlumno) {
		
		//Regla de negocio, no se puede crearl el mismo 
		//alunno con la misma matricula
		Optional<Alumno> alumnoOpt = alumnoRepository.findById(nuevoAlumno.getMatricula());
		
		if(!alumnoOpt.isPresent()) {
			return alumnoRepository.save(nuevoAlumno);
		}else {
			return null;
		}
	}
	
	/**
	 * 
	 * @return regresa todo lo que encuentre en el Repository
	 */
	public Iterable <Alumno> retrieveAll(){
		return alumnoRepository.findAll();
	}
	
	/**
	 * 
	 * @param matricula
	 * @return si el alumno existe lo regresa
	 * @return null si no se encontró el alumno
	 */
	public Alumno retrieveByMatricula(Integer matricula) {
		Optional<Alumno> alumnoOpt = alumnoRepository.findById(matricula);
		log.info("alumnoOpt es: " + alumnoOpt);
		
		if(alumnoOpt.isPresent()) {
			log.info("Se encontro al alumno con la matricula: " + matricula);
			return alumnoOpt.get();
			//return alumnoRepository.findById(matricula);
			
		}else {
			return null;
		}
		
	}
	
	/**
	 * 
	 * @param matricula, recibe un id para buscar
	 * @param alumnoActualizado, recibe el json con los datos a actualiza
	 * @return null si el alumno no está presente 
	 */
	@Transactional
	public Alumno update(Integer matricula, Alumno alumnoActualizado) {
		//primero buscamos a ver si esta el alumno
		log.info("Antes de actualizar verifico que el alumno con matricula: " + matricula + " existe");
		Optional<Alumno> alumnoOpt = alumnoRepository.findById(matricula);
		
		//Alumno alumno = alumnoOpt.get();
		if (alumnoOpt.isPresent()) {
			log.info("Si existe lo procedemos a aactualizar con los datos: " + alumnoActualizado);
			return alumnoRepository.save(alumnoActualizado);
		}else {
			log.info("Si no existe entonces, null");
			return null;
		}
	}
	
	/**
	 * 
	 * @param matricula, 
	 * @return
	 */
	public boolean delete(Integer matricula) {
		log.info("Buscando al alumno con matriucula: " + matricula + " para borrarlo");
		Optional<Alumno> alumnoOpt = alumnoRepository.findById(matricula);
		
		if(alumnoOpt.isPresent()) {
			log.info("Alumno con matricula: " + alumnoOpt + " procediendo a borrar");
			alumnoRepository.deleteById(matricula);
			return true;
		} else {
			return false;
		}
	}
}
