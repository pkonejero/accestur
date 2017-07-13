package secom.accestur.core.model;
import java.util.List;



public class IssuerMCoupon extends DomainObjectModelCoupon{

	private Long id;

	private List<MerchantMCoupon> merchants;

	private ManufacturerMCoupon manufacturer;

	private String name;

	public IssuerMCoupon(){}

	public IssuerMCoupon(String name, List<MerchantMCoupon> merchants){
		super();
		this.name = name;
		this.merchants = merchants;
	}
	
	public void setManufacturerMCoupon(ManufacturerMCoupon manufacturer){
		this.manufacturer = manufacturer;
	}

	public List<MerchantMCoupon> getMerchants(){
		return merchants;
	}

	public void setMerchantMCoupon(List<MerchantMCoupon> merchants){
		this.merchants = merchants;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
}