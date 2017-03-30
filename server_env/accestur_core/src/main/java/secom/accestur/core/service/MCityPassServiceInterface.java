package secom.accestur.core.service;

import java.util.List;

import secom.accestur.core.model.MCityPass;

public interface MCityPassServiceInterface{
	
	public MCityPass getMCityPassBySn(long sn);
	
	public List<MCityPass> getMCityPassesByUser(String user);
}