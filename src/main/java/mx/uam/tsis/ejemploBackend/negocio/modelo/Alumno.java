package mx.uam.tsis.ejemploBackend.negocio.modelo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(notes = "Matricula del alumno", required = true)
	private Integer matricula;
	
	
	@NotEmpty
	@ApiModelProperty(notes = "Nombre del alumno", required = true)
	private String nombre;
	
	@NotBlank                       
	@ApiModelProperty(notes = "Carrera del alumno", required = true)
	private String carrera;
}
