package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.Provider;
import secom.accestur.core.service.impl.ProviderService;
import secom.accestur.core.facade.impl.ProviderFacade;

@Controller
public class ProviderController {
	

	Issuer issuer;
	
	
	Provider provider;

	@Autowired
	@Qualifier("providerFacade")
	ProviderFacade providerFacade;

	@Autowired
	@Qualifier("providerService")
	ProviderService providerService;
	
	
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
	
	
	@RequestMapping(value = "/provider/verifyPass", method=RequestMethod.POST)
	@ResponseBody
	public String verifyPass(@RequestBody String json){
		return providerService.verifyPass(json);
	}
	
	@RequestMapping(value = "/provider/verifyPass2", method=RequestMethod.POST)
	@ResponseBody
	public String verifyPass2(@RequestBody String json){
		return providerService.verifyPass2(json);
	}
	
	@RequestMapping(value = "/provider/verifyProof", method=RequestMethod.POST)
	@ResponseBody
	public String verifyProof(@RequestBody String json){
		return providerService.verifyProof(json);
	}
	
	@RequestMapping(value = "/provider/verifyMPass", method=RequestMethod.POST)
	@ResponseBody
	public String verifyMPass(@RequestBody String json){
		return providerService.verifyMPass(json);
	}
	
	@RequestMapping(value = "/provider/verifyMPass2", method=RequestMethod.POST)
	@ResponseBody
	public String verifyMPass2(@RequestBody String json){
		return providerService.verifyMPass2(json);
	}
	
	@RequestMapping(value = "/provider/verifyMProof", method=RequestMethod.POST)
	@ResponseBody
	public String verifyMProof(@RequestBody String json){
		return providerService.verifyMProof(json);
	}
	
	@RequestMapping(value = "/provider/verifyInfinitePass", method=RequestMethod.POST)
	@ResponseBody
	public String verifyInfPass(@RequestBody String json){
		return providerService.verifyInfinitePass(json);
	}
	
	@RequestMapping(value = "/provider/verifyInfinitePass2", method=RequestMethod.POST)
	@ResponseBody
	public String verifyInfPass2(@RequestBody String json){
		return providerService.verifyInfinitePass2(json);
	}
	
	@RequestMapping(value = "/provider/verifyInfiniteProof", method=RequestMethod.POST)
	@ResponseBody
	public String verifyInfProof(@RequestBody String json){
		return providerService.verifyInfiniteProof(json);
	}

}
