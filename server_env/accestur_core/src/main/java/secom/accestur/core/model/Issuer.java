package secom.accestur.core.model;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="issuerEntity")
public class Issuer extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	// Provider
	@OneToMany(mappedBy="issuer")
	private List<Provider> providers;

	// Name
	private String name;

	public Issuer(){}
}