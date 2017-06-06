package accestur.secom.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.os.Environment;
import android.util.Base64;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import accestur.secom.core.crypto.Cryptography;
import accestur.secom.core.crypto.Schnorr;
import accestur.secom.core.model.Activation;
import accestur.secom.core.model.Counter;
import accestur.secom.core.model.MCityPass;
import accestur.secom.core.model.SecretValue;
import accestur.secom.core.model.ServiceAgent;
import accestur.secom.core.model.User;
import accestur.secom.core.service.UserServiceInterface;
import accestur.secom.core.utils.Constants;


public class UserService implements UserServiceInterface {
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


    private Schnorr schnorr;


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
    private String c;

    public UserService() {
        serviceAgentService = new ServiceAgentService();
        counterService = new CounterService();
        rightOfUseService = new RightOfUseService();
        activationService = new ActivationService();
        mCityPassService = new MCityPassService();
        secretValueService = new SecretValueService();
        schnorr = new Schnorr();
        crypto = new Cryptography();
        // initUser();
    }


    public User getUser(){
        return user;
    }


    @Override
    public String getUserByPseudonym1(String pseudonym) {
        return null;
    }

    @Override
    public User getUserByPseudonym(String pseudonym) {
        return null;
    }

    public void initUser() {
        // user = getUser();
        String schnorr = "{\"p\":\"5467897339840518162786237130690140681563426429253886144000446704182355497141936161065307037197592907962860058196217365910961519527018301194391986769229751223\",\"q\":\"8064745338997814399389730281253894810565525706864138855457885994369255895489581358503402709730962991095663802649288150311152683668168585832436558656681049\",\"g\":\"4646952560476358109126473448548917623796778482036695497649556543407949097775314878774996625110527933758294221262884258522370914327999672597655752894601109781\",\"x\":\"7733025593472488389256895258226197332268583878313092286696688930414899173839631136605671753229452825393617130762207267907316015448450850151189405257072479\",\"y\":\"4097784045753520584649660125040472632352443691185929683458123089553340188869221441314047620570808410670573788399629690033621641717480345580209198464560804215\"}";
        String pseudonym = "{\"signature\":\"GqbGQyZIuUC9ZTY+XQWiCdC8g+1Otl0kIp51dMc2FGOqgQ+lgFGHfjNGK76GbRbIROtOXYpUWlO/X0OoKg7Z5/ktYSuLw3wGqQ0mlR83J/roDTpG3TTwwY2dp+I30gXSq5UlIEE+O5IEHyxVscmvYPCuKKX4qCVRVvVSWkoLNGzpXxH0byHvaXbYU4RKaBLw0XZP/2YxCyfR1mAZu2p1D6yqzZxpgZ8tS9Wp61QRntcLTWUHe0T+Zaj4On5Ne5dCshRmY6wsnQd0lHX70ELihkXHFFL0296gA6ui6202ZIOyOg3Ry/8Iw/L/hvR4erIHQpzqGKOVVTl1/cBmaj/ZuQ==\",\"y\":\"4097784045753520584649660125040472632352443691185929683458123089553340188869221441314047620570808410670573788399629690033621641717480345580209198464560804215\"}";


        user = new User(1, pseudonym, schnorr);
        user.setRU(
                "2442975853956204694416926919548221861938948632294922324117640127873059971867599829506848099999990130709891529895069326940642472092588674577162440652652442");
        System.out.println(user.getSchnorr());
    }


    public void loadUser(int id){
        user = User.load(User.class, id);
//        System.out.println("User: " + user.getPseudonym());
    }
    ///////////////////////////////////////////////////////////////////////
    /////////////////////// PSEUDONYM///////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public String authenticateUser() {
        crypto.initPublicKey(Constants.PATH_PROVIDER_KEY);
        crypto.initPrivateKey(Constants.PATH_PRIVATE_KEY);
        // crypto.initPublicKey("cert/issuer/public_ISSUER.der");
        schnorr.Init();
        schnorr.SecretKey();
        schnorr.PublicKey();
        String params[] = new String[3];
        BigInteger y = schnorr.getY();
        params[0] = crypto.getSignature(y.toString());
        params[1] = crypto.encryptWithPublicKey(y.toString());

