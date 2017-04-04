package secom.accestur.core.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import secom.accestur.core.model.User;

@Repository("userRepository")
public interface UserRepository extends PagingAndSortingRepository<User, Long>{}