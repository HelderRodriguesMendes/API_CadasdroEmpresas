package br.com.testePratico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.testePratico.model.Country;
import br.com.testePratico.service.CountryService;

@RestController
@RequestMapping("/empresa")
public class CountryController {
	
	@Autowired
	CountryService countryService;
	
	//CADASTRA UM COUNTRY
	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Country> cadastrar(@RequestBody Country country){
		return new ResponseEntity<Country>(countryService.cadastrar(country, "cadastro"), HttpStatus.CREATED);
	}
}
