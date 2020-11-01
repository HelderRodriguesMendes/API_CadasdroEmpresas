package br.com.testePratico.log;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.testePratico.model.Country;

public class LogCountry {

	LogConfig lc = new LogConfig();

	public void salvar(Country country, String entity) {
		FileWriter arqui;

		try {
			arqui = new FileWriter(lc.salvar_deletar_config(entity), true);

			// MONTANDO A NOVA LINHA DO ARQUIVO
			List<Country> COUNTRYS = getCountry("country");
			
			if(COUNTRYS.isEmpty()) {
				arqui.write("id:" + country.getId() + "#");
				arqui.write("name:" + country.getName() + "#");
				arqui.write("ativo:" + country.getAtivo());
			}else {
				arqui.write("\n" + "id:" + country.getId() + "#");
				arqui.write("name:" + country.getName() + "#");
				arqui.write("ativo:" + country.getAtivo());
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

	public void desabilitar_ativar(Long id, String entity, boolean ativar) {
		BufferedReader bf = lc.get_Alter_config(entity);
		List<String> LINHAS = new ArrayList<>();
		String linha, linhaAlterada, alteracaoAtual;

		try {
			while (bf.ready()) {
				linha = bf.readLine();
				Country c = toObjetoCountry(linha);

				if (c.getId() == id) {
					if(!ativar) {
						c.setAtivo(false);
					}else {
						c.setAtivo(true);
					}
					alteracaoAtual = linhaAlteradaCountry(c);
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

	public Country toObjetoCountry(String linha) {
		Country c = new Country();

		if (!linha.equals("")) {
			String[] separado = linha.split("#");

			String ID = separado[0];
			String NAME = separado[1];
			String ATIVO = separado[2];

			String[] separaID = ID.split(":");
			String[] separaName = NAME.split(":");
			String[] separaAtivo = ATIVO.split(":");

			c.setId(Long.valueOf(separaID[1]));
			c.setName(separaName[1]);
			c.setAtivo(Boolean.valueOf(separaAtivo[1]));
		}
		return c;
	}
	
	public String linhaAlteradaCountry(Country c) {
		String linha = "id:" + c.getId();
		linha += "#" + "name:" + c.getName();
		linha += "#" + "ativo:" + c.getAtivo();

		return linha;
	}

}
