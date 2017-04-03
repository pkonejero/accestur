package secom.accestur.core.model;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;


@Entity
@Table(name="userEntity")
@Component("userModel")
public class User extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
	private String pseudonym;
	
	@Column(length = 1000)
	private String signature;
	
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String[] getSchnorrParameters() {
		String[] params = new String[schnorrParameters.size()];
		for(int i = 0; i < schnorrParameters.size(); i++){
			params[i] = schnorrParameters.get(i);
		}
		return params;
	}

	public void setSchnorrParameters(String[] params) {
		List<String> list = new ArrayList<String>(Arrays.asList(params));
		
		this.schnorrParameters = list;
	}

	@ElementCollection
	private List<String> schnorrParameters;
	
	//private String schnorrCertificate;
	
	// MCityPass
	@OneToMany(mappedBy="user")
	private List<MCityPass> mCityPass;
	
	


	public List<MCityPass> getmCityPass(){
		return mCityPass;
	}

	public void setmCityPass(List<MCityPass> mCityPass){
		this.mCityPass = mCityPass;
	}



	public User(){}

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