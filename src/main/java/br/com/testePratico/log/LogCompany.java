package br.com.testePratico.log;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.testePratico.model.City;
import br.com.testePratico.model.Company;
import br.com.testePratico.model.Country;
import br.com.testePratico.model.Neighborhood;
import br.com.testePratico.model.State;

public class LogCompany {
	LogConfig lc = new LogConfig();

	public void salvar(Company company, String entity) {
		FileWriter arqui;

		try {
			arqui = new FileWriter(lc.salvar_deletar_config(entity), true);

			// MONTANDO A NOVA LINHA DO ARQUIVO
			List<Company> COMPANYS = getCompany("company");
			if (COMPANYS.isEmpty()) {
				arqui.write("id:" + company.getId() + "#");
				arqui.write("tradeName:" + company.getTradeName() + "#");
				arqui.write("corporateName:" + company.getCorporateName() + "#");
				arqui.write("country:" + company.getCountry().getId() + "#");
				arqui.write("state:" + company.getState().getId() + "#");
				arqui.write("city:" + company.getCity().getId() + "#");
				arqui.write("neighborhood:" + company.getNeighborhood().getId() + "#");
				arqui.write("address:" + company.getAddress() + "#");
				arqui.write("phone:" + company.getPhone() + "#");
				arqui.write("federalTaxNumber:" + company.getFederalTaxNumber());
				arqui.write("ativo:" + company.getAtivo());
			} else {
				arqui.write("\n" + "id:" + company.getId() + "#");
				arqui.write("tradeName:" + company.getTradeName() + "#");
				arqui.write("corporateName:" + company.getCorporateName() + "#");
				arqui.write("country:" + company.getCountry().getId() + "#");
				arqui.write("state:" + company.getState().getId() + "#");
				arqui.write("city:" + company.getCity().getId() + "#");
				arqui.write("neighborhood:" + company.getNeighborhood().getId() + "#");
				arqui.write("address:" + company.getAddress() + "#");
				arqui.write("phone:" + company.getPhone() + "#");
				arqui.write("federalTaxNumber:" + company.getFederalTaxNumber());
				arqui.write("ativo:" + company.getAtivo());
			}

			arqui.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// BUSCA TODOS OS DADOS SALVOS NO ARQUIVO
	public List<Company> getCompany(String entity) {
		BufferedReader bf = lc.get_Alter_config(entity);
		List<Company> COMPANYS = new ArrayList<>();
		String linha;

		try {
			Company c;
			while (bf.ready()) {
				linha = bf.readLine();
				c = toObjetoCompany(linha);
				COMPANYS.add(c);
			}
		} catch (IOException e) {

		}
		return COMPANYS;
	}

	public void alterar(Company company, String entity) {
		BufferedReader bf = lc.get_Alter_config(entity);
		List<String> LINHAS = new ArrayList<>();
		String linha, linhaAlterada, alteracaoAtual;

		try {
			while (bf.ready()) {
				linha = bf.readLine();
				Company c = toObjetoCompany(linha);

				if (c.getId() == company.getId()) {
					alteracaoAtual = linhaAlteradaCompany(company);
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

	public Company toObjetoCompany(String linha) {
		Company c = new Company();

		if (!linha.equals("")) {
			String[] separado = linha.split("#");

			String ID = separado[0];
			String TRADENAME = separado[1];
			String CORPORATENAME = separado[2];
			String COUNTRY = separado[3];
			String STATE = separado[4];
			String CITY = separado[5];
			String NEIGHBORHOOD = separado[6];
			String ADDRESS = separado[7];
			String PHONE = separado[8];
			String FEDERALTAXNUMBER = separado[9];
			String ATIVO = separado[10];

			String[] separaID = ID.split(":");
			String[] separaTradeName = TRADENAME.split(":");
			String[] separaCorporateName = CORPORATENAME.split(":");
			String[] separaCountry = COUNTRY.split(":");
			String[] separaState = STATE.split(":");
			String[] separaCity = CITY.split(":");
			String[] separaNeighborhood = NEIGHBORHOOD.split(":");
			String[] separaAddress = ADDRESS.split(":");
			String[] separaPhone = PHONE.split(":");
			String[] separaFederalTaxNumber = FEDERALTAXNUMBER.split(":");
			String[] separaAtivo = ATIVO.split(":");

			c.setId(Long.valueOf(separaID[1]));
			c.setTradeName(separaTradeName[1]);
			c.setCorporateName(separaCorporateName[1]);
			Country country = new Country();
			country.setId(Long.valueOf(separaCountry[1]));
			c.setCountry(country);
			State state = new State();
			state.setId(Long.valueOf(separaState[1]));
			c.setState(state);
			City city = new City();
			city.setId(Long.valueOf(separaCity[1]));
			c.setCity(city);
			Neighborhood neighborhood = new Neighborhood();
			neighborhood.setId(Long.valueOf(separaNeighborhood[1]));
			c.setNeighborhood(neighborhood);
			c.setAddress(separaAddress[1]);
			c.setPhone(separaPhone[1]);
			c.setFederalTaxNumber(separaFederalTaxNumber[1]);
			c.setAtivo(Boolean.valueOf(separaAtivo[1]));
		}
		return c;
	}

	public String linhaAlteradaCompany(Company c) {
		String linha = "id:" + c.getId();
		linha += "#" + "tradeName:" + c.getTradeName();
		linha += "#" + "corporateName:" + c.getCorporateName();
		linha += "#" + "country:" + c.getCountry().getId();
		linha += "#" + "state:" + c.getState().getId();
		linha += "#" + "city:" + c.getCity().getId();
		linha += "#" + "neighborhood:" + c.getNeighborhood().getId();
		linha += "#" + "address:" + c.getAddress();
		linha += "#" + "phone:" + c.getPhone();
		linha += "#" + "federalTaxNumber:" + c.getFederalTaxNumber();
		linha += "#" + "ativo:" + c.getAtivo();

		return linha;
	}

	public void desabilitar_ativar(Long id, String entity, boolean ativar) {
		BufferedReader bf = lc.get_Alter_config(entity);
		List<String> LINHAS = new ArrayList<>();
		String linha, linhaAlterada, alteracaoAtual;

		try {
			while (bf.ready()) {
				linha = bf.readLine();
				Company c = toObjetoCompany(linha);

				if (c.getId() == id) {
					if(!ativar) {
						c.setAtivo(false);
					}else {
						c.setAtivo(true);
					}
					alteracaoAtual = linhaAlteradaCompany(c);
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
