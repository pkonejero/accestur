package secom.accestur.core.crypto.rsa;

import java.security.SecureRandom;
import java.util.Random;

public class RSA_Parameters{

	private int nb_bits;
	private Random prg;

	public RSA_Parameters(int nb_bits,Random prg){
		this.nb_bits=nb_bits;
		this.prg=prg;
	}

	public RSA_Parameters(int nb_bits){
		this.nb_bits=nb_bits;
		this.prg=new SecureRandom();
	}

	public int getNb_bits(){
		return this.nb_bits;
	}

	public Random getPrg(){
		return this.prg;
	}
}
