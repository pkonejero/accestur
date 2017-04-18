package secom.accestur.core.service.impl.coupon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.coupon.MCouponRepository;
import secom.accestur.core.model.coupon.MCoupon;
import secom.accestur.core.service.coupon.MCouponServiceInterface;

@Service("mcouponService")
public class MCouponService implements MCouponServiceInterface{	
	
	@Autowired
	@Qualifier("mcouponRepository")
	private MCouponRepository mcouponRepository;
	
	public MCoupon getMCouponSn(long sn) {
		return null;
	}
	
	public List<MCoupon> getMCouponByUserMCoupon(String user){
		return null;
	}


}