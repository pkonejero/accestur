package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.dao.RightOfUseRepository;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.RightOfUse;
import secom.accestur.core.service.RightOfUseServiceInterface;

@Service("rightOfUseService")
public class RightOfUseService implements RightOfUseServiceInterface{
	@Autowired
	@Qualifier("rightOfUseRepository")
	private RightOfUseRepository rightOfUseRepository;
	
//	@Autowired
//	@Qualifier("cryptography")
//	private Cryptography crypto;
	
	private RightOfUse rightOfUse;
	
	public RightOfUseService (){}
	
	public void initRightOfUse(MCityPass mCityPass){
		rightOfUse = new RightOfUse();
		rightOfUse.setK("{\"RI\":6809635339773717346970308667044077869362570140925579651844255519556977235047125507931580356022049410167625100129141835935810717513270975457441918330369466,\"K\":\"BZkqgpbuh+bFEPfYBXsDJVgqrKUDIeBGSDRME/dl1tE=\"}");
		rightOfUse.setmCityPass(mCityPass);
		rightOfUse.setSignature("v8tjsKURUUV2esNb/3YuifXMCxZd3P+QvnJrAa6AKkwU1h3fVhEyjkeCbeXLnqoWLC7TrZt2iQ5KTIZbYZ7i9WhnZZmKW3MNZl1SDymO5nl0l+NR5gcDso8i39DeAVdegYV7U0Q2AGTx+N9R1Ym4n7h4lshm1gc5cF67wT9zyXwHy1YAXonC5sqzIjEg/+FqSrqE4DT31wm6soYcST326iDkPQeF8Dj8DpqgxAhvPCNCae8uZrjngkJ8LKf3SLxCt0bpMU9Fzp8mEmCWKPWujgYJwivm/K9CShgpzxQpb6GZyOwVCOvHhnP7WIwGvLHW5nNWvbb+CFR/KFZ9n8KlWQ==");
	}

	@Override
	public void setDelta(String k) {
	}
	
	public RightOfUse getRightOfUse(){
		return rightOfUse;
	}
	
	public void saveRightOfUse(RightOfUse rou){
		rightOfUseRepository.save(rou);
	}

	public RightOfUse getByMCityPass(MCityPass mCityPass) {
		return rightOfUseRepository.findByMCityPass(mCityPass);
	}


	public void initRightOfUseByCityPass(MCityPass mCityPass) {
		rightOfUse = getByMCityPass(mCityPass);
		
	}
	
}