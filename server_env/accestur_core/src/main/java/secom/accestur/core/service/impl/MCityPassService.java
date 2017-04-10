package secom.accestur.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.MCityPassRepository;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.service.MCityPassServiceInterface;

@Service("mCityPassService")
public class MCityPassService implements MCityPassServiceInterface{	
	@Autowired
	@Qualifier("mcitypassRepository")
	private MCityPassRepository mcitypassRepository;

	public MCityPass getMCityPassBySn(long sn){
		return mcitypassRepository.findById(sn);
	}
	
	public List<MCityPass> getMCityPassesByUser(String user){
		return null;
	}
	
	public void saveMCityPass(MCityPass mCityPass){
		mcitypassRepository.save(mCityPass);
	}
}