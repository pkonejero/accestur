package secom.accestur.core.crypto.rsa;

import java.math.BigInteger;

public class RSA_PlainText {

	private BigInteger[] plt;

	public RSA_PlainText(BigInteger[] pt){
		this.plt=pt;
	}

	public BigInteger[] getText(){
		return plt;
	}
}
