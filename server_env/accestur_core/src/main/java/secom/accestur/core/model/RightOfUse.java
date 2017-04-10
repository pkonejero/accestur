package secom.accestur.core.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rightOfUseEntity")
public class RightOfUse extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MCITYPASS_ID")
	private MCityPass mCityPass;

	private String k;
	private String signature;

	public RightOfUse(){}

	public RightOfUse(String k, String signature, MCityPass mCityPass){
		super();
		this.k = k;
		this.mCityPass = mCityPass;
	}

	public RightOfUse(String k, String signature){
		this.k = k;		
		this.signature = signature;
	}

	public MCityPass getmCityPass(){
		return mCityPass;
	}

	public void setmCityPass(MCityPass mCityPass){
		this.mCityPass = mCityPass;
	}

	public String getK(){
		return k;
	}

	public void setK(String k){
		this.k = k;
	}

	public String getSignature(){
		return signature;
	}

	public void setSignature(String signature){
		this.signature = signature;
	}

	//	@ElementCollection
	//	private List<String> RI;
	//
	//	@ElementCollection
	//	private List<String> deltas;
	//
	//	public RightOfUse(String k, List<String> rI, List<String> deltas){
	//	super();
	//	this.k = k;
	//	RI = rI;
	//	this.deltas = deltas;
	//	}

	//	public List<String> getRI(){
	//	return RI;
	//}
	//
	//public void setRI(List<String> rI){
	//	RI = rI;
	//}

}