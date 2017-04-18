package secom.accestur.core.service.coupon;

import java.util.List;

import secom.accestur.core.model.coupon.IssuerMCoupon;
import secom.accestur.core.model.coupon.MerchantMCoupon;

public interface MerchantMCouponServiceInterface{
	public void newMerchantMCoupon (String name, IssuerMCoupon issuer);
		
	public MerchantMCoupon getMerchantMCouponByName(String name);
	
	//public List<MerchantMCoupon> getMerchantMCouponByIssuerMCoupon(IssuerMCoupon issuer);
	
	public String[] verifyMCoupon(String[] params);
	
	public void createCertificate();
}