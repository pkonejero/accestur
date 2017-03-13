package secom.accestur.core.model.impl;

public class DomainObjectModel{

	private Long id;

	public DomainObjectModel(){		
	}

	public Long getId(){
		return id;
	}

	public void setId(final Long id){
		this.id = id;
	}

	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomainObjectModel other = (DomainObjectModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String toString() {
		return this.getClass().getSimpleName() + " [id=" + id + "]";
	}
}