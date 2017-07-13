package secom.accestur.core.model;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.sql.Date;
import java.util.List;


@Table(name="mcouponEntity")
public class MCoupon extends Model{
	
	@Column
	private Long id;
	
	//Foreign Key User
	@Column
	private UserMCoupon user;
	
	//Merchant
	@Column
	private MerchantMCoupon merchant;
	
	//COUNTER
	@Column
	private CounterMCoupon counter;
	
	//Share Hash
	private String Yo;
	private Integer q; //Number of times to hash.
	
	//Own Hash
	private String Xo;
	private Integer p;//Number of times to hash.
	
	//Serial Number
	private Integer sn;  //Hauria de ser molt gran.

	
	//Dates
	private Date expDate;
	private Date genDate;
	
	public MCoupon(){}

//	public Long getId() {
//		return id;
//	}

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
