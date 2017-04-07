package secom.accestur.core.model;

import javax.persistence.*;

@Entity
@Table(name="counterEntity")
public class Counter extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	// MCityPass
	@ManyToOne
	@JoinColumn(name = "MCITYPASS_ID",referencedColumnName = "ID",updatable = false)
	private MCityPass mCityPass;

	// Service
	@ManyToOne
	@JoinColumn(name = "SERVICE_ID", referencedColumnName = "ID",updatable = false)
	private ServiceAgent service;

	// Counter
	private int counter;

	public Counter (){}

	public Counter(int counter, MCityPass mCityPass, ServiceAgent service){
		super();
		this.counter = counter;
		this.mCityPass = mCityPass;
		this.service = service;
	}

	public MCityPass getmCityPass(){
		return mCityPass;
	}

	public void setmCityPass(MCityPass mCityPass){
		this.mCityPass = mCityPass;
	}

	public ServiceAgent getService(){
		return service;
	}

	public void setService(ServiceAgent service){
		this.service = service;
	}	

	public int getCounter(){
		return counter;
	}

	public void setCounter(int counter){
		this.counter = counter;
	}
}