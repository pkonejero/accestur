package  accestur.secom.core.service.impl;



import com.activeandroid.query.Select;

import  accestur.secom.core.crypto.Cryptography;
import  accestur.secom.core.model.MCityPass;
import  accestur.secom.core.model.RightOfUse;
import  accestur.secom.core.service.RightOfUseServiceInterface;


public class RightOfUseService implements RightOfUseServiceInterface{

//	@Autowired
//	@Qualifier("cryptography")
//	private Cryptography crypto;

	private RightOfUse rightOfUse;

	public RightOfUseService (){}

	public void initRightOfUse(MCityPass mCityPass, String k, String RU){
		rightOfUse = new RightOfUse();
		rightOfUse.setK(k);
		rightOfUse.setmCityPass(mCityPass);
        rightOfUse.setRU(RU);
		rightOfUse.save();
		//rightOfUse.setSignature("v9ds2OPz5QoV6y3uY7mKnayhzUWsM80xEmzZp00PjKIaeMc06aY8eR+2J1cgvdJ6pjWKiSlSbt0/nLxiJ4t1qrRvgMBBvhh0l2edup2tcbZ0ezmeKncVfBznLsip/vXyJhaocQ5XcGnS9X1Cup292cGZFIndcricz5huMh1RXaeHMbDIxvJcsRouJv/p/iH6jDzYq0sTe8p3+wECZAV64/NPV4I4Tj8yOhOPPEDEM/iORTtkohUjMQh2qQPQqiP4+HlmKMFeT7IdM5HW7hZ3MkWS6CdLoLGfFZ99JWAzaDQ7yOKwHkWEeLQOpFTig/HoTMuslVhcR4bPXo2WxAv4Hw==");
	}

	@Override
	public void setDelta(String k) {
	}

	public RightOfUse getRightOfUse(){
		return rightOfUse;
	}

	@Override
	public void saveRightOfUse(RightOfUse rou) {

	}


	public RightOfUse getByMCityPass(MCityPass mCityPass) {
		return new Select().from(RightOfUse.class)
				.where("mCityPass = ?" , mCityPass.getId())
				.executeSingle();
	}


	public void initRightOfUseByCityPass(MCityPass mCityPass) {
		rightOfUse = getByMCityPass(mCityPass);

	}

}
