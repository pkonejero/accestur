package secom.accestur.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "schnorrEntity")
public class Schnorr extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(length = 40000)
	private String p;
	@Column(length = 40000)
	private String q;
	@Column(length = 40000)
	private String g;
	
	public Schnorr(){}

	public Schnorr(String p, String q, String g){
		this.p = p;
		this.q = q;
		this.g = g;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}
}