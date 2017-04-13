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
@Table(name="merchantmcouponEntity")
public class MerchantMCoupon extends DomainObjectModelCoupon{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	// Issuer
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ISSUER_ID")
	private IssuerMCoupon issuer;
	
	// Coupons
	@OneToMany(mappedBy="merchant")
	private List<MCoupon> mCoupon;

	// Name
	private String name;
	
	public MerchantMCoupon(){}
	
	public MerchantMCoupon(String name, IssuerMCoupon issuer){
		super();
		this.name = name;
		this.issuer = issuer;
	}
	
	public IssuerMCoupon getIssuer(){
		return issuer;
	}

	public void setIssuer(IssuerMCoupon issuer){
		this.issuer = issuer;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
}