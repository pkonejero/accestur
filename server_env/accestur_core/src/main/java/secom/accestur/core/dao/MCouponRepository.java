package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import secom.accestur.core.model.coupon.MCoupon;

public interface MCouponRepository extends PagingAndSortingRepository<MCoupon, Long>{}
