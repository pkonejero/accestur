package accestur.secom.core.model;

import java.util.List;

public class Provider extends DomainObjectModel{

	private Long id;


	private Issuer issuer;


	private List<ServiceAgent> services;

	// Name
	private String name;

	public Provider(){}
	
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