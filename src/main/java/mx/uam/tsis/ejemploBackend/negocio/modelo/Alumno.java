package mx.uam.tsis.ejemploBackend.negocio.modelo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
/**
 * Capa de modelo de dominio
 * Alumno es una entidad del modelo de dominio
 * que representa un alumno
 * @author aaron
 *
 */
@Builder
@Data
public class Alumno {
	@NotNull
	private Integer matricula;
	@NotEmpty
	private String nombre;
	@NotBlank                       
	private String carrera;
}
