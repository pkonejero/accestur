package secom.accestur.core.service.impl;

import android.provider.ContactsContract;

import com.activeandroid.query.Select;

import java.util.List;


import secom.accestur.core.model.MCoupon;
import secom.accestur.core.model.MerchantMCoupon;
import secom.accestur.core.model.UserMCoupon;
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

	public MCoupon getMCouponByUserMCoupon(UserMCoupon user){
		return new Select().from(MCoupon.class).where("username = ? ", user.getUsername()).executeSingle();
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
		return new Select().from(MCoupon.class).execute();
	}

	public List<MCoupon> getAllMCouponUser(){
		return new Select().from(MCoupon.class).where("user=?",null).execute();
	}

	public void storeMCoupon(MCoupon mCoupon) {
		this.mCoupon = mCoupon;
		saveMCoupon();
	}

	public MCoupon initMCoupon(int sn){
		mCoupon = loadMCoupon(sn);
		System.out.println(mCoupon.toString());
		return mCoupon;
	}

	public void saveMCoupon(){
		mCoupon.save();
	}

	public MCoupon loadMCoupon(int id){
		//MCityPass mPass =  new Select().from(MCityPass.class).orderBy("RANDOM()").executeSingle();
		MCoupon mCoupon = MCoupon.load(MCoupon.class, id);
		System.out.println("MCoupon: " + mCoupon);
		return mCoupon;

	}

	public MCoupon getmCouponbyId(Long id){
		return new Select().from(MCoupon.class).where("id = ? ", id).executeSingle();
	}

}


