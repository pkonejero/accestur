package secom.accestur.core.dao.coupon;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.model.coupon.MCoupon;

@Repository("mcouponRepository")
public interface MCouponRepository extends PagingAndSortingRepository<MCoupon, Long>{
	MCoupon findById(long id);
	
	List<MCoupon> findAll();
}