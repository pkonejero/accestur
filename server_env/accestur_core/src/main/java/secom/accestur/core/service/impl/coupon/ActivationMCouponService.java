package secom.accestur.core.service.impl.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.coupon.ActivationMCouponRepository;
import secom.accestur.core.model.coupon.ActivationMCoupon;
import secom.accestur.core.service.coupon.ActivationMCouponServiceInterface;

@Service("activationmcouponService")
public class ActivationMCouponService implements ActivationMCouponServiceInterface{	
	@Autowired
	@Qualifier("activationmcouponRepository")
	private ActivationMCouponRepository activationmcouponRepository;

	public ActivationMCoupon getActivationByMCouponSn(long sn){
		return null;
	}
}