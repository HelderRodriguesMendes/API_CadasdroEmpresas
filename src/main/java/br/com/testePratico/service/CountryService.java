package br.com.testePratico.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.log.Log;
import br.com.testePratico.model.Country;
import br.com.testePratico.repository.CountryRepository;

@Service
public class CountryService {

	@Autowired
	CountryRepository countryRepository;

	Log l = new Log();

	public Country cadastrar(Country country, String status) {
		Country countrySave = countryRepository.save(country);

		if (status.equals("cadastro")) {			
			// SALVANDO DADOS NO ARQUIVO DE LOG
			l.salvar(countrySave, "country");
		}

		return countrySave;
	}
}
