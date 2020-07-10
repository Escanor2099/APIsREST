package mx.uam.tsis.ejemploBackend.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemploBackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemploBackend.datos.GrupoRepository;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Grupo;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrupoControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@BeforeEach
	public void prepare() {
		//aqui se puede hacer cosas para preparar sus casos de prueba
		//incluyendo agregar datos a la BD
	}
	
	@Test
	public void testAddStudentToGroup() {
		log.info("1 Entrando al metodo testAddStudentToGroupOk() ");
		//Creo al alumno que voy a agregar al grupo
		Alumno alumno = new Alumno();
		alumno.setCarrera("Leo");
		alumno.setMatricula(888111);
		alumno.setNombre("Aioria Pruebin");
		
		//creamos el grupo 
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		grupo.addALumno(alumno);
		log.info("2 Antes de crear el encabezado ");
		//creo encabezado de la invocacion
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON.toString());
		log.info("3 creando request ");
		HttpEntity<Grupo> request = new HttpEntity<Grupo>(grupo, headers);
		log.info("Request vale: " + request);
		//usaremos restTemplate ´para hablar con el backend            //request      //responseTyp
		ResponseEntity<Grupo> responseEntity =  restTemplate.exchange("/grupos/1/alumnos/888111", HttpMethod.POST, request, Grupo.class);
		log.info("responseEntity Me regreso : " + responseEntity.getStatusCode()); //"/grupos/{id}/alumnos"
		
		//Corroboro que el endPoint me regresa el status esperado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	}
	
	/*
	 * //Creo al alumno que voy a enviar
		Alumno alumno = new Alumno();
		alumno.setCarrera("Leo");
		alumno.setMatricula(888111);
		alumno.setNombre("Aioria Pruebin");
		
		//creo encabezado de la invocacion
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON.toString());
		
		//Creo la peticion con el alumno como body y el encabezado
		HttpEntity<Alumno> request = new HttpEntity<Alumno>(alumno, headers);
		
		//usaremos restTemplate ´para hablar con el backend            //request      //responseTyp
		ResponseEntity<Alumno> responseEntity =  restTemplate.exchange("/v5/alumnos", HttpMethod.POST, request, Alumno.class);
		log.info("Me regreso: " + responseEntity.getBody());
		
		//Corroboro que el endPoint me regresa el status esperado
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
		Optional<Alumno> optAlumno = alumnoRepository.findById(888111);
		
		log.info("alumno es: " + optAlumno.get());
		assertEquals(alumno, optAlumno.get());
	 */
}


