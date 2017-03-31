/**
 * 
 */
package secom.accestur.core.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import secom.accestur.core.dao.CounterRepository;
import secom.accestur.core.model.Counter;
import secom.accestur.core.service.CounterServiceInterface;

/**
 * @author Sebastià
 *
 */
@Service("counterService")
public class CounterService implements CounterServiceInterface {
	@Autowired
	@Qualifier("counterModel")
	private Counter counter;
	
	@Autowired
	private CounterRepository counterRepository;


	public List<Counter> getCountersByMCityPass(long sn) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Counter> getCountersByService(long sn) {
		// TODO Auto-generated method stub
		return null;
	}


	public Counter getCounter(long mCityPass, long service) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
