package secom.accestur.core.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rightOfUseEntity")
public class RightOfUse extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ElementCollection
	private List<String> RI;

	@ElementCollection
	private List<String> deltas;

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