package br.com.testePratico.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.testePratico.model.Country;

public class LogConfig {

	public void novoTxtAlterado(List<String> LINHAS) {

		File deletar = salvar_deletar_config("country");
		deletar.delete();

		File TXTalterado = salvar_deletar_config("country");

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
	public File salvar_deletar_config(String entity) {
		File arq = null;

		if (entity.equals("country")) {
			arq = new File("C:\\Users\\helde\\Documents\\Country.txt");
		}
		return arq;
	}

	public BufferedReader get_Alter_config(String entity) {
		FileReader lerArq = null;
		BufferedReader bf = null;

		try {
			if (entity.equals("country")) {
				lerArq = new FileReader("C:\\Users\\helde\\Documents\\Country.txt");
			}

			bf = new BufferedReader(lerArq);
		} catch (FileNotFoundException ex) {

		}

		return bf;
	}

	public String linhaAlteradaCountry(Country c) {
		String linha = "id:" + c.getId();
		linha += "-" + "name:" + c.getName();
		linha += "-" + "ativo:" + c.getAtivo();

		return linha;
	}
}
