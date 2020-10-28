package br.com.testePratico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.exception.NotFound;
import br.com.testePratico.log.LogCountry;
import br.com.testePratico.model.Country;
import br.com.testePratico.repository.CountryRepository;

@Service
public class CountryService {

	@Autowired
	CountryRepository countryRepository;

	LogCountry l = new LogCountry();

	//SALVA UM COUNTRY NO BANCO E NO ARQUIVO DE LOG
	public Country cadastrar(Country country, String status) {		
		Country countrySave = countryRepository.save(country);

		if (status.equals("cadastrar")) {	
			
			// SALVANDO DADOS NO ARQUIVO DE LOG
			l.salvar(countrySave, "country");
			
		}else if (status.equals("alterar")) {
			l.alterar(countrySave, "country");
		}

		return countrySave;
	}
	
	//BUSCA TODOS OS COUNTRYS CADASTRADOS E ATIVOS
	public List<Country> findAllCountry(){
		List<Country> COUNTRYS = countryRepository.findAllCountry().orElseThrow(() -> new NotFound("Registros não encontrados"));
		return COUNTRYS;
	}
	
	//DESABILITA UM COUNTRY
	public List<Country> desabilitar(Long id){
		countryRepository.desabilitar(id);
		
		Country c = new Country();
		c.setId(id);		
		l.desabilitar(id, "country");
		
		return findAllCountry();
	}
}
