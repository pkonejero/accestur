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
	
	private BigInteger a1;
	private BigInteger a2;
	
	private BigInteger A_1;
	private BigInteger A_2;
	
	private BigInteger w1;
	private BigInteger w2;

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
	
	public BigInteger getRandom(){
		return new BigInteger(Constants.PRIME_BITS, new Random());
	}
	
	public BigInteger getPower(BigInteger bg){
		return g.modPow(bg, p);
	}
	
	public void getServiceQuery(){
		a1 = getRandom();
		a2 = getRandom();
		A_1 = getPower(a1);
		A_2 = getPower(a2);
	}
	
	public BigInteger getSessionSeed(BigInteger a, BigInteger RU){
		return a.add(e.multiply(RU.mod(q)));
	}
	
	public void solveChallengeQuery(BigInteger c, BigInteger RU){
		w1 = a1.add(c.multiply(x)).mod(q);
		w2 = a2.add(c.multiply(RU)).mod(q);
	}
	
	public void verifyPASSQuery(BigInteger yu_C, BigInteger Hu_C){
		boolean first;
		boolean second;
		
		BigInteger g_w1 = g.modPow(w1, p);
		BigInteger g_w2 = g.modPow(w2, p);
		
		first = g_w1.equals(yu_C.multiply(A_1));
		second = g_w2.equals(Hu_C.multiply(A_2));
		
		System.out.println("First check: " + first + "  Second check: " +second);
	}

	public BigInteger send_a_to_b_request(){
		c = new BigInteger(Constants.PRIME_BITS, new Random());
		w = g.modPow(c, p);
		return w;
	}

	public BigInteger send_b_to_a_challenge(){
		e = new BigInteger(Constants.PRIME_BITS, new Random());
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
	
	public BigInteger getA1(){
		return a1;
	}

	public void setA1(BigInteger a1){
		this.a1 = a1;
	}

	public BigInteger getA2(){
		return a2;
	}

	public void setA2(BigInteger a2){
		this.a2 = a2;
	}

	public BigInteger getA_1(){
		return A_1;
	}

	public void setA_1(BigInteger a_1){
		A_1 = a_1;
	}

	public BigInteger getA_2(){
		return A_2;
	}

	public void setA_2(BigInteger a_2){
		A_2 = a_2;
	}

	public BigInteger getW1(){
		return w1;
	}

	public void setW1(BigInteger w1){
		this.w1 = w1;
	}

	public BigInteger getW2(){
		return w2;
	}

	public void setW2(BigInteger w2){
		this.w2 = w2;
	}

	public String getCertificate(){
		JSONObject jsonObject = new JSONObject();
		System.out.println(p.toString());
		System.out.println(q.toString());
		System.out.println(g.toString());
		System.out.println(y.toString());
		jsonObject.put("p", p.toString());
		jsonObject.put("q", q.toString());
		jsonObject.put("g", g.toString());
		jsonObject.put("y", y.toString());
		return jsonObject.toString();
	}
	
	public String getPrivateCertificate(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("p", p.toString());
		jsonObject.put("q", q.toString());
		jsonObject.put("g", g.toString());
		jsonObject.put("y", y.toString());
		jsonObject.put("x", x.toString());
		return jsonObject.toString();
	}
	
	public static Schnorr fromCertificate(String json){
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
	
	public static Schnorr fromPrivateCertificate(String json){
		JSONObject jsonObject = new JSONObject(json);
		BigInteger p = new BigInteger(jsonObject.getString("p"));		
		BigInteger q = new BigInteger(jsonObject.getString("q"));
		BigInteger g = new BigInteger(jsonObject.getString("g"));
		BigInteger y = new BigInteger(jsonObject.getString("y"));
		BigInteger x = new BigInteger(jsonObject.getString("x"));
//		System.out.println("p: "+ p.toString());
//		System.out.println("q: "+q.toString());
//		System.out.println("g: "+g.toString());
//		System.out.println("y: "+y.toString());
		Schnorr schnorr = new Schnorr();
		schnorr.setP(p);
		schnorr.setG(g);
		schnorr.setQ(q);
		schnorr.setY(y);
		schnorr.setX(x);
//		System.out.println("p: "+schnorr.getP().toString());
//		System.out.println("q: "+schnorr.getQ().toString());
//		System.out.println("g: "+schnorr.getG().toString());
//		System.out.println("y: "+schnorr.getY().toString());
		//schnorr.printValues();
		return schnorr;
	}
	
	public static Schnorr fromParameters(String[] params){
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
	
	public void printValues(){
		System.out.println(p.toString());
		System.out.println(q.toString());
		System.out.println(g.toString());
		System.out.println(y.toString());
	}
}
