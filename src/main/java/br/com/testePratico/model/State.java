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
public class State implements Serializable { // ESTADO

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, columnDefinition = "varchar(30)")
	@Size(min = 2, max = 30)
	private String name;
	
	@ManyToOne()
	@JoinColumn(name = "COUNTRY_ID")
	private Country country;

	public State() {
	}

}
