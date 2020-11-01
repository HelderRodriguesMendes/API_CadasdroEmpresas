package br.com.testePratico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.testePratico.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

	// BUSCA TODOS OS COUNTRYS CADASTRADOS
	@Transactional
	@Query(value = "select * from country limit 100", nativeQuery = true)
	Optional<List<Country>> findAllCountry();

	// PESQUISA COUNTRY POR NOME
	@Transactional
	@Query(value = "select * from country where name like %?1% limit 100", nativeQuery = true)
	Optional<List<Country>> countryName(String name);

	// PESQUISA COUNTRY POR NOME
	@Transactional
	@Query(value = "select * from country where name = ?1", nativeQuery = true)
	Optional<Country> verificarCountry(String name);

}
