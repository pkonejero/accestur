package secom.accestur.core.service.coupon;

import secom.accestur.core.model.coupon.IssuerMCoupon;

public interface IssuerMCouponServiceInterface{
	public IssuerMCoupon getIssuerByName(String name);

	public void newIssuer(String name);

	public String getChallenge(String params);

	public String getPASS(String params);

	public String[] verifyTicket(String[] params);

	public boolean arrayGeneration();

	public void createCertificate();
}