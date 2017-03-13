package secom.accestur.core.crypto.rsa;

import java.math.BigInteger;

public class RSA_PublicKey{

	private BigInteger N;
	private BigInteger e;

	public RSA_PublicKey(BigInteger N,BigInteger e){
		this.N=N;
		this.e=e;
	}

	public BigInteger getN(){
		return N;
	}

	public BigInteger getE(){
		return e;
	}   
}
