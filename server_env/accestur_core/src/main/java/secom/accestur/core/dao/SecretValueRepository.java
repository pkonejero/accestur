/**
 * 
 */
package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.SecretValue;
/**
 * @author Sebastià
 *
 */
@Repository("secretvalueRepository")
public interface SecretValueRepository extends PagingAndSortingRepository<SecretValue, Long> {

}
