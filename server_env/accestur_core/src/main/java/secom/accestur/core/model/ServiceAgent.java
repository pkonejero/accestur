package secom.accestur.core.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name="service")
public class ServiceAgent extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PROVIDER_ID")
	private Provider provider;

	@OneToMany(mappedBy="service")
	private List<Counter> counters;

	private String name;
	private int m;
	private String indexHash;
	
	public ServiceAgent(long id, String indexHash, int m, String name){
		this.id = id;
		this.indexHash = indexHash;
		this.m = m;
		this.name = name;
	}

	public ServiceAgent(String name, int m, String indexHash, Provider provider, List<Counter> counters){
		super();
		this.name = name;
		this.m = m;
		this.indexHash = indexHash;
		this.provider = provider;
		this.counters = counters;
	}

	public ServiceAgent(){}

	public Provider getProvider(){
		return provider;
	}

	public void setProvider(Provider provider){
		this.provider = provider;
	}

	public List<Counter> getCounters(){
		return counters;
	}

	public void setCounters(List<Counter> counters){
		this.counters = counters;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public int getM(){
		return m;
	}

	public void setM(int m){
		this.m = m;
	}

	public String getIndexHash(){
		return indexHash;
	}

	public void setIndexHash(String indexHash){
		this.indexHash = indexHash;
	}

	public Long getId() {
		return id;
	}
	
	
}