        JSONObject json = new JSONObject();

        try {
            json.put("signature", params[0]);
            json.put("y", params[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return json.toString();
    }

    public boolean verifyPseudonym(String input) {
        JSONObject json = null;
        boolean verified = false;
        try {
            json = new JSONObject(input);
            String[] params = new String[2];
            params[0] = json.getString("y");
            params[1] = json.getString("signature");
            verified = crypto.getValidation(params[0], params[1]);
            if (verified) {
                User user = new User();
                user.setPseudonym(generatePseudonym(params[0], params[1], json.getInt("Sn")));
                user.setSchnorr(schnorr.getPrivateCertificate());
                user.save();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return verified;
    }

    ///////////////////////////////////////////////////////////////////////
    /////////////////// PURCHASE PASS///////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public String getService(String Category, String ExpDate, String Lifetime) {
        // user = userRepository.findAll().iterator().next();
        user = User.load(User.class, 1);
        crypto.initPublicKey(Constants.PATH_ISSUER_KEY);
        schnorr = Schnorr.fromPrivateCertificate(user.getSchnorr());
        String[] params = new String[10];
        params[0] = user.getPseudonym();
        params[1] = schnorr.getCertificate();
        RU = schnorr.getRandom();
        //user.setRU(RU.toString());
        params[2] = Cryptography.hash(RU.toString());
        BigInteger Hu = schnorr.getPower(RU);
        schnorr.getServiceQuery();
        params[3] = Hu.toString();
        params[4] = schnorr.getA_1().toString();
        params[5] = schnorr.getA_2().toString();
        int ltime = Integer.parseInt(Lifetime)*24*3600*1000;
        params[6] =  "" + ltime;
        params[7] = Category;
        params[8] = ExpDate;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date date = new Date();
        System.out.println("PURDATE:" + dateFormat.format(date));
        params[9] = dateFormat.format(date);
        // userRepository.save(user);
        user.save();
        return getServiceMessage(params);
    }

    public String solveChallenge(String input, String[] services) {
        String c = "";
        try {
            JSONObject json = new JSONObject(input);
            c = json.getString("c");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        try{
            JSONObject json = new JSONObject(params);
            long id = json.getLong("Sn");
            MCityPass mCityPass = new MCityPass();
            mCityPass.setCategory(json.getString("Category"));
            mCityPass.setHRI(json.getString("hRI"));
            mCityPass.setHRU(json.getString("hRU"));
            mCityPass.setHu(json.getString("Hu"));
            mCityPass.setExpDate(json.getString("expDate"));
            mCityPass.setPurDate(json.getString("purDate"));
            mCityPass.setLifeTime(json.getString("Lifetime"));
            mCityPass.setsN(id);
            mCityPass.setTermsAndConditions("TermsAndConditions");
            JSONArray jsonArray = json.getJSONArray("Services");

            System.out.println("MCITYPASS PURCHASED: " +mCityPass.save());
            List<Counter> counterList = new ArrayList<Counter>();
            String service = "";
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject  = jsonArray.getJSONObject(i);
                if(i == 0){
                    service = jsonObject.getString("Service");
                }
                serviceAgentService.getServiceByName(jsonObject.getString("Service"));
                counterList.add(new Counter(serviceAgentService.getServiceAgent().getM(), mCityPass, serviceAgentService.getServiceAgent(), jsonObject.getString("Psi")));
            }
            System.out.println("Saving counters");
            counterService.saveCounters(counterList);

            rightOfUseService.initRightOfUse(mCityPass, K, RU.toString());



            /*
            	List<Counter> counters = new ArrayList<Counter>();
			for(int i = 0; i< services.length; i++ ){
				serviceAgentService.initService((services[i]));
				counters.add(new Counter(serviceAgentService.getServiceAgent().getM(), mCityPass, serviceAgentService.getServiceAgent(), psi[i]));
			}
             */
            /*
            JSONArray jsonArray = json.getJSONArray("Services");
            JSONObject serviceJSON = jsonArray.getJSONObject(0);
            */
            ServiceAgent serviceAgent = serviceAgentService.getServiceByName(service);
            secretValueService.saveSecretValue(new SecretValue(mCityPass,serviceAgent.getProvider(), random.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "Everything OK";
    }

    // MESSAGE PROCESSORS

    private String getServiceMessage(String[] params) {
        JSONObject json = new JSONObject();
        try {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    private String solveChallengeMessage(String[] psi, String[] services, String[] ws) {
        JSONObject json = new JSONObject();
        try {
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String message = json.toString();

        return message;
    }


    public void createCertificate() {

        crypto.initPrivateKey(Constants.PATH_PRIVATE_KEY);
        crypto.initPublicKey(Constants.PATH_PROVIDER_KEY);
    }

    ///////////////////////////////////////////////////////////////////////
    /////////////////// ACTIVATE PASS///////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    public String showPass(long sn) {
        JSONObject json = new JSONObject();
        try {
            json.put("Sn", sn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }


    public boolean getVerifyTicketConfirmation(String s) {
        System.out.println(s);
        Activation activation = new Activation();
        try {
            JSONObject json = new JSONObject(s);
            MCityPass mCityPass = new Select().from(MCityPass.class).where("Sn = ? ", json.getInt("PASS")).executeSingle();

            activation.setMCityPass(mCityPass);
            activation.setActDate(json.getString("ACTDate"));
            activation.setState(json.getString("State"));
            activation.setSignature(json.getString("Signature"));

            System.out.println("Activation saved at: " + activation.save());
            return true;
        } catch (JSONException e) {
            return false;
        }
    }
    ///////////////////////////////////////////////////////////////////////
    /////////////// PASS Verification///////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public String showTicket(long CityPassId, long serviceId) {
        // System.out.println(user.getPseudonym());
        // System.out.println(user.getSchnorr());
        System.out.println("Show Ticket");
        createCertificate();
        System.out.println(user.getSchnorr());
        schnorr = Schnorr.fromPrivateCertificate(user.getSchnorr());
        schnorr.getP();
        JSONObject json;
        JSONObject message = null;
        try {
            json = new JSONObject();
            json.put("A3", schnorr.sendRequest());
            mCityPassService.initMCityPass(CityPassId);
            serviceAgentService.initService(serviceId);
            counterService.initCounter(mCityPassService.getMCityPass(), serviceAgentService.getServiceAgent());
            // Necessary without database
            activationService.initActivation(mCityPassService.getMCityPass());
            //
            rightOfUseService.initRightOfUseByCityPass(mCityPassService.getMCityPass());
            if(counterService.getCounter().getCounter()>0){
                System.out.println("Service: " + counterService.getCounter().getService().getName());
                json.put("PASS", mCityPassService.getMCityPass().getId());
                json.put("ACT", activationService.getActivation().getId());
                json.put("CERT", schnorr.getCertificate());
                json.put("SERVICE", counterService.getCounter().getService().getId());
                // json.put("Signature", crypto.getSignature(json.toString()));
                String signature = crypto.getSignature(json.toString());
                message = new JSONObject();
                message.put("m1", json.toString());
                message.put("Signature", signature);
            } else {
                System.out.println("This ticket has already been used");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(message.toString());
        return message.toString();
    }


    public String solveVerifyChallenge(String params) {
        System.out.println("Solve Verify Challenge");
        JSONObject message;
        JSONObject json = null;
        try {
            message = new JSONObject(params);
            json = new JSONObject(message.getString("Challenge"));
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
            json.put("w3", crypto.encryptWithPublicKey(schnorr.answerChallenge(new BigInteger(rightOfUseService.getRightOfUse().getRU())).toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public String showProof(String params) {
        System.out.println("Show proof");
//        crypto.initPrivateKey("cert/user/private_USER.der");
        JSONObject message = null;
        JSONObject json = null;
        try {
            // System.out.println(message.getString("Signature"));
            message = new JSONObject(params);
            json = new JSONObject(message.getString("Vsucc"));
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

           // kandRI = getKandRI();
            BigInteger K = new BigInteger(rightOfUseService.getRightOfUse().getK().getBytes());
            System.out.println("K: " + K.toString());
            BigInteger PRNG = schnorr.getPower(K);
            System.out.println("PRNG" + PRNG.toString());
            secretValueService.initSecretValue(mCityPassService.getMCityPass());
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message.toString();
    }

    public boolean getValidationConfirmation(String params) {
        System.out.println("Get Validation Confirmation");
        JSONObject message = null;
        JSONObject json = null;
        try {
            // System.out.println(message.getString("Signature"));
            message = new JSONObject(params);
            json = new JSONObject(message.getString("R"));
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
            BigInteger PRNG = schnorr.getPower(new BigInteger(Cryptography.hash(rightOfUseService.getRightOfUse().getK()).getBytes()));
            BigInteger RI = Ap.xor(PRNG);
            if (Cryptography.hash(RI.toString()).equals(mCityPassService.getMCityPass().getHRI())) {
                System.out.println("true");
                counterService.updateCounter();
                return true;
            } else {
                System.out.println("false");
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////
    /////////////// PASS Verification M-times//////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public void initValues(long CityPassId, long serviceId) {
        mCityPassService.initMCityPass(CityPassId);
        serviceAgentService.initService(serviceId);
        counterService.initCounter(mCityPassService.getMCityPass(), serviceAgentService.getServiceAgent());
        secretValueService.initSecretValue(mCityPassService.getMCityPass());
        // Necessary without database
        activationService.initActivation(mCityPassService.getMCityPass());
        //
        rightOfUseService.initRightOfUseByCityPass(mCityPassService.getMCityPass());

    }

    public String showMTicket() {
        System.out.println("Show M Ticket");
        createCertificate();
        schnorr = Schnorr.fromPrivateCertificate(user.getSchnorr());
        JSONObject json = null;
        JSONObject message = null;
        try {
            json = new JSONObject();
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
            message = new JSONObject();
            message.put("m1", json.toString());
            message.put("Signature", signature);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message.toString();
    }

    public String solveMVerifyChallenge(String params) {
        System.out.println("Solve Verify Challenge");
        JSONObject message = null;
        JSONObject json = null;
        try {
            message = new JSONObject(params);
            json = new JSONObject(message.getString("Challenge"));
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
            json.put("w3", crypto.encryptWithPublicKey(schnorr.answerChallenge(new BigInteger(rightOfUseService.getRightOfUse().getRU())).toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        JSONObject message = null;
        JSONObject json = null;
        try {
            message = new JSONObject(params);
            json = new JSONObject(message.getString("Vsucc"));
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
            //kandRI = getKandRI();
            BigInteger K = new BigInteger(rightOfUseService.getRightOfUse().getK().getBytes());
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message.toString();
    }

    public boolean getVerifyMTicketConfirmation(String params) {
        System.out.println("Get Validation Confirmation");
        JSONObject message = null;
        JSONObject json = null;

        try {
            message = new JSONObject(params);
            json = new JSONObject(message.getString("R"));
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
            BigInteger PRNG = schnorr.getPower(new BigInteger(Cryptography.hash(rightOfUseService.getRightOfUse().getK()).getBytes()));
            BigInteger RI = Ap.xor(PRNG);
            if (Cryptography.hash(RI.toString()).equals(mCityPassService.getMCityPass().getHRI())) {
                System.out.println("true");
                counterService.updateCounter(hash);
                return true;
            } else {
                System.out.println("false");
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;

    }

    private String getA(String PRNG) {
        String PRNG32 = Cryptography.hash(PRNG);
        // String PRNG32 = Cryptography.hash("Text 1");
        System.out.println("USER PRNG: " + PRNG32);
        System.out.println("USER Hash: " + hash);
        // System.out.println("USER hash hash: " + Cryptography.hash(hash) );
        byte[] PRNGbytes = null;
        byte[] HASHbytes = null;

        PRNGbytes = Base64.decode(PRNG32.getBytes(), Base64.DEFAULT);
        HASHbytes = Base64.decode(hash.getBytes(), Base64.DEFAULT);

        byte[] xor = new byte[PRNGbytes.length];
        for (int i = 0; i < PRNGbytes.length; i++) {
            xor[i] = (byte) (PRNGbytes[i] ^ HASHbytes[i]);
        }

        return new String(Base64.encodeToString(xor, Base64.DEFAULT)).replace("\n", "");
    }

    ///////////////////////////////////////////////////////////////////////
    /////////////// PASS Verification inf-times////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public String showInfinitePass(long CityPassId, long serviceId) {
        System.out.println("Show Inf Ticket");
        createCertificate();
        schnorr = Schnorr.fromPrivateCertificate(user.getSchnorr());
        JSONObject json = null;
        JSONObject message = null;
        try {
            json = new JSONObject();
            mCityPassService.initMCityPass(CityPassId);
            serviceAgentService.initService(serviceId);
            counterService.initCounter(mCityPassService.getMCityPass(), serviceAgentService.getServiceAgent());
            // Necessary without database
            activationService.initActivation(mCityPassService.getMCityPass());
            //
            rightOfUseService.initRightOfUseByCityPass(mCityPassService.getMCityPass());
            System.out.println("Service: " + counterService.getCounter().getService().getName());
            json.put("A3", schnorr.sendRequest());
            json.put("PASS", mCityPassService.getMCityPass().getId());
            json.put("ACT", activationService.getActivation().getId());
            json.put("CERT", schnorr.getCertificate());
            json.put("SERVICE", counterService.getCounter().getService().getId());

            String signature = crypto.getSignature(json.toString());
            message = new JSONObject();
            message.put("m1", json.toString());
            message.put("Signature", signature);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message.toString();
    }

    public String solveInfiniteVerifyChallenge(String params) {
        System.out.println("Solve Verify Challenge");
        JSONObject message = null;
        JSONObject json = null;
        try {
            message = new JSONObject(params);
            json = new JSONObject(message.getString("Challenge"));
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
            json.put("w3", crypto.encryptWithPublicKey(schnorr.answerChallenge(new BigInteger(rightOfUseService.getRightOfUse().getRU())).toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public String showInfiniteProof(String params) {
        System.out.println("Show proof");

        JSONObject message = null;
        JSONObject json = null;
        try {
            message = new JSONObject(params);
            json = new JSONObject(message.getString("Vsucc"));
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

            ///kandRI = getKandRI();
            BigInteger K = new BigInteger(rightOfUseService.getRightOfUse().getK().getBytes());
            BigInteger PRNG = schnorr.getPower(K);
            System.out.println("PRNG" + PRNG.toString());
            secretValueService.initSecretValue(mCityPassService.getMCityPass());
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message.toString();
    }

    public boolean getInfiniteValidationConfirmation(String params) {
        System.out.println("Get Validation Confirmation");
        JSONObject message = null;
        JSONObject json = null;

        try {
            message = new JSONObject(params);
            json = new JSONObject(message.getString("R"));
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
            BigInteger PRNG = schnorr.getPower(new BigInteger(Cryptography.hash(rightOfUseService.getRightOfUse().getK()).getBytes()));
            BigInteger RI = Ap.xor(PRNG);
            if (Cryptography.hash(RI.toString()).equals(mCityPassService.getMCityPass().getHRI())) {
                System.out.println("true");
                counterService.updateInfiniteCounter();
                return true;
            } else {
                System.out.println("false");
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
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
        JSONObject json = null;
        boolean verify = false;
        try {
            json = new JSONObject(param);
            String signature = json.getString("Signature");
            json.remove("Signature");
            verify = crypto.getValidation(json.toString(), signature);
            System.out.println("Validating signature: " + verify);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        // crypto.initPrivateKey("cert/ttp/private_TTP.{der");
        //rightOfUseService.initRightOfUse(mCityPassService.getMCityPass());
        JSONObject json = null;
        String[] params = null;
        try {
            json = new JSONObject(rightOfUseService.getRightOfUse().getK());
            params = new String[2];
            params[0] = json.getString("K");
            params[1] = json.getString("RI");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }

    ////////// STATIC METHODS///////////
    private static String generatePseudonym(String y, String signature, long Id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("y", y);
            jsonObject.put("signature", signature);
            jsonObject.put("Sn", Id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String getYu(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);

            return jsonObject.getString("y");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    /* (non-Javadoc)
     * @see  accestur.secom.core.service.UserServiceInterface#showMTicket(long, long)
     */
    @Override
    public String showMTicket(long CityPassId, long serviceId) {
        // TODO Auto-generated method stub
        return null;
    }

}
