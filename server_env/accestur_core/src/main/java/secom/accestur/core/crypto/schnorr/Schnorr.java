package secom.accestur.core.crypto.schnorr;

import java.math.BigInteger;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component("schnorr")
public class Schnorr{
	// https://es.wikipedia.org/wiki/Algoritmo_de_identificaci%C3%B3n_de_Schnorr
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
	

	public Schnorr(BigInteger p, BigInteger q, BigInteger g, int t, BigInteger x, BigInteger y){
		this.p = p;
		this.q = q;
		this.g = g;
		this.t = t;
		this.x = x;
		this.y = y;
	}
	
	
	

	public BigInteger getY() {
		return y;
	}


	public void setY(BigInteger y) {
		this.y = y;
	}


	// TODO -- https://en.wikipedia.org/wiki/Schnorr_group
	public void Init(){
		// q >= 2^t 
		q = BigInteger.probablePrime(256, new Random());
		// p = qr + 1 & p-1 disivible order q
		p = BigInteger.probablePrime(1024, new Random());
		r = (p.subtract(BigInteger.ONE)).divide(q);
		boolean valid = false;
		while (valid == false) {
			h = BigInteger.probablePrime(256, new Random());
			g = h.modPow(r, p);
			System.out.println("h: " + h + " g: " + g );
			System.out.println(g.compareTo(BigInteger.ONE));
			System.out.println(h.compareTo(BigInteger.ONE));
			if(g.compareTo(BigInteger.ONE) == 1 && h.compareTo(BigInteger.ONE) == 1){
				valid = true;
			}
		}
		t = 128;
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

	public BigInteger Generator(){
		h = g.modPow((p.subtract(BigInteger.ONE)).divide(q),p);
		return h;
	}
	
	public void setGenerator(BigInteger h) {
		this.h = h;
	}

	// TODO
	public BigInteger SecretKey(){		
		// x | Zq = 0 >= a >= q-1  
		x = new BigInteger(256, new Random());
		while (x.compareTo(BigInteger.ZERO) == 1 &&  x.compareTo(q.subtract(BigInteger.ONE)) == -1 && x.modPow(q, p).equals(BigInteger.ONE)){
			x = new BigInteger(256, new Random());
		}
		return x;
	}

	public BigInteger PublicKey(){
		y = h.modPow(q.subtract(x), p);
		return y;
	}

	public void SecureParam(int t){
		this.t = t;
	}
	
	public int SecureParam(){
		return t;
	}

	
	public BigInteger send_a_to_b_request(){
		// c | Zq = 0 >= a >= q-1
		boolean valid = false;
		while(!valid){
			c = new BigInteger(128, new Random());
			if(c.compareTo(BigInteger.ZERO) == 1 &&  c.compareTo(q.subtract(BigInteger.ONE)) == -1){
				valid = true;
			}
		}
	
		w = h.modPow(c, p);
		return w;
	}
	
	public void get_a_to_b_request(BigInteger w){
		this.w = w;
		
	}

	public BigInteger send_b_to_a_challenge(){
		int TWOpT = 2^t; 
		do {
			e = new BigInteger(128, new Random());
		}  while(e.compareTo(BigInteger.ZERO) == 1 &&  e.compareTo(new BigInteger(""+TWOpT))==-1);
		return e;
	}
	
	public void get_b_to_a_challenge(BigInteger e){
		this.e = e;
	}

	public BigInteger send_a_to_b_resolve(){
		j = (c.add(e.multiply(x)).mod(q));
		return j;
	}
	
	public void get_a_to_b_resolve(BigInteger j){
		this.j = j;
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
