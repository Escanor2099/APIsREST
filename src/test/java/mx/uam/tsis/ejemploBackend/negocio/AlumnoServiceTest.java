package mx.uam.tsis.ejemploBackend.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;  
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemploBackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemploBackend.negocio.modelo.Alumno;

@ExtendWith(MockitoExtension.class)
@Slf4j
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
		//una matricula de alumno que ya ha sido guardado
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
	
	@Test
	public void testSuccesfulUpdate() {
		Alumno alumno = new Alumno();
		alumno.setCarrera("Leo");
		alumno.setMatricula(888111);
		alumno.setNombre("Aioria Pruebin");
		
		Alumno alumnoActualizado = new Alumno();
		alumnoActualizado.setCarrera("Gold Lion");
		alumnoActualizado.setMatricula(888111);
		alumnoActualizado.setNombre("Aaron  Pruebin");
		
		//Simula lo que haria el alumnoRepository real cuando le pasan
		//una matricula de alumno que ya ha sido guardado
		when(alumnoRepositoryMock.findById(888111)).thenReturn(Optional.ofNullable(alumno));
		log.info("En este puntoooo ya pasamos el primer when, se llamó a findById y vamos al segundo when");
		//
		when(alumnoRepositoryMock.save(alumnoActualizado)).thenReturn(alumnoActualizado);
		log.info("Aquiiiiii ya pasamos el segundo when en donde se pide actualizar al alumno");
		/*when(alumnoRepositoryMock.save(alumnoActualizado)).thenAnswer(new Answer <Alumno>() {

			@Override
			public Alumno answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub
				//lo siguiente nos permite acceder al parametro que se le pasa a save
				Object[] args = invocation.getArguments();
				Alumno alumnoRecibidoComoParametro = (Alumno) args [0];
				
				//
				log.info("El nombre del Aumno es: " + alumnoRecibidoComoParametro.getNombre() +
						" Su carrera es: " + alumnoRecibidoComoParametro.getCarrera());
				
				//Alumno result = alumnoService.update(alumno.getMatricula(), alumnoActualizado);
				//assertEquals(alumnoActualizado.getCarrera(), alumnoRecibidoComoParametro.getCarrera());
				
				return alumnoRecibidoComoParametro;
			}});*/
		  
		Alumno result = alumnoService.update(888111, alumnoActualizado);
		log.info("Queremos que se actualice el alumno con alumnoActualizado que vale... lo que result: " + result);
		assertEquals(alumnoActualizado, result);
	}
	
}
