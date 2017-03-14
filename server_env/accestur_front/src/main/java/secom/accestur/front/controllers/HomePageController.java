package secom.accestur.front.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import secom.accestur.core.crypto.elgamal.*;
import secom.accestur.core.service.impl.IssuerService;

@RestController
public class HomePageController{
	
	@Autowired
	@Qualifier("issuerService")
	private IssuerService issuerService;
	
	@RequestMapping("/**")
	public String homepage(){
		Elgamal eg=new Elgamal(256);
		String s="Lorem ipsum dolor sit amet, consectetur adipisicing elit";
		return "Framework - ACCESTUR - " + eg.Elgamal_PtToString(eg.decrypt(eg.encrypt(s)));
	}
}