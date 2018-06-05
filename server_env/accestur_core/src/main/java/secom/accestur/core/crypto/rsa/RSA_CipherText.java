package secom.accestur.core.crypto.rsa;

import java.math.BigInteger;

public class RSA_CipherText{

	private BigInteger[]ciphers; 

	public RSA_CipherText(BigInteger[]ct){
		this.ciphers=ct;
	}

	public BigInteger[] getCiphers(){
		return ciphers;
	}
}
