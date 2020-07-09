package mx.uam.tsis.ejemploBackend.negocio.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * Capa de modelo de dominio
 * Alumno es una entidad del modelo de dominio
 * que representa un alumno
 * @author aaron
 *
 */
@NoArgsConstructor //inicializa el constructor de la entidad
@AllArgsConstructor
@Builder
@Data
@Entity //esta anotacion le informa a la BD que esta clase es una entidad y que hay que persistir en la BD
@EqualsAndHashCode
public class Alumno {
	
	@NotNull
	@ApiModelProperty(notes = "Matricula del alumno", required = true)
	@Id //este dice que matricula es el id de la entidad, es la llave primaria de la entidad
	private Integer matricula;
	
	
	@NotEmpty
	@ApiModelProperty(notes = "Nombre del alumno", required = true)
	private String nombre;
	
	@NotBlank                       
	@ApiModelProperty(notes = "Carrera del alumno", required = true)
	private String carrera;
}
