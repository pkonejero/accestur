package secom.accestur.front.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import secom.accestur.core.model.Issuer;
import secom.accestur.core.model.Provider;
import secom.accestur.core.service.impl.IssuerService;


@Controller
public class IssuerController {
	

	Issuer issuer;
	
	
	Provider provider;

	@Autowired
	@Qualifier("issuerService")
	IssuerService issuerService;
	
	@RequestMapping(value = "/issuer/getChallenge", method=RequestMethod.POST)
	@ResponseBody
	public String getChallenge(@RequestBody String json){
		return issuerService.getChallenge(json);
	}
	
	@RequestMapping(value = "/issuer/getPASS", method=RequestMethod.POST)
	@ResponseBody
	public String getPASS(@RequestBody String json){
		return issuerService.getPASS(json);
	}
	
	@RequestMapping(value = "/issuer/verifyTicket", method=RequestMethod.POST)
	@ResponseBody
	public String verifyTicket(@RequestBody String json){
		return issuerService.verifyTicket(json);
	}
	
	
	
	
	
}