package br.com.testePratico.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LogConfig {

	// DELETA O ARQUIVO DE LOG COM DADOS ANTIVOS E CRIA UM NOVO COM DADOS NOVOS
	public void novoTxtAlterado(List<String> LINHAS, String entity) {

		File deletar = configCaminhoPasta(entity);
		deletar.delete();

		File TXTalterado = configCaminhoPasta(entity);

		FileWriter arqui;

		try {
			arqui = new FileWriter(TXTalterado);

			int cont = 0;
			for (String l : LINHAS) {
				if (cont == 0) {
					arqui.write(l);
					cont += 1;
				} else if (cont > 0) {
					arqui.write("\n" + l);
				}

			}
			arqui.close();
		} catch (IOException e) {
		}
	}

	// IDENTIFICA O ARQUIVO A SER UTILIZADO
	public File configCaminhoPasta(String entity) {
		File arq = null;
		String caminhoPasta = "C:\\Users\\helde\\Documents\\";
		if (entity.equals("country")) {
			arq = new File(caminhoPasta + "Country.txt");
		} else if (entity.equals("state")) {
			arq = new File(caminhoPasta + "State.txt");
		} else if (entity.equals("city")) {
			arq = new File(caminhoPasta + "City.txt");
		} else if (entity.equals("neighborhood")) {
			arq = new File(caminhoPasta + "Neighborhood.txt");
		} else if (entity.equals("company")) {
			arq = new File(caminhoPasta + "Company.txt");
		}
		return arq;
	}

	// IDENTIFICA O ARQUIVO A SER UTILIZADO
	public BufferedReader configCaminho_Pasta(String entity) {
		FileReader lerArq = null;
		BufferedReader bf = null;
		
		String caminhoPasta = "C:\\Users\\helde\\Documents\\";
		try {
			if (entity.equals("country")) {
				lerArq = new FileReader(caminhoPasta + "Country.txt");
			} else if (entity.equals("state")) {
				lerArq = new FileReader(caminhoPasta + "State.txt");
			} else if (entity.equals("city")) {
				lerArq = new FileReader(caminhoPasta + "City.txt");
			} else if (entity.equals("neighborhood")) {
				lerArq = new FileReader(caminhoPasta + "Neighborhood.txt");
			} else if (entity.equals("company")) {
				lerArq = new FileReader(caminhoPasta + "Company.txt");
			}

			bf = new BufferedReader(lerArq);
		} catch (FileNotFoundException ex) {

		}

		return bf;
	}
}
