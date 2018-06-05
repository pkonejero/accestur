package secom.accestur.core.service.impl;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.dao.SchnorrRepository;
import secom.accestur.core.model.Schnorr;
import secom.accestur.core.service.SchnorrServiceInterface;
import secom.accestur.core.utils.Constants;

@Service("schnorrService")
public class SchnorrService implements SchnorrServiceInterface{
	@Autowired
	@Qualifier("schnorrRepository")
	private SchnorrRepository schnorrRepository;
	
	public void Init(){
		BigInteger p;
		BigInteger q;
		BigInteger g;
		BigInteger h;
		
		Schnorr schnorr = new Schnorr();
	
		if(schnorrRepository.count() == 0)
		{
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

			schnorr.setG(g.toString());
			schnorr.setP(p.toString());
			schnorr.setQ(q.toString());

			schnorrRepository.save(schnorr);
		}
	}

	public String getSchnorrPublicParameters(){
		JSONObject jsonObject = new JSONObject();
		
		if(schnorrRepository.count() != 0)
		{
			Schnorr schnorr = new Schnorr();
			schnorr = schnorrRepository.findAll().iterator().next();
			jsonObject.put("p", schnorr.getP());
			jsonObject.put("q", schnorr.getQ());
			jsonObject.put("g", schnorr.getG());
		}

		return jsonObject.toString();
	}
}