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
import br.com.testePratico.repository.StateRepository;

@Service
public class CityService {
	@Autowired
	CityRepository cityRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	CountryService countryService;

	@Autowired
	StateService stateService;

	LogCity lc = new LogCity();
	LogState ls = new LogState();
	LogCountry lCoun = new LogCountry();

	City citySave = new City();

	// SALVA UMA CITY NO BANCO E NO ARQUIVO DE LOG
	public Boolean cadastrar(City city_recebida, String status) {
		Country countrySave = null;
		State stateSave = null;
		boolean RESPOSTA = false;

		// VERIFICA SE O STATE JÁ ESTA CADASTRADO
		if (status.equals("cadastrar")) {
			Optional<City> c = cityRepository.verificarCity(city_recebida.getName());
			if (c.isEmpty()) {

				State state_recebido = city_recebida.getState();
				Country country_recebido = state_recebido.getCountry();

				// VERIFICANDO SE AS FKS ESTAO SALVAS OU NAO, CASO NÃO: ELAS SAO SALVAS
				countrySave = countryService.verificarCadastro(country_recebido);
				state_recebido.setCountry(countrySave);
				stateSave = stateService.verificarCadastro(state_recebido);
				city_recebida.setState(stateSave);

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

	// BUSCA POR FK AS CITYS CADASTRADAS
	public List<City> cityNameState(String name) {

		Optional<State> state = stateRepository.verificarState(name);
		List<City> CITYS = new ArrayList<>();
		if (!state.isEmpty()) {
			List<City> citys = lc.getCity("city");
			for (City c : citys) {
				if (c.getState().getId() == state.get().getId()) {
					CITYS.add(c);
				}
			}
		}

		return CITYS;
	}

	public City verificarCadastro(City city) {

		Optional<City> cityBanco = cityRepository.verificarCity(city.getName());

		if (cityBanco.isEmpty()) {
			citySave = cityRepository.save(city);
			lc.salvar(citySave, "city");
		} else {
			citySave = cityBanco.get();
		}
		return citySave;
	}
}
