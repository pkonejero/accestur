package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import secom.accestur.core.model.Provider;

public interface ProviderRepository extends PagingAndSortingRepository<Provider, Long>{}