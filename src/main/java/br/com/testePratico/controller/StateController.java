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

import br.com.testePratico.model.State;
import br.com.testePratico.service.StateService;

@RestController
@RequestMapping("/state")
public class StateController {
	@Autowired
	StateService stateService;

	// SALVA UM STATE NO BANCO E NO ARQUIVO DE LOG
	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Boolean> cadastrar(@Valid @RequestBody State state) {
		return new ResponseEntity<Boolean>(stateService.cadastrar(state, "cadastrar"), HttpStatus.CREATED);
	}

	// BUSCA TODOS OS STATES CADASTRADOS E ATIVOS
	@GetMapping("/findAllState")
	public ResponseEntity<List<State>> findAllState() {
		List<State> STATES = stateService.findAllState();
		return new ResponseEntity<List<State>>(STATES, HttpStatus.OK);
	}

	/// BUSCA POR NOME OS STATES CADASTRADOS E ATIVOS
	@GetMapping("/findAllState/name")
	public ResponseEntity<List<State>> stateName(@RequestParam String name) {
		List<State> STATES = stateService.stateName(name);
		return new ResponseEntity<List<State>>(STATES, HttpStatus.OK);
	}

	// ALTERA UM STATE
	@PutMapping("/alterar/{id}")
	public ResponseEntity<Boolean> alterar(@Valid @RequestBody State state, @PathVariable Long id) {
		state.setId(id);
		return new ResponseEntity<Boolean>(stateService.cadastrar(state, "alterar"), HttpStatus.CREATED);
	}
}
