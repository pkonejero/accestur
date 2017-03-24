/**
 * 
 */
package secom.accestur.core.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Sebastià
 *
 */

@Entity
@Table(name = "activationEntity")
public class Activation extends DomainObjectModel {

	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// Activation
	private Date actDate;
	private Long Sn;
	private String state;
	
	public Activation(){}

}
