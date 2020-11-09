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

	// SALVA UMA COMPANY NO BANCO E NO ARQUIVO DE LOG
	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Boolean> cadastrar(@RequestBody Company company) {
		return new ResponseEntity<Boolean>(companyService.cadastrar(company, "cadastrar"), HttpStatus.CREATED);
	}

	// BUSCA TODOS AS COMPANY CADASTRADAS E ATIVAS
	@GetMapping("/findAllCompany")
	public ResponseEntity<List<Company>> findAllCompany() {
		return new ResponseEntity<List<Company>>(companyService.findAllCompany(), HttpStatus.OK);
	}

	// BUSCA POR NOME AS COMPANY CADASTRADAS E ATIVAS
	@GetMapping("/findAllCompany/name")
	public ResponseEntity<List<Company>> company_Name(@RequestParam String name) {
		return new ResponseEntity<List<Company>>(companyService.companyName(name), HttpStatus.OK);
	}

	// BUSCA TODOS OS DADOS DE UMA UNICA COMPANY
	@GetMapping("/findAllCompany/{id}")
	public ResponseEntity<Company> detalharCompany(@PathVariable Long id) {
		return new ResponseEntity<Company>(companyService.detalharCompany(id), HttpStatus.OK);
	}

	// ALTERA UMA COMPANY
	@PutMapping("/alterar/{id}")
	public ResponseEntity<Boolean> alterar(@Valid @RequestBody Company company, @PathVariable Long id) {
		company.setId(id);
		return new ResponseEntity<Boolean>(companyService.cadastrar(company, "alterar"), HttpStatus.CREATED);
	}

	// DESATIVA UMA COMPANY
	@GetMapping("/desativar/{id}")
	public ResponseEntity<Boolean> desativar(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(companyService.desabilitar_ativar(id, false));
	}

	// BUSCA TODOS AS COMPANY CADASTRADAS E DESATIVADAS
	@GetMapping("/findAllCompanyDesativados")
	public ResponseEntity<List<Company>> findAllCompanyDesativados() {
		return new ResponseEntity<List<Company>>(companyService.findAllCompanyDesativados(), HttpStatus.OK);
	}

	// ATIVA UMA COMPANY
	@GetMapping("/ativar/{id}")
	public ResponseEntity<Boolean> ativar(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(companyService.desabilitar_ativar(id, true));
	}
}
