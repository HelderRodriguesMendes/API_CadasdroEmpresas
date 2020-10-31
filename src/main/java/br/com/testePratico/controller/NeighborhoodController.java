package br.com.testePratico.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.testePratico.model.Neighborhood;
import br.com.testePratico.service.NeighborhoodService;

@RestController
@RequestMapping("/neighborhood")
public class NeighborhoodController {	//FALTA TESTA AQI

	@Autowired
	NeighborhoodService neighborhoodService;
	
	// SALVA UM STATE NO BANCO E NO ARQUIVO DE LOG
		@PostMapping(value = "/cadastrar", produces = "application/json")
		public ResponseEntity<Boolean> cadastrar(@Valid @RequestBody Neighborhood neighborhood) {
			return new ResponseEntity<Boolean>(neighborhoodService.cadastrar(neighborhood, "cadastrar"), HttpStatus.CREATED);
		}

		// BUSCA TODOS OS STATES CADASTRADOS E ATIVOS
		@GetMapping("/findAllNeighborhood")
		public ResponseEntity<List<Neighborhood>> findAllNeighborhood() {
			List<Neighborhood> NEIG = neighborhoodService.findAllNeighborhood();
			return new ResponseEntity<List<Neighborhood>>(NEIG, HttpStatus.OK);
		}

		/// BUSCA POR NOME OS STATES CADASTRADOS E ATIVOS
		@GetMapping("/findAllNeighborhood/name")
		public ResponseEntity<List<Neighborhood>> neighborhoodName(@RequestParam String name) {
			List<Neighborhood> NEIG = neighborhoodService.neighborhoodName(name);
			return new ResponseEntity<List<Neighborhood>>(NEIG, HttpStatus.OK);
		}

		// ALTERA UM STATE
		@PutMapping("/alterar/{id}")
		public ResponseEntity<Boolean> alterar(@Valid @RequestBody Neighborhood neighborhood, @PathVariable Long id) {
			neighborhood.setId(id);
			return new ResponseEntity<Boolean>(neighborhoodService.cadastrar(neighborhood, "alterar"), HttpStatus.CREATED);
		}
}
