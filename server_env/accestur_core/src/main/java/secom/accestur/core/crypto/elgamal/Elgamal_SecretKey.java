package secom.accestur.core.crypto.elgamal;

import java.math.BigInteger;

public class Elgamal_SecretKey{

	private BigInteger p;
	private BigInteger x;

	public Elgamal_SecretKey(BigInteger p,BigInteger x){
		this.p=p;
		this.x=x;
	}

	public BigInteger getP(){
		return p;
	}

	public BigInteger getX(){
		return x;
	}   
}
