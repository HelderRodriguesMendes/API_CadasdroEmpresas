package br.com.testePratico.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.testePratico.model.State;
import br.com.testePratico.service.StateService;

@RestController
@RequestMapping("/state")
public class StateController {
	@Autowired
	StateService stateService;
	
		//SALVA UM STATE NO BANCO E NO ARQUIVO DE LOG
		@PostMapping(value = "/cadastrar", produces = "application/json")
		public ResponseEntity<Boolean> cadastrar(@Valid @RequestBody State state){
			return new ResponseEntity<Boolean>(stateService.cadastrar(state, "cadastrar"), HttpStatus.CREATED);
		}
}
