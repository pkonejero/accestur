package secom.accestur.core.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="issuerEntity")
public class Issuer extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy="issuer")
	private List<Provider> providers;

	private String name;

	public Issuer(){}

	public Issuer(String name, List<Provider> providers){
		super();
		this.name = name;
		this.providers = providers;
	}

	public List<Provider> getProviders(){
		return providers;
	}

	public void setProviders(List<Provider> providers){
		this.providers = providers;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
}