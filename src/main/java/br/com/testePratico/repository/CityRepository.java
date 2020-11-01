package br.com.testePratico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.testePratico.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	// PESQUISA STATEs POR NOME
	@Transactional
	@Query(value = "select * from city where name = ?1 order by name", nativeQuery = true)
	Optional<City> verificarCity(String name);

	// BUSCA TODOS AS CITYS CADASTRADAS
	@Transactional
	@Query(value = "select * from city order by name limit 100", nativeQuery = true)
	Optional<List<City>> findAllCity();

	// BUSCA POR NOME AS CITYS CADASTRADAS
	@Transactional
	@Query(value = "select * from city where name like %?1% order by name limit 100", nativeQuery = true)
	Optional<List<City>> cityName(String name);
}
