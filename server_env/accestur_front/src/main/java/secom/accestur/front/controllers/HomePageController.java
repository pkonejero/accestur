package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import secom.accestur.core.crypto.elgamal.Elgamal;
import secom.accestur.core.crypto.rsa.RSA;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.*;

@Controller
public class HomePageController{
	@Autowired
	@Qualifier("MCityPass")
	MCityPass mcp;



	@RequestMapping("/")
	public String welcome(Map<String, Object> model){
		mcp.setCategory("Private");
		mcp.setTermsAndContions("I can use it");
		long lt = 123456712;
		mcp.setLifeTime(lt);
		
		return "welcome";
	}
}