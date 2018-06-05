package secom.accestur.core.dao.coupon;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.coupon.CounterMCoupon;
import secom.accestur.core.model.coupon.MCoupon;

@Repository("countermcouponRepository")
public interface CounterMCouponRepository extends PagingAndSortingRepository<CounterMCoupon, Long>{
	
	CounterMCoupon findByMCoupon(MCoupon mCoupon);
}