package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import secom.accestur.core.service.impl.IssuerService;
import secom.accestur.core.service.impl.ProviderService;
import secom.accestur.core.service.impl.UserService;

@Controller
public class HomePageController{
	@Autowired
	@Qualifier("userService")
	UserService userService;
	
	@Autowired
	@Qualifier("providerService")
	ProviderService providerService;
	
	@Autowired
	@Qualifier("issuerService")
	IssuerService issuerService;
	
	@RequestMapping("/")
	public String welcome(Map<String, Object> model){
		
		// CREATE ISSUER
		issuerService.newIssuer("Accestur");
		
		// CREATE PROVIDER 
		providerService.newProvider("EMT", issuerService.getIssuerByName("Accestur"));
		
		// CREATE SERV_1 
		
		
		
		//createServices();
		//providerService.newProvider("EMT", issuerService.getIssuerByName("Accestur"));
		providerService.getProvidersByIssuer(issuerService.getIssuerByName("ACCESTUR"));
		System.out.println(providerService.getProvidersByIssuer(issuerService.getIssuerByName("ACCESTUR")).iterator().next().getName());
		//System.out.println(issuerService.getIssuerByName("ACCESTUR").getName());
		return "welcome";
	}
	
	private void createServices(){
		String[] names = new String[4];
		int[] counters = new int[4];
		
		names[0] = "InfiniteReusable";
		counters[0] = -1;
		
		names[1] = "NoReusable";
		counters[1] = 1;
		
		names[2] = "TwoTimesReusable";
		counters[2] = 2;
		
		names[3] = "TenTimesReusable";
		counters[3] = 10;
		
		providerService.initiateProviderByName("TIB");
		issuerService.generateCertificate(providerService.authenticateProvider(names, counters));
	}
}