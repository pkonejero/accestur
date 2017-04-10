package secom.accestur.core.model;

import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="mcitypassEntity")
public class MCityPass extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	@OneToMany(mappedBy="mCityPass")
	private List<Counter> counters;

	@OneToOne(fetch=FetchType.LAZY, mappedBy="mCityPass")
	private Activation activation;

	private String hRI;
	
	private String lifeTime;
	private String category;
	private String termsAndContions;

	private Date purDate;
	private Date expDate;

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
}