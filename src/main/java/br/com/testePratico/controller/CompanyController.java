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

import br.com.testePratico.model.Company;
import br.com.testePratico.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	CompanyService companyService;
	
	// SALVA UM COUNTRY NO BANCO E NO ARQUIVO DE LOG
		@PostMapping(value = "/cadastrar", produces = "application/json")
		public ResponseEntity<Boolean> cadastrar(@RequestBody Company company) {
			return new ResponseEntity<Boolean>(companyService.cadastrar(company, "cadastrar"), HttpStatus.CREATED);
		}

		// BUSCA TODOS OS COUNTRYS CADASTRADOS E ATIVOS
		@GetMapping("/findAllCompany")
		public ResponseEntity<List<Company>> findAllCountry() {
			List<Company> COMPANYS = companyService.findAllCompany();
			return new ResponseEntity<List<Company>>(COMPANYS, HttpStatus.OK);
		}

		// BUSCA POR NOME OS COUNTRYS CADASTRADOS E ATIVOS
		@GetMapping("/findAllCompany/name")
		public ResponseEntity<List<Company>> companyName(@RequestParam String name) {
			List<Company> COMPANYS = companyService.companyName(name);
			return new ResponseEntity<List<Company>>(COMPANYS, HttpStatus.OK);
		}

		// ALTERA UM COUNTRY
		@PutMapping("/alterar/{id}")
		public ResponseEntity<Boolean> alterar(@Valid @RequestBody Company company, @PathVariable Long id) {
			company.setId(id);
			return new ResponseEntity<Boolean>(companyService.cadastrar(company, "alterar"), HttpStatus.CREATED);
		}

		// DESATIVAR UM COUNTRY
		@GetMapping("/desativar/{id}")
		public ResponseEntity<Boolean> desativar(@PathVariable("id") Long id) {
			Boolean COMPANY = companyService.desabilitar_ativar(id, false);
			return ResponseEntity.ok().body(COMPANY);
		}

		// BUSCA TODOS OS COUNTRYS CADASTRADOS E DESATIVADOS
		@GetMapping("/findAllCompanyDesativados")
		public ResponseEntity<List<Company>> findAllCountryDesativados() {
			List<Company> COMPANYS = companyService.findAllCompanyDesativados();
			return new ResponseEntity<List<Company>>(COMPANYS, HttpStatus.OK);
		}

		// ATIVAR UM COUNTRY
		@GetMapping("/ativar/{id}")
		public ResponseEntity<Boolean> ativar(@PathVariable("id") Long id) {
			Boolean COMPANY = companyService.desabilitar_ativar(id, true);
			return ResponseEntity.ok().body(COMPANY);
		}
}