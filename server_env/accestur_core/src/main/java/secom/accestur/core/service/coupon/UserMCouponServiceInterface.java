package secom.accestur.core.service.coupon;

import secom.accestur.core.model.coupon.UserMCoupon;

public interface UserMCouponServiceInterface{
	public String getUserMCouponByPseudonym1(String pseudonym);
	
	public UserMCoupon getUserMCouponByPseudonym(String pseudonym);

	public String[] authenticateUserMCoupon();
	
	public boolean verifyPseudonym(String[] params);
	
	public String getService();
	
	public String[] showTicket();
	
	public String[] showPass();
	
	public String[] showProof(String[] params);
	
	public boolean getValidationConfirmation();
	
	public String solveChallenge(String c, String[] services);
	
	public String[] receivePass(String[] params);
	
	public String sendPass();
	
	public void createCertificate();
}