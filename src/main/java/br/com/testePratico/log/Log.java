package br.com.testePratico.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.testePratico.model.Country;

public class Log {

	public void salvar(Country country, String entity) {
		FileWriter arqui;

		try {
			arqui = new FileWriter(salvar_config(entity), true);

			// MONTANDO A NOVA LINHA DO ARQUIVO
			arqui.write("id:" + country.getId() + "-");
			arqui.write("name:" + country.getName() + "\n");

			arqui.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//BUSCA TODOS OS DADOS SALVOS NO ARQUIVO
	public List<Country>getCountry(String entity){
		BufferedReader bf = get_config(entity);
		List<Country> COUNTRYS = new ArrayList<>();
		String linha;
		
		try {
			Country c;
			while(bf.ready()) {
				linha = bf.readLine();
				c = toObjetoCountry(linha);
				COUNTRYS.add(c);
			}
		} catch (IOException e) {

		}
		return COUNTRYS;
	}
	
	
	

	// IDENTIFICA O ARQUIVO A SER UTILIZADO
	public File salvar_config(String entity) {
		File arq = null;

		if (entity.equals("country")) {
			arq = new File("C:\\Users\\helde\\Documents\\Country.txt");
		}
		return arq;
	}

	public BufferedReader get_config(String entity) {
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

	public Country toObjetoCountry(String linha) {
		Country c = new Country();

		if (!linha.equals("")) {
			String[] separado = linha.split("-");

			String ID = separado[0];
			String NAME = separado[1];

			String[] separaID = ID.split(":");
			String[] separaName = NAME.split(":");
			
			c.setId(Long.valueOf(separaID[1]));
			c.setName(separaName[1]);
		}
		return c;
	}
}
