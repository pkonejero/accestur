package secom.accestur.core.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


import secom.accestur.core.utils.Constants;


@Table(name="countermCoupon")
public class CounterMCoupon extends Model{
	//@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
//	@Column
//	private Long id;

	//Foreign Key
	@Column(name = "mCoupon")
	private MCoupon mCoupon;

	//private String lastHash;
	@Column
	private int counter;

	public CounterMCoupon (){}

	public CounterMCoupon(int counter, MCoupon mCoupon){
		super();
		this.counter = counter;
		this.mCoupon = mCoupon;
		//this.lastHash = Constants.NOTUSED;
	}
	public CounterMCoupon(int counter){
		super();
		this.counter=counter;
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

	//public String getLastHash() {
	//	return lastHash;
	//}

	//public void setLastHash(String lastHash) {
	//	this.lastHash = lastHash;
	//}
	
}