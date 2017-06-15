package accestur.secom.core.utils;

import android.os.Environment;

public abstract class Constants{
    public static final String PATH_PUBLIC_KEY =  Environment.getExternalStorageDirectory()+ "/DCIM/cert/public_USER.der";
    public static final String PATH_PRIVATE_KEY =  Environment.getExternalStorageDirectory()+ "/DCIM/cert/private_USER.der";
	public static final String PATH_PROVIDER_KEY = Environment.getExternalStorageDirectory()+ "/DCIM/cert/public_TTP.der";
	public static final String PATH_ISSUER_KEY = Environment.getExternalStorageDirectory()+ "/DCIM/cert/public_ISSUER.der";
	public static final Integer PRIME_BITS = 512;
	public static final Integer PRIME_CERTAINTY = 40;
	public static final String LOREM = "Lorem ipsum dolor sit amet.";
	public static final String LIFETIME = "" + 24*7*60*60*1000;
	public static final String CATEGORY = "Adult";
	public static final String EXPDATE = "08/06/2017";
	public static final String NOTUSED = "NOT USED";
	public static final String TERMS_AND_CONDITIONS = "Lorem ipsum dolor sit amet.";
	public static final String BASE_URL = "http://192.168.1.34:8080/";

}