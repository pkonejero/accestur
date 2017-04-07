package secom.accestur.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

import secom.accestur.core.dao.MCityPassRepository;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.service.MCityPassServiceInterface;

@Service("mCityPassService")
public class MCityPassService implements MCityPassServiceInterface{	
	@Autowired
	@Qualifier("mcitypassRepository")
	private MCityPassRepository mcitypassRepository;
	
	private MCityPass mCityPass;

	public MCityPass getMCityPassBySn(long sn){
		return null;
	}
	
	public List<MCityPass> getMCityPassesByUser(String user){
		return null;
	}
}