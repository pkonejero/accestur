package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import secom.accestur.core.model.impl.UserModel;

public interface UserRepository extends PagingAndSortingRepository<UserModel, Long>{}