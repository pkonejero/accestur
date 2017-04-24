package accestur.secom.core.model;



import org.json.JSONException;
import org.json.JSONObject;


public class Activation extends DomainObjectModel{

	private Long id;

	private MCityPass mCityPass;

	private String actDate;

	private String state;

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
	
	

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public MCityPass getmCityPass(){
		return mCityPass;
	}

	public void setmCityPass(MCityPass mCityPass){
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