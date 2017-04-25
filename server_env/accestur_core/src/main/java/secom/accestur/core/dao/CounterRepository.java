package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.Counter;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.ServiceAgent;

@Repository("counterRepository")
public interface CounterRepository extends PagingAndSortingRepository<Counter, Long>{
	
	Counter findByMCityPassAndService(MCityPass mCityPass, ServiceAgent service);
}