package secom.accestur.core.model;

import java.util.List;
import javax.persistence.*;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "rightOfUseEntity")
@Component("rightOfUseModel")
public class RightOfUse extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// Set of Random Values;
	@ElementCollection
	private List<String> RI;

	// Encryption of the rights of use
	@ElementCollection
	private List<String> deltas;

	// Shared Session Key
	private String k;

	public RightOfUse(){}

	public RightOfUse(String k, List<String> rI, List<String> deltas){
		super();
		this.k = k;
		RI = rI;
		this.deltas = deltas;
	}

	public List<String> getRI(){
		return RI;
	}

	public void setRI(List<String> rI){
		RI = rI;
	}

	public List<String> getDeltas(){
		return deltas;
	}

	public void setDeltas(List<String> deltas){
		this.deltas = deltas;
	}

	public String getK(){
		return k;
	}

	public void setK(String k){
		this.k = k;
	}
}