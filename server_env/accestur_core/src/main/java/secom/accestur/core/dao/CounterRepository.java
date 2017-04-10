package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.Counter;

@Repository("counterRepository")
public interface CounterRepository extends PagingAndSortingRepository<Counter, Long>{}