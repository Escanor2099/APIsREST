package mx.uam.tsis.ejemploBackend.negocio.modelo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Alumno {
	private Integer matricula;
	
	private String nombre;
	
	private String carrera;
}
