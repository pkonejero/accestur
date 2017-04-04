package secom.accestur.core.service;

import secom.accestur.core.model.MCityPass;

import java.util.List;

public interface MCityPassServiceInterface{
	public MCityPass getMCityPassBySn(long sn);
	
	public List<MCityPass> getMCityPassesByUser(String user);
}