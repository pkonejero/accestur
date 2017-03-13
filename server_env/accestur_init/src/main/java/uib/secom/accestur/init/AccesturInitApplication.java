package uib.secom.accestur.init;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import uib.secom.accestur.crypto.elgamal.Elgamal;


@SpringBootApplication
public class AccesturInitApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccesturInitApplication.class, args);
		Elgamal eg=new Elgamal(256);
        String s="Lorem ipsum dolor sit amet, consectetur adipisicing elit"+
                ", sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\nsed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit,sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\nsed do eiusmod tempor incididunt ut labore et dolore magna aliqua.Lorem ipsum dolor sit amet, consectetur adipisicing elit,\nsed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\nsed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        System.out.println(s+"\nELGAMAL");
        System.out.println(eg.Elgamal_PtToString(eg.decrypt(eg.encrypt(s))));
	}
}
