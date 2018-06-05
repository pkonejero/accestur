package accestur.secom.core.model;




/*import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;*/

/*
 annotationProcessor "com.github.Raizlabs.DBFlow:dbflow-processor:${dbflow_version}"

    compile "com.github.Raizlabs.DBFlow:dbflow-core:${dbflow_version}"
    compile "com.github.Raizlabs.DBFlow:dbflow:${dbflow_version}"
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

import accestur.secom.core.AppDatabase;

//@Table( database = AppDatabase.class , allFields = true)
@Table(name = "activation")
public class Activation extends Model {//} extends BaseModel{

	//@PrimaryKey
	//private Long id;

	//@ForeignKey(stubbedRelationship = true)
    @Column
	private MCityPass mCityPass;

    @Column
	private String actDate;

    @Column
	private String state;

	@Column(length = 40000)
	private String signature;
	
	public Activation(){}

	public Activation(String actDate, MCityPass mCityPass, String state){
		super();
		this.actDate = actDate;
		this.mCityPass = mCityPass;
		this.state = state;
	}
	
	public Activation(MCityPass mCityPass, String actDate, String state, String signature) {
		super();
		this.mCityPass = mCityPass;
		this.actDate = actDate;
		this.state = state;
		this.signature = signature;
	}
	
	

	/*public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}*/

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public MCityPass getMCityPass(){
		return mCityPass;
	}

	public void setMCityPass(MCityPass mCityPass){
		this.mCityPass = mCityPass;
	}

	public String getActDate(){
		return actDate;
	}

	public void setActDate(String actDate){
		this.actDate = actDate;
	}

	public String getState(){
		return state;
	}

	public void setState(String state){
		this.state = state;
	}
	
	public String stringToSign(){
		JSONObject json = new JSONObject();
		try{
			json.put("PASS", mCityPass.getId());
			json.put("ACTDate", actDate);
			json.put("State", state);
		} catch (JSONException e){
			e.printStackTrace();
		}

		return json.toString();
	}
	
	@Override
	public String toString(){
		JSONObject json = new JSONObject();
		try{
		json.put("PASS", mCityPass.getId());
		json.put("ACTDate", actDate);
		json.put("State", state);
		json.put("Signature", signature);
		} catch (JSONException e){
			e.printStackTrace();
		}
		return json.toString();
	}
}