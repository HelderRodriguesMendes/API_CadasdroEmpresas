package br.com.testePratico;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.testePratico.log.LogCountry;
import br.com.testePratico.log.LogState;
import br.com.testePratico.model.Country;
import br.com.testePratico.model.State;
import br.com.testePratico.service.CountryService;
import br.com.testePratico.service.StateService;

@SpringBootApplication
public class CadastroEmpresasApplication implements CommandLineRunner{
	
	@Autowired
	CountryService countryService;
	
	@Autowired
	StateService stateService;
	
	FileWriter fw;
	
	LogCountry lg = new LogCountry();
	LogState ls = new LogState();
	
	public static void main(String[] args) {
		SpringApplication.run(CadastroEmpresasApplication.class, args);
	}

	//SALVA NOVAMENTE NO BANCO TODOS OS DADOS QUE JÁ ESTÃO SALVOS NOS ARQUIVOS
	@Override
	public void run(String... args) throws Exception {
		
		 //CRIA OS ARQUIVOS EM BRANCO PARA NÃO DAR ERRO NO getCountry
		File arqCountry = new File("C:\\Users\\helde\\Documents\\Country.txt");
		fw = new FileWriter(arqCountry, true);
		
		File arqState = new File("C:\\Users\\helde\\Documents\\state.txt");
        fw = new FileWriter(arqState, true);
        
        
        List<Country> COUNTRYS = lg.getCountry("country");
        List<State> STATES = ls.getState("state");
        
        if(!COUNTRYS.isEmpty()) {
        	for(Country c : COUNTRYS) {
        		countryService.cadastrar(c, "banco");
        	}
        }
        
        if(!STATES.isEmpty()) {
        	for(State s : STATES) {
        		stateService.cadastrar(s, "banco");
        	}
        }
		
	}

}
