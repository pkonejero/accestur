package secom.accestur.core.service;

import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.RightOfUse;

public interface RightOfUseServiceInterface{
	public void setDelta(String k);
	
	public RightOfUse getRightOfUse();
	
	public void saveRightOfUse(RightOfUse rou);
	
	public RightOfUse getByMCityPass(MCityPass mCityPass);
	
	public void initRightOfUseByCityPass(MCityPass mCityPass);
}