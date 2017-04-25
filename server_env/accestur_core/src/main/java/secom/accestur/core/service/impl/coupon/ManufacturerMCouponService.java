package secom.accestur.core.service.impl.coupon;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.IssuerRepository;
import secom.accestur.core.dao.coupon.ManufacturerMCouponRepository;
import secom.accestur.core.model.Counter;
import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.RightOfUse;
import secom.accestur.core.model.coupon.ManufacturerMCoupon;
import secom.accestur.core.service.coupon.ManufacturerMCouponServiceInterface;
import secom.accestur.core.utils.Constants;

@Service("manufacturermcouponService")
public class ManufacturerMCouponService implements ManufacturerMCouponServiceInterface{	
	@Autowired
	@Qualifier("manufacturermcouponRepository")
	private ManufacturerMCouponRepository manufacturermcouponRepository;


	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;

	//Values necessary to create Coupons;
	
	private ManufacturerMCoupon manufacturer;
	
	
	private String[] paramsOfCoupon;
	private BigInteger X0;
	private BigInteger Y0;


	public void createCertificate(){
		crypto.initPrivateKey("cert/issuer/private_ISSUER.der");
		crypto.initPublicKey("cert/user/public_USER.der");	
	}

	public void newManufacturerMCoupon(String name){
		ManufacturerMCoupon i = getManufacturerMCouponByName(name);
		if(i==null){
			ManufacturerMCoupon manufacturer = new ManufacturerMCoupon();
			manufacturer.setName(name);
			System.out.println("Name: = " + manufacturer.getName());
			manufacturermcouponRepository.save(manufacturer);		
		} else {
			System.out.println("This manufacturer already exisits, it will be initialized to the existing values");
		}
	}
	
	public ManufacturerMCoupon getManufacturerMCouponByName(String name){
		return manufacturermcouponRepository.findByNameIgnoreCase(name);
	}
	
	public String[] generateUsername(String[] params){
		manufacturer = manufacturermcouponRepository.findAll().iterator().next();
		String[] message = new String[4];
		String username = crypto.decryptWithPrivateKey(params[1]);
		String password = crypto.decryptWithPrivateKey(params[2]);
		if (crypto.getValidation(username+password, params[0])){
			message[0] = username;
			message[1] = password;
			message[2] = crypto.getSignature(username+password);
			message[3] = manufacturer.getName();
			
		} else {
			message[0] = "Error";
		}
		return message;
	}
	
	public String initParamsMCoupon(Integer p, Integer q, Date EXD){
		String[] params= new String [2];
		params[0]=p.toString();
		params[1]=q.toString();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = EXD;
		System.out.println("EXD:" + dateFormat.format(date));
		params[2] = dateFormat.format(date);
		return sendInitMCouponMessage(params);
	}
	
	private String sendInitMCouponMessage(String[] params) {
		JSONObject json = new JSONObject();
		json.put("p", params[0]);
		json.put("q", params[1]);
		json.put("EXPDATE", params[2]);
		return json.toString();
	}
}