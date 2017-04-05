package secom.accestur.core.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name='mcouponEntity')
public class MCoupon extends DomainObjectModel{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	//User
	private String MUC;
	
	//Share Hash
	private String chain_Y;
	
	//Own Hash
	private String chain_X;
	
	//Dates
	private Date expDate;
	private Date genDate;
	
	public MCoupon(){}
	
} 
