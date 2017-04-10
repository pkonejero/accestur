package secom.accestur.core.service;

import java.util.List;

import secom.accestur.core.model.MCityPass;

public interface MCityPassServiceInterface{
	public MCityPass getMCityPassBySn(long sn);
	
	public List<MCityPass> getMCityPassesByUser(String user);
	
	public void saveMCityPass(MCityPass mCityPass);
	
	public void initMCityPass(long sn);
	
	public MCityPass getMCityPass();
	
	public boolean verifyMCityPass();
}