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
public interface CountryRepository extends JpaRepository<Country, Long>{
	
	@Transactional
	@Query(value = "select * from country where ativo = true limit 100", nativeQuery = true)
	Optional<List<Country>> findAllCountry();
	
	@Transactional
	@Modifying
	@Query(value = "update country set ativo = false where id = ?1", nativeQuery = true)
	void desabilitar(Long id);
}
