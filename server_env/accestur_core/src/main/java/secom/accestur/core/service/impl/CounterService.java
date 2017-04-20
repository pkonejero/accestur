package secom.accestur.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.CounterRepository;
import secom.accestur.core.model.Counter;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.service.CounterServiceInterface;

@Service("counterService")
public class CounterService implements CounterServiceInterface{	
	
	@Autowired
	@Qualifier("counterRepository")
	private CounterRepository counterRepository;
	
	private Counter counter;
	
	public void initCounter(MCityPass mCityPass, ServiceAgent service){
		counter = counterRepository.findByMCityPassAndService(mCityPass, service);
	}

	public List<Counter> getCountersByMCityPass(long sn){
		return null;
	}
	
	public List<Counter> getCountersByService(long sn){
		return null;
	}
	
	public Counter getCounter(){
		return counter;
	}
	
	public Counter getCounter(MCityPass mCityPass, ServiceAgent service){
		return counterRepository.findByMCityPassAndService(mCityPass, service);
	}
	
	public void saveCounter(Counter counter){
		counterRepository.save(counter);
	}
	
	public void saveCounters(List<Counter> counters){
		counterRepository.save(counters);
	}

	
	public void updateCounter() {
		counter.setCounter(counter.getCounter()-1);
		saveCounter(counter);
	}
	
	public void updateCounter(String hash) {
		counter.setLastHash(hash);
		counter.setCounter(counter.getCounter()-1);
		saveCounter(counter);
	}
}