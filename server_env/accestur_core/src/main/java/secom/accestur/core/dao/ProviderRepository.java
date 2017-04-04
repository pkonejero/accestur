package secom.accestur.core.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.Provider;

public interface ProviderRepository extends PagingAndSortingRepository<Provider, Long>{
	Provider findByNameIgnoreCase(String name);
	
	List<Provider> findByIssuer(Issuer issuerr);
}