package secom.accestur.core.crypto.schnorr;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Random;

import secom.accestur.core.utils.Constants;

@Component("schnorr")
public class Schnorr{
	// https://es.wikipedia.org/wiki/Algoritmo_de_identificaci%C3%B3n_de_Schnorr
	// Schnorr Group - https://en.wikipedia.org/wiki/Schnorr_group
	// TO DO -- DSAParameterGenerator Library
	private BigInteger p;
	private BigInteger q;

	private BigInteger g;
	private BigInteger h;
	private BigInteger r;

	private int t;
	private BigInteger x;
	private BigInteger y;

	private BigInteger w;
	private BigInteger c;
	private BigInteger e;
	private BigInteger j;
	private BigInteger z;

	public Schnorr(){}

	public Schnorr(BigInteger p, BigInteger q, BigInteger g){
		this.p = p;
		this.q = q;
		this.g = g;
	}

	public void Init(){
		boolean valid = false;
		while(valid!=true){
			q = BigInteger.probablePrime(Constants.SCHNORR_PRIME_Q_BITS, new Random());
			p = BigInteger.probablePrime(Constants.SCHNORR_PRIME_P_BITS, new Random());
			r = (p.subtract(BigInteger.ONE)).divide(q);
			if(p.equals((q.multiply(r)).add(BigInteger.ONE)))
				valid = true;
		}

		valid = false;

		while (valid!=true){
			h = BigInteger.probablePrime(Constants.SCHNORR_VALUE_H_BITS, new Random());
			if(h.modPow(r, p).compareTo(BigInteger.ONE) == 1)
				valid = true;
		}

		g = h.modPow(r, p);

		System.out.println("Public Parameters: p: " + p + " q: " + q + " g: " + g);
		System.out.println("Public_aux Parameters: h: " + h + " r: " + r);
	}

	public BigInteger SecretKey(){		
		boolean valid = false;
		while (valid!=true){
			x = new BigInteger(Constants.SCHNORR_PRIME_Q_BITS, new Random());
			x = x.subtract(BigInteger.ONE);
			if(x.compareTo(BigInteger.ZERO) != 0)
				//if(x.modPow(q, p).compareTo(BigInteger.ONE) == 1)
				valid = true;
		}
		System.out.println("Secret Key member of the group : " + x);
		return x;
	}

	public BigInteger PublicKey(){
		y = g.modPow(q.subtract(x), p);
		System.out.println("Public Key : " + y);
		return y;
	}

	public BigInteger send_a_to_b_request(){
		c = new BigInteger(Constants.SCHNORR_PRIME_Q_BITS, new Random());
		c = c.subtract(BigInteger.ONE);
		w = g.modPow(c, p);
		System.out.println("send_a_to_b_request : " + w);
		return w;
	}

	public BigInteger send_b_to_a_challenge(){
		boolean valid = false;
		while (valid!=true){
			e = new BigInteger(Constants.SCHNORR_PRIME_P_BITS, new Random());
			e = e.subtract(BigInteger.ONE);
			if(e.modPow(q, p).compareTo(BigInteger.ONE) == 1)
				valid = true;
		}
		h = c.add((x.multiply(e)).mod(q));
		System.out.println("challenge : " + h);
		return h;
	}

	public BigInteger send_a_to_b_resolve(){
		j = (c.add(e.multiply(x)).mod(q));
		return j;
	}

	public boolean verify(){
		BigInteger gs = g.modPow(j, p);
		BigInteger yh = y.modPow(e, p);
		z = (gs.multiply(yh)).mod(p);
		if(z.equals(w))
		{
			System.out.println("true");
			return true;
		}	
		else
		{
			System.out.println("false");
			return false;
		}
	}

	public void setPublicValues(BigInteger[] values){
		p = values[0];
		q = values[1];
		g = values[2];
	}

	public BigInteger[] getPublicValues(){
		BigInteger[] values = new BigInteger[3];
		values[0] = p;
		values[1] = q;
		values[2] = g;
		return values;
	}
}
