package br.com.testePratico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.testePratico.model.Neighborhood;

public interface NeighborhoodRepository extends JpaRepository<Neighborhood, Long> {
	// PESQUISA STATEs POR NOME
	@Transactional
	@Query(value = "select * from neighborhood where name = ?1 order by name", nativeQuery = true)
	Optional<Neighborhood> verificarNeighborhood(String name);

	// BUSCA TODOS OS STATES CADASTRADOS E ATIVOS
	@Transactional
	@Query(value = "select * from neighborhood order by name limit 100", nativeQuery = true)
	Optional<List<Neighborhood>> findAllNeighborhood();

	// BUSCA POR NOME TODAS AS NEIGHBORHOOR CADASTRADAS E ATIVAS
	@Transactional
	@Query(value = "select * from neighborhood where name like %?1% order by name limit 100", nativeQuery = true)
	Optional<List<Neighborhood>> neighborhoodName(String name);

	// DESATIVA UMA NEIGHBORHOOR
	@Transactional
	@Modifying
	@Query(value = "update neighborhood set ativo = false where id = ?1", nativeQuery = true)
	void desativar(Long id);

	// ATIVA UMA NEIGHBORHOOR
	@Transactional
	@Modifying
	@Query(value = "update neighborhood set ativo = true where id = ?1", nativeQuery = true)
	void ativar(Long id);
}
