package secom.accestur.core.crypto.elgamal;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Elgamal_KeySet{

	private Elgamal_PublicKey pk;
	private Elgamal_SecretKey sk;
	private Elgamal_Parameters params;
	private int nb_bits;

	public Elgamal_KeySet(Elgamal_PublicKey _pk,Elgamal_SecretKey _sk,int nbb){
		this.pk=_pk;
		this.sk=_sk;
		this.nb_bits=nbb; 
	}

	public Elgamal_KeySet(int nbb){
		//System.out.print(nbb);
		Elgamal_KeySet(new Elgamal_Parameters(nbb,new SecureRandom())); 
	}

	public void Elgamal_KeySet(Elgamal_Parameters par){
		this.params=par;
		this.nb_bits=par.getNb_bits();
		BigInteger p=Elgamal.getPrime_cert(nb_bits, this.params.getPrg(), 100);
		BigInteger p_prime=(p.subtract(BigInteger.ONE)).divide(new BigInteger("2"));
		//System.out.println("p---->"+p+"  et  p prime   "+p_prime);
		BigInteger g;
		boolean found=false;
		do{
			g = new BigInteger(p.bitCount()-1, new SecureRandom());
			if(p.compareTo(g)==1 && g.modPow(p_prime, p).equals(BigInteger.ONE) && !g.modPow(new BigInteger("2"), p).equals(BigInteger.ONE)){
				found=true;
			}
		} while(!found);

		//System.out.println("g  -> "+g);
		BigInteger x;
		do{
			x = new BigInteger(p_prime.bitCount()-1, new SecureRandom());
		}while(p_prime.compareTo(x)==-1);
		//System.out.println("x  -> "+x);
		BigInteger h=g.modPow(x, p);
		//System.out.println("h  -> "+h);
		Elgamal_PublicKey pk=new Elgamal_PublicKey(p,h,g);
		Elgamal_SecretKey sk=new Elgamal_SecretKey(p,x);
		this.pk=pk;
		this.sk=sk;
	}

	public Elgamal_PublicKey getPk(){
		return pk;
	}

	public Elgamal_SecretKey getSk(){
		return sk;
	}

	public Elgamal_Parameters getParams(){
		return params;
	}

	public int getNb_bits(){
		return nb_bits;
	} 
	
	
	
	  public String[] getParameters() {
	        String parameters[] = new String[4];
	        parameters[0] = pk.toJson();
	        parameters[1] = sk.toJson();
	        parameters[2] = "params";
	        parameters[3] = "" + nb_bits;

	        return parameters;
	    }

	    public static Elgamal_KeySet newKset(String[] params) {
	        Elgamal_KeySet kset = new Elgamal_KeySet(Elgamal_PublicKey.fromJson(params[0]), Elgamal_SecretKey.fromJson(params[1]), Integer.parseInt(params[3]));
	        //kset.setParams(Elgamal_Parameters.fromJson(params[2]));
	        
	        return kset;
	    }
	    
	    public static Elgamal_KeySet newPublicKset (String[] params) {
	        Elgamal_KeySet kset = new Elgamal_KeySet(Elgamal_PublicKey.fromJson(params[0]), new Elgamal_SecretKey() , Integer.parseInt(params[3]));
	        //kset.setParams(Elgamal_Parameters.fromJson(params[2]));
	        
	        return kset;
	    }
}


