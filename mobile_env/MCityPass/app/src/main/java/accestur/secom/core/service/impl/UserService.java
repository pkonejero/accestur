package accestur.secom.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.util.Base64;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import accestur.secom.core.crypto.Cryptography;
import accestur.secom.core.crypto.Schnorr;
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
        String schnorr = "{\"p\":\"15277160019691241425056343514263407750548889486111153986570545050251020131904394375904890848244527769038757280355403593200158044376264500469424023579806111987\",\"q\":\"8240107885486106486006657774683607200943306087438594383263508657093322616992661475676855905202010662911951068152860622006557736988276429595158588770121959\",\"g\":\"1514584839425919363525023347275785025835117601117443638644111269286983800930309213257206014157612761652599400252093511933272586856485729712397283986079989283\",\"x\":\"7708628560626367760465016407189387479320242873908245161782841436913570511985583732098641759217506298877394767011854554216545447918209312125411822909189291\",\"y\":\"14989039481374367292369303874278584668623867701174068970571531818536559077115464814862613693930941393933250033676427630417324282180404456732157669953456072214\"}";
        String pseudonym = "\"{\"signature\":\"SKWcc27uci691bCqyjqb29HgtVdgfUzqf9wnLrlQqDtRiw3Va4u7LVmxvi11LViyCua9Wthqq0Bh8Jky2Ly5Zcu46HOczadD78wrBN3pN0G2yq2mCv1/+ENhjHSv6c2ppC6Mm0nSrd4fNgY+/FzzYBMGvqvErIq/Bd8bEcDv+ThH60vbAqyHhzeomKpl0aNzmgOpdv0f/8SzZwvJbzPG+2xf6UIFccXiyU+PZbVryxVCVkDadXEC5gbV3uSsJuzznpdZqa646+v+0n2KgHSjTMjKUgoXkI0PxJlKYiOpTwDp7cjSu+QijgNvgcX2qn61QE6Ceps552U5H04b4VXrew==\",\"y\":\"14989039481374367292369303874278584668623867701174068970571531818536559077115464814862613693930941393933250033676427630417324282180404456732157669953456072214\"}";
        user = new User(1, pseudonym, schnorr);
        user.setRU(
                "12794049269479661747741922995769407597715574659446422944989680129048627175393339290248969736580625948742511551516917129928149840477131522749937199210210155");
    }

    @Override
    public String[] authenticateUser() {
        return new String[0];
    }

    @Override
    public boolean verifyPseudonym(String[] params) {
        return false;
    }

    @Override
    public String getService() {
        return null;
    }


    public void createCertificate() {

        crypto.initPrivateKey("cert/user/private_USER.der");
    }


    public String showTicket(long CityPassId, long serviceId) {
        // System.out.println(user.getPseudonym());
        // System.out.println(user.getSchnorr());
        System.out.println("Show Ticket");
        crypto.initPrivateKey("cert/user/private_USER.der");
        crypto.initPublicKey("cert/ttp/public_TTP.der");
        schnorr = Schnorr.fromPrivateCertificate(user.getSchnorr());
        JSONObject json;
        JSONObject message = null;
        try {
            json = new JSONObject();
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
            message = new JSONObject();
            message.put("m1", json.toString());
            message.put("Signature", signature);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(message.toString());
        return message.toString();
    }

    @Override
    public String showPass(long sn) {
        return null;
    }

    @Override
    public void getVerifyTicketConfirmation(String s) {

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
            json.put("w3", crypto.encryptWithPublicKey(schnorr.answerChallenge(new BigInteger(user.getRU())).toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public String showProof(String params) {
        System.out.println("Show proof");
        crypto.initPrivateKey("cert/user/private_USER.der");
        JSONObject message = null;
        JSONObject json = null;
        try {
            // System.out.println(message.getString("Signature"));
            message = new JSONObject(params);
            json = new JSONObject(message.getString("Challenge"));
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
            BigInteger PRNG = schnorr.getPower(new BigInteger(Cryptography.hash(kandRI[0]).getBytes()));
            BigInteger RI = Ap.xor(PRNG);
            if (Cryptography.hash(RI.toString()).equals(mCityPassService.getMCityPass().gethRI())) {
                System.out.println("true");
                return true;
            } else {
                System.out.println("false");
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return false;
        }
    }

    @Override
    public String solveChallenge(String c, String[] services) {
        return null;
    }

    @Override
    public String receivePass(String params) {
        return null;
    }

    ///////////////////////////////////////////////////////////////////////
    /////////////// PASS Verification M-times//////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public void initValues(long CityPassId, long serviceId) {
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
            json.put("w3", crypto.encryptWithPublicKey(schnorr.answerChallenge(new BigInteger(user.getRU())).toString()));
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
        crypto.initPrivateKey("cert/user/private_USER.der");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message.toString();
    }

    public String getVerifyMTicketConfirmation(String params) {
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
                counterService.updateCounter(hash, true);
                return "true";
            } else {
                System.out.println("false");
                return "false";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
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

        PRNGbytes = Base64.decode(PRNG32.getBytes(), Base64.DEFAULT);
        HASHbytes = Base64.decode(hash.getBytes(), Base64.DEFAULT);

        byte[] xor = new byte[PRNGbytes.length];
        for (int i = 0; i < PRNGbytes.length; i++) {
            xor[i] = (byte) (PRNGbytes[i] ^ HASHbytes[i]);
        }

        return new String(Base64.encodeToString(xor, Base64.DEFAULT));
    }

    ///////////////////////////////////////////////////////////////////////
    /////////////// PASS Verification inf-times////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public String showInfinitePass(long CityPassId, long serviceId) {
        System.out.println("Show Inf Ticket");
        crypto.initPrivateKey("cert/user/private_USER.der");
        crypto.initPublicKey("cert/ttp/public_TTP.der");
        schnorr = Schnorr.fromPrivateCertificate(user.getSchnorr());
        JSONObject json = null;
        JSONObject message = null;
        try {
            json = new JSONObject();
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
            json.put("w3", crypto.encryptWithPublicKey(schnorr.answerChallenge(new BigInteger(user.getRU())).toString()));
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
            BigInteger PRNG = schnorr.getPower(new BigInteger(Cryptography.hash(kandRI[0]).getBytes()));
            BigInteger RI = Ap.xor(PRNG);
            if (Cryptography.hash(RI.toString()).equals(mCityPassService.getMCityPass().gethRI())) {
                System.out.println("true");
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
        rightOfUseService.initRightOfUse(mCityPassService.getMCityPass());
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
    private static String generatePseudonym(String y, String signature) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("y", y);
            jsonObject.put("signature", signature);
         } catch (JSONException e){
        e.printStackTrace();
    }
        return jsonObject.toString();
    }

    public static String getYu(String json) {
        JSONObject jsonObject = null;
        try{
            jsonObject = new JSONObject(json);

            return jsonObject.getString("y");
        } catch (JSONException e){
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
