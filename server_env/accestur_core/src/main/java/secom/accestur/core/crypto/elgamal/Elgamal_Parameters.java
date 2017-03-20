package secom.accestur.core.crypto.elgamal;

import java.security.SecureRandom;
import java.util.Random;

public class Elgamal_Parameters{

	private int nb_bits;
	private Random prg;

	public Elgamal_Parameters(int nb_bits,Random prg){
		this.nb_bits=nb_bits;
		this.prg=prg;
	}

	public Elgamal_Parameters(int nb_bits){
		this.nb_bits=nb_bits;
		this.prg=new SecureRandom();
	}

	public int getNb_bits(){
		return nb_bits;
	}

	public Random getPrg(){
		return prg;
	}     
	
	
}

