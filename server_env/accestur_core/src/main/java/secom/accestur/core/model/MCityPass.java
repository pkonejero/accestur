package secom.accestur.core.model;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="mcitypassEntity")
@Component("mcitypassModel")
public class MCityPass extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	// User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	// Counter
	@OneToMany(mappedBy="mCityPass")
	private List<Counter> counters;

	// Activation
	@OneToOne(fetch=FetchType.LAZY, mappedBy="mCityPass")
	private Activation activation;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="mCityPass")
	private RightOfUse rightOfUse;

	public RightOfUse getRightOfUse() {
		return rightOfUse;
	}

	public void setRightOfUse(RightOfUse rightOfUse) {
		this.rightOfUse = rightOfUse;
	}


	private String hRI;

	//@ManyToMany
	//@JoinTable(
	//	      name="SERVICES_CITYPASS",
	//	      joinColumns=@JoinColumn(name="CITYPASS_ID", referencedColumnName="ID"),
	//	      inverseJoinColumns=@JoinColumn(name="SERVICE_ID", referencedColumnName="ID"))
	//private List<Service> services;

	//Type
	private String lifeTime;
	private String category;
	private String termsAndContions;

	private String delta;

	//Dates
	private Date purDate;
	private Date expDate;

	//Hash of the ownership of the pass
	private String hRU;

	public MCityPass(){}

	public User getUser(){
		return user;
	}

	public void setUser(User user){
		this.user = user;
	}

	public List<Counter> getCounters(){
		return counters;
	}

	public void setCounters(List<Counter> counters){
		this.counters = counters;
	}

	public Activation getActivation(){
		return activation;
	}

	public void setActivation(Activation activation){
		this.activation = activation;
	}

	public String gethRI(){
		return hRI;
	}

	public void sethRI(String hRI){
		this.hRI = hRI;
	}

	public String getLifeTime(){
		return lifeTime;
	}

	public void setLifeTime(String lifeTime){
		this.lifeTime = lifeTime;
	}

	public String getCategory(){
		return category;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getTermsAndContions(){
		return termsAndContions;
	}

	public void setTermsAndContions(String termsAndContions){
		this.termsAndContions = termsAndContions;
	}

	public Date getPurDate(){
		return purDate;
	}

	public void setPurDate(Date purDate){
		this.purDate = purDate;
	}

	public Date getExpDate(){
		return expDate;
	}

	public void setExpDate(Date expDate){
		this.expDate = expDate;
	}

	public String gethRU(){
		return hRU;
	}

	public void sethRU(String hRU){
		this.hRU = hRU;
	}
	
	public String getDelta() {
		return delta;
	}

	public void setDelta(String delta) {
		this.delta = delta;
	}
}