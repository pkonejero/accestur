package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.Schnorr;

@Repository("schnorrRepository")
public interface SchnorrRepository extends PagingAndSortingRepository<Schnorr, Long>{}