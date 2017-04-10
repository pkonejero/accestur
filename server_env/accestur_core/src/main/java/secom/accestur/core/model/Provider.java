package secom.accestur.core.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="providerEntity")
public class Provider extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	// Issuer
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ISSUER_ID")
	private Issuer issuer;

	// Service
	@OneToMany(mappedBy="provider")
	private List<ServiceAgent> services;

	// Name
	private String name;

	Provider(){}
	
	public Provider(String name, Issuer issuer, List<ServiceAgent> services){
		super();
		this.name = name;
		this.issuer = issuer;
		this.services = services;
	}
	
	public Issuer getIssuer(){
		return issuer;
	}

	public void setIssuer(Issuer issuer){
		this.issuer = issuer;
	}

	public List<ServiceAgent> getServices(){
		return services;
	}

	public void setServices(List<ServiceAgent> services){
		this.services = services;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
}