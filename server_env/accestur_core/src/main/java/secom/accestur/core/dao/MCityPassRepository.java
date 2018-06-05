package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.MCityPass;

@Repository("mcitypassRepository")
public interface MCityPassRepository extends PagingAndSortingRepository<MCityPass, Long>{
	MCityPass findById(long id);
}