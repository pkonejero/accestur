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

	//User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

	// Service
	@ManyToMany
	@JoinTable(
			name="SERVICES_CITYPASS",
			joinColumns=@JoinColumn(name="CITYPASS_ID", referencedColumnName="ID"),
			inverseJoinColumns=@JoinColumn(name="SERVICE_ID", referencedColumnName="ID"))
	private List<Service> services;

	//Hash of All Secret numbers
	@ElementCollection
	private List<String> hRI;

	//Hash of the ownership of the pass
	private String hRU;

	//Type
	private Long lifeTime;
	private String category;
	private String termsAndContions;

	//Dates
	private Date purDate;
	private Date expDate;

	public MCityPass(){}
}
