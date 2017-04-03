package secom.accestur.core.crypto.schnorr;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import org.json.*;

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

		q = new BigInteger(256, 40, sr);

		do
		{
			p = q.multiply(aux).multiply(new BigInteger("2")).add(BigInteger.ONE);
			if (p.isProbablePrime(256)) break;
			aux = aux.add(BigInteger.ONE);
		}
		while (true);

		while(true)
		{
			aux = (new BigInteger("2").add(new BigInteger(256, 40, sr))).mod(p);
			h = aux.modPow((p.subtract(BigInteger.ONE)).divide(q), p);
			if (h.compareTo(BigInteger.ONE) != 0)
				break;
		}

		aux = new BigInteger(256, sr);
		g = h.modPow(aux, p);
	}

	public BigInteger SecretKey(){		
		x = new BigInteger(256, new Random());
		return x;
	}

	public BigInteger PublicKey(){
		y = g.modPow(q.subtract(x), p);
		return y;
	}

	public BigInteger send_a_to_b_request(){
		c = new BigInteger(256, new Random());
		w = g.modPow(c, p);
		return w;
	}

	public BigInteger send_b_to_a_challenge(){
		e = new BigInteger(256, new Random());
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
		y = values[3];
	}

	public BigInteger[] getPublicValues(){
		BigInteger[] values = new BigInteger[4];
		values[0] = p;
		values[1] = q;
		values[2] = g;
		values[3] = y;
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
	
	public String getCertificate() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("p", p.toString());
		jsonObject.put("q", q.toString());
		jsonObject.put("g", g.toString());
		jsonObject.put("y", y.toString());
		return jsonObject.toString();
	}
	
	public String getPrivateCertificate() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("p", p.toString());
		jsonObject.put("q", q.toString());
		jsonObject.put("g", g.toString());
		jsonObject.put("y", y.toString());
		jsonObject.put("x", x.toString());
		return jsonObject.toString();
	}
	
	public static Schnorr fromCertificate(String json) {
		JSONObject jsonObject = new JSONObject(json);
		BigInteger p = new BigInteger(jsonObject.getString("p"));
		BigInteger q = new BigInteger(jsonObject.getString("q"));
		BigInteger g = new BigInteger(jsonObject.getString("g"));
		BigInteger y = new BigInteger(jsonObject.getString("y"));
		Schnorr schnorr = new Schnorr();
		schnorr.setP(p);
		schnorr.setG(g);
		schnorr.setP(p);
		schnorr.setY(y);
		
		return schnorr;
	}
	
	public static Schnorr fromPrivateCertificate(String json) {
		JSONObject jsonObject = new JSONObject(json);
		BigInteger p = new BigInteger(jsonObject.getString("p"));
		BigInteger q = new BigInteger(jsonObject.getString("q"));
		BigInteger g = new BigInteger(jsonObject.getString("g"));
		BigInteger y = new BigInteger(jsonObject.getString("y"));
		BigInteger x = new BigInteger(jsonObject.getString("x"));
		Schnorr schnorr = new Schnorr();
		schnorr.setP(p);
		schnorr.setG(g);
		schnorr.setP(p);
		schnorr.setY(y);
		schnorr.setX(x);
		
		return schnorr;
	}
	
	public static Schnorr fromParameters(String[] params) {
		BigInteger p = new BigInteger(params[0]);
		BigInteger q = new BigInteger(params[1]);
		BigInteger g = new BigInteger(params[2]);
		BigInteger x = new BigInteger(params[3]);
		BigInteger y = new BigInteger(params[4]);
		
		Schnorr schnorr = new Schnorr();
		schnorr.setP(p);
		schnorr.setG(g);
		schnorr.setP(p);
		schnorr.setY(y);
		schnorr.setX(x);
		
		return schnorr;
	}
	
	public String[] getParameters(){
		String[] values = new String[5];
		values[0] = p.toString();
		values[1] = q.toString();
		values[2] = g.toString();
		values[3] = x.toString();
		values[4] = y.toString();
		return values;
	}
}
