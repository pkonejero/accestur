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
	
	public RightOfUse(String k, List<String> rI, List<String> deltas) {
		super();
		this.k = k;
		RI = rI;
		this.deltas = deltas;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public List<String> getRI() {
		return RI;
	}

	public void setRI(List<String> rI) {
		RI = rI;
	}

	public List<String> getDeltas() {
		return deltas;
	}

	public void setDeltas(List<String> deltas) {
		this.deltas = deltas;
	}
	
	
}
