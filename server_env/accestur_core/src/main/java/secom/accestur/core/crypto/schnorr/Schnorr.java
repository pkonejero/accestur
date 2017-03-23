package secom.accestur.core.crypto.schnorr;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

@Component("schnorr")
public class Schnorr{
	// https://es.wikipedia.org/wiki/Algoritmo_de_identificaci%C3%B3n_de_Schnorr
	private BigInteger p;
	private BigInteger q;

	private BigInteger g;
	private BigInteger h;
	private BigInteger r;

	private BigInteger t;
	private BigInteger x;
	private BigInteger y;

	private BigInteger w;
	private BigInteger c;
	private BigInteger e;
	private BigInteger j;
	private BigInteger z;

	public Schnorr(){}

	public Schnorr(BigInteger p, BigInteger q, BigInteger g, BigInteger t, BigInteger x, BigInteger y){
		this.p = p;
		this.q = q;
		this.g = g;
		this.t = t;
		this.x = x;
		this.y = y;
	}

	// TODO -- https://en.wikipedia.org/wiki/Schnorr_group
	public void Init(){
		// q >= 2^t 
		q = new BigInteger("443");
		// p = qr + 1 & p-1 disivible order q
		p = new BigInteger("48731");
		r = new BigInteger("110");
		//h^r != 1 mod p
		h = new BigInteger("48731");
		// g=h^r mod p
		g = new BigInteger("6");
	}

	public BigInteger Generator(){
		h = g.modPow((p.subtract(BigInteger.ONE)).divide(q),p);
		return h;
	}

	// TODO
	public BigInteger SecretKey(){		
		// x | Zq = 0 >= a >= q-1  
		x = new BigInteger("357");
		return x;
	}

	public BigInteger PublicKey(){
		y = h.modPow(q.subtract(x), p);
		return y;
	}

	public void SecureParam(BigInteger t){
		this.t = t;
	}

	// TODO
	public BigInteger send_a_to_b_request(){
		// c | Zq = 0 >= a >= q-1
		c = new BigInteger("274");
		w = h.modPow(c, p);
		return w;
	}

	public BigInteger send_b_to_a_challenge(){
		e = new BigInteger("129");
		return e;
	}

	public BigInteger send_a_to_b_resolve(){
		j = (c.add(e.multiply(x)).mod(q));
		return j;
	}

	public boolean verify(){
		BigInteger gs = h.modPow(j, p);
		BigInteger yh = y.modPow(e, p);
		z = (gs.multiply(yh)).mod(p);
		if(z.equals(w))
			return true;
		else
			return false;
	}
}
