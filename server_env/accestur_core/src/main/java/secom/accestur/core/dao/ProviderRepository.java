package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import secom.accestur.core.model.impl.ProviderModel;

public interface ProviderRepository extends PagingAndSortingRepository<ProviderModel, Long>{}