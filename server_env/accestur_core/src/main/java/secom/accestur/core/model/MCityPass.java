package secom.accestur.core.model;

import java.util.Date;
import java.util.List;



import javax.persistence.*;

@Entity
@Table(name="mcitypassEntity")
public class MCityPass extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	//Type
	private Long lifeTime;
	private String category;
	private String termsAndContions;

	//User

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
 	private User user;

	//Dates
	private Date purDate;
	private Date expDate;


	//Hash of All Secret numbers
	@ElementCollection
	private List<String> hRI;

	//Hash of the ownership of the pass
	private String hRU;

	@ManyToMany
	@JoinTable(
		      name="SERVICES_CITYPASS",
		      joinColumns=@JoinColumn(name="CITYPASS_ID", referencedColumnName="ID"),
		      inverseJoinColumns=@JoinColumn(name="SERVICE_ID", referencedColumnName="ID"))
	private List<Service> services;
	
	
	@OneToMany(mappedBy="mCityPass")
	private List<Counter> counters;
	
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="mCityPass")
	private Activation activation;
	
	
	//private List<List<String>> reusableServices;
	//private List<String> nonReusableServices;
	//private List<String> infiniteReusableServices;

	public MCityPass(){}
}

