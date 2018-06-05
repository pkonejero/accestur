package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.Issuer;

@Repository("issuerRepository")
public interface IssuerRepository extends PagingAndSortingRepository<Issuer, Long>{
	Issuer findByNameIgnoreCase(String name);	
}