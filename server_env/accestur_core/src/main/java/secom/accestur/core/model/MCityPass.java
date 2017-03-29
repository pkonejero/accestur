package secom.accestur.core.model;

import java.util.Date;
import java.util.List;



import javax.persistence.*;

@Entity
@Table(name="mcitypassEntity")
public class MCityPass extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	//Type
	private Long lifeTime;
	private String category;
	private String termsAndContions;

	//User

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
 	private User user;

	//Dates
	private Date purDate;
	private Date expDate;


	//Hash of All Secret numbers
	@ElementCollection
	private List<String> hRI;

	//Hash of the ownership of the pass
	private String hRU;

	//@ManyToMany
	//@JoinTable(
	//	      name="SERVICES_CITYPASS",
	//	      joinColumns=@JoinColumn(name="CITYPASS_ID", referencedColumnName="ID"),
	//	      inverseJoinColumns=@JoinColumn(name="SERVICE_ID", referencedColumnName="ID"))
	//private List<Service> services;
	
	
	@OneToMany(mappedBy="mCityPass")
	private List<Counter> counters;
	
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="mCityPass")
	private Activation activation;
	
	
	//private List<List<String>> reusableServices;
	//private List<String> nonReusableServices;
	//private List<String> infiniteReusableServices;

	public MCityPass(){}


	public Long getLifeTime() {
		return lifeTime;
	}


	public void setLifeTime(Long lifeTime) {
		this.lifeTime = lifeTime;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getTermsAndContions() {
		return termsAndContions;
	}


	public void setTermsAndContions(String termsAndContions) {
		this.termsAndContions = termsAndContions;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Date getPurDate() {
		return purDate;
	}


	public void setPurDate(Date purDate) {
		this.purDate = purDate;
	}


	public Date getExpDate() {
		return expDate;
	}


	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}


	public List<String> gethRI() {
		return hRI;
	}


	public void sethRI(List<String> hRI) {
		this.hRI = hRI;
	}


	public String gethRU() {
		return hRU;
	}


	public void sethRU(String hRU) {
		this.hRU = hRU;
	}


	//public List<Service> getServices() {
		//return services;
	//}


	//public void setServices(List<Service> services) {
	//	this.services = services;
	//}


	public List<Counter> getCounters() {
		return counters;
	}


	public void setCounters(List<Counter> counters) {
		this.counters = counters;
	}


	public Activation getActivation() {
		return activation;
	}


	public void setActivation(Activation activation) {
		this.activation = activation;
	}
	
	
}

