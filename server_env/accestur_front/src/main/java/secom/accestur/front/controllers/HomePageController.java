package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import secom.accestur.core.service.impl.IssuerService;
import secom.accestur.core.service.impl.ProviderService;
import secom.accestur.core.service.impl.TrustedThirdPartyService;
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

	@Autowired
	@Qualifier("trustedThirdPartyService")
	TrustedThirdPartyService ttpService;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model){
//		Init();
//		generateUser();
//		passPurchase();
//		passActivation();
		passVerification();
		return "welcome";
	}

	private void Init(){
		issuerService.newIssuer("Accestur");
		providerService.newProvider("TIB", issuerService.getIssuerByName("Accestur"));
		createServices("Accestur");
	}

	private void createServices(String providerName){
		String[] serviceName = new String[4];
		int[] counters = new int[4];

		serviceName[0] = "InfiniteReusable";
		counters[0] = -1;

		serviceName[1] = "NoReusable";
		counters[1] = 1;

		serviceName[2] = "TwoTimesReusable";
		counters[2] = 2;

		serviceName[3] = "TenTimesReusable";
		counters[3] = 10;

		issuerService.generateCertificate(providerService.authenticateProvider(serviceName, counters, providerName));
	}

	private void generateUser(){
		userService.createCertificate();
		ttpService.createCertificate();
		System.out.println(userService.verifyPseudonym(ttpService.generatePseudonym(userService.authenticateUser())));
	}

	private void passPurchase(){
		userService.getUser();
		//userService.createCertificate();
		issuerService.createCertificate();
		String[] names = new String[4];
		names[0] = "InfiniteReusable";
		names[1] = "NoReusable";
		names[2] = "TwoTimesReusable";
		names[3] = "TenTimesReusable";
		userService.receivePass(issuerService.getPASS(userService.solveChallenge(issuerService.getChallenge(userService.getService()), names)));
	}
	
	private void passActivation(){
		userService.initUser();
		issuerService.createCertificate();
		userService.getVerifyTicketConfirmation(issuerService.verifyTicket(userService.showPass(1)));
	}
	
	private void passVerification(){
		userService.initUser();
		userService.getValidationConfirmation(providerService.verifyProof(userService.showProof(providerService.verifyPass2(userService.solveVerifyChallenge(providerService.verifyPass(userService.showTicket(1, 2)))))));
	}
}