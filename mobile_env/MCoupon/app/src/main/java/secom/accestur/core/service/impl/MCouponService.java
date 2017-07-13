package secom.accestur.core.service.impl;

import java.util.List;


import secom.accestur.core.model.MCoupon;
import secom.accestur.core.service.MCouponServiceInterface;

public class MCouponService implements MCouponServiceInterface{	

	//private MCouponRepository mcouponRepository;
	
	private MCoupon mCoupon;
	
	public MCoupon getMCouponSn(long sn) {
		return null;
	}
	
	public List<MCoupon> getMCouponByUserMCoupon(String user){
		return null;
	}
	
	//public void saveMCoupon(MCoupon mCoupon){
	//	mcouponRepository.save(mCoupon);
	//}
	
	//public void initMCoupon(Integer sn){
	//	mCoupon = getMCouponBySn(sn);
	//	System.out.println(mCoupon.toString());
	//}
	
	//public MCoupon getMCouponBySn(Integer sn){
	//	return mcouponRepository.findById(sn);
	//}
	
	public MCoupon getMCoupon() {
		return mCoupon;
	}
}


