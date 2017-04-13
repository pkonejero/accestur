package secom.accestur.core.dao.coupon;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.coupon.MCoupon;

@Repository("mcouponRepository")
public interface MCouponRepository extends PagingAndSortingRepository<MCoupon, Long>{}