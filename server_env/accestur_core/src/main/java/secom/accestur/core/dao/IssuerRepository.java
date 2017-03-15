package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import secom.accestur.core.model.impl.IssuerModel;

public interface IssuerRepository extends PagingAndSortingRepository<IssuerModel, Long>{}