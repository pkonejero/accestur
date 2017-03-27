package secom.accestur.core.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import secom.accestur.core.crypto.elgamal.Elgamal;

@Entity
@Table(name="providerEntity")
public class Provider extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String providerName;
	
//private Elgamal elgamal;
	//private 
	
	Provider(){}
	
	
	
	
}