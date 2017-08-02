package secom.accestur.mcoupon.activity.Queries;

import android.content.Context;
import android.util.Log;

//import com.example.sebasti.basic.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Sebastià on 26/10/2016.
 */

public abstract class QueryUtils{
    public static final String address = "http://localhost:8080/";



    //private static CertificateFactory cf;
    private static InputStream caInput;
    //private static Certificate ca;
    //public static SSLContext contextSSL;

//    public static void setSecurity(Context context) {
//        try {
//            cf = CertificateFactory.getInstance("X.509");
//            caInput = context.getResources().openRawResource(R.raw.ca_bundle);
//// From https://www.washington.edu/itconnect/security/ca/load-der.crt
//            try {
//                ca = cf.generateCertificate(caInput);
//                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
//            } finally {
//                caInput.close();
//            }
//
//// Create a KeyStore containing our trusted CAs
//            String keyStoreType = KeyStore.getDefaultType();
//            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
//            keyStore.load(null, null);
//            keyStore.setCertificateEntry("ca", ca);
//
//// Create a TrustManager that trusts the CAs in our KeyStore
//            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//            tmf.init(keyStore);
//
//// Create an SSLContext that uses our TrustManager
//            contextSSL = SSLContext.getInstance("TLS");
//            contextSSL.init(null, tmf.getTrustManagers(), null);
//            Log.v("Network", "Certificate created");
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }
//
//    }

//    public static String doPost(String queryUrl, String jsonPost){
//        String jsonResponse = null;
//        String finalAddress = address + queryUrl;
//        try {
//            URL url = new URL(finalAddress);
//            HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
//            httpURLConnection.setSSLSocketFactory(contextSSL.getSocketFactory());
//            httpURLConnection.setRequestMethod("POST");
//            httpURLConnection.setRequestProperty(keyCSFRTOKEN, cookieCSFRTOKEN);
//            httpURLConnection.setRequestProperty(keyCookie, cookie);
//            httpURLConnection.setRequestProperty("Referer", finalAddress);
//            // Sending post data
//            OutputStream outputPost = new BufferedOutputStream(httpURLConnection.getOutputStream());
//            outputPost.write(jsonPost.getBytes());
//            outputPost.flush();
//            Log.v("Network", "Sent Friend User Search");
//
//            if (httpURLConnection.getResponseCode() == 200) {
//
//                InputStreamReader in = new InputStreamReader(httpURLConnection.getInputStream());
//                BufferedReader br = new BufferedReader(in);
//                jsonResponse = br.readLine();
//                return jsonResponse;
//
//            } else {
//
//                System.out.println(httpURLConnection.getResponseCode());
//                Log.v("Network", "error");
//                return "Error";
//            }
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            return jsonResponse;
//        }
//    }

    public static String doGet(String queryUrl){
        String finalAddress = address +queryUrl;
        String json = null;
        try {
            URL url = new URL(finalAddress);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //httpURLConnection.setSSLSocketFactory(contextSSL.getSocketFactory());
            httpURLConnection.setRequestMethod("GET");
           // httpURLConnection.setRequestProperty(keyCSFRTOKEN, cookieCSFRTOKEN);
           // httpURLConnection.setRequestProperty(keyCookie, cookie);

            Log.v("Network", "Sent");
//            int responseCode = httpURLConnection.getResponseCode();
//            Log.v("Network", "Response code "+responseCode);
            //if (httpURLConnection.getResponseCode() == 200) {
                InputStreamReader in = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader br = new BufferedReader(in);
                json = br.readLine();
                Log.v("Network", "List of : "+json);
//            } else {
//                InputStreamReader in = new InputStreamReader(httpURLConnection.getErrorStream());
//                BufferedReader br = new BufferedReader(in);
//                String error = br.readLine();
//                while (error!=null){
//                    Log.v("Network", error);
//                    error = br.readLine();
//                }
           // }


        } catch (MalformedURLException m) {
            m.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return json;
        }
    };



}
