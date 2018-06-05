package secom.accestur.core.dao.coupon;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.Activation;

@Repository("activationmcouponRepository")
public interface ActivationMCouponRepository extends PagingAndSortingRepository<Activation, Long>{}