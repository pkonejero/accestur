package secom.accestur.core.crypto.rsa;

import java.math.BigInteger;

public class RSA_KeySet{

	private RSA_PublicKey pk;
	private RSA_SecretKey sk;
	private RSA_Parameters params;
	private int nb_bits;

	public RSA_KeySet(RSA_PublicKey pubk, RSA_SecretKey seck){
		this.pk=pubk;
		this.sk=seck;
	}

	public RSA_KeySet(int nbits){
		nb_bits=nbits;
		params=new RSA_Parameters(this.nb_bits);
		BigInteger p=RSA.getPrime((params.getNb_bits()/2),params.getPrg());  
		BigInteger q=RSA.getPrime((params.getNb_bits()/2),params.getPrg()); 
		BigInteger n=p.multiply(q);
		BigInteger e=new BigInteger("65537");
		BigInteger totient=p.subtract(BigInteger.ONE).multiply((q.subtract(BigInteger.ONE)));
		BigInteger d=e.modInverse(totient);    
		pk=new RSA_PublicKey(n,e);
		sk=new RSA_SecretKey(d,p,q);
	}

	public RSA_PublicKey getPk(){
		return pk;
	}

	public RSA_SecretKey getSk(){
		return sk;
	}

	public RSA_Parameters getParams(){
		return params;
	}
}
