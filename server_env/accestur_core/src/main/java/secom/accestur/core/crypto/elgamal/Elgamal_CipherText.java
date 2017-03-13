package secom.accestur.core.crypto.elgamal;

import java.math.BigInteger;

public class Elgamal_CipherText{

	private BigInteger mhr[];
	private BigInteger gr;

	public Elgamal_CipherText(BigInteger p[],BigInteger gr){
		mhr=p;
		this.gr=gr;
	}

	public BigInteger[] getCt(){
		return mhr;
	}

	public BigInteger getGr(){
		return gr;
	}   
}
