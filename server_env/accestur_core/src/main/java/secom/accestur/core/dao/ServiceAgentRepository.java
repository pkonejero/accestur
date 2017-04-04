package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import secom.accestur.core.model.Provider;
import secom.accestur.core.model.ServiceAgent;

@Repository("serviceAgentRepository")
public interface ServiceAgentRepository extends PagingAndSortingRepository<ServiceAgent, Long>{
	ServiceAgent findByNameIgnoreCase(String name);
	
	List<ServiceAgent> findByProvider(Provider provider);
}