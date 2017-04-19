package secom.accestur.core.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.MCityPassRepository;
import secom.accestur.core.model.Activation;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.service.MCityPassServiceInterface;

@Service("mCityPassService")
public class MCityPassService implements MCityPassServiceInterface{	
	@Autowired
	@Qualifier("mcitypassRepository")
	private MCityPassRepository mcitypassRepository;
	
	@Autowired
	@Qualifier("activationService")
	private ActivationService activationService;
	
	private MCityPass mCityPass;

	public MCityPass getMCityPassBySn(long sn){
		return mcitypassRepository.findById(sn);
	}
	
	public List<MCityPass> getMCityPassesByUser(String user){
		return null;
	}
	
	public void saveMCityPass(MCityPass mCityPass){
		mcitypassRepository.save(mCityPass);
	}
	
	public void initMCityPass(long sn){
		mCityPass = getMCityPassBySn(sn);
		System.out.println(mCityPass.toString());
	}


	public boolean verifyMCityPass() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateExp = new Date();
		Date now = new Date();
		try{
			dateExp = dateFormat.parse(mCityPass.getExpDate());
		} catch(Exception e){
			System.out.println("MCityPass has expired");
		}
		
		return dateExp.after(now) && mCityPass.getActivation()==null;
	}
	
	public boolean verifyDatesPass(){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateExp = new Date();
		Date datePur = new Date();
		Date dateAct = new Date();
		Date limDate = new Date();
		Date now = new Date();
		try{
			dateExp = dateFormat.parse(mCityPass.getExpDate());
			datePur = dateFormat.parse(mCityPass.getPurDate());
			dateAct = dateFormat.parse(activationService.getActivationByMCityPassSn(mCityPass).getActDate());
			limDate = new Date(dateAct.getTime() + Long.parseLong(mCityPass.getLifeTime()));
			System.out.println(limDate.toString());
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return dateExp.after(now) && datePur.before(now) && dateAct.before(now) && limDate.after(now);
	}

	public MCityPass getMCityPass() {
		return mCityPass;
	}
	
	
}