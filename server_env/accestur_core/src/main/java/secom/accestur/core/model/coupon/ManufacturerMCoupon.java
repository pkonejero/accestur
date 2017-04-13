package secom.accestur.core.model.coupon;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="manufacturermcouponEntity")
public class ManufacturerMCoupon extends DomainObjectModelCoupon{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy="manufacturer")
	private List<IssuerMCoupon> issuers;
	
	@OneToMany(mappedBy="manufacturer")
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