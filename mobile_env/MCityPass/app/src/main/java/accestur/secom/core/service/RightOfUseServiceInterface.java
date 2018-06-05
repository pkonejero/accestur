package accestur.secom.core.service;

import accestur.secom.core.model.MCityPass;
import accestur.secom.core.model.RightOfUse;

public interface RightOfUseServiceInterface{
	public void setDelta(String k);
	
	public RightOfUse getRightOfUse();
	
	public void saveRightOfUse(RightOfUse rou);
	
	public RightOfUse getByMCityPass(MCityPass mCityPass);
	
	public void initRightOfUseByCityPass(MCityPass mCityPass);
}