package secom.accestur.core.service.impl.coupon;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.coupon.IssuerMCouponRepository;
import secom.accestur.core.model.coupon.CounterMCoupon;
import secom.accestur.core.model.coupon.IssuerMCoupon;
import secom.accestur.core.model.coupon.MCoupon;
import secom.accestur.core.model.coupon.ManufacturerMCoupon;
import secom.accestur.core.model.coupon.UserMCoupon;
import secom.accestur.core.service.coupon.IssuerMCouponServiceInterface;

@Service("issuermcouponService")
public class IssuerMCouponService implements IssuerMCouponServiceInterface{	
	@Autowired
	@Qualifier("issuermcouponRepository")
	private IssuerMCouponRepository issuermcouponRepository;


	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;
	
	@Autowired
	@Qualifier("mcouponService")
	private MCouponService mcouponService;
	
	@Autowired
	@Qualifier("usermcouponService")
	private UserMCouponService usermcouponService;
	
	@Autowired
	@Qualifier("countermcouponService")
	private CounterMCouponService countermcouponService;
	
	@Autowired
	@Qualifier("merchantmcouponService")
	private MerchantMCouponService merchantmcouponService;

	//Values necessary to create Coupons;
	private String MC;

	public IssuerMCoupon getIssuerMCouponByName(String name){
		return issuermcouponRepository.findByNameIgnoreCase(name);
	}

	public void createCertificate(){
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		crypto.initPublicKey("cert/user/public_USER.der");	
	}
	
	public void newIssuerMCoupon(String name, ManufacturerMCoupon manufacturer){
		IssuerMCoupon i = getIssuerMCouponByName(name);
		if(i==null){
			IssuerMCoupon issuer = new IssuerMCoupon();
			issuer.setName(name);
			issuer.setManufacturerMCoupon(manufacturer);
			System.out.println("Name: = " + issuer.getName());
			issuermcouponRepository.save(issuer);		
		} else {
			System.out.println("This issuer already exisits, it will be initialized to the existing values");
		}
	}

	public static String getYu(String json){
		JSONObject jsonObject = new JSONObject(json);
		return jsonObject.getString("y");
	}

	
	///////////////////////////////////////////////////////////////////////
	/////////////////// PURCHASE COUPON///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	//Purchase 3 Issuer recieves information Coupon and sends to manufacturer, generating SN
	
	public String getInitMCouponMessage(String input) {
		createCertificate(); //INIT PUBLIC KEY USER FOR THE VALIDATION OF THE SIGNATURE AND INIT PRIVATE KEY OF ISSUER FOR MAKING THE SIGNATURE AGAIN.
		//String[] paramsJson = solveUserMCouponParams(json);
		
		JSONObject json = new JSONObject(input);

		
		String[] params = new String[15];
		
		params[10] = json.getString("id");
	
		params[0] = json.getString("username"); //Username
		
		System.out.println("USERNAME ARRIBAT:"+params[0]);
		
		params[1] = json.getString("Xo"); //Xo
		
		System.out.println("Xo ARRIBAT:"+params[1]);
		
		params[2] = json.getString("Yo"); //Yo
		
		System.out.println("Yo ARRIBAT:"+params[2]);
		
		params[3] = json.getString("p"); //P
		
		System.out.println("p ARRIBAT:"+params[3]);
		
		params[4] = json.getString("q"); //Q
		
		System.out.println("q ARRIBAT:"+params[4]);
		
		params[5]=json.getString("EXD"); //EXD
		
		System.out.println("EXPDATE ARRIBAT:"+params[5]);
		
		params[6]= json.getString("merchant");
		
		System.out.println("MERCHANT ARRIBAT:"+params[6]);
		
		params[8]=params[6]; //ID Merchant
		
		params[7]=json.getString("signature");
		
		System.out.println("Siganture ARRIBAT:"+params[7]);
		
		//Comparacio de Dates
		Date today = new Date();
		
		
		/*DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String today2 = dateFormat.format(today);
		boolean expired = false;
		try {
			Date date1 = dateFormat.parse(params[5]);
			Date today1 = dateFormat.parse(today2);
			System.out.println("DATE1 IS:"+date1.toString()+" TODAY'S DATE:"+today.toString());
			if (date1.compareTo(today1) < 0) {
					expired = true;
		            System.out.println("Date1 is before Today, so it's expired");
		      }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
  
        //if(expired == false){
       
        
		
        //Validació de Firmes
		if (crypto.getValidation(params[0]+params[1]+params[2]+params[3]+params[4]+params[5], params[7])){//+params[5]
			
			System.out.println("S'HA VALIDAD LA FIRMA DE USUARI AL ISSUER");
		
		MC=params[1]+params[2]+params[3]+params[4]+params[5];//+params[5]
		
		//Creating SN
		Random rand = new Random();
		Integer sn = rand.nextInt(500) + rand.nextInt();
		params[6]=sn.toString();
		
		
			
			params[9]=crypto.getSignature(params[6]);
			
		
		return sendIssuerToManufacturerPurchase(params);
		}else{
			System.out.println("HA FALLAT LA VERIFICACIO!!!!");
			return "FAILED";
		}
        /*}else{
        	System.out.println("DATEEXPIRED");
        	return "DATEEXPIRED";
        }*/
	}
	
