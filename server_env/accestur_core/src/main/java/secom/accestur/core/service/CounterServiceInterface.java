package secom.accestur.core.service;

import java.util.List;

import secom.accestur.core.model.Counter;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.ServiceAgent;

public interface CounterServiceInterface{
	
	public void initCounter(MCityPass mCityPass, ServiceAgent service);
	
	public List<Counter> getCountersByMCityPass(long sn);
	
	public List<Counter> getCountersByService(long sn);
	
	public Counter getCounter(MCityPass mCityPass, ServiceAgent service);	
	
	public void saveCounters(List<Counter> counters);
	
	public void updateCounter();
	
	public void updateCounter(String hash);
	
	public void saveCounter(Counter counter);
	
}