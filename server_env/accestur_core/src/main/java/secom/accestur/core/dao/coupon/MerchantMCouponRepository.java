package secom.accestur.core.dao.coupon;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.coupon.IssuerMCoupon;
import secom.accestur.core.model.coupon.MerchantMCoupon;

@Repository("merchantmcouponRepository")
public interface MerchantMCouponRepository extends PagingAndSortingRepository<MerchantMCoupon, Long>{
	MerchantMCoupon findByNameIgnoreCase(String name);
	
	List<MerchantMCoupon> findByIssuer(IssuerMCoupon issuerr);
	
}