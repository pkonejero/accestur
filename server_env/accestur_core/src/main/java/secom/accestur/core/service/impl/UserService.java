package secom.accestur.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import secom.accestur.core.crypto.Crypto.Cryptography;
import secom.accestur.core.crypto.schnorr.Schnorr;
import secom.accestur.core.dao.MCityPassRepository;
import secom.accestur.core.dao.UserRepository;
import secom.accestur.core.model.Counter;
import secom.accestur.core.model.MCityPass;
import secom.accestur.core.model.SecretValue;
import secom.accestur.core.model.ServiceAgent;
import secom.accestur.core.model.User;
import secom.accestur.core.service.UserServiceInterface;
import secom.accestur.core.utils.Constants;

@Service("userService")
public class UserService implements UserServiceInterface {
	@Autowired
	@Qualifier("userRepository")
	private UserRepository userRepository;

	// @Autowired
	// @Qualifier("serviceAgentService")
	private ServiceAgentService serviceAgentService;

	// @Autowired
	// @Qualifier("secretvalueService")
	private SecretValueService secretValueService;

	// @Autowired
	// @Qualifier("mCityPassService")
	private MCityPassService mCityPassService;

	// @Autowired
	// @Qualifier("activationService")
	private ActivationService activationService;

	// @Autowired
	// @Qualifier("rightOfUseService")
	private RightOfUseService rightOfUseService;

	// @Autowired
	// @Qualifier("counterService")
	private CounterService counterService;

//	@Autowired
//	@Qualifier("schnorr")
	private Schnorr schnorr;

//	@Autowired
//	@Qualifier("cryptography")
	private Cryptography crypto;

	private User user;

	private String[] paramsOfPass;
	private String[] psi;
	private String K;
	private BigInteger random;
	private BigInteger RU;

	// private long timestamp;
	private String indexHash;
	private String[] kandRI;

	private String hash;

	public UserService() {
		serviceAgentService = new ServiceAgentService();
		counterService = new CounterService();
		rightOfUseService = new RightOfUseService();
		activationService = new ActivationService();
		mCityPassService = new MCityPassService();
		secretValueService = new SecretValueService();
		schnorr = new Schnorr();
		crypto = new Cryptography();
	}

	public String getUserByPseudonym1(String pseudonym) {
		User user = userRepository.findAll().iterator().next();
		if (user != null) {
			user.setPseudonym(pseudonym);
			userRepository.save(user);
		}

		return pseudonym;
	}

	public User getUser() {
		return userRepository.findAll().iterator().next();
	}
	
	public void saveUser(String y, String signature){
		User user = new User();
		user.setPseudonym(generatePseudonym(y, signature));
		userRepository.save(user);
	}
	
	

	public void initUser() {
		// user = getUser();
		String schnorr = "{\"p\":\"15277160019691241425056343514263407750548889486111153986570545050251020131904394375904890848244527769038757280355403593200158044376264500469424023579806111987\",\"q\":\"8240107885486106486006657774683607200943306087438594383263508657093322616992661475676855905202010662911951068152860622006557736988276429595158588770121959\",\"g\":\"1514584839425919363525023347275785025835117601117443638644111269286983800930309213257206014157612761652599400252093511933272586856485729712397283986079989283\",\"x\":\"7708628560626367760465016407189387479320242873908245161782841436913570511985583732098641759217506298877394767011854554216545447918209312125411822909189291\",\"y\":\"14989039481374367292369303874278584668623867701174068970571531818536559077115464814862613693930941393933250033676427630417324282180404456732157669953456072214\"}";
		String pseudonym = "\"{\"signature\":\"SKWcc27uci691bCqyjqb29HgtVdgfUzqf9wnLrlQqDtRiw3Va4u7LVmxvi11LViyCua9Wthqq0Bh8Jky2Ly5Zcu46HOczadD78wrBN3pN0G2yq2mCv1/+ENhjHSv6c2ppC6Mm0nSrd4fNgY+/FzzYBMGvqvErIq/Bd8bEcDv+ThH60vbAqyHhzeomKpl0aNzmgOpdv0f/8SzZwvJbzPG+2xf6UIFccXiyU+PZbVryxVCVkDadXEC5gbV3uSsJuzznpdZqa646+v+0n2KgHSjTMjKUgoXkI0PxJlKYiOpTwDp7cjSu+QijgNvgcX2qn61QE6Ceps552U5H04b4VXrew==\",\"y\":\"14989039481374367292369303874278584668623867701174068970571531818536559077115464814862613693930941393933250033676427630417324282180404456732157669953456072214\"}";
		user = new User(1, pseudonym, schnorr);
		user.setRU(
				"12794049269479661747741922995769407597715574659446422944989680129048627175393339290248969736580625948742511551516917129928149840477131522749937199210210155");
	}

