package mx.uam.tsis.ejemploBackend.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import antlr.collections.List;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemploBackend.datos.GrupoRepository;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Grupo;

@ExtendWith(MockitoExtension.class) // Uso de Mockito
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
	//@Mock
	//private GrupoService grupoServiceMock;
	
	@Mock
	private AlumnoService alumnoServiceMock;
	
	@InjectMocks
	private GrupoService grupoService;
	
	/**
	 * 
	 */
	
	@Test
	public void testSuccesfullCreate() {
		log.info("Entramos a create de GrupoServiceTest ");
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//Simula lo que haria el grupoRepository real cuando le pasan
		//un id de grupo que no ha sido guardado
	//	when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));
		
		when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);
		
		//aqui ejecuto la unidad que quiero probar
		grupo = grupoService.create(grupo);
		log.info("Grupo debe ser (No Null),  es: " + grupo);
		//Aqui compruebo el resultado
		assertNotNull(grupo); //probar que la referencia al grupo no es nula
	}
	
	@Test
	public void testUnsuccessFullCreate() {
		log.info("Entramos a UnsuccessFullCreate de GrupoServiceTest ");
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
				
		//aqui ejecuto la unidad que quiero probar
		grupo = grupoService.create(grupo);
		log.info("despues de intentar crear este grupo nos saldra que es (NULL): " + grupo + "porque ya estaba en BD " );
		//Aqui compruebo el resultado
		assertNull(grupo); //probar que la referencia a grupo es nula porque el grupo ya existia 
	}
	
	@Test
	public void testSuccessfulRetrieveAll() {
		log.info("Entramos a testSuccessfulRetrieveAll() de GrupoServiceTest ");
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		//List grupos = new ArrayList<>();
		//when(grupoRepositoryMock.findAll()).thenReturn(grupo.getId());
		
		
		//assert
		
	
		//when(grupoRepositoryMock.findAll());

		//grupo
		//aqui ejecuto la unidad que quiero probar
		//grupo = (Grupo) grupoService.retrieveAll();
		//log.info("Grupo debe ser (...),  es: " + grupo);
		//Aqui compruebo el resultado
		//assertNotNull(grupo); //probar que la referencia al grupo no es nula
		
	}
	
	@Test
	public void testUnsuccessfulRetrieveAll() {
		
	}
	
	@Test
	public void testSuccessfulRetrieveById() {
		log.info("Entramos a testSuccessfulRetrieveById() de GrupoServiceTest ");
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
				
		Grupo result = grupoService.retrieveById(1);  //addStudentToGroup(1, 888111);
		log.info("Lo que se busca es que se haya encontradoo el grupo , por lo tanto result debe de ser ... Valor de result: " + result);
			
		assertEquals(grupo, result);
		
	}
	
	@Test
	public void testUnsuccesfullRetrieveById() {
		
		//Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
				
		Grupo result = grupoService.retrieveById(1);
		log.info("Result debe de ser ... porque al no haber grupo , result es: " + result);
		
		assertEquals(null,result);
	}
	
	@Test
	public void testSuccessfulUpdate() {
		log.info("Entramos a testSuccessfulUpdate() de GrupoServiceTest ");
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//grupo actualizado
		Grupo grupo1 = new Grupo();
		grupo1.setId(1);
		grupo1.setClave("TST02");
		
		
		//Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(grupo));
		
		when(grupoRepositoryMock.save(grupo1)).thenReturn(grupo1);
		
		Grupo result = grupoService.update(1, grupo1);
		
		log.info("Lo que se busca es que se haya encontradoo el grupo , por lo tanto result debe de ser ... Valor de result: " + result);
		
		assertEquals(grupo1, result);
	
	}
	
	@Test
	public void testUnsuccessfulUpdate() {
		log.info("Entrando al metodo testUnsuccessfulUpdate() de la clase GrupoServiceTest");
		//grupo actualizado
		Grupo grupo1 = new Grupo();
		grupo1.setId(1);
		grupo1.setClave("TST02");
		
		//when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(grupo));
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		Grupo result = grupoService.update(1, grupo1);
		log.info("aqui resut dbe de valer null,....... el valor de resut es: " + result);
		assertEquals(null, result);
	}
	
	@Test
	public void testSuccesfulDelete() {
		log.info("Entramos a testSuccesfulDelete() de GrupoServiceTest ");
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		//Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
		
		boolean result = grupoService.delete(grupo.getId());
		
		log.info("Si se pudo eliminar ese grupo entonces debe regresar un verdadero, result es: "+ result);
		
		assertEquals(true, result);
	}
	
	@Test
	public void testUnsuccesfulDelete() {
		log.info("Entramos a testUnsuccesfulDelete() de GrupoServiceTest ");
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		Grupo result = grupoService.retrieveById(1);
		log.info("Result debe de ser ... porque al no haber grupo , result es: " + result);
		
		assertEquals(null,result);
	}
	
	@Test
	public void testSuccessfulAddStudentToGroup() {
		/*
		 * Necesitamos que ya exista un grupo y un alumno
		 */
		log.info("Estamos en el GrupoServiceTest probando el metodo testSuccessfullAddStudentToGroup");
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
		log.info("Lo que se busca es que se haya agregado el alumno a el grupo especificado, por lo tanto result debe de ser verdadero. Valor de result: " + result);
		
		assertEquals(true, result);
		
		
		assertEquals(grupo.getAlumnos().get(0), alumno);
		//log.info(assertEquals(grupo.getAlumnos().get(0), alumno));
		
	}
	
	@Test
	public void testUnsuccessfulAddStudentToGroup() {
	
		Alumno alumno = new Alumno();
		alumno.setCarrera("Leo");
		alumno.setMatricula(888111);
		alumno.setNombre("Aioria Pruebin");
		
		//Stubbing para el alumnoService
		when(alumnoServiceMock.retrieveByMatricula(888111)).thenReturn(alumno);
		
		//Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		boolean result = grupoService.addStudentToGroup(1, 888111);
		log.info("Result debe de ser falso porque al no haber grupo al cual agregar ese alumno, result es: " + result);
		
		assertEquals(false, result);
		
	}
	
	//public void testSuccesfull

}
