/**
 * 
 */
package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import secom.accestur.core.model.Counter;


/**
 * @author Sebastià
 *
 */
public interface CounterRepository extends PagingAndSortingRepository<Counter, Long>{

}
