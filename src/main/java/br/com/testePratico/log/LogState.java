package br.com.testePratico.log;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.testePratico.model.Country;
import br.com.testePratico.model.State;

public class LogState {
	LogConfig lc = new LogConfig();

	// SALVA OS DADOS NO ARQUIVO DE LOG
	public void salvar(State state, String entity) {
		FileWriter arqui;

		try {
			arqui = new FileWriter(lc.configCaminhoPasta(entity), true);

			// MONTANDO A NOVA LINHA DO ARQUIVO
			List<State> STATES = getState("state");
			if (STATES.isEmpty()) {
				arqui.write("id:" + state.getId() + "#");
				arqui.write("name:" + state.getName() + "#");
				arqui.write("country:" + state.getCountry().getId());
			} else {
				arqui.write("\n" + "id:" + state.getId() + "#");
				arqui.write("name:" + state.getName() + "#");
				arqui.write("country:" + state.getCountry().getId());
			}

			arqui.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// BUSCA TODOS OS DADOS SALVOS NO ARQUIVO
	public List<State> getState(String entity) {
		BufferedReader bf = lc.configCaminho_Pasta(entity);
		List<State> STATES = new ArrayList<>();
		String linha;

		try {
			State s;
			while (bf.ready()) {
				linha = bf.readLine();
				s = toObjetoState(linha);
				STATES.add(s);
			}
			bf.close();
		} catch (IOException e) {

		}
		return STATES;
	}

	// ALTERA DADOS NO ARQUIVO DE LOG
	public void alterar(State state, String entity) {
		BufferedReader bf = lc.configCaminho_Pasta(entity);
		List<String> LINHAS = new ArrayList<>();
		String linha, linhaAlterada, alteracaoAtual;

		try {
			while (bf.ready()) {
				linha = bf.readLine();
				State s = toObjetoState(linha);
				if (s.getId() == state.getId()) {
					alteracaoAtual = linhaAlteradaState(state);
					linhaAlterada = linha.replace(linha, alteracaoAtual);
					LINHAS.add(linhaAlterada);
				} else {
					LINHAS.add(linha);
				}
			}
			bf.close();
			lc.novoTxtAlterado(LINHAS, entity);
		} catch (IOException e) {

		}
	}

	// CONVERTE UMA LINHA DO ARQUIVO PARA OBJETO
	public State toObjetoState(String linha) {
		State s = new State();

		if (!linha.equals("")) {
			String[] separado = linha.split("#");

			String ID = separado[0];
			String NAME = separado[1];
			String FK = separado[2];

			String[] separaID = ID.split(":");
			String[] separaName = NAME.split(":");
			String[] separaFk = FK.split(":");

			s.setId(Long.valueOf(separaID[1]));
			s.setName(separaName[1]);
			Country c = new Country();
			c.setId(Long.valueOf(separaFk[1]));
			s.setCountry(c);
		}
		return s;
	}

	// CONVERTE UM OBJETO PARA UMA LINHA A SER SALVA OU ALTERADA NO ARQUIVO
	public String linhaAlteradaState(State s) {
		String linha = "id:" + s.getId();
		linha += "#" + "name:" + s.getName();
		linha += "#" + "country:" + s.getCountry().getId();
		return linha;
	}
}
