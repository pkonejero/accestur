package  accestur.secom.core.service.impl;

import java.util.List;


import  accestur.secom.core.model.MCityPass;
import  accestur.secom.core.model.Provider;
import  accestur.secom.core.model.ServiceAgent;
import  accestur.secom.core.service.ServiceAgentInterface;

public class ServiceAgentService implements ServiceAgentInterface{


	private ServiceAgent serviceAgent;

	public ServiceAgent getServiceAgent(){
		return serviceAgent;
	}


	public void initService(long id) {
		serviceAgent  = getServiceBySn(id);
	}

	public void initService(long id, boolean user){
		if(id == 1){
			serviceAgent = new ServiceAgent(1,"11071517341259957021400102350374716943843216445567934842131423223624389226020862482132369792224170331987537244451641396126431797530031570316025873406659783",-1,"InfiniteReusable");
		} else if (id == 2){
			serviceAgent  = new ServiceAgent(2, "10451944853989757415347339212599340338256628776040194990105299983846722323505994584277331974426523017142034761677659405953861573121656854047441972074374309",1,"NoReusable");
		} else if (id == 3) {
			serviceAgent = new ServiceAgent(3, "6872697559039177648737207692817225887047535462069465414807745696443168570422818945243679193891839190988514728109431401855857751123771201744562700729777507",2,"TwoTimesReusable");
		} else {
			serviceAgent = new ServiceAgent(4, "11392979547410824194480369401914165105210309527281143097757756290619102528318470598155402289947925149239927795442262168690569375491515431066768584688828929",10,"TenTimesReusable");

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

	@Override
	public ServiceAgent getServiceByName(String name) {
		return null;
	}

	@Override
	public ServiceAgent getServiceBySn(long id) {
		return null;
	}


}
