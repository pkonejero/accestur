package secom.accestur.core.model.coupon;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activationmcouponEntity")
public class ActivationMCoupon extends DomainObjectModelCoupon{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MCOUPON_ID")
	private MCoupon mCoupon;

	private Date actDate;

	private String state;

	public ActivationMCoupon(){}

	public ActivationMCoupon(Date actDate, MCoupon mCoupon, String state){
		super();
		this.actDate = actDate;
		this.mCoupon = mCoupon;
		this.state = state;
	}

	public MCoupon getmCoupon(){
		return mCoupon;
	}

	public void setmCoupon(MCoupon mCoupon){
		this.mCoupon = mCoupon;
	}

	public Date getActDate(){
		return actDate;
	}

	public void setActDate(Date actDate){
		this.actDate = actDate;
	}

	public String getState(){
		return state;
	}

	public void setState(String state){
		this.state = state;
	}
}