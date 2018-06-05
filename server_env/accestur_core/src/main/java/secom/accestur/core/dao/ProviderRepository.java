package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.Provider;

@Repository("providerRepository")
public interface ProviderRepository extends PagingAndSortingRepository<Provider, Long>{
	Provider findByNameIgnoreCase(String name);
	
	Provider findById(Long id);
	
	List<Provider> findByIssuer(Issuer issuerr);
}