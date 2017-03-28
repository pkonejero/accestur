package secom.accestur.core.model;

import java.util.List;


import javax.persistence.*;

@Entity
@Table(name="providerEntity")
public class Provider extends DomainObjectModel{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ISSUER_ID")
	private Issuer issuer;
	
	@OneToMany(mappedBy="provider")
	private List<Service> services;
	
	Provider(){}
}