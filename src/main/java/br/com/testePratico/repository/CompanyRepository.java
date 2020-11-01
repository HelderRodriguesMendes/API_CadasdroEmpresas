package br.com.testePratico.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.testePratico.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	// BUSCA TODOS OS COUNTRYS CADASTRADOS E ATIVOS
		@Transactional
		@Query(value = "select * from company where ativo = true limit 100", nativeQuery = true)
		Optional<List<Company>> findAllCompany();

		// BUSCA TODOS OS COUNTRYS CADASTRADOS E DESATIVADOS
		@Transactional
		@Query(value = "select * from company where ativo = false limit 100", nativeQuery = true)
		Optional<List<Company>> findAllDesativados();

		// PESQUISA COUNTRY POR NOME
		@Transactional
		@Query(value = "select * from company where ativo = true and trade_Name like %?1% limit 100", nativeQuery = true)
		Optional<List<Company>> companyName(String trade_Name);
		
		@Transactional
		@Query(value = "select * from company where ID = ?1", nativeQuery = true)
		Optional<Company> findByNeighborhood(Long id);

		// DESATIVA UM COUNTRY
		@Transactional
		@Modifying
		@Query(value = "update company set ativo = false where id = ?1", nativeQuery = true)
		void desativar(Long id);

		// ATIVA UM COUNTRY
		@Transactional
		@Modifying
		@Query(value = "update company set ativo = true where id = ?1", nativeQuery = true)
		void ativar(Long id);
}
