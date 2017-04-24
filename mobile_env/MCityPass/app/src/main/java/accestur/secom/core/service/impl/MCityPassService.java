package  accestur.secom.core.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import  accestur.secom.core.model.Activation;
import  accestur.secom.core.model.MCityPass;
import  accestur.secom.core.model.User;
import  accestur.secom.core.service.MCityPassServiceInterface;


public class MCityPassService implements MCityPassServiceInterface{	

	private ActivationService activationService;
	
	private MCityPass mCityPass;


	@Override
	public MCityPass getMCityPassBySn(long sn) {
		return null;
	}

	public List<MCityPass> getMCityPassesByUser(String user){
		return null;
	}

	@Override
	public void saveMCityPass(MCityPass mCityPass) {

	}


	public void initMCityPass(long sn){
		mCityPass = getMCityPassBySn(sn);
		System.out.println(mCityPass.toString());
	}
	
	public void initMCityPass(){
		mCityPass = new MCityPass();
		mCityPass.setId(new Long(1));
		mCityPass.setHu("13249626973785225943064492781125502991354587408085548737730232317592517340935984078593430175266076672323976323670938764089922654900371604099092070206881340638");
		mCityPass.setCategory("Adult");
		mCityPass.setDelta("{\"signature\":\"v8tjsKURUUV2esNb/3YuifXMCxZd3P+QvnJrAa6AKkwU1h3fVhEyjkeCbeXLnqoWLC7TrZt2iQ5KTIZbYZ7i9WhnZZmKW3MNZl1SDymO5nl0l+NR5gcDso8i39DeAVdegYV7U0Q2AGTx+N9R1Ym4n7h4lshm1gc5cF67wT9zyXwHy1YAXonC5sqzIjEg/+FqSrqE4DT31wm6soYcST326iDkPQeF8Dj8DpqgxAhvPCNCae8uZrjngkJ8LKf3SLxCt0bpMU9Fzp8mEmCWKPWujgYJwivm/K9CShgpzxQpb6GZyOwVCOvHhnP7WIwGvLHW5nNWvbb+CFR/KFZ9n8KlWQ==\",\"k\":\"ryHL7LY8bp1SlvJesx6ZR79fgWVBsraOhskXEEE7H+X5d/MIG7PcWu5/ZVk+LoRmxYaf00qk6t/6Aw+z0VL2QOKl8r0gF+TOY6gqWz3KBinH2XhpzAToNXsChaq43nMbRqRaCFiaVwIUxXJRTiLVDUx/xqAw+NI6XseIzBffPBCEum7R6SdYRdnB68i6/lTNWvsbdLH1wtJYf6MCtTYOFNqKe8QTM84EW1j39So17hVjCmiJBK1nptir2skob/Ik1cBec6ZQNmnB0vEJp3wltp/ih/7NyLUqEtT16ePrZ9WjUfrJYc4xdrGhU8WT7lRN9gXGPFrYhcRToqGUwzxj3g==\"}");
		mCityPass.setExpDate("08/06/2017");
		mCityPass.sethRI("28qrsA7VR4jcD+opCRUq6UGfZx3crPsj1Pyyw24yC9k=");
		mCityPass.sethRU("h4ZNEEKZmqAetjFJUdBYxbVN+O5KAtFxColYWJ8uPGE=");
		mCityPass.setLifeTime("604800000");
		mCityPass.setPurDate("21/04/2017");
		mCityPass.setSignature("q/WqzBpf6LgOW3hGHgsLWySJUcqpNPRPYcPeD+mEnP4nii8kyAwcy/nI7xhNktSKAL/CeI1oP25QDWAH+PYMUgJzzDms5DifbBkZoS9OOMynejxizuEa8yLlNl8tjGKdF6GwwUsm4UpyBVQ++JzwkmraftcMM44t3tAkcohMtNp57ueiJF/+rhWJdxYNRB6WYW8pZpbXBBHr9TU+73SwcqlyOhZdagS3D++SnxktjnL44TkojNgB9XBPS/dElf6x31V2FC4pxrkwDsbiTcwKq/r24IlkKXHhif0ATkktkFcZ8Eh/4uxnocABiPcvCDVrbEjJN7GQejrgBRNWqxH7fA==");
		mCityPass.setTermsAndConditions("Lorem ipsum dolor sit amet.");
		String schnorr = "{\"p\":\"15277160019691241425056343514263407750548889486111153986570545050251020131904394375904890848244527769038757280355403593200158044376264500469424023579806111987\",\"q\":\"8240107885486106486006657774683607200943306087438594383263508657093322616992661475676855905202010662911951068152860622006557736988276429595158588770121959\",\"g\":\"1514584839425919363525023347275785025835117601117443638644111269286983800930309213257206014157612761652599400252093511933272586856485729712397283986079989283\",\"x\":\"7708628560626367760465016407189387479320242873908245161782841436913570511985583732098641759217506298877394767011854554216545447918209312125411822909189291\",\"y\":\"14989039481374367292369303874278584668623867701174068970571531818536559077115464814862613693930941393933250033676427630417324282180404456732157669953456072214\"}";
		String pseudonym = "\"{\"signature\":\"SKWcc27uci691bCqyjqb29HgtVdgfUzqf9wnLrlQqDtRiw3Va4u7LVmxvi11LViyCua9Wthqq0Bh8Jky2Ly5Zcu46HOczadD78wrBN3pN0G2yq2mCv1/+ENhjHSv6c2ppC6Mm0nSrd4fNgY+/FzzYBMGvqvErIq/Bd8bEcDv+ThH60vbAqyHhzeomKpl0aNzmgOpdv0f/8SzZwvJbzPG+2xf6UIFccXiyU+PZbVryxVCVkDadXEC5gbV3uSsJuzznpdZqa646+v+0n2KgHSjTMjKUgoXkI0PxJlKYiOpTwDp7cjSu+QijgNvgcX2qn61QE6Ceps552U5H04b4VXrew==\",\"y\":\"14989039481374367292369303874278584668623867701174068970571531818536559077115464814862613693930941393933250033676427630417324282180404456732157669953456072214\"}";
		mCityPass.setUser(new User(1, pseudonym, schnorr));
	}


	public boolean verifyMCityPass() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateExp = new Date();
		Date now = new Date();
		try{
			dateExp = dateFormat.parse(mCityPass.getExpDate());
		} catch(Exception e){
			System.out.println("MCityPass has expired");
		}
		
		return dateExp.after(now) && mCityPass.getActivation()==null;
	}
	
	public boolean verifyDatesPass(){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateExp = new Date();
		Date datePur = new Date();
		Date dateAct = new Date();
		Date limDate = new Date();
		Date now = new Date();
		try{
			dateExp = dateFormat.parse(mCityPass.getExpDate());
			datePur = dateFormat.parse(mCityPass.getPurDate());
			dateAct = dateFormat.parse(activationService.getActivationByMCityPassSn(mCityPass).getActDate());
			limDate = new Date(dateAct.getTime() + Long.parseLong(mCityPass.getLifeTime()));
			System.out.println(limDate.toString());
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return dateExp.after(now) && datePur.before(now) && dateAct.before(now) && limDate.after(now);
	}

	public MCityPass getMCityPass() {
		return mCityPass;
	}
	
	
}