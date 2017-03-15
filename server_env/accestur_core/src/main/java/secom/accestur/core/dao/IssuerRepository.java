package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import secom.accestur.core.model.Issuer;

public interface IssuerRepository extends PagingAndSortingRepository<Issuer, Long>{}