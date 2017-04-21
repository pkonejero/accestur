package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.Activation;
import secom.accestur.core.model.MCityPass;

@Repository("activationRepository")
public interface ActivationRepository extends PagingAndSortingRepository<Activation, Long>{
	Activation findByMCityPass(MCityPass mCityPass);
	
	Activation findById(Long sn);
}