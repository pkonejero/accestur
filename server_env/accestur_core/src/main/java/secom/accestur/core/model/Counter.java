package secom.accestur.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="counterEntity")
public class Counter extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "MCITYPASS_ID",referencedColumnName = "ID",updatable = false)
	private MCityPass mCityPass;

	@ManyToOne
	@JoinColumn(name = "SERVICE_ID", referencedColumnName = "ID",updatable = false)
	private ServiceAgent service;
	
	private String psi;
	private int counter;

	public Counter (){}

	public Counter(int counter, MCityPass mCityPass, ServiceAgent service){
		super();
		this.counter = counter;
		this.mCityPass = mCityPass;
		this.service = service;
	}
	
	public Counter(int counter, MCityPass mCityPass, ServiceAgent service, String psi){
		this.counter = counter;
		this.mCityPass = mCityPass;
		this.service = service;
		this.psi = psi;
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

	public String getPsi() {
		return psi;
	}

	public void setPsi(String psi) {
		this.psi = psi;
	}
	
	
}