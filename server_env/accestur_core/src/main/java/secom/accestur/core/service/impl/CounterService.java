package secom.accestur.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

import secom.accestur.core.dao.CounterRepository;
import secom.accestur.core.model.Counter;
import secom.accestur.core.service.CounterServiceInterface;

@Service("counterService")
public class CounterService implements CounterServiceInterface{
	@Autowired
	@Qualifier("counterModel")
	private Counter counter;
	
	@Autowired
	@Qualifier("counterRepository")
	private CounterRepository counterRepository;

	public List<Counter> getCountersByMCityPass(long sn){
		return null;
	}
	
	public List<Counter> getCountersByService(long sn){
		return null;
	}
	
	public Counter getCounter(long mCityPass, long service){
		return null;
	}
	
}