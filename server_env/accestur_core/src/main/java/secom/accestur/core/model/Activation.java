package secom.accestur.core.model;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "activationEntity")
public class Activation extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// MCityPass
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MCITYPASS_ID")
	private MCityPass mCityPass;

	// Activation
	private Date actDate;

	// State
	private String state;

	public Activation(){}

	public Activation(Date actDate, MCityPass mCityPass, String state){
		super();
		this.actDate = actDate;
		this.mCityPass = mCityPass;
		this.state = state;
	}

	public MCityPass getmCityPass(){
		return mCityPass;
	}

	public void setmCityPass(MCityPass mCityPass){
		this.mCityPass = mCityPass;
	}

	public Date getActDate(){
		return actDate;
	}

	public void setActDate(Date actDate){
		this.actDate = actDate;
	}

	public String getState(){
		return state;
	}

	public void setState(String state){
		this.state = state;
	}
}
