package secom.accestur.core.service.coupon;

import java.util.List;

import secom.accestur.core.model.coupon.CounterMCoupon;
import secom.accestur.core.model.coupon.MCoupon;

public interface CounterMCouponServiceInterface{
	
	public void initCounter(MCoupon mCoupon);
	
	public CounterMCoupon getCounter(MCoupon mCoupon);	
	
	public void saveCountersMCoupon(List<CounterMCoupon> counters);
	
	public void updateCounterMCoupon();
	
	public void updateCounterMCoupon(String hash);
	
	public void saveCounterMCoupon(CounterMCoupon counter);
	
}