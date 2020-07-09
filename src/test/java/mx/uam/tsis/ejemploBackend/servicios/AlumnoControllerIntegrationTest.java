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
import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlumnoControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@BeforeEach
	public void prepare() {
		//aqui se puede hacer cosas para preparar sus casos de prueba
		//incluyendo agregar datos a la BD
	}
	
	@Test
	public void testCreate201() {
		
		//Creo al alumno que voy a enviar
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
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
		Optional<Alumno> optAlumno = alumnoRepository.findById(888111);
		
		log.info("alumno es: " + optAlumno.get());
		assertEquals(alumno, optAlumno.get());
		
	}
	
	@Test
	public void testCreate400() {
		
	}
}
