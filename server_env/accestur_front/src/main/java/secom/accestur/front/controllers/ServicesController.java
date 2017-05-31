package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import secom.accestur.core.service.impl.ServiceAgentService;

@Controller
public class ServicesController {
	
	@Autowired
	@Qualifier("serviceAgentService")
	ServiceAgentService serviceAgentService;
	
	@RequestMapping("/services")
	public String welcome(Map<String, Object> model){	
		
		return "provider";
	}
	
	@RequestMapping(value = "/services/getServices", method=RequestMethod.GET)
	@ResponseBody
	public String getServices(){
		return serviceAgentService.getAllServices();
	}
	


}
