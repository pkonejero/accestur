package secom.accestur.core.model.coupon;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import secom.accestur.core.model.Activation;
import secom.accestur.core.model.DomainObjectModel;
import secom.accestur.core.model.User;

@Entity
@Table(name="mcouponEntity")
public class MCoupon extends DomainObjectModel{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	//User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private UserMCoupon user;
	
	
	//Share Hash
	private String chain_Y;
	private Integer q; //Number of times to hash.
	
	//Own Hash
	private String chain_X;
	private Integer p;//Number of times to hash.
	
	//Serial Number
	private Long serialNumber;
	
	//Activation
	@OneToOne(fetch=FetchType.LAZY, mappedBy="mCoupon")
	private Activation activation;
	
	//Dates
	private Date expDate;
	private Date genDate;
	
	public MCoupon(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserMCoupon getUser() {
		return user;
	}

	public void setUser(UserMCoupon user) {
		this.user = user;
	}

	public String getChain_Y() {
		return chain_Y;
	}

	public void setChain_Y(String chain_Y) {
		this.chain_Y = chain_Y;
	}

	public String getChain_X() {
		return chain_X;
	}

	public void setChain_X(String chain_X) {
		this.chain_X = chain_X;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public Date getGenDate() {
		return genDate;
	}

	public void setGenDate(Date genDate) {
		this.genDate = genDate;
	}
	
} 
