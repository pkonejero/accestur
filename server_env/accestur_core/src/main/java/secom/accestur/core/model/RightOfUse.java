package secom.accestur.core.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "rightOfUseEntity")
@Component("rightofuseModel")
public class RightOfUse extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MCITYPASS_ID")
	private MCityPass mCityPass;

	// Shared Session Key
	private String k;
	private String signature;

	public RightOfUse(){}

	public RightOfUse(String k, String signature, MCityPass mCityPass){
		this.k = k;		
		this.signature = signature;
		this.mCityPass = mCityPass;
	}
	
	public RightOfUse(String k, String signature){
		this.k = k;		
		this.signature = signature;
	}

	public String getK(){
		return k;
	}

	public void setK(String k){
		this.k = k;
	}

	public MCityPass getmCityPass() {
		return mCityPass;
	}

	public void setmCityPass(MCityPass mCityPass) {
		this.mCityPass = mCityPass;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	
	

}