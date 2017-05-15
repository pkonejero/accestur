package secom.accestur.core.model.coupon;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import secom.accestur.core.model.MCityPass;
import secom.accestur.core.utils.Constants;

@Entity
@Table(name="countermcouponEntity")
public class CounterMCoupon extends DomainObjectModelCoupon{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;


	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MCOUPON_ID")
	private MCoupon mCoupon;

	private String lastHash;
	private int counter;

	public CounterMCoupon (){}

	public CounterMCoupon(int counter, MCoupon mCoupon){
		super();
		this.counter = counter;
		this.mCoupon = mCoupon;
		this.lastHash = Constants.NOTUSED;
	}
	
	public MCoupon getmCoupon(){
		return mCoupon;
	}

	public void setmCoupon(MCoupon mCoupon){
		this.mCoupon = mCoupon;
	}

	public int getCounterMCoupon(){
		return counter;
	}

	public void setCounterMCoupon(int counter){
		this.counter = counter;
	}

	public String getLastHash() {
		return lastHash;
	}

	public void setLastHash(String lastHash) {
		this.lastHash = lastHash;
	}
	
}