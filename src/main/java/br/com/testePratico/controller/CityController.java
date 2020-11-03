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

import br.com.testePratico.model.City;
import br.com.testePratico.service.CityService;

@RestController
@RequestMapping("/city")
public class CityController {
	@Autowired
	CityService cityService;

	// SALVA UMA CITY NO BANCO E NO ARQUIVO DE LOG
	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Boolean> cadastrar(@Valid @RequestBody City city) {
		return new ResponseEntity<Boolean>(cityService.cadastrar(city, "cadastrar"), HttpStatus.CREATED);
	}

	// BUSCA TODOS AS CITYS CADASTRADAS
	@GetMapping("/findAllCity")
	public ResponseEntity<List<City>> findAllCity() {
		List<City> CITYS = cityService.findAllCity();
		return new ResponseEntity<List<City>>(CITYS, HttpStatus.OK);
	}

	// BUSCA POR NOME AS CITYS CADASTRADAS
	@GetMapping("/findAllCity/name")
	public ResponseEntity<List<City>> cityName(@RequestParam String name) {
		List<City> CITYS = cityService.cityName(name);
		return new ResponseEntity<List<City>>(CITYS, HttpStatus.OK);
	}

	// BUSCA AS CITYS POR STATES
	@GetMapping("/findAllCityState/name")
	public ResponseEntity<List<City>> cityNameState(@RequestParam String name) {
		List<City> CITYS = cityService.cityNameState(name);
		return new ResponseEntity<List<City>>(CITYS, HttpStatus.OK);
	}

	// ALTERA UMA CITY
	@PutMapping("/alterar/{id}")
	public ResponseEntity<Boolean> alterar(@Valid @RequestBody City city, @PathVariable Long id) {
		city.setId(id);
		return new ResponseEntity<Boolean>(cityService.cadastrar(city, "alterar"), HttpStatus.CREATED);
	}
}
