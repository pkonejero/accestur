package secom.accestur.core.model;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.sql.Date;
import java.util.List;
import java.util.jar.Attributes;


@Table(name="mCoupon")
public class MCoupon extends Model{
	
	@Column( name = "sN")
	private Integer sN;
	
	//Foreign Key User
	@Column(name="user")
	private UserMCoupon user;
	
	//Merchant
	@Column(name = "merchant")
	private String merchant;
	
	//COUNTER
	//@Column
	public List<CounterMCoupon> counters (){return getMany(CounterMCoupon.class, "MCoupon");}
	
	//Share Hash
	//@Column
	private String Yo;
	@Column(name = "q")
	private Integer q; //Number of times to hash.
	//@Column
	//Own Hash
	private String Xo;
	@Column(name="p")
	private Integer p;//Number of times to hash.

	@Column(name="counter")
	private CounterMCoupon counterMCoupon;


	
//	//Serial Number
//	private Integer sn;  //Hauria de ser molt gran.

	
	//Dates
	private Date expDate;
	private Date genDate;
	
	public MCoupon(){
		super();
	}

	public MCoupon(Integer sn, int p, int q) {
		super();
		this.sN = sn;
		this.p = p;
		this.q = q;
	}

//	public Long getId() {
//		return id;
//	}

//	public void setId(Long id) {
//		this.id = id;
//	}

	public UserMCoupon getUser() {
		return user;
	}

	public void setUser(UserMCoupon user) {
		this.user = user;
	}
	
	public String getMerchant(){
		return merchant;
	}
	
	public void setMerchant(String merchant) {
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
		return sN;
	}

	public void setSn(Integer sN) {
		this.sN = sN;
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

	public void setCounterMCoupon(CounterMCoupon counterMCoupon){
		this.counterMCoupon=counterMCoupon;
	}

	public CounterMCoupon getCounterMCoupon(){
		return counterMCoupon;
	}
//	public CounterMCoupon getCounter() {
//		return counter;
//	}
//
//	public void setCounter(CounterMCoupon counter) {
//		this.counter = counter;
//	}
	
} 
