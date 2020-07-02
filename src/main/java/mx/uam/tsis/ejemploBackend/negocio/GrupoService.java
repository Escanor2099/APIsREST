package mx.uam.tsis.ejemploBackend.negocio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemploBackend.datos.GrupoRepository;
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
	//inicializar una variable de tipo GrupoRepository 
	private GrupoRepository grupoRepository;
	
	public Grupo create(Grupo nuevoGrupo) {
		log.info("estoy en la clase GrupoService creando un nuevo grupo con los datos: " + nuevoGrupo);
		return grupoRepository.save(nuevoGrupo);
	}
	
	public Iterable<Grupo> retrieveAll(){
		 return grupoRepository.findAll(); 
	}
	
}
