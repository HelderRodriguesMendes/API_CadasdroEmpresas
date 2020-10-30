package br.com.testePratico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.testePratico.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

	// BUSCA TODOS OS COUNTRYS CADASTRADOS E ATIVOS
	@Transactional
	@Query(value = "select * from country where ativo = true limit 100", nativeQuery = true)
	Optional<List<Country>> findAllCountry();

	// BUSCA TODOS OS COUNTRYS CADASTRADOS E DESATIVADOS
	@Transactional
	@Query(value = "select * from country where ativo = false limit 100", nativeQuery = true)
	Optional<List<Country>> findAllDesativados();

	// PESQUISA COUNTRY POR NOME
	@Transactional
	@Query(value = "select * from country where ativo = true and name like %?1% limit 100", nativeQuery = true)
	Optional<List<Country>> countryName(String name);

	// DESATIVA UM COUNTRY
	@Transactional
	@Modifying
	@Query(value = "update country set ativo = false where id = ?1", nativeQuery = true)
	void desativar(Long id);

	// ATIVA UM COUNTRY
	@Transactional
	@Modifying
	@Query(value = "update country set ativo = true where id = ?1", nativeQuery = true)
	void ativar(Long id);

	// PESQUISA COUNTRY POR NOME
	@Transactional
	@Query(value = "select * from country where name = ?1", nativeQuery = true)
	Optional<Country> verificarCcountry(String name);

}
