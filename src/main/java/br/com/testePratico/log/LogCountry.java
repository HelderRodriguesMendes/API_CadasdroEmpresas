package br.com.testePratico.log;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.testePratico.model.Country;

public class LogCountry {

	LogConfig lc = new LogConfig();

	// SALVA OS DADOS NO ARQUIVO DE LOG
	public void salvar(Country country, String entity) {
		FileWriter arqui;

		try {
			arqui = new FileWriter(lc.configCaminhoPasta(entity), true);

			// MONTANDO A NOVA LINHA DO ARQUIVO
			List<Country> COUNTRYS = getCountry("country");

			if (COUNTRYS.isEmpty()) {
				arqui.write("id:" + country.getId() + "#");
				arqui.write("name:" + country.getName());
			} else {
				arqui.write("\n" + "id:" + country.getId() + "#");
				arqui.write("name:" + country.getName());
			}

			arqui.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// BUSCA TODOS OS DADOS SALVOS NO ARQUIVO
	public List<Country> getCountry(String entity) {
		BufferedReader bf = lc.get_Alter_config(entity);
		List<Country> COUNTRYS = new ArrayList<>();
		String linha;

		try {
			Country c;
			while (bf.ready()) {
				linha = bf.readLine();
				c = toObjetoCountry(linha);
				COUNTRYS.add(c);
			}
		} catch (IOException e) {

		}
		return COUNTRYS;
	}

	// ALTERA DADOS NO ARQUIVO DE LOG
	public void alterar(Country country, String entity) {
		BufferedReader bf = lc.get_Alter_config(entity);
		List<String> LINHAS = new ArrayList<>();
		String linha, linhaAlterada, alteracaoAtual;
		System.out.println("country id: " + country.getId());

		try {
			while (bf.ready()) {
				linha = bf.readLine();
				Country c = toObjetoCountry(linha);

				if (c.getId() == country.getId()) {
					alteracaoAtual = linhaAlteradaCountry(country);
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
	public Country toObjetoCountry(String linha) {
		Country c = new Country();

		if (!linha.equals("")) {
			String[] separado = linha.split("#");

			String ID = separado[0];
			String NAME = separado[1];

			String[] separaID = ID.split(":");
			String[] separaName = NAME.split(":");

			c.setId(Long.valueOf(separaID[1]));
			c.setName(separaName[1]);
		}
		return c;
	}

	// CONVERTE UM OBJETO PARA UMA LINHA A SER SALVA OU ALTERADA NO ARQUIVO
	public String linhaAlteradaCountry(Country c) {
		String linha = "id:" + c.getId();
		linha += "#" + "name:" + c.getName();

		return linha;
	}

}
