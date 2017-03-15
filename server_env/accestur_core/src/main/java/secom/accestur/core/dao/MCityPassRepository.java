package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import secom.accestur.core.model.impl.MCityPassModel;

public interface MCityPassRepository extends PagingAndSortingRepository<MCityPassModel, Long>{}