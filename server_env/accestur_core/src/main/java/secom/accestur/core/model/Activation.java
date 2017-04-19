package secom.accestur.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "activationEntity")
public class Activation extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MCITYPASS_ID")
	private MCityPass mCityPass;

	private String actDate;

	private String state;
	
	@Column(length = 40000)
	private String signature;
	
	public Activation(){}

	public Activation(String actDate, MCityPass mCityPass, String state){
		super();
		this.actDate = actDate;
		this.mCityPass = mCityPass;
		this.state = state;
	}
	
	public Activation(MCityPass mCityPass, String actDate, String state, String signature) {
		super();
		this.mCityPass = mCityPass;
		this.actDate = actDate;
		this.state = state;
		this.signature = signature;
	}
	
	

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public MCityPass getmCityPass(){
		return mCityPass;
	}

	public void setmCityPass(MCityPass mCityPass){
		this.mCityPass = mCityPass;
	}

	public String getActDate(){
		return actDate;
	}

	public void setActDate(String actDate){
		this.actDate = actDate;
	}

	public String getState(){
		return state;
	}

	public void setState(String state){
		this.state = state;
	}
	
	public String stringToSign(){
		JSONObject json = new JSONObject();
		json.put("PASS", mCityPass.getId());
		json.put("ACTDate", actDate);
		json.put("State", state);
		return json.toString();
	}
	
	@Override
	public String toString(){
		JSONObject json = new JSONObject();
		json.put("PASS", mCityPass.getId());
		json.put("ACTDate", actDate);
		json.put("State", state);
		json.put("Signature", signature);
		return json.toString();
	}
}