package secom.accestur.core.service.impl.coupon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.coupon.CounterMCouponRepository;
import secom.accestur.core.model.Counter;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.model.coupon.CounterMCoupon;
import secom.accestur.core.model.coupon.MCoupon;
import secom.accestur.core.service.coupon.CounterMCouponServiceInterface;

@Service("countermcouponService")
public class CounterMCouponService implements CounterMCouponServiceInterface{	
	
	@Autowired
	@Qualifier("countermcouponRepository")
	private CounterMCouponRepository countermcouponRepository;
	
	private CounterMCoupon counter;
	
	public void initCounter(MCoupon mCoupon){
		counter = countermcouponRepository.findByMCoupon(mCoupon);
	}

	
	public CounterMCoupon getCounter(){
		return counter;
	}
	
	public CounterMCoupon getCounter(MCoupon mCoupon){
		return countermcouponRepository.findByMCoupon(mCoupon);
	}
	
	public void saveCounterMCoupon(CounterMCoupon counter){
		countermcouponRepository.save(counter);
	}
	
	public void saveCountersMCoupon(List<CounterMCoupon> counters){
		countermcouponRepository.save(counters);
	}

	
	public void updateCounterMCoupon() {
		counter.setCounterMCoupon(counter.getCounterMCoupon()-1);
		counter.setLastHash("Already used");
		saveCounterMCoupon(counter);
	}
	
	public void updateCounterMCoupon(String hash) {
		counter.setLastHash(hash);
		counter.setCounterMCoupon(counter.getCounterMCoupon()-1);
		saveCounterMCoupon(counter);
	}
	
}