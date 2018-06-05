package secom.accestur.core.service;

import secom.accestur.core.model.UserMCoupon;

public interface UserMCouponServiceInterface{
	public String getUserMCouponByUsername1(String username);
	
	//public UserMCoupon getUserMCouponByUsername(String username);

	//public String authenticateUsername(String username, String password);
	
	//public String verifyUsername(String[] params);
	
	public void createCertificate();
}