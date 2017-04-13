package secom.accestur.core.service.coupon;

import secom.accestur.core.model.coupon.ManufacturerMCoupon;

public interface ManufacturerMCouponServiceInterface{
	
	public ManufacturerMCoupon getManufacturerByName(String name);

	public void newManufacturer(String name);

	public String getChallenge(String params);

	public String getPASS(String params);

	public String[] verifyTicket(String[] params);

	public boolean arrayGeneration();

	public void createCertificate();
}