	public User getUserByPseudonym(String pseudonym) {
		return userRepository.findByPseudonym(pseudonym);
	}
	
	public User getUserById(long id) {
		return userRepository.findById(id);
	}

	public void createCertificate() {

		crypto.initPrivateKey("cert/user/private_USER.der");
	}

	///////////////////////////////////////////////////////////////////////
	/////////////////////// PSEUDONYM///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////

	public String authenticateUser() {
		crypto.initPublicKey("cert/ttp/public_TTP.der");
		// crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		schnorr.Init();
		schnorr.SecretKey();
		schnorr.PublicKey();
		String params[] = new String[3];
		BigInteger y = schnorr.getY();
		params[0] = crypto.getSignature(y.toString());
		params[1] = crypto.encryptWithPublicKey(y.toString());
		
		JSONObject json = new JSONObject();
		json.put("signature", params[0]);
		json.put("y", params[1]);

		return json.toString();
	}

	public boolean verifyPseudonym(String input) {
		JSONObject json = new JSONObject(input);
		String[] params = new String[2] ;
		params[0] = json.getString("y");
		params[1] = json.getString("signature");
		boolean verified = crypto.getValidation(params[0], params[1]);
		if (verified) {
			User user = new User();
			user.setPseudonym(generatePseudonym(params[0], params[1]));
			user.setSchnorr(schnorr.getPrivateCertificate());
			userRepository.save(user);
		}

		return verified;
	}

	///////////////////////////////////////////////////////////////////////
	/////////////////// PURCHASE PASS///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////

	public String getService() {
		user = userRepository.findAll().iterator().next();
		crypto.initPublicKey("cert/issuer/public_ISSUER.der");
		schnorr = Schnorr.fromPrivateCertificate(user.getSchnorr());
		String[] params = new String[10];
		params[0] = user.getPseudonym();
		params[1] = schnorr.getCertificate();
		RU = schnorr.getRandom();
		user.setRU(RU.toString());
		params[2] = Cryptography.hash(RU.toString());
		BigInteger Hu = schnorr.getPower(RU);
		schnorr.getServiceQuery();
		params[3] = Hu.toString();
		params[4] = schnorr.getA_1().toString();
		params[5] = schnorr.getA_2().toString();
		params[6] = Constants.LIFETIME;
		params[7] = Constants.CATEGORY;
		params[8] = Constants.EXPDATE;
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		System.out.println("PURDATE:" + dateFormat.format(date));
		params[9] = dateFormat.format(date);
		userRepository.save(user);
		return getServiceMessage(params);
	}

	public String solveChallenge(String input, String[] services) {
		JSONObject json = new JSONObject(input);  
		String c = json.getString("c");
		schnorr.solveChallengeQuery(new BigInteger(c), RU);
		K = Cryptography.hash(schnorr.getW2().toString());
		random = schnorr.getRandom();

		psi = new String[services.length];
		for (int i = 0; i < services.length; i++) {
			ServiceAgent service = serviceAgentService.getServiceByName(services[i]);
			if (service.getM() == -1) {
				psi[i] = Cryptography.hash(random.toString());
			} else if (service.getM() == 1) {
				psi[i] = Cryptography.hash(random.toString());
			} else {
				for (int j = 0; j <= service.getM(); j++) {
					if (j == 0) {
						psi[i] = Cryptography.hash(random.toString());
					} else {
						psi[i] = Cryptography.hash(psi[i]);
					}
				}
			}
		}
		String[] ws = new String[2];
		ws[0] = schnorr.getW1().toString();
		ws[1] = schnorr.getW2().toString();

		return solveChallengeMessage(psi, services, ws);
	}

	public String receivePass(String params) {
		System.out.println(params);
		JSONObject json = new JSONObject(params);
		long id = json.getLong("Sn");
		JSONArray jsonArray = json.getJSONArray("Services");
		JSONObject serviceJSON = jsonArray.getJSONObject(0);
		String service = serviceJSON.getString("Service");
		secretValueService.saveSecretValue(new SecretValue(mCityPassService.getMCityPassBySn(id),
				serviceAgentService.getServiceByName(service).getProvider(), random.toString()));
		return "Everything OK";
	}

