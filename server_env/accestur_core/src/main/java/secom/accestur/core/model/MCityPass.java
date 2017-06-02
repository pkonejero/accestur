package secom.accestur.core.model;

import java.util.Date;
import java.util.List;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import secom.accestur.core.service.impl.UserService;

import javax.persistence.Column;
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
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="mCityPass")
	private SecretValue secretValue;

	private String hRI;
	
	private String lifeTime;
	private String category;
	private String termsAndConditions;

	private String purDate;
	private String expDate;
	
	@Column(length = 40000)
	private String delta;
	
	private String hRU;
	private String Hu;
	
	
	@Column(length = 40000)
	private String signature;
	
	
	public MCityPass(){}

	
	public Long getId() {
		return id;
	}

	



	public void setId(Long id) {
		this.id = id;
	}


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

	public String getTermsAndConditions(){
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions){
		this.termsAndConditions = termsAndConditions;
	}

	public String getPurDate() {
		return purDate;
	}

	public void setPurDate(String purDate) {
		this.purDate = purDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
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
	
	
	
	public SecretValue getSecretValue() {
		return secretValue;
	}


	public void setSecretValue(SecretValue secretValue) {
		this.secretValue = secretValue;
	}


	public String getHu() {
		return Hu;
	}


	public void setHu(String hu) {
		Hu = hu;
	}


	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString(){
		//UserService userService = new UserService();
		JSONObject json = new JSONObject();
		json.put("Sn", id);
		json.put("User", user);
		json.put("hRI", hRI);
		json.put("hRU", hRU);
		json.put("Hu", Hu);
		json.put("expDate", expDate);
		json.put("purDate", purDate);
		json.put("Lifetime", lifeTime);
		json.put("Category", category);
		json.put("TermsAndConditions", termsAndConditions);
		json.put("Signature", signature);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;
		for (int i = 0; i < counters.size(); i++){
			jsonObject = new JSONObject();
			jsonObject.put("Service", counters.get(i).getService().getName());
			jsonObject.put("Psi", counters.get(i).getPsi());
			jsonArray.put(jsonObject);
		}
		json.put("Services", jsonArray);
		
		return json.toString();
	}
	
	
}