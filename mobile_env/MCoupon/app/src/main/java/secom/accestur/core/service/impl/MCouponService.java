package secom.accestur.core.service.impl;

import android.provider.ContactsContract;

import com.activeandroid.query.Select;

import java.util.List;


import secom.accestur.core.model.MCoupon;
import secom.accestur.core.model.MerchantMCoupon;
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

	public List<MCoupon> getAllMCoupon(){
		MCoupon mCoupon = new MCoupon();
		mCoupon.setP(5);
		mCoupon.setId(Long.parseLong("1"));
		mCoupon.save();

		return new Select().from(MCoupon.class).execute();
	}
}


