package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import secom.accestur.core.service.impl.TrustedThirdPartyService;

@Controller
public class TTPController {

	@Autowired
	@Qualifier("trustedThirdPartyService")
	TrustedThirdPartyService ttpService;
	
	@RequestMapping(value = "/ttp/generatePseudonym", method=RequestMethod.POST)
	@ResponseBody
	public String getCHallenge(@RequestBody String json){
		return ttpService.generatePseudonym(json);
	}
	
	
	
	
}
