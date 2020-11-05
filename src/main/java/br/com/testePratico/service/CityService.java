package br.com.testePratico.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.exception.NotFound;
import br.com.testePratico.log.LogCity;
import br.com.testePratico.log.LogCountry;
import br.com.testePratico.log.LogState;
import br.com.testePratico.model.City;
import br.com.testePratico.model.Country;
import br.com.testePratico.model.State;
import br.com.testePratico.repository.CityRepository;
import br.com.testePratico.repository.CountryRepository;
import br.com.testePratico.repository.StateRepository;

@Service
public class CityService {
	@Autowired
	CityRepository cityRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	CountryRepository countryRepository;

	LogCity lc = new LogCity();
	LogState ls = new LogState();
	LogCountry lCoun = new LogCountry();

	// SALVA UMA CITY NO BANCO E NO ARQUIVO DE LOG
	public Boolean cadastrar(City city_recebida, String status) {
		Country countrySave = null;
		State stateSave = null;
		City citySave = null;
		boolean RESPOSTA = false;

		// VERIFICA SE O STATE JÁ ESTA CADASTRADO
		if (status.equals("cadastrar")) {
			Optional<City> c = cityRepository.verificarCity(city_recebida.getName());
			if (c.isEmpty()) {

				State state_recebido = city_recebida.getState();
				Country country_recebido = state_recebido.getCountry();

				// VERIFICA SE O PAIS DA CIDADE JA ESTA CADASTRADO OU NAO
				Optional<Country> countryOptional = countryRepository.verificarCountry(country_recebido.getName());

				// SE O PAIS NAO ESTIVAR CADASTRADO, JA CADASTRA O PAIS E O ESTADO
				if (countryOptional.isEmpty()) {
					countrySave = countryRepository.save(country_recebido);
					// SALVANDO NO LOG
					lCoun.salvar(countrySave, "country");
					state_recebido.setCountry(countrySave);
					stateSave = stateRepository.save(state_recebido);
					// SALVANDO NO LOG
					ls.salvar(stateSave, "state");
					city_recebida.setState(stateSave);
				} else {
					// VERIFICA DE O ESTADO DA CIDADE JA ESTA CADASTRADO
					Optional<State> stateOptional = stateRepository.verificarState(state_recebido.getName());

					if (stateOptional.isEmpty()) {
						stateSave = stateRepository.save(state_recebido);
						// SALVANDO NO LOG
						ls.salvar(stateSave, "state");
						city_recebida.setState(stateSave);
					}
				}

				citySave = cityRepository.save(city_recebida);

				// SALVANDO DADOS NO ARQUIVO DE LOG
				lc.salvar(citySave, "city");
				RESPOSTA = true;
			} else {
				RESPOSTA = true;
			}

			// SALVA OS DADOS QUE ESTAO NO LOG AO INICIAR A API
		} else if (status.equals("banco")) {
			cityRepository.save(city_recebida);
		} else if (status.equals("alterar")) {
			citySave = cityRepository.save(city_recebida);
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
		List<City> CITYS = new ArrayList<>();
		if(!state.isEmpty()) {
			List<City> citys = lc.getCity("city");			
			for (City c : citys) {
				if (c.getState().getId() == state.get().getId()) {
					CITYS.add(c);
				}
			}
		}
		
		return CITYS;
	}
}
