package secom.accestur.core.service.impl.coupon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.coupon.MCouponRepository;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.coupon.MCoupon;
import secom.accestur.core.service.coupon.MCouponServiceInterface;

@Service("mcouponService")
public class MCouponService implements MCouponServiceInterface{	
	
	@Autowired
	@Qualifier("mcouponRepository")
	private MCouponRepository mcouponRepository;
	
	private MCoupon mCoupon;
	
	public MCoupon getMCouponSn(long sn) {
		return mCoupon;
	}
	
	public List<MCoupon> getMCouponByUserMCoupon(String user){
		return null;
	}
	
	public void saveMCoupon(MCoupon mCoupon){
		mcouponRepository.save(mCoupon);
	}
	
	public void initMCoupon(Integer sn){
		mCoupon = getMCouponBySn(sn);
		System.out.println(mCoupon.toString());
	}
	
	public MCoupon getMCouponBySn(Integer sn){
		return mcouponRepository.findById(sn);
	}
	
	public MCoupon getMCoupon() {
		return mCoupon;
	}
}


