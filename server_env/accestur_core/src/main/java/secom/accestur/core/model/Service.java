package secom.accestur.core.model;

import java.util.List;
import javax.persistence.*;

import org.springframework.stereotype.Component;

@Entity
@Table(name="service")
@Component("serviceModel")
public class Service extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROVIDER_ID")
	private Provider provider;

	@OneToMany(mappedBy="service")
	private List<Counter> counters;

	private String name;
	private int m;
	private String indexHash;

	public Service(String name, int m, String indexHash, Provider provider, List<Counter> counters){
		super();
		this.name = name;
		this.m = m;
		this.indexHash = indexHash;
		this.provider = provider;
		this.counters = counters;
	}

	public Service(){}

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
}