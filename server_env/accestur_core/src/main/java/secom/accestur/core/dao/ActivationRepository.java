package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.Activation;

@Repository("activationRepository")
public interface ActivationRepository extends PagingAndSortingRepository<Activation, Long>{}