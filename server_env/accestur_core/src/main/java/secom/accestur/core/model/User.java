package secom.accestur.core.model;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="userEntity")
public class User extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	// MCityPass
	@OneToMany(mappedBy="user")
	private List<MCityPass> mCityPass;

	// Pseudonym
	private String pseudonym;

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