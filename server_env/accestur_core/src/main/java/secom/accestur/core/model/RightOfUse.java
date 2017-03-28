package secom.accestur.core.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "rightOfUseEntity")
public class RightOfUse extends DomainObjectModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// Shared Session Key
	private String k;

	// Set of Random Values;
	@ElementCollection
	private List<String> RI;

	// Encryption of the rights of use
	@ElementCollection
	private List<String> deltas;

	public RightOfUse(){}
}
