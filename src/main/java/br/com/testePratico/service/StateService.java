package br.com.testePratico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.exception.NotFound;
import br.com.testePratico.log.LogState;
import br.com.testePratico.model.State;
import br.com.testePratico.repository.StateRepository;

@Service
public class StateService {
	@Autowired
	StateRepository stateRepository;

	LogState ls = new LogState();

	public Boolean cadastrar(State state, String status) {
		State stateSave = null;
		boolean RESPOSTA = false;

		// VERIFICA SE O STATE JÁ ESTA CADASTRADO E SE ESTA ATIVO OU NÃO
		if (status.equals("cadastrar")) {
			Optional<State> s = stateRepository.verificarState(state.getName());

			// SE NÃO ESTIVER CADASTRADO
			if (s.isEmpty()) {
				stateSave = stateRepository.save(state);
				
				// SALVANDO DADOS NO ARQUIVO DE LOG
				ls.salvar(stateSave, "state");
				RESPOSTA = true;
			} else {
				// SE O STATE A SER CADASTRADO JA EXISTIR, E ESTIVER DESATIVO, SERA ATIVADO
				if (!s.get().getAtivo()) {

					// ainda falta
				}
			}
		// SALVA OS DADOS QUE ESTAO NO LOG AO INICIAR A API
		} else if (status.equals("banco")) {
			stateRepository.save(state);
		}else if (status.equals("alterar")) {
			stateSave = stateRepository.save(state);
			System.out.println("nome: " + stateSave.getName());
			ls.alterar(stateSave, "state");
			RESPOSTA = true;
		}
		return RESPOSTA;
	}

	// BUSCA TODOS OS STATES CADASTRADOS E ATIVOS
	public List<State> findAllState() {
		List<State> STATES = stateRepository.findAllState()
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return STATES;
	}

	// BUSCA POR NOME OS STATES CADASTRADOS E ATIVOS
	public List<State> stateName(String name) {
		List<State> STATES = stateRepository.stateName(name)
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return STATES;
	}
}
