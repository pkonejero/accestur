package secom.accestur.core.crypto.schnorr;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import secom.accestur.core.utils.Constants;

@Component("schnorr")
@Scope("prototype")
public class Schnorr{
	private BigInteger p;
	private BigInteger q;
	private BigInteger g;
	private BigInteger h;
	private BigInteger x;
	private BigInteger y;
	private BigInteger c;
	private BigInteger e;
	private BigInteger w;
	private BigInteger j;

	public Schnorr(){}

	public Schnorr(BigInteger p, BigInteger q, BigInteger g){
		this.p = p;
		this.q = q;
		this.g = g;
	}

	public void Init(){
		BigInteger aux = BigInteger.ZERO;
		SecureRandom sr = new SecureRandom();

		q = new BigInteger(Constants.PRIME_BITS, Constants.PRIME_CERTAINTY, sr);

		do
		{
			p = q.multiply(aux).multiply(new BigInteger("2")).add(BigInteger.ONE);
			if (p.isProbablePrime(Constants.PRIME_CERTAINTY)) break;
			aux = aux.add(BigInteger.ONE);
		}
		while (true);

		while(true)
		{
			aux = (new BigInteger("2").add(new BigInteger(Constants.PRIME_BITS, Constants.PRIME_CERTAINTY, sr))).mod(p);
			h = aux.modPow((p.subtract(BigInteger.ONE)).divide(q), p);
			if (h.compareTo(BigInteger.ONE) != 0)
				break;
		}

		aux = new BigInteger(Constants.PRIME_BITS, sr);
		g = h.modPow(aux, p);
	}

	public BigInteger SecretKey(){		
		x = new BigInteger(Constants.PRIME_BITS, new Random());
		return x;
	}

	public BigInteger PublicKey(){
		y = g.modPow(q.subtract(x), p);
		return y;
	}

	public BigInteger send_a_to_b_request(){
		c = new BigInteger(Constants.PRIME_BITS, new Random());
		w = g.modPow(c, p);
		return w;
	}

	public BigInteger send_b_to_a_challenge(){
		e = new BigInteger(Constants.PRIME_BITS, new Random());
		//h = c.add((x.multiply(e)).mod(q));
		//return h;
		return e;
	}

	public BigInteger send_a_to_b_resolve(){
		j = (c.add(e.multiply(x)).mod(q));
		return j;
	}

	public boolean verify(){
		BigInteger gs = g.modPow(j, p);
		BigInteger yh = y.modPow(e, p);

		if((gs.multiply(yh)).mod(p).equals(w))
			return true;
		else
			return false;
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

	public BigInteger getP(){
		return p;
	}

	public void setP(BigInteger p){
		this.p = p;
	}

	public BigInteger getQ(){
		return q;
	}

	public void setQ(BigInteger q){
		this.q = q;
	}

	public BigInteger getG(){
		return g;
	}

	public void setG(BigInteger g){
		this.g = g;
	}

	public BigInteger getH(){
		return h;
	}

	public void setH(BigInteger h){
		this.h = h;
	}

	public BigInteger getX(){
		return x;
	}

	public void setX(BigInteger x){
		this.x = x;
	}

	public BigInteger getY(){
		return y;
	}

	public void setY(BigInteger y){
		this.y = y;
	}

	public BigInteger getC(){
		return c;
	}

	public void setC(BigInteger c){
		this.c = c;
	}

	public BigInteger getE(){
		return e;
	}

	public void setE(BigInteger e){
		this.e = e;
	}

	public BigInteger getW(){
		return w;
	}

	public void setW(BigInteger w){
		this.w = w;
	}

	public BigInteger getJ(){
		return j;
	}

	public void setJ(BigInteger j){
		this.j = j;
	}
}
