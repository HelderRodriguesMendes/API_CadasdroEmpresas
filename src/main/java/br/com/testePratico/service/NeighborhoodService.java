package br.com.testePratico.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testePratico.exception.NotFound;
import br.com.testePratico.log.LogNeighborhood;
import br.com.testePratico.model.Neighborhood;
import br.com.testePratico.repository.NeighborhoodRepository;

@Service
public class NeighborhoodService {
	
	@Autowired
	NeighborhoodRepository neighborhoodRepository;
	
	LogNeighborhood ln = new LogNeighborhood();
	
	
	public Boolean cadastrar(Neighborhood neighborhood, String status) {	
		Neighborhood neighborhoodSave = null;
		boolean RESPOSTA = false;

		// VERIFICA SE O STATE JÁ ESTA CADASTRADO E SE ESTA ATIVO OU NÃO
		if (status.equals("cadastrar")) {
			Optional<Neighborhood> n = neighborhoodRepository.verificarNeighborhood(neighborhood.getName());

			// SE NÃO ESTIVER CADASTRADO
			if (n.isEmpty()) {
				neighborhoodSave = neighborhoodRepository.save(neighborhood);

				// SALVANDO DADOS NO ARQUIVO DE LOG
				ln.salvar(neighborhoodSave, "neighborhood");
				RESPOSTA = true;
			} else if(n.get().getAtivo() == false){
				desabilitar_ativar(n.get().getId(), true);
				RESPOSTA = true;
			}
			
			// SALVA OS DADOS QUE ESTAO NO LOG AO INICIAR A API
		} else if (status.equals("banco")) {
			neighborhoodRepository.save(neighborhood);
		} else if (status.equals("alterar")) {
			neighborhoodSave = neighborhoodRepository.save(neighborhood);
			ln.alterar(neighborhoodSave, "neighborhood");
			RESPOSTA = true;
		}
		return RESPOSTA;
	}

	// BUSCA TODOS OS STATES CADASTRADOS E ATIVOS
	public List<Neighborhood> findAllNeighborhood() {
		List<Neighborhood> NEIG = neighborhoodRepository.findAllNeighborhood()
				.orElseThrow(() -> new NotFound("Registros não encontrados"));
		return NEIG;
	}

	// BUSCA POR NOME OS STATES CADASTRADOS E ATIVOS
	public List<Neighborhood> neighborhoodName(String name) {
		List<Neighborhood> NEIG = neighborhoodRepository.stateName(name)
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
}
