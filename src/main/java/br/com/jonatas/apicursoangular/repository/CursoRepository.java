package br.com.jonatas.apicursoangular.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<CursoEntity, Integer> {
	
	CursoEntity findByCodigo(Integer codigo);

}
