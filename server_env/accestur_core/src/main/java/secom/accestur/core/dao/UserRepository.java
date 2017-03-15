package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import secom.accestur.core.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>{}