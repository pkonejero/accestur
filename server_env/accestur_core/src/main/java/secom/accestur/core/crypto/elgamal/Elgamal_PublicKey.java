package secom.accestur.core.crypto.elgamal;

import java.math.BigInteger;

public class Elgamal_PublicKey{

	private BigInteger p;
	private BigInteger h;
	private BigInteger g;

	public Elgamal_PublicKey(BigInteger p,BigInteger h,BigInteger g){
		this.p=p;
		this.h=h;
		this.g=g;
	}

	public BigInteger getP(){
		return p;
	}

	public BigInteger getH(){
		return h;
	}

	public BigInteger getG(){
		return g;
	}
}
