package secom.accestur.core.utils;

import android.os.Environment;

public abstract class Constants {
    public static final String PATH_PUBLIC_KEY =  Environment.getExternalStorageDirectory()+ "/DCIM/cert/user/public_USER.der";
    public static final String PATH_PRIVATE_KEY =  Environment.getExternalStorageDirectory()+ "/DCIM/cert/user/private_USER.der";
	public static final String PATH_ISSUER_KEY = Environment.getExternalStorageDirectory()+ "/DCIM/cert/issuer/public_ISSUER.der";
	public static final Integer PRIME_BITS = 512;
	public static final Integer PRIME_CERTAINTY = 40;
	public static final String LIFETIME = "" + 24*7*60*60*1000;
	public static final String CATEGORY = "Adult";
	public static final String EXPDATE = "26/07/2018";
	public static final String BASE_URL = "http://192.168.1.177:8080/";
    public static final String SHARE_URL = "http://";

}