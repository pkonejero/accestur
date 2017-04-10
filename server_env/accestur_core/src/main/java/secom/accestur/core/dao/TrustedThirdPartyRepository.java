package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.TrustedThirdParty;

@Repository("ttpRepository")
public interface TrustedThirdPartyRepository extends PagingAndSortingRepository<TrustedThirdParty, Long>{}