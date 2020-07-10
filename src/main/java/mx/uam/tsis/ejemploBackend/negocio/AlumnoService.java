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
	 * CREATE
	 * Método que permite persistir un nuevo alumno en la BD si es que se cumple
	 * con su logica de negocios
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
			
			log.info("Voy a guardar a alumno " + nuevoAlumno);
			
			Alumno returnAlumno = alumnoRepository.save(nuevoAlumno);
			
			log.info("Voy a regresar a alumno " + returnAlumno);
			
			return returnAlumno;
			
		}else {
			return null;
		}
	}
	
	/**
	 * RETRIEVE ALL
	 * Método que nos regresa todos aquellos alumnos que esten en la BD
	 * @return regresa todo lo que encuentre en el Repository
	 */
	public Iterable <Alumno> retrieveAll(){
		return alumnoRepository.findAll();
	}
	
	/**
	 * RETRIEVE BY MATRICULA
	 * Método que nos permite regresar a un alumno en especifico
	 * por medio de su matricula
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
	 * UPDATE
	 * Método que nos permite actualizar los valores de un alumno (excepto su matricula) debidamente identificado
	 * por medio de su matricula
	 * @param matricula, recibe un id para buscar
	 * @param alumnoActualizado, recibe el json con los datos a actualiza
	 * @return null si el alumno no está presente 
	 */
	@Transactional
	public Alumno update(Integer matricula, Alumno alumnoActualizado) {
		//primero buscamos a ver si esta el alumno
		log.info("Antes de actualizar verifico que el alumno con matricula: " + matricula + " exista");
		Optional<Alumno> alumnoOpt = alumnoRepository.findById(matricula);
		log.info("El alumno antes d actualizar:  " + alumnoOpt.get());
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
	 * DELETE
	 * Método en la clase AlumnoService que nos permite acceder a la base de datos (respetando logica de negocios)
	 * y nos permite eliminar a un alumno que fue buscado previamente por su matricula.
	 * @param matricula, Se le pas por parametro la matricula del alumno que se quiere eliminar
	 * @return Regresa un booleano, true si se borró, false si no exisistia ese alumno con dicha matricula
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
