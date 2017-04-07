package secom.accestur.core.model;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="issuerEntity")
public class Issuer extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	// Provider
	@OneToMany(mappedBy="issuer")
	private List<Provider> providers;

	// Name
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