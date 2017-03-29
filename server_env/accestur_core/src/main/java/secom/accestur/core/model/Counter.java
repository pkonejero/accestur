/**
 * 
 */
package secom.accestur.core.model;

import javax.persistence.*;

@Entity
@Table(name="COUNTER")
public class Counter extends DomainObjectModel {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private int counter;
	
	
	@ManyToOne
	@JoinColumn(
			name = "MCITYPASS_ID", 
			referencedColumnName = "ID",
			updatable = false)
	private MCityPass mCityPass;
	
	
	@ManyToOne
	@JoinColumn(
			name = "SERVICE_ID", 
			referencedColumnName = "ID",
			updatable = false)
	private Service service;
	
	
	public Counter (){}


	public int getCounter() {
		return counter;
	}


	public void setCounter(int counter) {
		this.counter = counter;
	}


	public MCityPass getmCityPass() {
		return mCityPass;
	}


	public void setmCityPass(MCityPass mCityPass) {
		this.mCityPass = mCityPass;
	}


	public Service getService() {
		return service;
	}


	public void setService(Service service) {
		this.service = service;
	}


	public Counter(int counter, MCityPass mCityPass, Service service) {
		super();
		this.counter = counter;
		this.mCityPass = mCityPass;
		this.service = service;
	}
	
	
	
	
}
