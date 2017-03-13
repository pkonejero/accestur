package uib.secom.accestur.core.model.src;

import uib.secom.accestur.core.model.itf.UserModelInterface;

public class UserModel extends DomainObjectModel implements UserModelInterface {

	private String pseudonym;

	public String getPseudonym() {
		return pseudonym;
	}

	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserModel other = (UserModel) obj;
		if ( pseudonym != other.pseudonym)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + getId() + ", pseudonym=" + pseudonym + "]";
	}

}
