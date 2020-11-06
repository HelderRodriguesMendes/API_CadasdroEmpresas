package br.com.testePratico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.exception.NotFound;
import br.com.testePratico.log.LogCity;
import br.com.testePratico.log.LogCountry;
import br.com.testePratico.log.LogNeighborhood;
import br.com.testePratico.log.LogState;
import br.com.testePratico.model.City;
import br.com.testePratico.model.Country;
import br.com.testePratico.model.Neighborhood;
import br.com.testePratico.model.State;
import br.com.testePratico.repository.NeighborhoodRepository;

@Service
public class NeighborhoodService {

	@Autowired
	NeighborhoodRepository neighborhoodRepository;
	
	@Autowired
	CountryService countryService;

	@Autowired
	StateService stateService;
	
	@Autowired
	CityService cityService;

	LogNeighborhood ln = new LogNeighborhood();
	LogCity lc = new LogCity();
	LogState ls = new LogState();
	LogCountry lCoun = new LogCountry();
	Neighborhood neighborhoodSave = new Neighborhood();

	// SALVA UMA NEIGHBORHOOR NO BANCO E NO ARQUIVO DE LOG
	public Boolean cadastrar(Neighborhood neighborhood_recebida, String status) {		
		Country countrySave = null;
		State stateSave = null;
		City citySave = null;
		boolean RESPOSTA = false;

		// VERIFICA SE O STATE JÁ ESTA CADASTRADO E SE ESTA ATIVO OU NÃO
		if (status.equals("cadastrar")) {
			Optional<Neighborhood> n = neighborhoodRepository.verificarNeighborhood(neighborhood_recebida.getName());

			// SE NÃO ESTIVER CADASTRADO
			if (n.isEmpty()) {

				City city_recebida = neighborhood_recebida.getCity();
				State state_recebido = city_recebida.getState();
				Country country_recebido = state_recebido.getCountry();
				
				//VERIFICANDO SE AS FKS ESTAO SALVAS OU NAO, CASO NÃO: ELAS SAO SALVAS
				countrySave = countryService.verificarCadastro(country_recebido);
				state_recebido.setCountry(countrySave);
				stateSave = stateService.verificarCadastro(state_recebido);
				city_recebida.setState(stateSave);
				citySave = cityService.verificarCadastro(city_recebida);
				neighborhood_recebida.setCity(citySave);
				
				neighborhoodSave = neighborhoodRepository.save(neighborhood_recebida);

				// SALVANDO DADOS NO ARQUIVO DE LOG
				ln.salvar(neighborhoodSave, "neighborhood");
				RESPOSTA = true;
			} else if (n.get().getAtivo() == false) {
				desabilitar_ativar(n.get().getId(), true);
				RESPOSTA = true;
			}

			// SALVA OS DADOS QUE ESTAO NO LOG AO INICIAR A API
		} else if (status.equals("banco")) {
			neighborhoodRepository.save(neighborhood_recebida);
		} else if (status.equals("alterar")) {
			neighborhoodSave = neighborhoodRepository.save(neighborhood_recebida);
			ln.alterar(neighborhoodSave, "neighborhood");
			RESPOSTA = true;
		}
		return RESPOSTA;
	}

	// BUSCA TODAS AS NEIGHBORHOOR CADASTRADAS E ATIVAS
	public List<Neighborhood> findAllNeighborhood() {
		List<Neighborhood> NEIG = neighborhoodRepository.findAllNeighborhood()
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return NEIG;
	}

	// BUSCA POR NOME TODAS AS NEIGHBORHOOR CADASTRADAS E ATIVAS
	public List<Neighborhood> neighborhoodName(String name) {
		List<Neighborhood> NEIG = neighborhoodRepository.neighborhoodName(name)
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return NEIG;
	}

	// DESATIVAR OU ATIVAR UM COUNTRY
	public Boolean desabilitar_ativar(Long id, boolean ativar) {

		if (!ativar) {
			neighborhoodRepository.desativar(id);
			ln.desabilitar_ativar(id, "neighborhood", false);
		} else {
			neighborhoodRepository.ativar(id);
			ln.desabilitar_ativar(id, "neighborhood", true);
		}
		return true;
	}
	
	public Neighborhood verificarCadastro(Neighborhood neighborhood) {

		Optional<Neighborhood> neighborhoodBanco = neighborhoodRepository.verificarNeighborhood(neighborhood.getName());

		if (neighborhoodBanco.isEmpty()) {
			neighborhoodSave = neighborhoodRepository.save(neighborhood);
			ln.salvar(neighborhoodSave, "neighborhood");
		} else {
			neighborhoodSave = neighborhoodBanco.get();
		}
		return neighborhoodSave;
	}
}
