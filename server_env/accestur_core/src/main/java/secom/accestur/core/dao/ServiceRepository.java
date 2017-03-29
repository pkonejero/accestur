/**
 * 
 */
package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import secom.accestur.core.model.Service;


public interface ServiceRepository extends PagingAndSortingRepository<Service, Long> {}
