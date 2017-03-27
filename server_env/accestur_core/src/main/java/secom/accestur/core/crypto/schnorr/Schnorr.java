package secom.accestur.core.crypto.schnorr;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

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
	private BigInteger a;
	private BigInteger qp;

	public Schnorr(){}

	public Schnorr(BigInteger p, BigInteger q, BigInteger g){
		this.p = p;
		this.q = q;
		this.g = g;
	}

	public void Init(){
		BigInteger one = new BigInteger("1");
		BigInteger two = new BigInteger("2");
		int certainty = 100;
		SecureRandom sr = new SecureRandom();
		q = new BigInteger(Constants.Q_PRIME_BITS, certainty, sr);
		qp = BigInteger.ONE;

		do{
			p = q.multiply(qp).multiply(two).add(one);
			if (p.isProbablePrime(certainty)) break;
			qp = qp.add(BigInteger.ONE);
		}while (true);

		while(true){
			a = (two.add(new BigInteger(Constants.Q_PRIME_BITS, 100, sr))).mod(p);
			BigInteger ga = (p.subtract(BigInteger.ONE)).divide(q);
			h = a.modPow(ga, p);
			if (h.compareTo(BigInteger.ONE) != 0)
				break;
		}

		w = new BigInteger(Constants.Q_PRIME_BITS, sr);
		g = h.modPow(w, p);
	}

	public BigInteger SecretKey(){		
		x = new BigInteger(Constants.Q_PRIME_BITS, new Random());
		return x;
	}

	public BigInteger PublicKey(){
		y = g.modPow(q.subtract(x), p);
		System.out.println("Public Key : " + y);
		return y;
	}

	public BigInteger send_a_to_b_request(){
		c = new BigInteger(Constants.Q_PRIME_BITS, new Random());
		w = g.modPow(c, p);
		System.out.println("send_a_to_b_request : " + w);
		return w;
	}

	public BigInteger send_b_to_a_challenge(){
		e = new BigInteger(Constants.Q_PRIME_BITS, new Random());
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

	public BigInteger getP() {
		return p;
	}

	public void setP(BigInteger p) {
		this.p = p;
	}

	public BigInteger getQ() {
		return q;
	}

	public void setQ(BigInteger q) {
		this.q = q;
	}

	public BigInteger getG() {
		return g;
	}

	public void setG(BigInteger g) {
		this.g = g;
	}

	public BigInteger getH() {
		return h;
	}

	public void setH(BigInteger h) {
		this.h = h;
	}

	public BigInteger getR() {
		return r;
	}

	public void setR(BigInteger r) {
		this.r = r;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public BigInteger getX() {
		return x;
	}

	public void setX(BigInteger x) {
		this.x = x;
	}

	public BigInteger getY() {
		return y;
	}

	public void setY(BigInteger y) {
		this.y = y;
	}

	public BigInteger getW() {
		return w;
	}

	public void setW(BigInteger w) {
		this.w = w;
	}

	public BigInteger getC() {
		return c;
	}

	public void setC(BigInteger c) {
		this.c = c;
	}

	public BigInteger getE() {
		return e;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	public BigInteger getJ() {
		return j;
	}

	public void setJ(BigInteger j) {
		this.j = j;
	}

	public BigInteger getZ() {
		return z;
	}

	public void setZ(BigInteger z) {
		this.z = z;
	}


}
