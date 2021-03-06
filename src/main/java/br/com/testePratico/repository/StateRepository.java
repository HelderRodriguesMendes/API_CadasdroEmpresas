package br.com.testePratico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.testePratico.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	// PESQUISA STATEs POR NOME
	@Transactional
	@Query(value = "select * from state where name = ?1 order by name", nativeQuery = true)
	Optional<State> verificarState(String name);

	// BUSCA TODOS OS STATES CADASTRADOS
	@Transactional
	@Query(value = "select * from state order by name limit 100", nativeQuery = true)
	Optional<List<State>> findAllState();

	// PESQUISA STATE POR NOME
	@Transactional
	@Query(value = "select * from state where name like %?1% order by name limit 100", nativeQuery = true)
	Optional<List<State>> stateName(String name);

	// PESQUISA STATE POR COUNTRY
	@Transactional
	@Query(value = "select s.id, s.name from state s, country c where s.COUNTRY_ID = c.id and c.name = ?1 order by name limit 100", nativeQuery = true)
	Optional<List<State>> stateNameCountry(String name);

}
