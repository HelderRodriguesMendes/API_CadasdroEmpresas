package br.com.testePratico.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
public class Company implements Serializable { // VIZINHANÇA

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "varchar(30)")
	@Size(min = 2, max = 30)
	private String tradeName;

	@Column(nullable = false, columnDefinition = "varchar(30)")
	@Size(min = 2, max = 30)
	private String corporateName;

	@ManyToOne()
	@JoinColumn(name = "COUNTRY_ID")
	private Country country;

	@ManyToOne()
	@JoinColumn(name = "STATE_ID")
	private State state;

	@ManyToOne()
	@JoinColumn(name = "CITY_ID")
	private City city;

	@ManyToOne()
	@JoinColumn(name = "NEIGHBORHOOD_ID")
	private Neighborhood neighborhood;

	@Column(nullable = false, columnDefinition = "varchar(30)")
	@Size(min = 2, max = 30)
	private String address;

	@Column(nullable = false, columnDefinition = "varchar(15)")
	@Size(min = 2, max = 15)
	private String phone;

	@Column(nullable = false, columnDefinition = "varchar(30)", unique = true)
	@Size(min = 2, max = 30)
	private String federalTaxNumber;
	
	private Boolean ativo;
}
