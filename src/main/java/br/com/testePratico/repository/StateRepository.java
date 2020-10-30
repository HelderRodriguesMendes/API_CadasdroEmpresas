package br.com.testePratico.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.testePratico.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
	
	// PESQUISA COUNTRY POR NOME
	@Transactional
	@Query(value = "select * from state where name = ?1", nativeQuery = true)
	Optional<State> verificarState(String name);
}
