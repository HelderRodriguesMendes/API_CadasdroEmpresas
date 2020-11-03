package br.com.testePratico.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.exception.NotFound;
import br.com.testePratico.log.LogCity;
import br.com.testePratico.model.City;
import br.com.testePratico.model.State;
import br.com.testePratico.repository.CityRepository;
import br.com.testePratico.repository.StateRepository;

@Service
public class CityService {
	@Autowired
	CityRepository cityRepository;

	@Autowired
	StateRepository stateRepository;

	LogCity lc = new LogCity();

	// SALVA UMA CITY NO BANCO E NO ARQUIVO DE LOG
	public Boolean cadastrar(City city, String status) {
		City citySave = null;
		boolean RESPOSTA = false;

		// VERIFICA SE O STATE JÁ ESTA CADASTRADO
		if (status.equals("cadastrar")) {
			Optional<City> c = cityRepository.verificarCity(city.getName());
			if (c.isEmpty()) {
				citySave = cityRepository.save(city);

				// SALVANDO DADOS NO ARQUIVO DE LOG
				lc.salvar(citySave, "city");
				RESPOSTA = true;
			} else {
				RESPOSTA = true;
			}

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

	// BUSCA TODOS AS CITYS CADASTRADAS
	public List<City> findAllCity() {
		List<City> CITYS = cityRepository.findAllCity().orElseThrow(() -> new NotFound("Registros não encontrados"));
		return CITYS;
	}

	// BUSCA POR NOME AS CITYS CADASTRADAS
	public List<City> cityName(String name) {
		List<City> CITYS = cityRepository.cityName(name).orElseThrow(() -> new NotFound("Registros não encontrados"));
		return CITYS;
	}

	// BUSCA POR NOME AS CITYS CADASTRADAS
	public List<City> cityNameState(String name) {		
		Optional<State> state = stateRepository.verificarState(name);
		List<City> citys = lc.getCity("city");
		List<City> CITYS = new ArrayList<>();

		for (City c : citys) {
			if (c.getState().getId() == state.get().getId()) {
				CITYS.add(c);
			}
		}
		return CITYS;
	}
}
