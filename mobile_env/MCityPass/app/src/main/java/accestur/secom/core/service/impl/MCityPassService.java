package  accestur.secom.core.service.impl;

import com.activeandroid.query.Select;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import  accestur.secom.core.model.Activation;
import  accestur.secom.core.model.MCityPass;
import  accestur.secom.core.model.User;
import  accestur.secom.core.service.MCityPassServiceInterface;


public class MCityPassService implements MCityPassServiceInterface{

	private ActivationService activationService;

	private MCityPass mCityPass;


	@Override
	public MCityPass getMCityPassBySn(long sn) {
		return null;
	}

	public List<MCityPass> getMCityPassesByUser(String user){
		return null;
	}

	@Override
	public void saveMCityPass(MCityPass mCityPass) {

	}


	public void initMCityPass(long sn){
		mCityPass = loadMcitypass(sn);
		System.out.println(mCityPass.toString());
	}

	public void initMCityPass(){
		mCityPass = new MCityPass();
		//mCityPass.setId(new Long(1));
		mCityPass.setHu("4228103472873156375544411390523624082868203727534051249333853717079942409671671511453170699872717389638804717693350326414639482762948883779536460372438517343");
		mCityPass.setCategory("Adult");
		mCityPass.setDelta("{\"signature\":\"v9ds2OPz5QoV6y3uY7mKnayhzUWsM80xEmzZp00PjKIaeMc06aY8eR+2J1cgvdJ6pjWKiSlSbt0/nLxiJ4t1qrRvgMBBvhh0l2edup2tcbZ0ezmeKncVfBznLsip/vXyJhaocQ5XcGnS9X1Cup292cGZFIndcricz5huMh1RXaeHMbDIxvJcsRouJv/p/iH6jDzYq0sTe8p3+wECZAV64/NPV4I4Tj8yOhOPPEDEM/iORTtkohUjMQh2qQPQqiP4+HlmKMFeT7IdM5HW7hZ3MkWS6CdLoLGfFZ99JWAzaDQ7yOKwHkWEeLQOpFTig/HoTMuslVhcR4bPXo2WxAv4Hw==\",\"k\":\"vegC0+/Fi4Q4OQgO/qlda8ica34nJghne/O31zrRarJeCOyYmGxTbUp5xvquT3JhvUbrQndk61rPXXxKb8zRzN9G7L0rXDuGavCcuc1caG4THQTV5qV92FStAsSKXHertxcGY3ohSsJm8TLPXCirfSwoPVtZjr76ZsBQGqP4q1DtMeHz9FGDZezlJi1njvlbj4HJRMkhj1SBhFKSt1SIAB/Sdpg2f5yNTMMkXT5NqR6XyC74wQ4EdcuuzJkSuTQX0I0fuBm/4muWtwM6dm+Gq3yh1HbalYn+/EKo0RLX3eARv77rtFsoQrXOYEfMdTkz+onDuLX5SXbOqqjorJBHTg==\"}");
		mCityPass.setExpDate("08/06/2017");
		mCityPass.setHRI("/cTIH828yrztrDQuGNINiivgBAiO0Ugy20xdimlTJHc=");
		mCityPass.setHRU("95VNTtuAMvKu8yy0wAl7+CTrtsupTvbdfCZhk+zXC6k=");
		mCityPass.setLifeTime("604800000");
		mCityPass.setPurDate("24/04/2017");
		mCityPass.setSignature("aoiiyTY++oCXzStN4uDHooQXLezTbgdMeYWiu4nCRrEDiHrPBaatJustK6eeKdRknjQCyzZNF3WwdkZCnZj524SDTG+hO1ixmWyy+B0VFIFbp9EheT2wohheNZf3jIc8Z7BfgeHPrQ7lLqcCh8OQQVLxWz9R1XwWySTFhD7ioYSp3N437OK0GoiaA4ei3uPDmt/joXu0jec+XkOg6MFBD6R7B/tn9wqfHEc7CCLjL2JOzkG+CzIwbB3Y9xZI1OWAnZV/FPC/IEHFbIkHzjrHMBZNFN0eumpaSZGD97q+FtPGkjuWJeqdnjJ1Je1B7dR9EsZxoDsEYHbYrtlUidPCHQ==");
		mCityPass.setTermsAndConditions("Lorem ipsum dolor sit amet.");
		String schnorr = "{\"p\":\"5467897339840518162786237130690140681563426429253886144000446704182355497141936161065307037197592907962860058196217365910961519527018301194391986769229751223\",\"q\":\"8064745338997814399389730281253894810565525706864138855457885994369255895489581358503402709730962991095663802649288150311152683668168585832436558656681049\",\"g\":\"4646952560476358109126473448548917623796778482036695497649556543407949097775314878774996625110527933758294221262884258522370914327999672597655752894601109781\",\"x\":\"7733025593472488389256895258226197332268583878313092286696688930414899173839631136605671753229452825393617130762207267907316015448450850151189405257072479\",\"y\":\"4097784045753520584649660125040472632352443691185929683458123089553340188869221441314047620570808410670573788399629690033621641717480345580209198464560804215\"}";
		String pseudonym = "{\"signature\":\"GqbGQyZIuUC9ZTY+XQWiCdC8g+1Otl0kIp51dMc2FGOqgQ+lgFGHfjNGK76GbRbIROtOXYpUWlO/X0OoKg7Z5/ktYSuLw3wGqQ0mlR83J/roDTpG3TTwwY2dp+I30gXSq5UlIEE+O5IEHyxVscmvYPCuKKX4qCVRVvVSWkoLNGzpXxH0byHvaXbYU4RKaBLw0XZP/2YxCyfR1mAZu2p1D6yqzZxpgZ8tS9Wp61QRntcLTWUHe0T+Zaj4On5Ne5dCshRmY6wsnQd0lHX70ELihkXHFFL0296gA6ui6202ZIOyOg3Ry/8Iw/L/hvR4erIHQpzqGKOVVTl1/cBmaj/ZuQ==\",\"y\":\"4097784045753520584649660125040472632352443691185929683458123089553340188869221441314047620570808410670573788399629690033621641717480345580209198464560804215\"}";

		mCityPass.setUser(new User(1, pseudonym, schnorr));
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


	public MCityPass loadMcitypass(long id){
		//MCityPass mPass =  new Select().from(MCityPass.class).orderBy("RANDOM()").executeSingle();
		MCityPass mPass = MCityPass.load(MCityPass.class, id);
		System.out.println("MCityPass: " + mPass);
		return mPass;

	}

	public List<MCityPass> getAllMCityPass(){
		return new Select().from(MCityPass.class).execute();
	}

}
