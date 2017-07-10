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
	
	private List<Counter> counters;
	
	public CounterService(){}
	
	public void initCounter(MCityPass mCityPass, ServiceAgent service){
		counter = counterRepository.findByMCityPassAndService(mCityPass, service);
	}
	
	public void initCounter(MCityPass mCityPass, ServiceAgent service, boolean user){
		if(service.getId()==1){
			counter = new Counter(-1,"NOT USED","xPFi5JfIYcKBjgNeijV9vjCpbV+kt+9Eb9ZFK1MKiPk=",mCityPass,service);
		} else if(service.getId() == 2){
			counter = new Counter(1,"NOT USED","xPFi5JfIYcKBjgNeijV9vjCpbV+kt+9Eb9ZFK1MKiPk=",mCityPass,service);
		} else if(service.getId() == 3){
			counter = new Counter(2,"NOT USED","Ar6Zq8kgeqmf5EGqpb6uCYdRwDQpgRdCbGAIn8BImeQ=",mCityPass,service);
		} else {
			counter = new Counter(10,"NOT USED","ANH2zguj0X5R+ls0vONZSxs7PWsDP71weNLkJ6hNRuI=",mCityPass,service);
		}
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
		this.counters = counters;
	}
	
	public void setCounter(Counter counter){
		this.counter = counter;
	}

	
	public void updateCounter() {
		counter.setCounter(counter.getCounter()-1);
		counter.setLastHash("Already used");
		saveCounter(counter);
	}
	
	public void updateCounter(boolean user){
		counter.setCounter(counter.getCounter()-1);
		counter.setLastHash("Already used");
	}
	
	public void updateCounter(String hash) {
		counter.setLastHash(hash);
		counter.setCounter(counter.getCounter()-1);
		saveCounter(counter);
	}
	
	public void updateCounter(String hash, boolean user){
		counter.setLastHash(hash);
		counter.setCounter(counter.getCounter()-1);
	}
	
	public void updateInfiniteCounter() {
		if(counter.getCounter()==-1){
			counter.setCounter(1);
		} else {
			counter.setCounter(counter.getCounter()+1);
		}
		counter.setLastHash("Infinite uses");
		saveCounter(counter);
	}
	
	public void updateInfiniteCounter(boolean user) {
		if(counter.getCounter()==-1){
			counter.setCounter(1);
		} else {
			counter.setCounter(counter.getCounter()+1);
		}
		counter.setLastHash("Infinite uses");
		//saveCounter(counter);
	}

	/* (non-Javadoc)
	 * @see secom.accestur.core.service.CounterServiceInterface#initCounter(secom.accestur.core.model.MCityPass, secom.accestur.core.model.ServiceAgent, int, java.lang.String)
	 */
	@Override
	public void initCounter(MCityPass mCityPass, ServiceAgent service, int counter, String psi) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see secom.accestur.core.service.CounterServiceInterface#getCounter(secom.accestur.core.model.MCityPass, secom.accestur.core.model.ServiceAgent, int, java.lang.String)
	 */
	@Override
	public Counter getCounter(MCityPass mCityPass, ServiceAgent service, int counter, String psi) {
		// TODO Auto-generated method stub
		return null;
	}


	
	
}