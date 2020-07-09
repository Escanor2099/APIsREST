package mx.uam.tsis.ejemploBackend.negocio;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;  
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import mx.uam.tsis.ejemploBackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;

@ExtendWith(MockitoExtension.class)
public class AlumnoServiceTest {
	
	@Mock
	private AlumnoRepository alumnoRepositoryMock; //mock generado por Mockito
	
	@InjectMocks 
	private AlumnoService alumnoService; //unidad a probar
	
	@Test //prueba de un alumno que no existe
	public void testSuccesfullCreate() {
		Alumno alumno = new Alumno();
		alumno.setCarrera("Leo");
		alumno.setMatricula(888111);
		alumno.setNombre("Aioria Pruebin");
		
		//Simula lo que haria el alumnoRepository real cuando le pasan
		//una matricula de alumno que no ha sido guardado
		when(alumnoRepositoryMock.findById(888111)).thenReturn(Optional.ofNullable(null));
		
		
		when(alumnoRepositoryMock.save(alumno)).thenReturn(alumno);
		 
		
		//aqui ejecuto la unidad que quiero probar
		alumno = alumnoService.create(alumno);
		
		//Aqui compruebo el resultado
		assertNotNull(alumno); //probar que la referencia a alumno no es nula
	}
	
	@Test //prueba de un alumno existente 
	public void testUnsuccesfullCreate() {
		Alumno alumno = new Alumno();
		alumno.setCarrera("Leo");
		alumno.setMatricula(888111);
		alumno.setNombre("Aioria Pruebin");
		
		//Simula lo que haria el alumnoRepository real cuando le pasan
		//una matricula de alumno que no ha sido guardado
		when(alumnoRepositoryMock.findById(888111)).thenReturn(Optional.ofNullable(alumno));
		
		/*Linea de código que esta de más ya que si tiene que regresar false en el AlumnoService
		 * entonces no entra al if y no se ejecuta el create
		 * (alumnoRepositoryMock.save(alumno)).thenReturn(alumno);
		 */
		
		//aqui ejecuto la unidad que quiero probar
		alumno = alumnoService.create(alumno);
		
		//Aqui compruebo el resultado
		assertNull(alumno); //probar que la referencia a alumno es nula porque el alumno ya existia 
	}
	
}
