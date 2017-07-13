package secom.accestur.core.model;

import java.util.List;



public class MerchantMCoupon extends DomainObjectModelCoupon{

	private Long id;

	// Issuer

	private IssuerMCoupon issuer;
	
	// Coupons

	private List<MCoupon> mCoupon;

	// Name
	private String name;
	
	public MerchantMCoupon(){}
	
	public MerchantMCoupon(String name, IssuerMCoupon issuer){
		super();
		this.name = name;
		this.issuer = issuer;
	}
	
	public IssuerMCoupon getIssuerMCoupon(){
		return issuer;
	}

	public void setIssuerMCoupon(IssuerMCoupon issuer){
		this.issuer = issuer;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
}