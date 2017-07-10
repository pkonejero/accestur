package accestur.secom.core.crypto;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import accestur.secom.core.utils.Constants;

import org.json.*;

public class Schnorr {
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

    private BigInteger a3;
    private BigInteger A_3;
    private BigInteger w3;
    private JSONObject jsonObject;

    public Schnorr() {
    }

    public Schnorr(BigInteger p, BigInteger q, BigInteger g) {
        this.p = p;
        this.q = q;
        this.g = g;
    }

    public void Init() {
        BigInteger aux = BigInteger.ZERO;
        SecureRandom sr = new SecureRandom();

        q = new BigInteger(Constants.PRIME_BITS, Constants.PRIME_CERTAINTY, sr);

        do {
            p = q.multiply(aux).multiply(new BigInteger("2")).add(BigInteger.ONE);
            if (p.isProbablePrime(Constants.PRIME_CERTAINTY)) break;
            aux = aux.add(BigInteger.ONE);
        }
        while (true);

        while (true) {
            aux = (new BigInteger("2").add(new BigInteger(Constants.PRIME_BITS, Constants.PRIME_CERTAINTY, sr))).mod(p);
            h = aux.modPow((p.subtract(BigInteger.ONE)).divide(q), p);
            if (h.compareTo(BigInteger.ONE) != 0)
                break;
        }

        aux = new BigInteger(Constants.PRIME_BITS, sr);
        g = h.modPow(aux, p);
    }

    public BigInteger SecretKey() {
        x = new BigInteger(Constants.PRIME_BITS, new Random());
        return x;
    }

    public BigInteger PublicKey() {
        y = g.modPow(x, p);
        return y;
    }

    public BigInteger getRandom() {
        return new BigInteger(Constants.PRIME_BITS, new Random());
    }

    public BigInteger getPower(BigInteger bg) {
        return g.modPow(bg, p);
    }

    public void getServiceQuery() {
        a1 = getRandom();
        a2 = getRandom();
        A_1 = getPower(a1);
        A_2 = getPower(a2);
    }

    public BigInteger getSessionSeed(BigInteger a, BigInteger RU) {
        return a.add(e.multiply(RU.mod(q)));
    }

    public void solveChallengeQuery(BigInteger c, BigInteger RU) {
        BigInteger cx = c.multiply(x);
        BigInteger a_cx = a1.add(cx);
        w1 = a_cx.mod(q);

        BigInteger cRU = c.multiply(RU);
        BigInteger a2_cRU = a2.add(cRU);
        w2 = a2_cRU.mod(q);
    }

    public boolean verifyPASSQuery(BigInteger yu_C, BigInteger Hu_C) {
        boolean first;
        boolean second;

        BigInteger g_w1 = g.modPow(w1, p);
        BigInteger g_w2 = g.modPow(w2, p);

        BigInteger yca = yu_C.multiply(A_1);
        BigInteger ycaMODp = yca.mod(p);

        BigInteger hca = Hu_C.multiply(A_2);
        BigInteger hcaMODp = hca.mod(p);

        first = g_w1.equals(ycaMODp);
        second = g_w2.equals(hcaMODp);

        System.out.println("First check: " + first + "  Second check: " + second);

        return first && second;
    }


    public BigInteger sendRequest() {
        a3 = new BigInteger(Constants.PRIME_BITS, new Random());
        System.out.println(c);
        A_3 = g.modPow(a3, p);
        return A_3;
    }

    public BigInteger sendChallenge() {
        c = new BigInteger(Constants.PRIME_BITS, new Random());
        return c;
    }

    public BigInteger answerChallenge(BigInteger RU) {
        BigInteger cRU = c.multiply(RU);
        BigInteger a3_cRU = a3.add(cRU);
        w3 = a3_cRU.mod(q);
        return w3;
    }

    public boolean verifyChallenge(BigInteger Hu_C) {
        BigInteger g_w3 = g.modPow(w3, p);

        BigInteger hca = Hu_C.multiply(A_3);
        BigInteger hcaMODp = hca.mod(p);

        return g_w3.equals(hcaMODp);
    }


    public BigInteger send_a_to_b_request() {
        c = new BigInteger(Constants.PRIME_BITS, new Random());
        System.out.println(c);
        w = g.modPow(c, p);
        return w;
    }

    public BigInteger send_b_to_a_challenge() {
        e = new BigInteger(Constants.PRIME_BITS, new Random());
        System.out.println(e);
        return e;
    }

