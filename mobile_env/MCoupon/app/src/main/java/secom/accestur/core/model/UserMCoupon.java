package secom.accestur.core.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name="usermCoupon")
public class UserMCoupon extends Model{

//	@Column
//	private Long id;
	
	@Column(length = 20)
	private String username;
	
	@Column(length = 20)
	private String password;
	
	//MCoupons of the user
	private List<MCoupon> mCoupon(){
		return getMany(MCoupon.class, "usermCoupon");
	}
	
	//User registered to a manufacturer
	@Column
	private ManufacturerMCoupon manufacturer;

	@Column
	private String X;
	@Column
	private String Y;

	@Column
	private String Xo;
	@Column
	private String Yo;

	@Column
	private String signature;


	public UserMCoupon(){}
	
	public UserMCoupon( String username, String password){
		//this.id = id;
		this.username = username;
		this.password = password;
	}
	

//	public List<MCoupon> getmCoupon(){
//		return mCoupon;
//	}
//
//	public void setmCoupon(List<MCoupon> mCoupon){
//		this.mCoupon = mCoupon;
//	}

	public String getUsername(){
		return username;
	}

	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}
	
	public ManufacturerMCoupon getManufacturer(){
		return manufacturer;
	}

	public void setManufacturerMCoupon(ManufacturerMCoupon manufacturer){
		this.manufacturer = manufacturer;
	}
	
	public String getX() {
		return X;
	}

	public void setX(String x) {
		X = x;
	}
	
	public String getY() {
		return Y;
	}

	public void setY(String y) {
		Y = y;
	}

	public String getXo() {
		return Xo;
	}

	public void setXo(String xo) {
		Xo = xo;
	}

	public String getYo() {
		return Yo;
	}

	public void setYo(String yo) {
		Yo = yo;
	}

	public void setSignature(String signature){
		this.signature=signature;
	}
	public String getSignature(){
		return signature;
	}
	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserMCoupon other = (UserMCoupon) obj;
		if ( username != other.username)
			return false;
		return true;
	}

	@Override
	public String toString(){
		return "User [username=" + username + "]";
	}
}