	// MESSAGE PROCESSORS

	private String getServiceMessage(String[] params) {
		JSONObject json = new JSONObject();
		json.put("user", params[0]);
		json.put("certificate", params[1]);
		json.put("hRU", params[2]);
		json.put("Hu", params[3]);
		json.put("A1", params[4]);
		json.put("A2", params[5]);
		json.put("Lifetime", params[6]);
		json.put("Category", params[7]);
		json.put("EXPDATE", params[8]);
		json.put("PURDATE", params[9]);
		return json.toString();
	}

	private String solveChallengeMessage(String[] psi, String[] services, String[] ws) {
		JSONObject json = new JSONObject();
		json.put("w1", crypto.encryptWithPublicKey(ws[0]));
		json.put("w2", crypto.encryptWithPublicKey(ws[1]));
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;

		for (int i = 0; i < psi.length; i++) {
			jsonObject = new JSONObject();
			jsonObject.put("service", services[i]);
			jsonObject.put("psi", psi[i]);
			String s = crypto.encryptWithPublicKey(jsonObject.toString());
			jsonArray.put(i, s);
		}

		json.put("services", jsonArray);
		String message = json.toString();

		return message;
	}

	///////////////////////////////////////////////////////////////////////
	/////////////////// ACTIVATE PASS///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////

	public String showPass(long sn) {
		JSONObject json = new JSONObject();
		json.put("Sn", sn);
		return json.toString();
	}

	public void getVerifyTicketConfirmation(String s) {
		System.out.println(s);
	}

	///////////////////////////////////////////////////////////////////////
	/////////////// PASS Verification///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////

	public String showTicket(long CityPassId, long serviceId) {
		// System.out.println(user.getPseudonym());
		// System.out.println(user.getSchnorr());
		System.out.println("Show Ticket");
		crypto.initPrivateKey("cert/user/private_USER.der");
		crypto.initPublicKey("cert/ttp/public_TTP.der");
		schnorr = Schnorr.fromPrivateCertificate(user.getSchnorr());
		JSONObject json = new JSONObject();
		json.put("A3", schnorr.sendRequest());
		mCityPassService.initMCityPass();
		serviceAgentService.initService(serviceId, true);
		counterService.initCounter(mCityPassService.getMCityPass(), serviceAgentService.getServiceAgent(), true);
		// Necessary without database
		activationService.initActivation(mCityPassService.getMCityPass());
		//
		System.out.println("Service: " + counterService.getCounter().getService().getName());
		json.put("PASS", mCityPassService.getMCityPass().getId());
		json.put("ACT", activationService.getActivation().getId());
		json.put("CERT", schnorr.getCertificate());
		json.put("SERVICE", counterService.getCounter().getService().getId());
		// json.put("Signature", crypto.getSignature(json.toString()));
		String signature = crypto.getSignature(json.toString());
		JSONObject message = new JSONObject();
		message.put("m1", json.toString());
		message.put("Signature", signature);
		System.out.println(message.toString());
		return message.toString();
	}

	public String solveVerifyChallenge(String params) {
		System.out.println("Solve Verify Challenge");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("Challenge"));
		// System.out.println(message.getString("Signature"));
		if (!validateSignature(message.getString("Challenge"), message.getString("Signature"))) {
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		if (json.getLong("PASS") == -1) {
			System.out.println("An error has ocurred");
			System.exit(0);
		}
		schnorr.setC(new BigInteger(json.getString("c")));
		json.put("w3", crypto.encryptWithPublicKey(schnorr.answerChallenge(new BigInteger(user.getRU())).toString()));

		return json.toString();
	}

	public String showProof(String params) {
		System.out.println("Show proof");
		crypto.initPrivateKey("cert/user/private_USER.der");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("Vsucc"));
		// System.out.println(message.getString("Signature"));
		if (!validateSignature(message.getString("Vsucc"), message.getString("Signature"))) {
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		if (json.getInt("PASS") == -1) {
			System.out.println("An error has ocurred");
			System.exit(0);
		}
		indexHash = json.getString("SERVICE");

		kandRI = getKandRI();
		BigInteger K = new BigInteger(kandRI[0].getBytes());
		BigInteger PRNG = schnorr.getPower(K);
		System.out.println("PRNG" + PRNG.toString());
		secretValueService.initSecretValue(mCityPassService.getMCityPass(), true);
		BigInteger secret = new BigInteger(secretValueService.getSecretValue().getSecret());
		System.out.println("Secret");
		json = new JSONObject();
		json.put("Au", secret.xor(PRNG).toString());
		System.out.println("Au" + secret.xor(PRNG).toString());
		json.put("PASS", mCityPassService.getMCityPass().getId());

		String signature = crypto.getSignature(json.toString());
		message = new JSONObject();
		message.put("m3", json.toString());
		message.put("Signature", signature);
		return message.toString();
	}

