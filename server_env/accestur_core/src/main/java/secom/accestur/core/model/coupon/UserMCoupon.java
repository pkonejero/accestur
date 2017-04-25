package secom.accestur.core.model.coupon;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="usermcouponEntity")
public class UserMCoupon extends DomainObjectModelCoupon{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(length = 20)
	private String username;
	
	@Column(length = 20)
	private String password;
	
	//MCoupons of the user
	@OneToMany(mappedBy="user")
	private List<MCoupon> mCoupon;
	
	//User registered to a manufacturer
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MANUFACTURER_ID")
	private ManufacturerMCoupon manufacturer;
	
	
	public UserMCoupon(){}
	

	public List<MCoupon> getmCoupon(){
		return mCoupon;
	}

	public void setmCoupon(List<MCoupon> mCoupon){
		this.mCoupon = mCoupon;
	}

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
