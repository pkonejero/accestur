package secom.accestur.core.model;

import org.springframework.stereotype.Component;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="providerEntity")
@Component("providerModel")
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