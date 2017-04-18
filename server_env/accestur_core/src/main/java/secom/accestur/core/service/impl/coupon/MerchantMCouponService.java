package secom.accestur.core.service.impl.coupon;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.coupon.MerchantMCouponRepository;
import secom.accestur.core.model.coupon.IssuerMCoupon;
import secom.accestur.core.model.coupon.MerchantMCoupon;
import secom.accestur.core.service.coupon.MerchantMCouponServiceInterface;
import secom.accestur.core.utils.Constants;

@Service("merchantmcouponService")
public class MerchantMCouponService implements MerchantMCouponServiceInterface{
	@Autowired
	@Qualifier("merchantmcouponRepository")
	private MerchantMCouponRepository merchantmcouponRepository;
	
	@Autowired
	@Qualifier("schnorr")
	private Schnorr schnorr;

	@Autowired
	@Qualifier("cryptography")
	private Cryptography crypto;

	public void newMerchantMCoupon(String name, IssuerMCoupon issuer){
		MerchantMCoupon m = getMerchantMCouponByName(name);
		if(m == null){
			MerchantMCoupon merchant = new MerchantMCoupon();
			merchant.setName(name);
			merchant.setIssuerMCoupon(issuer);
			merchantmcouponRepository.save(merchant);
			System.out.println("Name: = " + merchant.getName());
		} else {
			System.out.println("This merchant already exisits, it will be initialized to the existing values");
		}
		
	}
		
	public MerchantMCoupon getMerchantMCouponByName(String name){
		return merchantmcouponRepository.findByNameIgnoreCase(name);
	}
	
	public List<MerchantMCoupon> getMerchantMCouponByIssuerMCoupon(IssuerMCoupon issuer){
		return merchantmcouponRepository.findByIssuer(issuer);
	}

	public String verifyMCoupon(){
		return null;
	}

	public String[] authenticateMerchantMCoupon(String[] params){
		return null;
	}

	public String[] verifyMCoupon(String[] params){
		return null;
	}

	public void createCertificate(){
		crypto.initPrivateKey("private_ISSUER.der");
		crypto.initPublicKey("public_ISSUER.der");		
	}
	
}