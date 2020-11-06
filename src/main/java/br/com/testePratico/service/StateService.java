package br.com.testePratico.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.exception.NotFound;
import br.com.testePratico.log.LogCountry;
import br.com.testePratico.log.LogState;
import br.com.testePratico.model.Country;
import br.com.testePratico.model.State;
import br.com.testePratico.repository.CountryRepository;
import br.com.testePratico.repository.StateRepository;

@Service
public class StateService {
	@Autowired
	StateRepository stateRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	CountryService countryService;

	LogState ls = new LogState();
	LogCountry lCoun = new LogCountry();
	State stateSave = new State();

	// SALVA UM STATE NO BANCO E NO ARQUIVO DE LOG
	public Boolean cadastrar(State state_recebido, String status) {
		boolean RESPOSTA = false;

		// VERIFICA SE O STATE JÁ ESTA CADASTRADO
		if (status.equals("cadastrar")) {

			Optional<State> s = stateRepository.verificarState(state_recebido.getName());

			if (s.isEmpty()) {
				Country country_recebido = state_recebido.getCountry();

				// VERIFICANDO SE A FK ESTA SALVA OU NAO, CASO NÃO: ELA É SALVA
				state_recebido.setCountry(countryService.verificarCadastro(country_recebido));

				stateSave = stateRepository.save(state_recebido);

				// SALVANDO DADOS NO ARQUIVO DE LOG
				ls.salvar(stateSave, "state");
				RESPOSTA = true;
			} else {
				RESPOSTA = true;
			}

			// SALVA OS DADOS QUE ESTAO NO LOG AO INICIAR A API
		} else if (status.equals("banco")) {
			stateRepository.save(state_recebido);
		} else if (status.equals("alterar")) {
			stateSave = stateRepository.save(state_recebido);
			ls.alterar(stateSave, "state");
			RESPOSTA = true;
		}
		return RESPOSTA;
	}

	// BUSCA TODOS OS STATES CADASTRADOS
	public List<State> findAllState() {
		List<State> STATES = stateRepository.findAllState()
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return STATES;
	}

	// BUSCA POR NOME OS STATES CADASTRADOS
	public List<State> stateName(String name) {
		List<State> STATES = stateRepository.stateName(name)
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return STATES;
	}

	// PESQUISA STATE POR COUNTRY
	public List<State> stateNameCountry(String name) {
		Optional<Country> country = countryRepository.verificarCountry(name);
		List<State> states = ls.getState("state");
		List<State> STATES = new ArrayList<>();

		for (State s : states) {
			if (s.getCountry().getId() == country.get().getId()) {
				STATES.add(s);
			}
		}
		return STATES;
	}

	public State verificarCadastro(State state) {

		Optional<State> stateBanco = stateRepository.verificarState(state.getName());

		if (stateBanco.isEmpty()) {
			stateSave = stateRepository.save(state);
			ls.salvar(stateSave, "state");
		} else {
			stateSave = stateBanco.get();
		}
		return stateSave;
	}
}
