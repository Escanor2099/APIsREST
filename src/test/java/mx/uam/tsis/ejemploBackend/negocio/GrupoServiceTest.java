package mx.uam.tsis.ejemploBackend.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Optional;

import org.junit.jupiter.api.Test;  
import org.mockito.InjectMocks;
import org.mockito.Mock;

import jdk.internal.jline.internal.Log;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemploBackend.datos.GrupoRepository;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Grupo;

@Slf4j
public class GrupoServiceTest {
	
	/**
	 * @Mock conecta servicio con repository
	 */
	@Mock
	//inicializar una variable de tipo GrupoRepository para conectar con GrupoRepository
	private GrupoRepository grupoRepositoryMock;
	
	/**
	 * @Mock Conecta con AlumnoService
	 */
	@Mock
	private AlumnoService alumnoServiceMock;
	
	@InjectMocks
	private GrupoService grupoService;
	
	/**
	 * 
	 */
	@Test
	public void testSuccessfullAddStudentToGroup() {
		/*
		 * Necesitamos que ya exista un grupo y un alumno
		 */
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Leo");
		alumno.setMatricula(888111);
		alumno.setNombre("Aioria Pruebin");
		
		//Stubbing para el alumnoService
		when(alumnoServiceMock.retrieveByMatricula(888111)).thenReturn(alumno);
		
		//Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
		
		boolean result = grupoService.addStudentToGroup(1, 888111);
		Log.info("esto sera falso o verdadero " + result);
		
		assertEquals(true, result);
		
		assertEquals(grupo.getAlumnos().get(0), alumno);
		//log.info(assertEquals(grupo.getAlumnos().get(0), alumno));
		
	}
	
	public void testUnsuccessfullAddStudentToGroup() {
	
		Alumno alumno = new Alumno();
		alumno.setCarrera("Leo");
		alumno.setMatricula(888111);
		alumno.setNombre("Aioria Pruebin");
		
		//Stubbing para el alumnoService
		when(alumnoServiceMock.retrieveByMatricula(888111)).thenReturn(alumno);
		
		//Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		boolean result = grupoService.addStudentToGroup(1, 888111);
		log.info("esto sera falso o verdadero " + result);
		
		assertEquals(false, result);
		
	}
	
	//public void testSuccesfull

}
