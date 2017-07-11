package secom.accestur.core.dao.coupon;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.coupon.IssuerMCoupon;

@Repository("issuermcouponRepository")
public interface IssuerMCouponRepository extends PagingAndSortingRepository<IssuerMCoupon, Long>{
	IssuerMCoupon findByNameIgnoreCase(String name);

}