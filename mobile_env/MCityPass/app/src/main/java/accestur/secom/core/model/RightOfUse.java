package accestur.secom.core.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

public class RightOfUse extends Model{

	@Column(name = "mCityPass")
	private MCityPass mCityPass;

	@Column
	private String k;

	@Column
	private String RU;

	public RightOfUse(){}

	public RightOfUse(String k, String signature, MCityPass mCityPass){
		super();
		this.k = k;
		this.mCityPass = mCityPass;
	}

	public RightOfUse(String k, String RU){
		this.k = k;		
		this.RU = RU;
	}

	public MCityPass getmCityPass(){
		return mCityPass;
	}

	public void setmCityPass(MCityPass mCityPass){
		this.mCityPass = mCityPass;
	}

	public String getK(){
		return k;
	}

	public void setK(String k){
		this.k = k;
	}

	public String getRU(){
		return RU;
	}

	public void setRU(String signature){
		this.RU = signature;
	}

	//	@ElementCollection
	//	private List<String> RI;
	//
	//	@ElementCollection
	//	private List<String> deltas;
	//
	//	public RightOfUse(String k, List<String> rI, List<String> deltas){
	//	super();
	//	this.k = k;
	//	RI = rI;
	//	this.deltas = deltas;
	//	}

	//	public List<String> getRI(){
	//	return RI;
	//}
	//
	//public void setRI(List<String> rI){
	//	RI = rI;
	//}

}