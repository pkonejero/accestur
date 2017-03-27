package secom.accestur.core.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rightOfUseEntity")
public class RightOfUse extends DomainObjectModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// Shared Session Key
	private String k;

	// Set of Random Values;
	private List<String> RI;

	// Encryption of the rights of use
	private List<String> deltas;

	public RightOfUse(){}
}
