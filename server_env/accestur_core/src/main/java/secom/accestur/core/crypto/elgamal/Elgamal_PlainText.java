package secom.accestur.core.crypto.elgamal;

import java.math.BigInteger;

public class Elgamal_PlainText{
    
	private BigInteger pt[];
    
    public Elgamal_PlainText(BigInteger p[]){
        pt=p;
    }

    public BigInteger[] getPt(){
        return pt;
    }
}
