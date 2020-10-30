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
	
	public void salvar(State state, String entity) {
		FileWriter arqui;

		try {
			arqui = new FileWriter(lc.salvar_deletar_config(entity), true);

			// MONTANDO A NOVA LINHA DO ARQUIVO
			arqui.write("id:" + state.getId() + "-");
			arqui.write("name:" + state.getName() + "-");
			arqui.write("country:" + state.getCountry().getId() + "-");
			arqui.write("ativo:" + state.getAtivo() + "\n");

			arqui.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// BUSCA TODOS OS DADOS SALVOS NO ARQUIVO
		public List<State> getState(String entity) {
			BufferedReader bf = lc.get_Alter_config(entity);
			List<State> STATES = new ArrayList<>();
			String linha;

			try {
				State s;
				while (bf.ready()) {
					linha = bf.readLine();
					s = toObjetoState(linha);
					STATES.add(s);
				}
			} catch (IOException e) {

			}
			return STATES;
		}
		
		public State toObjetoState(String linha) {
			State s = new State();

			if (!linha.equals("")) {
				String[] separado = linha.split("-");

				String ID = separado[0];
				String NAME = separado[1];
				String FK = separado[2];
				String ATIVO = separado[3];

				String[] separaID = ID.split(":");
				String[] separaName = NAME.split(":");
				String[] separaAtivo = ATIVO.split(":");
				String[] separaFk = FK.split(":");

				s.setId(Long.valueOf(separaID[1]));
				s.setName(separaName[1]);
				s.setAtivo(Boolean.valueOf(separaAtivo[1]));
				Country c = new Country();
				c.setId(Long.valueOf(separaFk[1]));
				s.setCountry(c);
			}
			return s;
		}
}
