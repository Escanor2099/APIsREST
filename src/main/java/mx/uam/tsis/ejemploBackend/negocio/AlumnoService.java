package mx.uam.tsis.ejemploBackend.negocio;





import java.util.List;

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
		Alumno alumno = alumnoRepository.findByMatricula(nuevoAlumno.getMatricula());
		
		if(alumno== null) {
			return alumnoRepository.save(nuevoAlumno);
		}else {
			return null;
		}
	}
	
	public List <Alumno> retrieveAll(){
		return alumnoRepository.find();
	}
	
	/**
	 * 
	 * @param matricula
	 * @return si el alumno existe lo regresa y sino null
	 */
	public Alumno retrieveByMatricula(Integer matricula) {
		Alumno alumno = alumnoRepository.findByMatricula(matricula);
		
		if(alumno != null) {
			log.info("Se encontro al alumno con la matricula: " + matricula);
			return alumnoRepository.findByMatricula(matricula);
			
		}else {
			return null;
		}
		
	}
	
	public Alumno update(Integer matricula, Alumno alumnoActualizado) {
		//primero buscamos a ver si esta el alumno
		log.info("Antes de actualizar verifico que el alumno con matricula: " + matricula + " existe");
		Alumno alumno = alumnoRepository.findByMatricula(matricula);
		
		if (alumno != null) {
			log.info("Si existe lo procedemos a aactualizar con los datos: " + alumnoActualizado);
			return alumnoRepository.update(matricula, alumnoActualizado);
		}else {
			log.info("Si no existe entonces, null");
			return null;
		}
	}
	
	public Alumno delete(Integer matricula) {
		log.info("Buscando al alumno con matriucula: " + matricula + " para borrarlo");
		Alumno alumno = alumnoRepository.findByMatricula(matricula);
		
		if(alumno != null) {
			log.info("Alumno con matricula: " + alumno + " procediendo a borrar");
			return alumnoRepository.delete(matricula);
		} else {
			return null;
		}
	}
}
