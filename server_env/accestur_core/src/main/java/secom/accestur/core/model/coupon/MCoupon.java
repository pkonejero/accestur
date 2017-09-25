package secom.accestur.core.model.coupon;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import secom.accestur.core.model.Counter;

@Entity
@Table(name="mcouponEntity")
public class MCoupon extends DomainObjectModelCoupon{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	//User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private UserMCoupon user;
	
	//Merchant
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MERCHANT_ID")
	private MerchantMCoupon merchant;
	
	//COUNTER
	@OneToOne(mappedBy="mCoupon")
	private CounterMCoupon counter;
	
	//Share Hash
	private String Yo;
	private Integer q; //Number of times to hash.
	
	//Own Hash
	private String Xo;
	private Integer p;//Number of times to hash.
	
	//Serial Number
	private Integer sn;  //Hauria de ser molt gran.
	
	//Activation
	@OneToOne(fetch=FetchType.LAZY, mappedBy="mCoupon")
	private ActivationMCoupon activation;
	
	//Dates
	private Date expDate;
	private Date genDate;
	
	private String EXD;
	
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
	
	public MerchantMCoupon getMerchant(){
		return merchant;
	}
	
	public void setMerchant(MerchantMCoupon merchant) {
		this.merchant = merchant;
	}

	public String getYo() {
		return Yo;
	}

	public void setYo(String Y0) {
		this.Yo = Y0;
	}

	public String getXo() {
		return Xo;
	}

	public void setXo(String X0) {
		this.Xo = X0;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date date) {
		this.expDate = date;
	}
	
	public String getEXD(){
		return EXD;
	}
	
	public void setEXD(String exd){
		this.EXD=exd;
	}
	public Date getGenDate() {
		return genDate;
	}
	

	public void setGenDate(Date genDate) {
		this.genDate = genDate;
	}
	
	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sN) {
		this.sn = sN;
	}
	
	public Integer getP() {
		return p;
	}

	public void setP(Integer P) {
		this.p = P;
	}
	
	public Integer getQ() {
		return q;
	}

	public void setQ(Integer Q) {
		this.q = Q;
	}
	
	public CounterMCoupon getCounter() {
		return counter;
	}

	public void setCounter(CounterMCoupon counter) {
		this.counter = counter;
	}
	
} 
