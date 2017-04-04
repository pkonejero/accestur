package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import secom.accestur.core.model.Activation;

public interface ActivationRepository extends PagingAndSortingRepository<Activation, Long>{}