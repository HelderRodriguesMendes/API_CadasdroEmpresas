package br.com.testePratico.log;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.testePratico.model.City;
import br.com.testePratico.model.Neighborhood;

public class LogNeighborhood {

	LogConfig lc = new LogConfig();

	// SALVA OS DADOS NO ARQUIVO DE LOG
	public void salvar(Neighborhood neighborhood, String entity) {
		FileWriter arqui;

		try {
			arqui = new FileWriter(lc.configCaminhoPasta(entity), true);

			// MONTANDO A NOVA LINHA DO ARQUIVO
			List<Neighborhood> NEIG = getNeighborhood("neighborhood");
			if (NEIG.isEmpty()) {
				arqui.write("id:" + neighborhood.getId() + "#");
				arqui.write("name:" + neighborhood.getName() + "#");
				arqui.write("city:" + neighborhood.getCity().getId() + "#");
				arqui.write("ativo:" + neighborhood.getAtivo());
			} else {
				arqui.write("\n" + "id:" + neighborhood.getId() + "#");
				arqui.write("name:" + neighborhood.getName() + "#");
				arqui.write("city:" + neighborhood.getCity().getId() + "#");
				arqui.write("ativo:" + neighborhood.getAtivo());
			}

			arqui.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// BUSCA TODOS OS DADOS SALVOS NO ARQUIVO
	public List<Neighborhood> getNeighborhood(String entity) {
		BufferedReader bf = lc.configCaminho_Pasta(entity);
		List<Neighborhood> NEIG = new ArrayList<>();
		String linha;

		try {
			Neighborhood n;
			while (bf.ready()) {
				linha = bf.readLine();
				n = toObjetoNeighborhood(linha);
				NEIG.add(n);
			}
			bf.close();
		} catch (IOException e) {

		}
		return NEIG;
	}

	// ALTERA DADOS NO ARQUIVO DE LOG
	public void alterar(Neighborhood neighborhood, String entity) {
		BufferedReader bf = lc.configCaminho_Pasta(entity);
		List<String> LINHAS = new ArrayList<>();
		String linha, linhaAlterada, alteracaoAtual;

		try {
			while (bf.ready()) {
				linha = bf.readLine();
				Neighborhood n = toObjetoNeighborhood(linha);

				if (n.getId() == neighborhood.getId()) {
					alteracaoAtual = linhaAlteradaNeighborhood(neighborhood);
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
	public Neighborhood toObjetoNeighborhood(String linha) {
		Neighborhood n = new Neighborhood();

		if (!linha.equals("")) {
			String[] separado = linha.split("#");

			String ID = separado[0];
			String NAME = separado[1];
			String FK = separado[2];
			String ATIVO = separado[3];

			String[] separaID = ID.split(":");
			String[] separaName = NAME.split(":");
			String[] separaFk = FK.split(":");
			String[] separaAtivo = ATIVO.split(":");

			n.setId(Long.valueOf(separaID[1]));
			n.setName(separaName[1]);
			City ci = new City();
			ci.setId(Long.valueOf(separaFk[1]));
			n.setCity(ci);
			n.setAtivo(Boolean.valueOf(separaAtivo[1]));
		}
		return n;
	}

	// CONVERTE UM OBJETO PARA UMA LINHA A SER SALVA OU ALTERADA NO ARQUIVO
	public String linhaAlteradaNeighborhood(Neighborhood n) {
		String linha = "id:" + n.getId();
		linha += "#" + "name:" + n.getName();
		linha += "#" + "city:" + n.getCity().getId();
		linha += "#" + "ativo:" + n.getAtivo();

		return linha;
	}

	// DESATIVA OU ATIVA UMA LINHA SALVA NO ARQUIVO
	public void desabilitar_ativar(Long id, String entity, boolean ativar) {
		BufferedReader bf = lc.configCaminho_Pasta(entity);
		List<String> LINHAS = new ArrayList<>();
		String linha, linhaAlterada, alteracaoAtual;
		try {
			while (bf.ready()) {
				linha = bf.readLine();
				Neighborhood n = toObjetoNeighborhood(linha);

				if (n.getId() == id) {
					if (!ativar) {
						n.setAtivo(false);
					} else {
						n.setAtivo(true);
					}
					alteracaoAtual = linhaAlteradaNeighborhood(n);
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
}
