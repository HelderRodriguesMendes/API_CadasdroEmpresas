package br.com.testePratico.log;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.testePratico.model.City;
import br.com.testePratico.model.State;

public class LogCity {
	LogConfig lc = new LogConfig();

	public void salvar(City city, String entity) {
		FileWriter arqui;

		try {
			arqui = new FileWriter(lc.salvar_deletar_config(entity), true);

			// MONTANDO A NOVA LINHA DO ARQUIVO
			List<City> CITYS = getCity("city");
			if (CITYS.isEmpty()) {
				arqui.write("id:" + city.getId() + "#");
				arqui.write("name:" + city.getName() + "#");
				arqui.write("state:" + city.getState().getId());
			} else {
				arqui.write("\n" + "id:" + city.getId() + "#");
				arqui.write("name:" + city.getName() + "#");
				arqui.write("state:" + city.getState().getId());
			}

			arqui.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// BUSCA TODOS OS DADOS SALVOS NO ARQUIVO
	public List<City> getCity(String entity) {
		BufferedReader bf = lc.get_Alter_config(entity);
		List<City> CITYS = new ArrayList<>();
		String linha;

		try {
			City c;
			while (bf.ready()) {
				linha = bf.readLine();
				c = toObjetoCity(linha);
				CITYS.add(c);
			}
		} catch (IOException e) {

		}
		return CITYS;
	}

	public void alterar(City city, String entity) {
		BufferedReader bf = lc.get_Alter_config(entity);
		List<String> LINHAS = new ArrayList<>();
		String linha, linhaAlterada, alteracaoAtual;

		try {
			while (bf.ready()) {
				linha = bf.readLine();
				City s = toObjetoCity(linha);

				if (s.getId() == city.getId()) {
					alteracaoAtual = linhaAlteradaCity(city);
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

	public City toObjetoCity(String linha) {
		City c = new City();

		if (!linha.equals("")) {
			String[] separado = linha.split("#");

			String ID = separado[0];
			String NAME = separado[1];
			String FK = separado[2];

			String[] separaID = ID.split(":");
			String[] separaName = NAME.split(":");
			String[] separaFk = FK.split(":");

			c.setId(Long.valueOf(separaID[1]));
			c.setName(separaName[1]);
			State s = new State();
			s.setId(Long.valueOf(separaFk[1]));
			c.setState(s);
		}
		return c;
	}

	public String linhaAlteradaCity(City c) {
		String linha = "id:" + c.getId();
		linha += "#" + "name:" + c.getName();
		linha += "#" + "state:" + c.getState().getId();

		return linha;
	}
}
