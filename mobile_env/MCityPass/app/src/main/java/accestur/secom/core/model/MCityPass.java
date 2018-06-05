package accestur.secom.core.model;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

import org.json.*;


/*
import com.raizlabs.android.dbflow.annotation.*;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;*/

import accestur.secom.core.AppDatabase;


//@Table( database = AppDatabase.class , allFields = true)
@Table(name = "mCityPass")
public class MCityPass extends Model{// extends BaseModel{
	//@PrimaryKey
	@Column
	private long sN;

    //@ForeignKey(saveForeignKeyModel = false)
	@Column
	private User user;


	public List<Counter> counters (){
        return getMany(Counter.class, "mCityPass");
    }

	//@OneToMany(methods = {OneToMany.Method.ALL}, variableName = "counters" )
	/*public List<Counter> getCounters(){
			if (counters == null || counters.isEmpty()) {
			//	counters = SQLite.select().from(Counter.class).where(Counter_Table.mCityPass_id.eq(id)).queryList();
			}
			return counters;
	}*/

	//@ForeignKey(stubbedRelationship = true)
	@Column
	private Activation activation;

	//@ForeignKey(stubbedRelationship = true)
	@Column
	private SecretValue secretValue;

	@Column
	private String hRI;

	@Column
	private String lifeTime;

	@Column
	private String category;

	@Column
	private String termsAndConditions;

	@Column
	private String purDate;

	@Column
	private String expDate;

	@Column(length = 40000)
	private String delta;

	@Column
	private String hRU;

	@Column
	private String Hu;


//	@Column(length = 40000)
	private String signature;
	
	
	public MCityPass(){}

	
	public Long getsN() {
		return sN;
	}

	public void setsN(Long id) {
		this.sN = id;
	}


	public User getUser(){
		return user;
	}

	public void setUser(User user){
		this.user = user;
	}

	//public List<Counter> getCounters(){
	//	return counters;
	//}

	//public void setCounters(List<Counter> counters){
	//	this.counters = counters;
	//}

	public Activation getActivation(){
		return activation;
	}

	public void setActivation(Activation activation){
		this.activation = activation;
	}

	public String getHRI(){
		return hRI;
	}

	public void setHRI(String hRI){
		this.hRI = hRI;
	}

	public String getLifeTime(){
		return lifeTime;
	}

	public void setLifeTime(String lifeTime){
		this.lifeTime = lifeTime;
	}

	public String getCategory(){
		return category;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getTermsAndConditions(){
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions){
		this.termsAndConditions = termsAndConditions;
	}

	public String getPurDate() {
		return purDate;
	}

	public void setPurDate(String purDate) {
		this.purDate = purDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getHRU(){
		return hRU;
	}

	public void setHRU(String hRU){
		this.hRU = hRU;
	}

	public String getDelta() {
		return delta;
	}

	public void setDelta(String delta) {
		this.delta = delta;
	}
	
	
	
	public SecretValue getSecretValue() {
		return secretValue;
	}


	public void setSecretValue(SecretValue secretValue) {
		this.secretValue = secretValue;
	}


	public String getHu() {
		return Hu;
	}


	public void setHu(String hu) {
		Hu = hu;
	}


	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public String toString(){
		return "" + Hu;
	}
	public String toJSON(){
		JSONObject json = new JSONObject();
		try{
			json.put("Sn", sN);
			json.put("User", user.getPseudonym());
			json.put("hRI", hRI);
			json.put("hRU", hRU);
			json.put("Hu", Hu);
			json.put("expDate", expDate);
			json.put("purDate", purDate);
			json.put("Lifetime", lifeTime);
			json.put("Category", category);
			json.put("TermsAndConditions", termsAndConditions);
			json.put("Signature", signature);
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject;
			for (int i = 0; i < counters().size(); i++){
				jsonObject = new JSONObject();
				jsonObject.put("Service", counters().get(i).getService().getName());
				jsonObject.put("Psi", counters().get(i).getPsi());
				jsonArray.put(jsonObject);
			}
			json.put("Services", jsonArray);
		} catch (JSONException e){
			e.printStackTrace();
		}

		
		return json.toString();
	}
	
	
}