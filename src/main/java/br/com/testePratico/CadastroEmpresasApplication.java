package br.com.testePratico;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.testePratico.log.LogCity;
import br.com.testePratico.log.LogCompany;
import br.com.testePratico.log.LogConfig;
import br.com.testePratico.log.LogCountry;
import br.com.testePratico.log.LogNeighborhood;
import br.com.testePratico.log.LogState;
import br.com.testePratico.model.City;
import br.com.testePratico.model.Company;
import br.com.testePratico.model.Country;
import br.com.testePratico.model.Neighborhood;
import br.com.testePratico.model.State;
import br.com.testePratico.service.CityService;
import br.com.testePratico.service.CompanyService;
import br.com.testePratico.service.CountryService;
import br.com.testePratico.service.NeighborhoodService;
import br.com.testePratico.service.StateService;

@SpringBootApplication
public class CadastroEmpresasApplication implements CommandLineRunner {

	@Autowired
	CountryService countryService;

	@Autowired
	StateService stateService;

	@Autowired
	CityService cityService;

	@Autowired
	NeighborhoodService neighborhoodService;

	@Autowired
	CompanyService companyService;

	FileWriter fw;

	LogCountry lg = new LogCountry();
	LogState ls = new LogState();
	LogCity lc = new LogCity();
	LogNeighborhood ln = new LogNeighborhood();
	LogCompany lcom = new LogCompany();

	public static void main(String[] args) {
		SpringApplication.run(CadastroEmpresasApplication.class, args);
	}

	// SALVA NOVAMENTE NO BANCO TODOS OS DADOS QUE JÁ ESTÃO SALVOS NOS ARQUIVOS
	@Override
	public void run(String... args) throws Exception {

		LogConfig config = new LogConfig();
		
		// CRIA OS ARQUIVOS EM BRANCO PARA NÃO DAR ERRO NO getCountry
		File arqCountry = config.salvar_deletar_config("country");
		fw = new FileWriter(arqCountry, true);

		File arqState = config.salvar_deletar_config("state");
		fw = new FileWriter(arqState, true);

		File arqCity = config.salvar_deletar_config("city");
		fw = new FileWriter(arqCity, true);

		File arqNeighborhood = config.salvar_deletar_config("neighborhood");
		fw = new FileWriter(arqNeighborhood, true);

		File arqCompany = config.salvar_deletar_config("company");
		fw = new FileWriter(arqCompany, true);

		// PEGA TODOS OS DADOS SALVOS NOS ARQUIVOS DE LOG
		List<Country> COUNTRYS = lg.getCountry("country");
		List<State> STATES = ls.getState("state");
		List<City> CITYS = lc.getCity("city");
		List<Neighborhood> NEIG = ln.getNeighborhood("neighborhood");
		List<Company> COMPA = lcom.getCompany("company");

		// SE HOUVER DADOS SALVOS NOS ARQUIVOS, SALVA ELES NO BANCO NOVAMENTE
		if (!COUNTRYS.isEmpty()) {
			for (Country c : COUNTRYS) {
				countryService.cadastrar(c, "banco");
			}
		}
		if (!STATES.isEmpty()) {
			for (State s : STATES) {
				stateService.cadastrar(s, "banco");
			}
		}
		if (!CITYS.isEmpty()) {
			for (City c : CITYS) {
				cityService.cadastrar(c, "banco");
			}
		}
		if (!NEIG.isEmpty()) {
			for (Neighborhood n : NEIG) {
				neighborhoodService.cadastrar(n, "banco");
			}
		}
		if (!COMPA.isEmpty()) {
			for (Company c : COMPA) {
				companyService.cadastrar(c, "banco");
			}
		}

	}

}
