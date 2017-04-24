package  accestur.secom.core.service.impl;

import java.util.List;


import  accestur.secom.core.model.Counter;
import  accestur.secom.core.model.MCityPass;
import  accestur.secom.core.model.ServiceAgent;
import  accestur.secom.core.service.CounterServiceInterface;


public class CounterService implements CounterServiceInterface{

	private Counter counter;

	private List<Counter> counters;

	public CounterService(){}



	public void initCounter(MCityPass mCityPass, ServiceAgent service, boolean user){
		if(service.getId()==1){
			counter = new Counter(-1,"NOT USED","2iTHjdJK+x3SyXvjpKEkvjHUTCVpKO3RCbgfyqP8i+8=",mCityPass,service);
		} else if(service.getId() == 2){
			counter = new Counter(1,"NOT USED","2iTHjdJK+x3SyXvjpKEkvjHUTCVpKO3RCbgfyqP8i+8='",mCityPass,service);
		} else if(service.getId() == 3){
			counter = new Counter(2,"NOT USED","6Tfe6M7DYmnPskxvBMAQQCMCy+M3uhgyzwWetBJOnaU=",mCityPass,service);
		} else {
			counter = new Counter(10,"NOT USED","U312l2ly2kP7i65FQviiO3dP1sXL/mNKFWPSRrkwESg=",mCityPass,service);
		}
	}


	public List<Counter> getCountersByMCityPass(long sn){
		return null;
	}

	public List<Counter> getCountersByService(long sn){
		return null;
	}

	@Override
	public Counter getCounter(MCityPass mCityPass, ServiceAgent service) {
		return null;
	}

	public Counter getCounter(){
		return counter;
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

	@Override
	public void saveCounter(Counter counter) {

	}

	@Override
	public void initCounter(MCityPass mCityPass, ServiceAgent service) {

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

	@Override
	public void saveCounters(List<Counter> counters) {

	}


}
