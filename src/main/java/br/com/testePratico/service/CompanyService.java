package br.com.testePratico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.exception.NotFound;
import br.com.testePratico.log.LogCompany;
import br.com.testePratico.model.City;
import br.com.testePratico.model.Company;
import br.com.testePratico.model.Country;
import br.com.testePratico.model.Neighborhood;
import br.com.testePratico.model.State;
import br.com.testePratico.repository.CityRepository;
import br.com.testePratico.repository.CompanyRepository;
import br.com.testePratico.repository.CountryRepository;
import br.com.testePratico.repository.NeighborhoodRepository;
import br.com.testePratico.repository.StateRepository;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	CountryRepository countryRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	NeighborhoodRepository neighborhoodRepository;
	
	@Autowired
	NeighborhoodService neighborhoodService;
	
	LogCompany lc = new LogCompany();

	// SALVA UM COUNTRY NO BANCO E NO ARQUIVO DE LOG
	public Boolean cadastrar(Company company, String status) {
		Company companySave = null;
		boolean RESPOSTA = false;

		// VERIFICA SE O COUNTRY JÁ ESTA CADASTRADO E SE ESTA ATIVO OU NÃO
		if (status.equals("cadastrar")) {

			// VERIFICA SE O COUNTRY INFORMADO JA ESTA CADASTRADO NO SISTEMA, PARA PEGAR A
			// FK
			Optional<Country> getCountry = countryRepository.verificarCcountry(company.getCountry().getName());
			if (getCountry.isEmpty()) {
				Country countrySave = countryRepository.save(company.getCountry());
				company.setCountry(countrySave);
			} else {
				company.setCountry(getCountry.get());
			}

			// VERIFICA SE O STATE INFORMADO JA ESTA CADASTRADO NO SISTEMA, PARA PEGAR A FK
			Optional<State> getState = stateRepository.verificarState(company.getState().getName());
			if (getState.isEmpty()) {
				State stateSave = stateRepository.save(company.getState());
				company.setState(stateSave);
			} else {
				company.setState(getState.get());
			}

			// VERIFICA SE A CITY INFORMADA JA ESTA CADASTRADO NO SISTEMA, PARA PEGAR A FK
			Optional<City> getCity = cityRepository.verificarCity(company.getCity().getName());
			if (getCity.isEmpty()) {
				City citySave = cityRepository.save(company.getCity());
				company.setCity(citySave);
			} else {
				company.setCity(getCity.get());
			}

			// VERIFICA SE A NEIGHBORHOOD INFORMADA JA ESTA CADASTRADO NO SISTEMA, PARA
			// PEGAR A FK
			Optional<Neighborhood> getNeighborhood = neighborhoodRepository
					.verificarNeighborhood(company.getNeighborhood().getName());
			if (getNeighborhood.isEmpty()) {
				Neighborhood neighborhoodSave = neighborhoodRepository.save(company.getNeighborhood());
				company.setNeighborhood(neighborhoodSave);
			} else {
				company.setNeighborhood(getNeighborhood.get());
			}

			companyRepository.save(company);

			// SALVA OS DADOS QUE ESTAO NO LOG AO INICIAR A API
		} else if (status.equals("banco")) {
			companyRepository.save(company);
		} else if (status.equals("alterar")) {
			companySave = companyRepository.save(company);
			lc.alterar(companySave, "company");
			RESPOSTA = true;
		}

		return RESPOSTA;
	}

	// BUSCA TODOS OS COUNTRYS CADASTRADOS E DESATIVADOS
	public List<Company> findAllCompanyDesativados() {
		List<Company> COMPANYS = companyRepository.findAllDesativados()
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return COMPANYS;
	}

	// BUSCA TODOS OS COUNTRYS CADASTRADOS E ATIVOS
	public List<Company> findAllCountry() {
		List<Company> COMPANYS = companyRepository.findAllCountry()
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return COMPANYS;
	}

	// BUSCA POR NOME OS COUNTRYS CADASTRADOS E ATIVOS
	public List<Company> countryName(String name) {
		List<Company> COMPANYS = companyRepository.countryName(name)
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return COMPANYS;
	}

	// DESATIVAR OU ATIVAR UM COUNTRY
	public Boolean desabilitar_ativar(Long id, boolean ativar) {

		if (!ativar) {
			companyRepository.desativar(id);
			lc.desabilitar_ativar(id, "company", false);
			neighborhoodService.desabilitar_ativar(id, false);
		} else {
			companyRepository.ativar(id);
			lc.desabilitar_ativar(id, "company", true);
			neighborhoodService.desabilitar_ativar(id, true);
		}
		return true;
	}
}