    public BigInteger send_a_to_b_resolve() {
        j = (c.add(e.multiply(x)).mod(q));
        return j;
    }

    public boolean verify() {
        BigInteger gs = g.modPow(j, p);
        BigInteger yh = y.modPow(e, p);

        if ((gs.multiply(yh)).mod(p).equals(w))
            return true;
        else
            return false;
    }

    public void setPublicValues(BigInteger[] values) {
        p = values[0];
        q = values[1];
        g = values[2];
        y = values[3];
    }

    public BigInteger[] getPublicValues() {
        BigInteger[] values = new BigInteger[4];
        values[0] = p;
        values[1] = q;
        values[2] = g;
        values[3] = y;
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

    public BigInteger getW() {
        return w;
    }

    public void setW(BigInteger w) {
        this.w = w;
    }

    public BigInteger getJ() {
        return j;
    }

    public void setJ(BigInteger j) {
        this.j = j;
    }

    public BigInteger getA1() {
        return a1;
    }

    public void setA1(BigInteger a1) {
        this.a1 = a1;
    }

    public BigInteger getA2() {
        return a2;
    }

    public void setA2(BigInteger a2) {
        this.a2 = a2;
    }

    public BigInteger getA_1() {
        return A_1;
    }

    public void setA_1(BigInteger a_1) {
        A_1 = a_1;
    }

    public BigInteger getA_2() {
        return A_2;
    }

    public void setA_2(BigInteger a_2) {
        A_2 = a_2;
    }

    public BigInteger getW1() {
        return w1;
    }

    public void setW1(BigInteger w1) {
        this.w1 = w1;
    }

    public BigInteger getW2() {
        return w2;
    }

    public void setW2(BigInteger w2) {
        this.w2 = w2;
    }


    public BigInteger getA3() {
        return a3;
    }

    public void setA3(BigInteger a3) {
        this.a3 = a3;
    }

    public BigInteger getA_3() {
        return A_3;
    }

    public void setA_3(BigInteger a_3) {
        A_3 = a_3;
    }

    public BigInteger getW3() {
        return w3;
    }

    public void setW3(BigInteger w3) {
        this.w3 = w3;
    }

    public String getCertificate() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();

            jsonObject.put("p", p.toString());
            jsonObject.put("q", q.toString());
            jsonObject.put("g", g.toString());
            jsonObject.put("y", y.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String getPrivateCertificate() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("p", p.toString());
            jsonObject.put("q", q.toString());
            jsonObject.put("g", g.toString());
            jsonObject.put("y", y.toString());
            jsonObject.put("x", x.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static Schnorr fromCertificate(String json) {
        JSONObject jsonObject;
        Schnorr schnorr = new Schnorr();
        try {
            jsonObject = new JSONObject(json);
            schnorr.setP((new BigInteger(jsonObject.getString("p"))));
            schnorr.setG((new BigInteger(jsonObject.getString("g"))));
            schnorr.setQ((new BigInteger(jsonObject.getString("q"))));
            schnorr.setY((new BigInteger(jsonObject.getString("y"))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return schnorr;
    }

    public static Schnorr fromPrivateCertificate(String json) {
        JSONObject jsonObject;
        Schnorr schnorr = new Schnorr();
        try {
            jsonObject = new JSONObject(json);
            schnorr.setP((new BigInteger(jsonObject.getString("p"))));
            schnorr.setG((new BigInteger(jsonObject.getString("g"))));
            schnorr.setQ((new BigInteger(jsonObject.getString("q"))));
            schnorr.setY((new BigInteger(jsonObject.getString("y"))));
            schnorr.setX((new BigInteger(jsonObject.getString("x"))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return schnorr;
    }

    public static Schnorr fromParameters(String[] params) {
        Schnorr schnorr = new Schnorr();
        schnorr.setP((new BigInteger(params[0])));
        schnorr.setQ((new BigInteger(params[1])));
        schnorr.setG((new BigInteger(params[2])));
        schnorr.setX((new BigInteger(params[3])));
        schnorr.setY((new BigInteger(params[4])));

        return schnorr;
    }

    public String[] getParameters() {
        String[] values = new String[5];
        values[0] = p.toString();
        values[1] = q.toString();
        values[2] = g.toString();
        values[3] = x.toString();
        values[4] = y.toString();

        return values;
    }

    public void printValues() {
        System.out.println(p.toString());
        System.out.println(q.toString());
        System.out.println(g.toString());
        System.out.println(y.toString());
    }
}