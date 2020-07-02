package mx.uam.tsis.ejemploBackend.negocio.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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

public class Grupo {
	@Id
	@GeneratedValue //autogenera un ID único
	private Integer id;
	
	@NotBlank
	private String clave;
	
	@Builder.Default
	@OneToMany //especifica la asociacion un gruppo puede tener de uno a muchos alumnos
	private List <Alumno> alumnos = new ArrayList<>();
}

