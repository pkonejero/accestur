package secom.accestur.core.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name="counterEntity")
@Component("counterModel")
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
	private Service service;

	// Counter
	private int counter;

	public Counter (){}

	public Counter(int counter, MCityPass mCityPass, Service service){
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

	public Service getService(){
		return service;
	}

	public void setService(Service service){
		this.service = service;
	}	

	public int getCounter(){
		return counter;
	}

	public void setCounter(int counter){
		this.counter = counter;
	}
}