package accestur.secom.core.model;



import java.util.List;


public class Issuer extends DomainObjectModel{

	private Long id;

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