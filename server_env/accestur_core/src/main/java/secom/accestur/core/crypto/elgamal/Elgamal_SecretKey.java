package secom.accestur.core.crypto.elgamal;

import java.math.BigInteger;
import com.google.gson.Gson;

public class Elgamal_SecretKey{

	private BigInteger p;
	private BigInteger x;

	public Elgamal_SecretKey(BigInteger p,BigInteger x){
		this.p=p;
		this.x=x;
	}
	
	public Elgamal_SecretKey(){
        
    }

	public BigInteger getP(){
		return p;
	}

	public BigInteger getX(){
		return x;
	}   
	
	public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    
    public static Elgamal_SecretKey fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Elgamal_SecretKey.class);
    }
}
