package accestur.secom.core.service;

import java.util.List;

import accestur.secom.core.model.Counter;
import accestur.secom.core.model.MCityPass;
import accestur.secom.core.model.ServiceAgent;

public interface CounterServiceInterface{
	
	public void initCounter(MCityPass mCityPass, ServiceAgent service);
	
	public void initCounter(MCityPass mCityPass, ServiceAgent service, int counter, String psi);
	
	public List<Counter> getCountersByMCityPass(long sn);
	
	public List<Counter> getCountersByService(long sn);
	
	public Counter getCounter(MCityPass mCityPass, ServiceAgent service);	
	
	public Counter getCounter(MCityPass mCityPass, ServiceAgent service, int counter, String psi);
	
	public void saveCounters(List<Counter> counters);
	
	public void updateCounter();
	
	public void updateCounter(boolean user);
	
	public void updateCounter(String hash);
	
	public void updateCounter(String hash, boolean user);
	
	public void updateInfiniteCounter();
	
	public void updateInfiniteCounter(boolean user);

	public void saveCounter(Counter counter);
	
}