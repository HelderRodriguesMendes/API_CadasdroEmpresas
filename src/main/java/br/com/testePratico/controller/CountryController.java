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

import br.com.testePratico.model.Country;
import br.com.testePratico.service.CountryService;

@RestController
@RequestMapping("/country")
public class CountryController {

	@Autowired
	CountryService countryService;

	// SALVA UM COUNTRY NO BANCO E NO ARQUIVO DE LOG
	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Boolean> cadastrar(@Valid @RequestBody Country country) {
		return new ResponseEntity<Boolean>(countryService.cadastrar(country, "cadastrar"), HttpStatus.CREATED);
	}

	// BUSCA TODOS OS COUNTRYS CADASTRADOS E ATIVOS
	@GetMapping("/findAllCountry")
	public ResponseEntity<List<Country>> findAllCountry() {
		List<Country> COUNTRYS = countryService.findAllCountry();
		return new ResponseEntity<List<Country>>(COUNTRYS, HttpStatus.OK);
	}

	// BUSCA POR NOME OS COUNTRYS CADASTRADOS E ATIVOS
	@GetMapping("/findAllCountry/name")
	public ResponseEntity<List<Country>> countryName(@RequestParam String name) {
		List<Country> COUNTRYS = countryService.countryName(name);
		return new ResponseEntity<List<Country>>(COUNTRYS, HttpStatus.OK);
	}

	// ALTERA UM COUNTRY
	@PutMapping("/alterar/{id}")
	public ResponseEntity<Boolean> alterar(@Valid @RequestBody Country country, @PathVariable Long id) {
		country.setId(id);
		return new ResponseEntity<Boolean>(countryService.cadastrar(country, "alterar"), HttpStatus.CREATED);
	}

	// DESATIVAR UM COUNTRY
	@GetMapping("/desativar/{id}")
	public ResponseEntity<Boolean> desativar(@PathVariable("id") Long id) {
		Boolean COUNTRY = countryService.desabilitar_ativar(id, false);
		return ResponseEntity.ok().body(COUNTRY);
	}

	// BUSCA TODOS OS COUNTRYS CADASTRADOS E DESATIVADOS
	@GetMapping("/findAllCountryDesativados")
	public ResponseEntity<List<Country>> findAllCountryDesativados() {
		List<Country> COUNTRYS = countryService.findAllCountryDesativados();
		return new ResponseEntity<List<Country>>(COUNTRYS, HttpStatus.OK);
	}

	// ATIVAR UM COUNTRY
	@GetMapping("/ativar/{id}")
	public ResponseEntity<Boolean> ativar(@PathVariable("id") Long id) {
		Boolean COUNTRY = countryService.desabilitar_ativar(id, true);
		return ResponseEntity.ok().body(COUNTRY);
	}
}
