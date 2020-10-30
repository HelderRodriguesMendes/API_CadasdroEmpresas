package br.com.testePratico.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		boolean ok = true, RESPOSTA = false;

		// VERIFICA SE O STATE JÁ ESTA CADASTRADO E SE ESTA ATIVO OU NÃO
		if (status.equals("cadastrar")) {
			Optional<State> s = stateRepository.verificarState(state.getName());

			// SE NÃO ESTIVER CADASTRADO
			if (s.isEmpty()) {
				stateSave = stateRepository.save(state);
				RESPOSTA = true;
			} else {
				// SE O STATE A SER CADASTRADO JA EXISTIR, E ESTIVER DESATIVO, SERA ATIVADO
				if (!s.get().getAtivo()) {
					ok = false;
					
					//ainda falta
				}
			}
			// SALVA OS DADOS QUE ESTAO NO LOG AO INICIAR A API
		} else if (status.equals("banco")) {
			stateRepository.save(state);
		}

		
		// SALSA OU ALTERA OS DADOS NO LOG
		if (status.equals("cadastrar") && ok) {
			ls.salvar(stateSave, "state");
		}
		return RESPOSTA;
	}
}
