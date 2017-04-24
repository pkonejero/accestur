package  accestur.secom.core.service.impl;



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

	public void initRightOfUse(MCityPass mCityPass){
		rightOfUse = new RightOfUse();
		rightOfUse.setK("{\"RI\":5696207525060959906622982182912848746497417533364212216523313814345264242737855210825385633342317042388516960446253758268850030641584038605738802618984293,\"K\":\"YLdBDB4f4JfNuMT07oCnRAPT17Oa7aUUAlNfiupj0DM=\"}");
		rightOfUse.setmCityPass(mCityPass);
		rightOfUse.setSignature("v9ds2OPz5QoV6y3uY7mKnayhzUWsM80xEmzZp00PjKIaeMc06aY8eR+2J1cgvdJ6pjWKiSlSbt0/nLxiJ4t1qrRvgMBBvhh0l2edup2tcbZ0ezmeKncVfBznLsip/vXyJhaocQ5XcGnS9X1Cup292cGZFIndcricz5huMh1RXaeHMbDIxvJcsRouJv/p/iH6jDzYq0sTe8p3+wECZAV64/NPV4I4Tj8yOhOPPEDEM/iORTtkohUjMQh2qQPQqiP4+HlmKMFeT7IdM5HW7hZ3MkWS6CdLoLGfFZ99JWAzaDQ7yOKwHkWEeLQOpFTig/HoTMuslVhcR4bPXo2WxAv4Hw==");
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

	@Override
	public RightOfUse getByMCityPass(MCityPass mCityPass) {
		return null;
	}


	public void initRightOfUseByCityPass(MCityPass mCityPass) {
		rightOfUse = getByMCityPass(mCityPass);

	}

}
