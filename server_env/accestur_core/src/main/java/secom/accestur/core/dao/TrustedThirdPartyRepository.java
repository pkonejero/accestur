package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import secom.accestur.core.model.impl.TrustedThirdPartyModel;

public interface TrustedThirdPartyRepository extends PagingAndSortingRepository<TrustedThirdPartyModel, Long>{}