package accestur.secom.core.model;

import java.util.List;



public class User extends DomainObjectModel{

	private Long id;

	private String pseudonym;

	private List<MCityPass> mCityPass;

	private String schnorr;
	
	private String RU;
	
	public String getRU() {
		return RU;
	}

	public void setRU(String rU) {
		RU = rU;
	}

	public User(){}
	
	public User(long id, String pseudonym, String schnorr){
		this.id = id;
		this.pseudonym = pseudonym;
		this.schnorr = schnorr;
	}
	
	public String getSchnorr() {
		return schnorr;
	}

	public void setSchnorr(String schnorr) {
		this.schnorr = schnorr;
	}

	public List<MCityPass> getmCityPass(){
		return mCityPass;
	}

	public void setmCityPass(List<MCityPass> mCityPass){
		this.mCityPass = mCityPass;
	}

	public String getPseudonym(){
		return pseudonym;
	}

	public void setPseudonym(String pseudonym){
		this.pseudonym = pseudonym;
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if ( pseudonym != other.pseudonym)
			return false;
		return true;
	}

	@Override
	public String toString(){
		return "User [pseudonym=" + pseudonym + "]";
	}
}