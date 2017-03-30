package secom.accestur.core.service;

import java.util.List;

import secom.accestur.core.model.Counter;

public interface CounterServiceInterface{
	
	public List<Counter> getCountersByMCityPass(long sn);
	
	public List<Counter> getCountersByService(long sn);
	
	public Counter getCounter(long mCityPass, long service);
	
	
}