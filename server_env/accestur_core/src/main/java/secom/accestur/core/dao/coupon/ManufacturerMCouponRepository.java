package secom.accestur.core.dao.coupon;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.coupon.ManufacturerMCoupon;

@Repository("manufacturermcouponRepository")
public interface ManufacturerMCouponRepository extends PagingAndSortingRepository<ManufacturerMCoupon, Long>{
	ManufacturerMCoupon findByNameIgnoreCase(String name);
}