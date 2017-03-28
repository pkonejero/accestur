package secom.accestur.core.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "activationEntity")
public class Activation extends DomainObjectModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// Activation
	private Date actDate;
	
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MCITYPASS_ID")
	private MCityPass mCityPass;
	
	
	private String state;

	public Activation(){}
}
