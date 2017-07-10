package accestur.secom.core.model;





/*import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;*/


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import accestur.secom.core.AppDatabase;
import accestur.secom.core.utils.Constants;


@Table( name = "counter")
public class Counter extends Model {// extends BaseModel{

   // @PrimaryKey
	//@Column(index =true)
	//private Long id;


    //@ForeignKey
	@Column(name = "mCityPass")
	private MCityPass mCityPass;

    //@ForeignKey
	@Column(name = "service")
	private ServiceAgent service;

	@Column
	private String psi;

	@Column
	private String lastHash;

	@Column
	private int counter;

	public Counter (){}

	public Counter(int counter, MCityPass mCityPass, ServiceAgent service){
		super();
		this.counter = counter;
		this.mCityPass = mCityPass;
		this.service = service;
		this.lastHash = Constants.NOTUSED;
	}
	
	public Counter(int counter, String psi, String lastHash,  MCityPass mCityPass, ServiceAgent service){
		super();
		this.counter = counter;
		this.mCityPass = mCityPass;
		this.service = service;
		this.lastHash = lastHash;
	}
	
	public Counter(int counter, MCityPass mCityPass, ServiceAgent service, String psi){
		this.counter = counter;
		this.mCityPass = mCityPass;
		this.service = service;
		this.psi = psi;
		this.lastHash = Constants.NOTUSED;
	}

  //  public Long getId() {
    //    return id;
    //}

    //public void setId(Long id) {
      //  this.id = id;
    //}

    public MCityPass getMCityPass(){
		return mCityPass;
	}

	public void setMCityPass(MCityPass mCityPass){
		this.mCityPass = mCityPass;
	}

	public ServiceAgent getService(){
		return service;
	}

	public void setService(ServiceAgent service){
		this.service = service;
	}

	public int getCounter(){
		return counter;
	}

	public void setCounter(int counter){
		this.counter = counter;
	}

	public String getPsi() {
		return psi;
	}

	public void setPsi(String psi) {
		this.psi = psi;
	}

	public String getLastHash() {
		return lastHash;
	}

	public void setLastHash(String lastHash) {
		this.lastHash = lastHash;
	}
	
	
	
	
}