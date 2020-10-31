package br.com.testePratico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.exception.NotFound;
import br.com.testePratico.log.LogCity;
import br.com.testePratico.model.City;
import br.com.testePratico.repository.CityRepository;

@Service
public class CityService {
	@Autowired
	CityRepository cityRepository;

	LogCity lc = new LogCity();

	public Boolean cadastrar(City city, String status) {
		City citySave = null;
		boolean RESPOSTA = false;

		// VERIFICA SE O STATE JÁ ESTA CADASTRADO E SE ESTA ATIVO OU NÃO
		if (status.equals("cadastrar")) {
			citySave = cityRepository.save(city);

			// SALVANDO DADOS NO ARQUIVO DE LOG
			lc.salvar(citySave, "city");
			RESPOSTA = true;

			// SALVA OS DADOS QUE ESTAO NO LOG AO INICIAR A API
		} else if (status.equals("banco")) {
			cityRepository.save(city);
		} else if (status.equals("alterar")) {
			citySave = cityRepository.save(city);
			lc.alterar(citySave, "city");
			RESPOSTA = true;
		}
		return RESPOSTA;
	}

	// BUSCA TODOS OS STATES CADASTRADOS E ATIVOS
	public List<City> findAllCity() {
		List<City> CITYS = cityRepository.findAllCity().orElseThrow(() -> new NotFound("Registros não encontrados"));
		return CITYS;
	}

	// BUSCA POR NOME OS STATES CADASTRADOS E ATIVOS
	public List<City> cityName(String name) {
		List<City> CITYS = cityRepository.stateName(name).orElseThrow(() -> new NotFound("Registros não encontrados"));
		return CITYS;
	}
}
