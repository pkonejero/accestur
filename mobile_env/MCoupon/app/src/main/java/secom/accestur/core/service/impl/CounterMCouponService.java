package secom.accestur.core.service.impl;

import java.util.List;

import com.activeandroid.query.Select;

import secom.accestur.core.model.CounterMCoupon;
import secom.accestur.core.model.MCoupon;
import secom.accestur.core.service.CounterMCouponServiceInterface;

public class CounterMCouponService implements CounterMCouponServiceInterface{	

	//private CounterMCouponRepository countermcouponRepository;
	
	private CounterMCoupon counter;
	
	//public void initCounter(MCoupon mCoupon){
	//	counter = countermcouponRepository.findByMCoupon(mCoupon);
	//}

	
	public CounterMCoupon getCounter(){
		return counter;
	}
	
	//public CounterMCoupon getCounter(MCoupon mCoupon){
	//	return countermcouponRepository.findByMCoupon(mCoupon);
	//}
	
	//public void saveCounterMCoupon(CounterMCoupon counter){
	//	countermcouponRepository.save(counter);
	//}
	
	//public void saveCountersMCoupon(List<CounterMCoupon> counters){
	//	countermcouponRepository.save(counters);
	//}

	public void init (int counter){
			this.counter = new CounterMCoupon(counter);
	}

	public void updateCounterMCoupon() {
		counter.setCounterMCoupon(counter.getCounterMCoupon()-1);
		//saveCounterMCoupon(counter);
	}
	
	public void updateCounterMCoupon(String hash) {
		counter.setCounterMCoupon(counter.getCounterMCoupon()-1);
		//saveCounterMCoupon(counter);
	}

	public void storeCounterMCoupon(CounterMCoupon counterMCoupon) {
		this.counter = counterMCoupon;
		saveCounterMCoupon();
	}
	public void saveCounterMCoupon(){
		counter.save();
	}

	public CounterMCoupon getCounterMCouponbyMCouponId (Long id){
		return new Select().from(CounterMCoupon.class).where("id = ? ", id).executeSingle();
	}
}