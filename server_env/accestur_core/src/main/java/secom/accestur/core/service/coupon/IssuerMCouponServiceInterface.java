package secom.accestur.core.service.coupon;

import secom.accestur.core.model.coupon.IssuerMCoupon;
import secom.accestur.core.model.coupon.ManufacturerMCoupon;

public interface IssuerMCouponServiceInterface{
	public IssuerMCoupon getIssuerMCouponByName(String name);

	public void newIssuerMCoupon(String name, ManufacturerMCoupon manufacturer);

	public String getChallenge(String params);

	public String getPASS(String params);

	public String[] verifyTicket(String[] params);

	public boolean arrayGeneration();

	public void createCertificate();
}