package secom.accestur.core.service;

import java.util.List;

import secom.accestur.core.model.MCoupon;

public interface MCouponServiceInterface{
	//public MCoupon getMCouponSn(long sn);
	
	public List<MCoupon> getMCouponByUserMCoupon(String user);
	
	//public void saveMCoupon(MCoupon mCoupon);
}