/**
 * 
 */
package secom.accestur.core.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import secom.accestur.core.model.Provider;
import secom.accestur.core.model.Service;


public interface ServiceRepository extends PagingAndSortingRepository<Service, Long> {
	
	Service findByNameIgnoreCase(String name);
	
	List<Service> findByProvider(Provider provider);
	
	
}
