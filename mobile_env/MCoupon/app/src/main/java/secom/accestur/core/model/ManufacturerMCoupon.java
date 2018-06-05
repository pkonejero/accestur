package secom.accestur.core.model;

import com.activeandroid.Model;

import java.util.List;




public class ManufacturerMCoupon extends Model{

	private Long id;


	private List<IssuerMCoupon> issuers;

	private List<UserMCoupon> users;

	private String name;

	public ManufacturerMCoupon(){}

	public ManufacturerMCoupon(String name, List<IssuerMCoupon> issuers,List<UserMCoupon> users){
		super();
		this.name = name;
		this.issuers = issuers;
		this.users = users;
	}

	public List<IssuerMCoupon> getIssuers(){
		return issuers;
	}

	public void setIssuers(List<IssuerMCoupon> users){
		this.issuers = issuers;
	}
	
	public List<UserMCoupon> getUsers(){
		return users;
	}

	public void setUsers(List<UserMCoupon> users){
		this.users = users;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
}