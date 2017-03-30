/**
 * 
 */
package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import secom.accestur.core.dao.RightOfUseRepository;
import secom.accestur.core.service.RightOfUseServiceInterface;

/**
 * @author Sebastià
 *
 */
@Service("rightOfUseService")
public class RightOfUseService implements RightOfUseServiceInterface{

	@Autowired
	private RightOfUseRepository rightOfUseRepository;
}
