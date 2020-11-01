package br.com.testePratico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.exception.NotFound;
import br.com.testePratico.log.LogCity;
import br.com.testePratico.log.LogCompany;
import br.com.testePratico.log.LogCountry;
import br.com.testePratico.log.LogNeighborhood;
import br.com.testePratico.log.LogState;
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

	LogCompany logCompany = new LogCompany();
	LogState logState = new LogState();
	LogNeighborhood logNeighborhood = new LogNeighborhood();
	LogCity logCity = new LogCity();
	LogCountry logCountry = new LogCountry();

	// SALVA UMA COMPANY NO BANCO E NO ARQUIVO DE LOG
	public Boolean cadastrar(Company company, String status) {
		Company companySave = null;
		boolean RESPOSTA = false;

		Country countrySave = null;
		State stateSave = null;
		City citySave = null;
		Neighborhood neighborhoodSave = null;

		// VERIFICA SE A COMPANY JÁ ESTA CADASTRADA E SE ESTA ATIVA OU NÃO
		if (status.equals("cadastrar") || status.equals("alterar")) {

			if (status.equals("cadastrar")) {
				// VERIFICA SE A COMPANY INFORMADA JA ESTA CADASTRADA NO SISTEMA, PARA PEGAR A
				// FK
				Optional<Country> getCountry = countryRepository.verificarCountry(company.getCountry().getName());
				if (getCountry.isEmpty()) {
					countrySave = countryRepository.save(company.getCountry());
					logCountry.salvar(countrySave, "country");
					company.setCountry(countrySave);
				} else {
					company.setCountry(getCountry.get());
					countrySave = getCountry.get();
				}

				Optional<State> getState = stateRepository.verificarState(company.getState().getName());
				if (getState.isEmpty()) {
					company.getState().setCountry(countrySave);
					stateSave = stateRepository.save(company.getState());
					logState.salvar(stateSave, "state");
					company.setState(stateSave);
				} else {
					company.setState(getState.get());
					stateSave = getState.get();
				}

				Optional<City> getCity = cityRepository.verificarCity(company.getCity().getName());
				if (getCity.isEmpty()) {
					company.getCity().setState(stateSave);
					citySave = cityRepository.save(company.getCity());
					logCity.salvar(citySave, "city");
					company.setCity(citySave);
				} else {
					company.setCity(getCity.get());
					citySave = getCity.get();
				}

				company.getNeighborhood().setCity(citySave);
				neighborhoodSave = neighborhoodRepository.save(company.getNeighborhood());
				neighborhoodSave.setAtivo(true);
				logNeighborhood.salvar(neighborhoodSave, "neighborhood");
				company.setNeighborhood(neighborhoodSave);

			} else {
				String ID = "";

				ID = String.valueOf(company.getCountry().getId());
				if (ID.equals("") || company.getCountry().getId() == null) {
					countrySave = countryRepository.save(company.getCountry());
					logCountry.salvar(countrySave, "country");
					company.getState().setCountry(countrySave);
					company.setCountry(countrySave);
				} else {
					countrySave = company.getCountry();
					company.getState().setCountry(company.getCountry());
				}

				ID = String.valueOf(company.getState().getId());
				if (ID.equals("") || company.getState().getId() == null) {
					company.getState().setCountry(countrySave);
					stateSave = stateRepository.save(company.getState());
					logState.salvar(stateSave, "state");
					company.getCity().setState(stateSave);
					company.setState(stateSave);
				} else {
					stateSave = company.getState();
					company.getCity().setState(company.getState());
				}

				ID = String.valueOf(company.getCity().getId());
				if (ID.equals("") || company.getCity().getId() == null) {
					company.getCity().setState(stateSave);
					citySave = cityRepository.save(company.getCity());
					logCity.salvar(citySave, "city");
					company.getNeighborhood().setCity(citySave);
					company.setCity(citySave);
				} else {
					citySave = company.getCity();
					company.getNeighborhood().setCity(company.getCity());
				}

				ID = String.valueOf(company.getNeighborhood().getId());
				if (ID.equals("") || company.getNeighborhood().getId() == null) {
					company.getNeighborhood().setCity(citySave);
					neighborhoodSave = neighborhoodRepository.save(company.getNeighborhood());
					logNeighborhood.salvar(neighborhoodSave, "neighborhood");
					company.setNeighborhood(neighborhoodSave);
				}

			}

			companySave = null;
			companySave = companyRepository.save(company);
			companySave.getNeighborhood().setName(company.getNeighborhood().getName());
			companySave.getNeighborhood().setCity(company.getCity());

			if (status.equals("cadastrar")) {
				logCompany.salvar(companySave, "company");
			} else if (status.equals("alterar")) {
				logCompany.alterar(companySave, "company");
			}
			RESPOSTA = true;

			// SALVA OS DADOS QUE ESTAO NO LOG AO INICIAR A API
		} else if (status.equals("banco")) {
			companyRepository.save(company);
		}

		return RESPOSTA;
	}

	// BUSCA TODOS AS COMPANY CADASTRADAS E DESATIVADAS
	public List<Company> findAllCompanyDesativados() {
		List<Company> COMPANYS = companyRepository.findAllDesativados()
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return COMPANYS;
	}

	// BUSCA TODOS AS COMPANY CADASTRADAS E ATIVAS
	public List<Company> findAllCompany() {
		List<Company> COMPANYS = companyRepository.findAllCompany()
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return COMPANYS;
	}

	// BUSCA POR NOME AS COMPANY CADASTRADAS E ATIVAS
	public List<Company> companyName(String name) {
		List<Company> COMPANYS = companyRepository.companyName(name)
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return COMPANYS;
	}

	// DESATIVA OU ATIVA UMA COMPANY
	public Boolean desabilitar_ativar(Long id, boolean ativar) {

		Company neighborhood = companyRepository.findById(id)
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		if (!ativar) {
			companyRepository.desativar(id);
			logCompany.desabilitar_ativar(id, "company", false);
			neighborhoodService.desabilitar_ativar(neighborhood.getNeighborhood().getId(), false);
		} else {
			companyRepository.ativar(id);
			logCompany.desabilitar_ativar(id, "company", true);
			neighborhoodService.desabilitar_ativar(neighborhood.getNeighborhood().getId(), true);
		}
		return true;
	}
}
