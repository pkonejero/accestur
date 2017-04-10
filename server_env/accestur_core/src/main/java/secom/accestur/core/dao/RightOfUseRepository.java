package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.RightOfUse;

@Repository("rightOfUseRepository")
public interface RightOfUseRepository extends PagingAndSortingRepository<RightOfUse, Long>{}