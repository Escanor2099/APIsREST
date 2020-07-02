package mx.uam.tsis.ejemploBackend.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.tsis.ejemploBackend.negocio.modelo.Grupo;

public interface GrupoRepository extends CrudRepository<Grupo, Integer> {

}
