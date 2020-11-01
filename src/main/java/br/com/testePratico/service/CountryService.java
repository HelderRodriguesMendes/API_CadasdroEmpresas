package br.com.testePratico.service;

import java.util.List;
import java.util.Optional;

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

	// SALVA UM COUNTRY NO BANCO E NO ARQUIVO DE LOG
	public Boolean cadastrar(Country country, String status) {
		Country countrySave = null;
		boolean RESPOSTA = false;

		// VERIFICA SE O COUNTRY JÁ ESTA CADASTRADO
		if (status.equals("cadastrar")) {
			Optional<Country> c = countryRepository.verificarCountry(country.getName());

			// SE NÃO ESTIVER CADASTRADO
			if (c.isEmpty()) {
				countrySave = countryRepository.save(country);

				// SALVANDO DADOS NO ARQUIVO DE LOG
				l.salvar(countrySave, "country");
				RESPOSTA = true;
			} else {
				RESPOSTA = true;
			}

			// SALVA OS DADOS QUE ESTAO NO LOG AO INICIAR A API
		} else if (status.equals("banco")) {
			countryRepository.save(country);
		} else if (status.equals("alterar")) {
			countrySave = countryRepository.save(country);
			l.alterar(countrySave, "country");
			RESPOSTA = true;
		}

		return RESPOSTA;
	}

	// BUSCA TODOS OS COUNTRYS CADASTRADOS
	public List<Country> findAllCountry() {
		List<Country> COUNTRYS = countryRepository.findAllCountry()
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return COUNTRYS;
	}

	// BUSCA POR NOME OS COUNTRYS CADASTRADOS
	public List<Country> countryName(String name) {
		List<Country> COUNTRYS = countryRepository.countryName(name)
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return COUNTRYS;
	}
}
