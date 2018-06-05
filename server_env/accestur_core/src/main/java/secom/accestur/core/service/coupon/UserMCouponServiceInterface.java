package secom.accestur.core.service.coupon;

import secom.accestur.core.model.coupon.UserMCoupon;

public interface UserMCouponServiceInterface{
	public String getUserMCouponByUsername1(String username);
	
	public UserMCoupon getUserMCouponByUsername(String username);

	public String authenticateUsername(String username, String password);
	
	public void verifyUsername(String[] params); //STRING
	
	public void createCertificate();
}