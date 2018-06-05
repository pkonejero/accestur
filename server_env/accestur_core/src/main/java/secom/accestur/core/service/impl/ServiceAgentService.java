package secom.accestur.core.service.impl;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.ServiceAgentRepository;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.Provider;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.service.impl.ProviderService;
import secom.accestur.core.service.ServiceAgentInterface;

@Service("serviceAgentService")
public class ServiceAgentService implements ServiceAgentInterface{
	@Autowired
	@Qualifier("serviceAgentRepository")
	private ServiceAgentRepository serviceAgentRepository;
	
	@Autowired
	@Qualifier("providerService")
	ProviderService providerService;
	
	private ServiceAgent serviceAgent;
	
	public ServiceAgent getServiceAgent(){
		return serviceAgent;
	}

	public ServiceAgent getServiceBySn(long id){
		return serviceAgentRepository.findById(id);
	}
	
	public ServiceAgent getServiceByName(String name){
		return  serviceAgentRepository.findByName(name);
	}
	
	public void initService(long id) {
		serviceAgent  = getServiceBySn(id);
	}
	
	public void initService(long id, boolean user){
		if(id == 1){
			serviceAgent = new ServiceAgent(1,"7562473743326355864567018110410836001424017384150522432843822326218750495872621628356666960216312893563443330630054706815237209774135436738652818359027979",-1,"InfiniteReusable");
		} else if (id == 2){
			serviceAgent  = new ServiceAgent(2, "11271956907630820636612717481188292433563296253060718312957070472505869753069168283489298021850235961562196943389107169762186492856904380763074222711393669",1,"NoReusable");
		} else if (id == 3) {
			serviceAgent = new ServiceAgent(3, "12090367814846049819937291679142298051867717047999649648067805200457207554229872124277111420842525290858188812911709051753866283873787095083912367646958729",2,"TwoTimesReusable");
		} else {
			serviceAgent = new ServiceAgent(4, "8367623385260915505707698448174651762076798878204057887916028374704732010599348494031065937339322658276254733722812618605698653499314789338863151702810571",10,"TenTimesReusable");

		}
	}
	
	public void storeServices(ServiceAgent[] services){
		for (int i = 0; i < services.length; i++){
			if(serviceAgentRepository.findByName(services[i].getName())==null){
				serviceAgentRepository.save(services[i]);
			} else {
				System.out.println("This service already exists");
			}
			
		}
	}

	public List<ServiceAgent> getServicesByProvider(Provider provider){
		return null;
	}


	public List<ServiceAgent> getServicesByMCityPass(MCityPass mCityPass){
		return null;
	}


	public void initService(String service) {
		serviceAgent  = getServiceByName(service);	
	}
	
	public String getAllServices(){
		List<ServiceAgent> serviceAgentList = getAll();
		JSONArray message = new JSONArray();
		JSONObject json;
		for (int i  = 0; i < serviceAgentList.size(); i++){
			json = new JSONObject();
			json.put("id", serviceAgentList.get(i).getId());
			json.put("name", serviceAgentList.get(i).getName());
			json.put("provider", serviceAgentList.get(i).getProvider().getName());
			json.put("m", serviceAgentList.get(i).getM());
			message.put(json);
		}
		System.out.println(message.toString());
		return message.toString();
	}
	
	
	
	
	
	public List<ServiceAgent> getAll(){
		return serviceAgentRepository.findAll();
	}





}