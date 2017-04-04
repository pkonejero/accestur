package secom.accestur.core.service;

import secom.accestur.core.model.Counter;

import java.util.List;

public interface CounterServiceInterface{
	public List<Counter> getCountersByMCityPass(long sn);
	
	public List<Counter> getCountersByService(long sn);
	
	public Counter getCounter(long mCityPass, long service);	
}