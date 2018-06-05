package secom.accestur.core.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="userEntity")
public class User extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(length = 40000)
	private String pseudonym;

	@OneToMany(mappedBy="user")
	private List<MCityPass> mCityPass;
	
	@Column(length = 40000)
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
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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