package secom.accestur.core.crypto.elgamal;

import java.math.BigInteger;
import com.google.gson.Gson;

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
	
	 public String toJson() {
         Gson gson = new Gson();
         return gson.toJson(this);
     }
     
     public static Elgamal_PublicKey fromJson(String json){
         Gson gson = new Gson();
         return gson.fromJson(json, Elgamal_PublicKey.class);
     }
}
