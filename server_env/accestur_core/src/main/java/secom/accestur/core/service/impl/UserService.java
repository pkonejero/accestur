package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.UserRepository;
import secom.accestur.core.model.User;
import secom.accestur.core.service.UserServiceInterface;

@Service("userService")
public class UserService implements UserServiceInterface{
	@Autowired
	@Qualifier("userModel")
	private User user;
	
	@Autowired
	private UserRepository userRepository;
	
	public String getUserByPseudonym(String pseudonym){
		user.setPseudonym(pseudonym);
		userRepository.save(user);
		return pseudonym;
	}
}