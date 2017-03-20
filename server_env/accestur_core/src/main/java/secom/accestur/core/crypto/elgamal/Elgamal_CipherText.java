package secom.accestur.core.crypto.elgamal;

import java.math.BigInteger;

public class Elgamal_CipherText{

	private BigInteger mhr[];
	private BigInteger gr;

	public Elgamal_CipherText(BigInteger p[],BigInteger gr){
		mhr=p;
		this.gr=gr;
	}
	
	 public Elgamal_CipherText(String s) {
	        String[] lines = s.split("\r\n");
	        gr = new BigInteger(lines[0].substring(0, lines[0].length()));
	        mhr = new BigInteger[lines.length-1];
	        for(int i = 1; i < lines.length; i++){
	            mhr[i-1] = new BigInteger(lines[i].substring(0, lines[i].length()));
	        }
	    }

	public BigInteger[] getCt(){
		return mhr;
	}

	public BigInteger getGr(){
		return gr;
	}   
	
	
	@Override
    public String toString() {
        String s = gr.toString();
        s = s + "\r\n";
        for (int i = 0; i < mhr.length; i++) {
            s = s + mhr[i].toString() + "\r\n";
        }
        return s;

    }
}
