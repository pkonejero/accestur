package secom.accestur.front.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import secom.accestur.core.crypto.elgamal.Elgamal;
import secom.accestur.core.crypto.rsa.RSA;

@Controller
public class HomePageController{
	
	 String m = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras molestie, mi in fermentum egestas, dui est pellentesque massa, a mattis orci velit sit amet leo. Integer a cursus tellus, non vestibulum ligula. Sed a pellentesque sem, id tempor nisi. Ut eleifend augue molestie sollicitudin mollis. Integer congue rhoncus tincidunt. Nam a vestibulum mi. Ut vestibulum urna at lacus faucibus placerat. Fusce et eros in leo imperdiet sollicitudin eget ut lacus. In mauris ligula, viverra ac massa id, suscipit egestas eros. Pellentesque porta tellus sem, a faucibus erat faucibus sit amet. Sed turpis mauris, lobortis nec dolor non, finibus pellentesque est. Maecenas viverra imperdiet venenatis. Maecenas at risus lobortis, posuere ipsum vestibulum, luctus nisi. Maecenas purus risus, tempor non suscipit in, consequat vitae elit. Cras efficitur consequat sapien ac pulvinar. Aenean eget quam varius, fermentum nulla a, vulputate sapien.\n" +
			  "\n" +
			  "Curabitur lorem ex, fermentum ut rhoncus ac, rhoncus quis neque. Nullam vel dolor malesuada, malesuada sem pellentesque, rhoncus ex. Suspendisse posuere elit sed ex gravida maximus. Suspendisse eget fermentum metus, ac mollis ante. Etiam sed purus eu neque volutpat efficitur. Aenean fermentum fermentum lacus, eget porttitor odio elementum ut. Aliquam erat nisi, iaculis at pretium eget, condimentum in massa. Curabitur nulla purus, porta ac tempus id, consequat eleifend elit. Vestibulum ut tristique lorem, sit amet vulputate mauris. Sed non lorem eu leo auctor pretium id laoreet justo.\n" +
			  "\n" +
			  "Integer efficitur ante in iaculis finibus. Maecenas commodo porttitor dolor in tempor. Ut maximus fringilla elit, eu vulputate urna. Sed pulvinar neque et nibh consectetur rhoncus. Curabitur sed urna leo. Morbi vel arcu pharetra, dapibus ipsum tristique, posuere nisl. Sed eleifend at massa blandit faucibus. Vivamus nec erat in nisi scelerisque pharetra. Sed ac sem nec turpis sodales tempor. Donec turpis nulla, bibendum sed vehicula et, efficitur vitae enim. Integer luctus sapien porttitor, fringilla ex et, feugiat ligula. Etiam vel urna id odio bibendum suscipit. In pretium urna at metus tincidunt, in eleifend sem faucibus. Nunc at est efficitur, sodales enim et, mattis neque.\n" +
			  "\n" +
			  "Curabitur sapien neque, feugiat sed arcu et, suscipit finibus dolor. Sed blandit egestas placerat. Sed turpis justo, semper a est consequat, molestie pharetra mi. Praesent nec consectetur dolor. Donec ac lacus eget augue lobortis tristique. Duis hendrerit, ante sed condimentum imperdiet, ligula tortor venenatis eros, sed suscipit massa mi ut mauris. Vivamus ornare tristique mi lobortis vehicula. Etiam aliquet tellus et ex lobortis tincidunt.\n" +
			  "\n" +
			  "In pellentesque elit ac lacus pulvinar fermentum. Quisque ultrices massa neque, sit amet auctor tortor volutpat quis. Morbi et ligula ex. Proin eu purus fermentum, dictum tellus ut, sodales tellus. Praesent porta eros sapien, sed pellentesque felis semper eget. Nullam quis mi nibh. Aenean at quam lacus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Praesent ac risus eget metus efficitur interdum. Integer imperdiet ornare justo sed tincidunt. Nunc dictum elit sed scelerisque tristique. Sed fermentum risus ut cursus tincidunt. Donec nec malesuada risus. Duis vel ante ligula. Nam et lacinia quam, non ultrices eros.";
			  	

	
	@Autowired
	@Qualifier("elGamal")
	Elgamal elgamal;

	@Autowired
	@Qualifier("rsa")
	RSA rsa;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model){
		elgamal.createPrivateCertificate("ttpPrivateCertificate");
		elgamal.createPublicCertificate("ttpPublicCertificate");
		
		elgamal.modifyPublicKey(Elgamal.readPublicCertificate("ttpPublicCertificate"));
		elgamal.modifyKset(Elgamal.readPrivateCertificate("ttpPrivateCertificate"));
		
		model.put("elgamal",""+elgamal.verify(elgamal.sign(m).toString(),m));
		
		return "welcome";
	}
}