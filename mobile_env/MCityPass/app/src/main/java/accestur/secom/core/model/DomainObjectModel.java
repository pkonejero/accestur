package accestur.secom.core.model;

public class DomainObjectModel{
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	public String toString(){
		return this.getClass().getSimpleName();
	}
}