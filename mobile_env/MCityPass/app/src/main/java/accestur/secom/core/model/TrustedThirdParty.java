package accestur.secom.core.model;


public class TrustedThirdParty extends DomainObjectModel{

	private Long id;

	private String name;

	public TrustedThirdParty(){}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
}