	private String[] solveUserMCouponParams (String message){
		JSONObject json = new JSONObject(message);
		String[] params = new String[10];
		params[0] = json.getString("username");
		params[1] = json.getString("Xo");
		params[2] = json.getString("Yo");
		params[3] = json.getString("p");
		params[4] = json.getString("q");
		params[5] = json.getString("EXPDATE");
		params[6] = json.getString("signature");
		params[7] = json.getString("merchant");
		return params;
	}
	
	private String sendIssuerToManufacturerPurchase(String[] params) {
		JSONObject json = new JSONObject();
		json.put("username", params[0]);
		json.put("Xo", params[1]);
		json.put("Yo", params[2]);
		json.put("p", params[3]);
		json.put("q", params[4]);
		json.put("EXD", params[5]);
		json.put("sn", params[6]);
		json.put("signatureIssuer", params[9]);
		json.put("merchant", params[8]);
		json.put("signatureUser", params[7]);
		json.put("id", params[10]);
		return json.toString();
	}
	
	public void errorIssuing3(){
		System.out.println("Error Sended By The Manufacturer Validating Digital Signature Of the User or Issuer");
	}
	
	//Purchase 5 Issuer Stores MCoupon
public String getMCouponGeneratedByManufacturer(String json) {
	
	crypto.initPublicKey("cert/issuer/public_ISSUER.der"); //SHOULD BE MANUFACTURER PUBLIC KEY
	
	String[] paramsJson = solveFinishPurchaseManufacturer(json);
	
	String[] params = new String[15];
	Integer id = new Integer(paramsJson[10]);
	Integer sn = new Integer(paramsJson[2]);
	
	System.out.println("AQUEST SN ES NULL="+sn);
	
	MCoupon coupon = mcouponService.getMCouponBySn(id) ;
	
	System.out.println("AQUEST CUPON ES NULL="+coupon.toString());
	
	CounterMCoupon counter = new CounterMCoupon(coupon.getP(),coupon);
	
	if (crypto.getValidation(MC, paramsJson[6])){
		
	crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		
	coupon.setXo(paramsJson[0]);
	coupon.setYo(paramsJson[1]);
	
	Integer p = new Integer(paramsJson[3]);
	Integer q = new Integer(paramsJson[4]);
	
	//coupon.setP(p);
	//coupon.setQ(q);
	
	
	coupon.setSn(sn);
	
	UserMCoupon user = usermcouponService.getUserMCouponByUsername(paramsJson[5]);
	
	coupon.setUser(user);
	
	/////FALTA LA COMPROVACIÓ DE DATES.//////
	
	//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	//try {
	//Date date = dateFormat.parse(paramsJson[8]);
	//java.sql.Date insert = new java.sql.Date(date.getTime());
	//System.out.println("MERDA DE DATA="+insert);
	//coupon.setExpDate(insert);
	//java.sql.Date sqlDate = new java.sql.Date();
	//} catch (ParseException e) {
		// TODO Auto-generated catch block
	//	e.printStackTrace();
	//}
	//System.out.println("DATE=="+ dateFormat.format(paramsJson[8]));
	
	//coupon.setMerchant(merchantmcouponService.getMerchantMCouponByName(paramsJson[7]));
	
	params[0] = crypto.getSignature(MC+sn.toString());
	
	params[1] = paramsJson[0];
	
	params[2] = paramsJson[1];
	
	params[3] = paramsJson[2];
	
	params[4] = paramsJson[3];
	
	params[5] = paramsJson[4];
	
	params[6] = paramsJson [5];
	
	mcouponService.saveMCoupon(coupon);
	countermcouponService.saveCounterMCoupon(counter);
	
		//Missing Issuer Signature.
		return sendIssuerToUserPurchase(params);
	}else{
		return "FAILED SIGNATURE";
	}
	}

private String[] solveFinishPurchaseManufacturer (String message){
	JSONObject json = new JSONObject(message);
	String[] params = new String[15];
	params[0] = json.getString("xo");
	params[1] = json.getString("yo");
	params[2] = json.getString("sn");
	params[3] = json.getString("p");
	params[4] = json.getString("q");
	params[5] = json.getString("username");
	params[6] = json.getString("signature");
	params[7] = json.getString("merchant");
	//params[8] = json.getString("EXD");
	params[10] = json.getString("id");
	return params;
}

private String sendIssuerToUserPurchase(String[] params) {
	JSONObject json = new JSONObject();
	json.put("signature", params[0]);
	json.put("sn", params[3]);
	return json.toString();
}

public void errorIssuing5(){
	System.out.println("Error Sended By The User Validating MC and Digital Signature");
}

///////////////////////////////////////////////////////////////////////
/////////////////// REDEEM COUPON///////////////////////////////////////
///////////////////////////////////////////////////////////////////////

//REDEEM 4 ISSUER MAKES VERIFICATION OF THE REDEEM COUPON.

public String redeemingMCoupon(String json) {
	
	crypto.initPublicKey("cert/issuer/public_ISSUER.der");
	MCoupon coupon = new MCoupon();
	String[] paramsJson = solveRedeemMCouponParams(json);
	//Validate Signature of the merchant
	
	if (crypto.getValidation(paramsJson[0]+paramsJson[1]+paramsJson[2]+paramsJson[3]+paramsJson[4]+paramsJson[5], paramsJson[7])){//+paramsJson[5]
		
	System.out.println("NO ES SA PRIMERA CONDICIO");
	createCertificate();
	
	String[] params = new String[16];

	params[0] = paramsJson[8];//Id Merchant
	
	//Validate Signature of the User
	if (crypto.getValidation(paramsJson[0]+paramsJson[1]+paramsJson[2]+paramsJson[3]+paramsJson[4], paramsJson[5])){
	
	System.out.println("NO ES SA SEGONA CONDICIO");
		
	params[1] = paramsJson[0];//Rid
	
	params[2] = paramsJson[1];//Label
	
	params[3] = crypto.decryptWithPrivateKey(paramsJson[2]); //Xi Decrypted //NEEDED FOR THE PHASE OF CLEARING,ENCRYPT WITH PUBLIC KEY OF THE MANUFACTURER
	
	params[4] = crypto.decryptWithPrivateKey(paramsJson[3]); //IndexHash Decrypted //NEEDED FOR THE PHASE OF CLEARING,ENCRYPT WITH PUBLIC KEY OF THE MANUFACTURER
	
	params[5] = crypto.decryptWithPrivateKey(paramsJson[4]); //Sn Decrypted //NEEDED FOR THE PHASE OF CLEARING,ENCRYPT WITH PUBLIC KEY OF THE MANUFACTURER

	Integer sn = new Integer(params[5]);
	
	System.out.println("AQUEST ES SN:"+sn);
	
	coupon = mcouponService.getMCouponBySn(sn);
	
	System.out.println("AQUEST ES EL COUPON"+coupon.toString());
	
	System.out.println("AQUEST ES LA P DEL COUPON:"+coupon.getP());
	
	System.out.println("AQUEST ES LA Q DEL COUPON:"+coupon.getQ());
	
	//Verification of Rid
	
	String nXo = coupon.getXo();
	
	String nYo = coupon.getYo();
	
	System.out.println("AQUESTA ES LA XO DEL COUPON:"+nXo);
	
	System.out.println("AQUESTA ES LA XO DEL COUPON:"+nYo);
	
	String nRid = Cryptography.hash(params[0]+paramsJson[6]+nXo+params[3]);
	
	String nRid2 = Cryptography.hash(params[0]+paramsJson[6]+nYo+params[3]);
	
	
	String[] xVerification = new String[coupon.getP()+2];
	
	String[] xVerification2 = new String[coupon.getQ()+2];
	
	
	Integer indexHash = new Integer(params[4]);
	
	xVerification[indexHash]=params[3];
	
	xVerification2[indexHash]=params[3];
	
	Integer number_hash = coupon.getP()+1;
	
	Integer number_hash2 = coupon.getQ()+1;
	
	for (int i =indexHash+1; i <=number_hash;i++){
		xVerification[i]=Cryptography.hash(xVerification[i-1]);
	}
	
	for (int i =indexHash+1; i <=number_hash2;i++){
		xVerification2[i]=Cryptography.hash(xVerification2[i-1]);
	}
	
	System.out.println("AQUESTA ES LA XVERIFICATION:"+xVerification[number_hash]);
	
	System.out.println("AQUESTA ES LA XVERIFICATION2:"+xVerification[number_hash]);
	
	CounterMCoupon counter = coupon.getCounter();
	
	System.out.println("AQUEST ES EL COUNTER:"+counter.toString());
	
	Integer ncounter = counter.getCounterMCoupon();
	
	System.out.println("AQUEST ES INDEX HASH:"+indexHash);
	
	System.out.println("AQUEST ES NCOUNTER:"+ncounter);
	
	System.out.println("AQUEST ES EL COMPTADOR:"+ncounter.toString());
	
	if (indexHash.equals(ncounter)&&(indexHash<=coupon.getP()||indexHash<=coupon.getQ())){
		
	System.out.println("NUMBER OF THE COUNTER IS CORRECT");
	
	System.out.println("XVERIFICATION="+xVerification[number_hash]);
	
	System.out.println("NXO="+nXo);
	
	
	if(xVerification[number_hash].equals(nXo)||xVerification2[number_hash2].equals(nYo)){
		System.out.println("THE HASH IS CORRECT");
	
		System.out.println("AQUEST ES EL RID NOU:"+nRid);
		System.out.println("AQUEST ES EL RID ANTIC"+params[1]);
	if (nRid.equals(params[1])){
	
		
	System.out.println("AQUEST ES EL RID NOU:"+nRid);
		
	params[6] = nRid; //New Rid (Validation Signature)
		
	params[7]=crypto.getSignature(nRid); //Signature of issuer
	
	System.out.println("FIRMA ENVIADA:"+params[7]);
	
	//PARAMETERS NEEDED FOR THE CLEARING PHASE
	crypto.initPublicKey("cert/issuer/public_ISSUER.der"); //IT SHOULD BE MANUFACTURER PUK
	
	params[8] = crypto.encryptWithPublicKey(params[3]);//Xi encrypted for the manufacturer on claring phase.
	
	params[9] = crypto.encryptWithPublicKey(params[4]);//IndexHash encrypted for the manufacturer on claring phase.
	
	params[10] = crypto.encryptWithPublicKey(params[5]);//SN encrypted for the manufacturer on claring phase.
	
	return sendIssuerToMerchantRedeem(params);
	}else{
		return "FAILED";//return "THE HASH IS INCORRECT";
	}
	}else{
		return "FAILED";//return "NUMBER OF THE COUNTER IS INCORRECT";
	}
	}else{
		return "FAILED";//return "Failed Rid Verification";
	}
	}else{
		return "FAILED";//return "Failed Signature USER";
	}
	}else{
		return "FAILED";//return "Failed Signature MERCHANT";
	}

}

private String sendIssuerToMerchantRedeem(String[] params) {
	JSONObject json = new JSONObject();
	json.put("signature", params[7]);
	json.put("rid", params[1]);
	json.put("xi", params[8]);
	json.put("indexhash", params[9]);
	json.put("sn", params[10]);
	return json.toString();
}

private String[] solveRedeemMCouponParams (String message){
	JSONObject json = new JSONObject(message);
	String[] params = new String[9];
	params[0] = json.getString("rid");
	params[1] = json.getString("label");
	params[2] = json.getString("xi");
	params[3] = json.getString("indexhash");
	params[4] = json.getString("sn");
	params[5] = json.getString("signaturecustomer");
	params[6] = json.getString("username");
	params[7] = json.getString("signaturemerchant");
	params[8] = json.getString("idmerchant");
	return params;
}

//ISSUER REB ERROR DEL MERCHANT

public void errorRedeem4(){
	System.out.println("Error Sended By The MERCHANT Validating RID,HASH ISSUER");
}


	@Override
	public String getChallenge(String params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPASS(String params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] verifyTicket(String[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean arrayGeneration() {
		// TODO Auto-generated method stub
		return false;
	}

}