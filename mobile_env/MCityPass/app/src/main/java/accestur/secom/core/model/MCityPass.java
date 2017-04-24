package accestur.secom.core.model;

import java.util.Date;
import java.util.List;

import org.json.*;






public class MCityPass extends DomainObjectModel{

	private Long id;

	private User user;

	private List<Counter> counters;

	private Activation activation;
	
	private SecretValue secretValue;

	private String hRI;
	
	private String lifeTime;
	private String category;
	private String termsAndConditions;

	private String purDate;
	private String expDate;
	
	private String delta;
	
	private String hRU;
	private String Hu;
	
	
	private String signature;
	
	
	public MCityPass(){}

	
	public Long getId() {
		return id;
	}

	



	public void setId(Long id) {
		this.id = id;
	}


	public User getUser(){
		return user;
	}

	public void setUser(User user){
		this.user = user;
	}

	public List<Counter> getCounters(){
		return counters;
	}

	public void setCounters(List<Counter> counters){
		this.counters = counters;
	}

	public Activation getActivation(){
		return activation;
	}

	public void setActivation(Activation activation){
		this.activation = activation;
	}

	public String gethRI(){
		return hRI;
	}

	public void sethRI(String hRI){
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

	public String gethRU(){
		return hRU;
	}

	public void sethRU(String hRU){
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
		JSONObject json = new JSONObject();
		try{
			json.put("Sn", id);
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
			for (int i = 0; i < counters.size(); i++){
				jsonObject = new JSONObject();
				jsonObject.put("Service", counters.get(i).getService().getName());
				jsonObject.put("Psi", counters.get(i).getPsi());
				jsonArray.put(jsonObject);
			}
			json.put("Services", jsonArray);
		} catch (JSONException e){
			e.printStackTrace();
		}

		
		return json.toString();
	}
	
	
}