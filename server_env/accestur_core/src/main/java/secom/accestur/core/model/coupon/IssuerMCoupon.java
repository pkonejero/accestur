package secom.accestur.core.model.coupon;

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
@Table(name="issuermcouponEntity")
public class IssuerMCoupon extends DomainObjectModelCoupon{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@OneToMany(mappedBy="issuer")
	private List<MerchantMCoupon> merchants;
	
	//Manufacturer
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MANUFACTURER_ID")
	private ManufacturerMCoupon manufacturer;

	private String name;

	public IssuerMCoupon(){}

	public IssuerMCoupon(String name, List<MerchantMCoupon> merchants){
		super();
		this.name = name;
		this.merchants = merchants;
	}

	public List<MerchantMCoupon> getMerchants(){
		return merchants;
	}

	public void setMerchantMCoupon(List<MerchantMCoupon> merchants){
		this.merchants = merchants;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
}