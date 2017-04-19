package secom.accestur.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.CounterRepository;
import secom.accestur.core.model.Counter;
import secom.accestur.core.service.CounterServiceInterface;

@Service("counterService")
public class CounterService implements CounterServiceInterface{	
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
	
	public void saveCounter(Counter counter){
		counterRepository.save(counter);
	}
	
	public void savaCounters(List<Counter> counters){
		counterRepository.save(counters);
	}
}