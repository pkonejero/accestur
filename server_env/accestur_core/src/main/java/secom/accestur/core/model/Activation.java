package secom.accestur.core.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "activationEntity")
public class Activation extends DomainObjectModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// Activation
	private Date actDate;
	
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MCITYPASS_ID")
	private MCityPass mCityPass;
	
	
	private String state;

	public Activation(){}

	public Date getActDate() {
		return actDate;
	}

	public void setActDate(Date actDate) {
		this.actDate = actDate;
	}

	public MCityPass getmCityPass() {
		return mCityPass;
	}

	public void setmCityPass(MCityPass mCityPass) {
		this.mCityPass = mCityPass;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Activation(Date actDate, MCityPass mCityPass, String state) {
		super();
		this.actDate = actDate;
		this.mCityPass = mCityPass;
		this.state = state;
	}
	
	
	
	
}
