package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.RightOfUseRepository;
import secom.accestur.core.service.RightOfUseServiceInterface;

@Service("rightOfUseService")
public class RightOfUseService implements RightOfUseServiceInterface{
	@Autowired
	@Qualifier("rightOfUseRepository")
	private RightOfUseRepository rightOfUseRepository;
}