package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.Provider;
import secom.accestur.core.dao.IssuerRepository;
import secom.accestur.core.dao.ProviderRepository;
import secom.accestur.core.facade.impl.ProviderFacade;

@Controller
public class ProviderController {
	

	Issuer issuer;
	
	
	Provider provider;

	@Autowired
	@Qualifier("providerFacade")
	ProviderFacade providerFacade;

	
	@RequestMapping("/provider")
	public String welcome(Map<String, Object> model){	
		provider.setName("EMT");
		provider.setIssuer(issuer);
		
		providerFacade.getProviderService();
		
		model.put("issuer_name", issuer.getName());
		model.put("provider_name", provider.getName());
		model.put("provider_issuer", provider.getIssuer().getName() );
		return "provider";
	}

}