	public boolean getValidationConfirmation(String params) {
		System.out.println("Get Validation Confirmation");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("R"));
		// System.out.println(message.getString("Signature"));
		if (!validateSignature(message.getString("R"), message.getString("Signature"))) {
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return false;
		}
		if (json.getInt("PASS") == -1) {
			return false;
		}

		BigInteger Ap = new BigInteger(json.getString("Ap"));
		BigInteger PRNG = schnorr.getPower(new BigInteger(Cryptography.hash(kandRI[0]).getBytes()));
		BigInteger RI = Ap.xor(PRNG);
		if (Cryptography.hash(RI.toString()).equals(mCityPassService.getMCityPass().gethRI())) {
			System.out.println("true");
			return true;
		} else {
			System.out.println("false");
			return false;
		}
	}

	///////////////////////////////////////////////////////////////////////
	/////////////// PASS Verification M-times//////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	public void initValues(long CityPassId, long serviceId){
		mCityPassService.initMCityPass();
		serviceAgentService.initService(serviceId, true);
		counterService.initCounter(mCityPassService.getMCityPass(), serviceAgentService.getServiceAgent(), true);
		secretValueService.initSecretValue(mCityPassService.getMCityPass(), true);
		// Necessary without database
		activationService.initActivation(mCityPassService.getMCityPass());
		//
	}

	public String showMTicket() {
		System.out.println("Show M Ticket");
		crypto.initPrivateKey("cert/user/private_USER.der");
		crypto.initPublicKey("cert/ttp/public_TTP.der");
		schnorr = Schnorr.fromPrivateCertificate(user.getSchnorr());
		
	
		JSONObject json = new JSONObject();
		json.put("A3", schnorr.sendRequest());
		System.out.println("User A3:" + schnorr.getA_3());

		BigInteger secret = new BigInteger(secretValueService.getSecretValue().getSecret());
		hash = secret.toString();
		if (counterService.getCounter().getLastHash().equals(Constants.NOTUSED)) {
			System.out.println("First time used.");
			for (int i = 0; i < serviceAgentService.getServiceAgent().getM(); i++) {
				hash = Cryptography.hash(hash.toString());
			}
			System.out.println("LAST HASH: " + Cryptography.hash(hash));
			System.out.println("PSI:" + counterService.getCounter().getPsi());
		} else {
			System.out.println("Use number "
					+ (serviceAgentService.getServiceAgent().getM() - counterService.getCounter().getCounter()));
			if (counterService.getCounter().getCounter() == 1) {
				hash = Cryptography.hash(hash.toString());
			} else {
				for (int i = 0; i < counterService.getCounter().getCounter(); i++) {
					hash = Cryptography.hash(hash.toString());
				}
			}
		}

		System.out.println("HASH: " + hash);

		json.put("PASS", mCityPassService.getMCityPass().getId());
		json.put("ACT", activationService.getActivation().getId());
		json.put("CERT", schnorr.getCertificate());
		json.put("SERVICE", counterService.getCounter().getService().getId());
		// json.put("K", hash);
		String signature = crypto.getSignature(json.toString());
		JSONObject message = new JSONObject();
		message.put("m1", json.toString());
		message.put("Signature", signature);
		return message.toString();
	}

	public String solveMVerifyChallenge(String params) {
		System.out.println("Solve Verify Challenge");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("Challenge"));
		// System.out.println(message.getString("Signature"));
		if (!validateSignature(message.getString("Challenge"), message.getString("Signature"))) {
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		if (json.getLong("PASS") == -1) {
			System.out.println("An error has ocurred");
			System.exit(0);
		}
		schnorr.setC(new BigInteger(json.getString("c")));
		json.put("w3", crypto.encryptWithPublicKey(schnorr.answerChallenge(new BigInteger(user.getRU())).toString()));

		//
		// String[] kandRI = getKandRI();
		// BigInteger hk = new
		// BigInteger(Cryptography.hash(kandRI[0]).getBytes());
		// BigInteger RI = new BigInteger(kandRI[1]);
		// System.out.println(RI.xor(schnorr.getPower(hk)));
		return json.toString();
	}

	public String showMProof(String params) {
		System.out.println("Show proof");
		crypto.initPrivateKey("cert/user/private_USER.der");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("Vsucc"));
		// System.out.println(message.getString("Signature"));
		if (!validateSignature(message.getString("Vsucc"), message.getString("Signature"))) {
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		if (json.getInt("PASS") == -1) {
			return null;
		}
		indexHash = json.getString("SERVICE");
		kandRI = getKandRI();
		BigInteger K = new BigInteger(kandRI[0].getBytes());
		BigInteger PRNG = schnorr.getPower(K);

		// System.out.println("PRNG" + PRNG.toString());
//		secretValueService.initSecretValue(mCityPassService.getMCityPass(), true);

		json = new JSONObject();
		// System.out.println("Show proof hash:" + hash);
		json.put("Au", getA(PRNG.toString()));
		// System.out.println("Au" + getA(PRNG.toString()));
		json.put("PASS", mCityPassService.getMCityPass().getId());
		String signature = crypto.getSignature(json.toString());
		message = new JSONObject();
		message.put("m3", json.toString());
		message.put("Signature", signature);
		return message.toString();
	}

	public String getVerifyMTicketConfirmation(String params) {
		System.out.println("Get Validation Confirmation");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("R"));
		// System.out.println(message.getString("Signature"));
		if (!validateSignature(message.getString("R"), message.getString("Signature"))) {
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return "false";
		}
		if (json.getInt("PASS") == -1) {
			return "false";
		}

		BigInteger Ap = new BigInteger(json.getString("Ap"));
		BigInteger PRNG = schnorr.getPower(new BigInteger(Cryptography.hash(kandRI[0]).getBytes()));
		BigInteger RI = Ap.xor(PRNG);
		if (Cryptography.hash(RI.toString()).equals(mCityPassService.getMCityPass().gethRI())) {
			System.out.println("true");
			counterService.updateCounter(hash,true);
			return "true";
		} else {
			System.out.println("false");
			return "false";
		}
	}

	private String getA(String PRNG) {
		String PRNG32 = Cryptography.hash(PRNG);
		// String PRNG32 = Cryptography.hash("Text 1");
		System.out.println("USER PRNG: " + PRNG32);
		System.out.println("USER Hash: " + hash);
		// System.out.println("USER hash hash: " + Cryptography.hash(hash) );
		byte[] PRNGbytes = null;
		byte[] HASHbytes = null;

		PRNGbytes = Base64.getDecoder().decode(PRNG32.getBytes());
		HASHbytes = Base64.getDecoder().decode(hash.getBytes());

		byte[] xor = new byte[PRNGbytes.length];
		for (int i = 0; i < PRNGbytes.length; i++) {
			xor[i] = (byte) (PRNGbytes[i] ^ HASHbytes[i]);
		}

		return new String(Base64.getEncoder().encode(xor));
	}

	///////////////////////////////////////////////////////////////////////
	/////////////// PASS Verification inf-times////////////////////////////
	///////////////////////////////////////////////////////////////////////

	public String showInfinitePass(long CityPassId, long serviceId) {
		System.out.println("Show Inf Ticket");
		crypto.initPrivateKey("cert/user/private_USER.der");
		crypto.initPublicKey("cert/ttp/public_TTP.der");
		schnorr = Schnorr.fromPrivateCertificate(user.getSchnorr());
		JSONObject json = new JSONObject();
		mCityPassService.initMCityPass();
		serviceAgentService.initService(serviceId, true);
		counterService.initCounter(mCityPassService.getMCityPass(), serviceAgentService.getServiceAgent(), true);
		// Necessary without database
		activationService.initActivation(mCityPassService.getMCityPass());
		//
		System.out.println("Service: " + counterService.getCounter().getService().getName());
		json.put("A3", schnorr.sendRequest());
		json.put("PASS", mCityPassService.getMCityPass().getId());
		json.put("ACT", activationService.getActivation().getId());
		json.put("CERT", schnorr.getCertificate());
		json.put("SERVICE", counterService.getCounter().getService().getId());

		String signature = crypto.getSignature(json.toString());
		JSONObject message = new JSONObject();
		message.put("m1", json.toString());
		message.put("Signature", signature);
		return message.toString();
	}

	public String solveInfiniteVerifyChallenge(String params) {
		System.out.println("Solve Verify Challenge");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("Challenge"));
		// System.out.println(message.getString("Signature"));
		if (!validateSignature(message.getString("Challenge"), message.getString("Signature"))) {
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		if (json.getLong("PASS") == -1) {
			System.out.println("An error has ocurred");
			System.exit(0);
		}
		schnorr.setC(new BigInteger(json.getString("c")));
		json.put("w3", crypto.encryptWithPublicKey(schnorr.answerChallenge(new BigInteger(user.getRU())).toString()));

		return json.toString();
	}

	public String showInfiniteProof(String params) {
		System.out.println("Show proof");

		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("Vsucc"));
		// System.out.println(message.getString("Signature"));
		if (!validateSignature(message.getString("Vsucc"), message.getString("Signature"))) {
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return json.toString();
		}
		if (json.getInt("PASS") == -1) {
			return null;
		}
		indexHash = json.getString("SERVICE");

		kandRI = getKandRI();
		BigInteger K = new BigInteger(kandRI[0].getBytes());
		BigInteger PRNG = schnorr.getPower(K);
		System.out.println("PRNG" + PRNG.toString());
		secretValueService.initSecretValue(mCityPassService.getMCityPass(), true);
		BigInteger secret = new BigInteger(secretValueService.getSecretValue().getSecret());
		System.out.println("Secret");
		json = new JSONObject();
		json.put("Au", secret.xor(PRNG).toString());
		System.out.println("Au" + secret.xor(PRNG).toString());
		json.put("PASS", mCityPassService.getMCityPass().getId());

		String signature = crypto.getSignature(json.toString());
		message = new JSONObject();
		message.put("m3", json.toString());
		message.put("Signature", signature);
		return message.toString();
	}

	public boolean getInfiniteValidationConfirmation(String params) {
		System.out.println("Get Validation Confirmation");
		JSONObject message = new JSONObject(params);
		JSONObject json = new JSONObject(message.getString("R"));
		// System.out.println(message.getString("Signature"));
		if (!validateSignature(message.getString("R"), message.getString("Signature"))) {
			System.out.println("Signature not valid");
			json = new JSONObject();
			json.put("PASS", -1);
			return false;
		}
		if (json.getInt("PASS") == -1) {
			return false;
		}

		BigInteger Ap = new BigInteger(json.getString("Ap"));
		BigInteger PRNG = schnorr.getPower(new BigInteger(Cryptography.hash(kandRI[0]).getBytes()));
		BigInteger RI = Ap.xor(PRNG);
		if (Cryptography.hash(RI.toString()).equals(mCityPassService.getMCityPass().gethRI())) {
			System.out.println("true");
			return true;
		} else {
			System.out.println("false");
			return false;
		}
	}

	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////

	private boolean validateSignature(String param, String signature) {
		boolean verify = crypto.getValidation(param, signature);
		System.out.println("Validating signature: " + verify);

		return verify;

	}

	private boolean validateSignature(String param) {
		JSONObject json = new JSONObject(param);
		String signature = json.getString("Signature");
		json.remove("Signature");
		boolean verify = crypto.getValidation(json.toString(), signature);
		System.out.println("Validating signature: " + verify);

		return verify;
	}

	public String receivePass() {
		return null;
	}

	public String sendPass() {
		return null;
	}

	public String[] showProof(String[] params) {
		return null;
	}

	private String[] getKandRI() {
		// crypto.initPrivateKey("cert/ttp/private_TTP.der");
		rightOfUseService.initRightOfUse(mCityPassService.getMCityPass());
		JSONObject json = new JSONObject(rightOfUseService.getRightOfUse().getK());
		String[] params = new String[2];
		params[0] = json.getString("K");
		params[1] = json.getString("RI");

		return params;
	}

	////////// STATIC METHODS///////////
	private static String generatePseudonym(String y, String signature) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("y", y);
		jsonObject.put("signature", signature);

		return jsonObject.toString();
	}

	public static String getYu(String json) {
		JSONObject jsonObject = new JSONObject(json);
		return jsonObject.getString("y");
	}

	/* (non-Javadoc)
	 * @see secom.accestur.core.service.UserServiceInterface#showMTicket(long, long)
	 */
	@Override
	public String showMTicket(long CityPassId, long serviceId) {
		// TODO Auto-generated method stub
		return null;
	}

}
