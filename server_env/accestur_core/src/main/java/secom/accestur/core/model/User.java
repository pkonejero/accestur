package secom.accestur.core.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="userEntity")
public class User extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	public User(){}
	
	@Column(name = "PSEUDONYM")
	private String pseudonym;
	 
	
	@OneToMany(mappedBy="user")
	private List<MCityPass> mCityPass;

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