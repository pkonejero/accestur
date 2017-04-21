package secom.accestur.front.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import secom.accestur.core.service.impl.SchnorrService;

@Controller
public class Init {
	@Autowired
	@Qualifier("schnorrService")
	SchnorrService schnorrService;

	@RequestMapping(value="/initschnorr", method=RequestMethod.GET)
	@ResponseBody
	public void initSchnorrPublicParameters() {
		schnorrService.Init();
	}
	
	@RequestMapping(value="/getschnorr", method=RequestMethod.GET)
	@ResponseBody
	public String getSchnorrPublicParameters() {
		return schnorrService.getSchnorrPublicParameters();
	}
}