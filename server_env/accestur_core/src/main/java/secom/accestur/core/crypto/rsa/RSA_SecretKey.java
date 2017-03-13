package secom.accestur.core.crypto.rsa;

import java.math.BigInteger;

public class RSA_SecretKey{

	private BigInteger d;
	private BigInteger p;
	private BigInteger q;

	public RSA_SecretKey(BigInteger d,BigInteger p,BigInteger q){
		this.d=d;
		this.p=p;
		this.q=q;
	}

	public BigInteger getD(){
		return d;
	}

	public BigInteger getP(){
		return p;
	}

	public BigInteger getQ(){
		return q;
	}
}
