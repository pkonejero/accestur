package secom.accestur.core.dao.coupon;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.coupon.UserMCoupon;

@Repository("usermcouponRepository")
public interface UserMCouponRepository extends PagingAndSortingRepository<UserMCoupon, Long>{}