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
		boolean ok = true, RESPOSTA = false;
		
		//VERIFICA SE O COUNTRY JÁ ESTA CADASTRADO E SE ESTA ATIVO OU NÃO
		if (status.equals("cadastrar")) {
			Optional<Country> c = countryRepository.verificarCcountry(country.getName());
			
			//SE NÃO ESTIVER CADASTRADO
			if(c.isEmpty()) {
				countrySave = countryRepository.save(country);
				RESPOSTA = true;
			}else {
				//SE O Country A SER CADASTRADO JA EXISTIR, E ESTIVER DESATIVO, SERA ATIVADO
				if(!c.get().getAtivo()) {
					ok = false;
					desabilitar_ativar(c.get().getId(), true);			
				}
				RESPOSTA = true;
				ok = false;
			}
			
			//SALVA OS DADOS QUE ESTAO NO LOG AO INICIAR A API
		}else if(status.equals("banco")) {
			countryRepository.save(country);
		}

		//SALSA OU ALTERA OS DADOS NO LOG
		if (status.equals("cadastrar") && ok) {

			// SALVANDO DADOS NO ARQUIVO DE LOG
			l.salvar(countrySave, "country"); 

		} else if (status.equals("alterar")) {
			l.alterar(countrySave, "country");
		}

		return RESPOSTA;
	}

	// BUSCA TODOS OS COUNTRYS CADASTRADOS E DESATIVADOS
	public List<Country> findAllCountryDesativados() {
		List<Country> COUNTRYS = countryRepository.findAllDesativados()
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return COUNTRYS;
	}

	// BUSCA TODOS OS COUNTRYS CADASTRADOS E ATIVOS
	public List<Country> findAllCountry() {
		List<Country> COUNTRYS = countryRepository.findAllCountry()
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		
		return COUNTRYS;
	}

	// BUSCA POR NOME OS COUNTRYS CADASTRADOS E ATIVOS
	public List<Country> countryName(String name) {
		List<Country> COUNTRYS = countryRepository.countryName(name)
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return COUNTRYS;
	}

	// DESABILITA UM COUNTRY
	public Boolean desabilitar_ativar(Long id, boolean ativar) {

		if (!ativar) {
			countryRepository.desativar(id);
			l.desabilitar_ativar(id, "country", false);
		} else {
			countryRepository.ativar(id);
			l.desabilitar_ativar(id, "country", true);
		}
		return true;
	}
}
