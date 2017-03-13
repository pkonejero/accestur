package secom.accestur.front.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import secom.accestur.core.crypto.elgamal.*;

@RestController
public class HomePageController{
	@RequestMapping("/**")
	public String homepage(){
		Elgamal eg=new Elgamal(256);
		String s="Lorem ipsum dolor sit amet, consectetur adipisicing elit";
		return "Framework - ACCESTUR - " + eg.Elgamal_PtToString(eg.decrypt(eg.encrypt(s)));
	}
}