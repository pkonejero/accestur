package accestur.secom.core.model;

public class Schnorr extends DomainObjectModel{

	private Long id;

	private String p;

	private String q;

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