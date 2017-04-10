/**
 * 
 */
package secom.accestur.core.crypto.Crypto;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Sebastià
 *
 */
@Component("cryptography")
@Scope("prototype")
public class Cryptography {

	// private String publicCertificate;
	//
	// private String privateCertificate;

	private PrivateKey privateKey;

	private PublicKey publicKey;

	public Cryptography() {
//		java.security.Security.addProvider(
//                new org.bouncycastle.jce.provider.BouncyCastleProvider()
//       );
	}

	// public String getPublicCertificate() {
	// return publicCertificate;
	// }
	//
	// public void setPublicCertificate(String publicCertificate) {
	// this.publicCertificate = publicCertificate;
	// }
	//
	// public String getPrivateCertificate() {
	// return privateCertificate;
	// }
	//
	// public void setPrivateCertificate(String privateCertificate) {
	// this.privateCertificate = privateCertificate;
	// }

	
	public void initPrivateKey(String filename) {
		try {
			privateKey = readPrivateKey(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initPublicKey(String filename) {
		try {
			publicKey = readPublicKey(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getSignature(String toSign){
		String signature = "";
		try {
			signature = sign(toSign, privateKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return signature;
	}
	
	public boolean getValidation(String toVerify, String signature){
		boolean validated = false;
		try {
			validated = verify(toVerify, signature, publicKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return validated;
	}

	public String encryptWithPublicKey(String toEncrypt) {
		byte[] secret = "".getBytes();
		try {
			byte[] message = toEncrypt.getBytes("UTF8");
			secret = encrypt(publicKey, message);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new String(Base64.getEncoder().encode(secret));
	}

	public String encryptWithPrivateKey(String toEncrypt) {
		byte[] secret = "".getBytes();
		try {
			byte[] message = toEncrypt.getBytes("UTF8");
			secret = encrypt(privateKey, message);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new String(Base64.getEncoder().encode(secret));

	}

	public String decryptWithPublicKey(String toDecrypt) {
		byte[] secret = Base64.getDecoder().decode(toDecrypt.getBytes());
		byte[] recovered_message = "".getBytes();
		String message = "";
		try {
			recovered_message = decrypt(publicKey, secret);
			message = new String(recovered_message, "UTF8");
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}

	public String decryptWithPrivateKey(String toDecrypt) {
		byte[] secret = Base64.getDecoder().decode(toDecrypt.getBytes());
		byte[] recovered_message = "".getBytes();
		String message = "";
		try {
			recovered_message = decrypt(privateKey, secret);
			message = new String(recovered_message, "UTF8");
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return message;
	}


	public static byte[] readFileBytes(String filename) throws IOException {
		Path path = Paths.get(filename);
		return Files.readAllBytes(path);
	}

	private PublicKey readPublicKey(String filename)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		return keyFactory.generatePublic(publicSpec);
	}

	private PrivateKey readPrivateKey(String filename)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

	private byte[] encrypt(PublicKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(plaintext);
	}

	private byte[] encrypt(PrivateKey key, byte[] plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(plaintext);
	}

	private byte[] decrypt(PrivateKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(ciphertext);
	}

	private byte[] decrypt(PublicKey key, byte[] ciphertext) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(ciphertext);
	}

	private String sign(String plainText, PrivateKey privateKey) throws Exception {
		Signature privateSignature = Signature.getInstance("SHA256withRSA");
		privateSignature.initSign(privateKey);
		privateSignature.update(plainText.getBytes("UTF-8"));

		byte[] signature = privateSignature.sign();

		return new String(Base64.getEncoder().encode(signature));
	}

	private boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
		Signature publicSignature = Signature.getInstance("SHA256withRSA");
		publicSignature.initVerify(publicKey);
		publicSignature.update(plainText.getBytes("UTF-8"));

		byte[] signatureBytes = Base64.getDecoder().decode(signature.getBytes());

		return publicSignature.verify(signatureBytes);
	}
	
	public static String hash(String text) {
		String hash = "";
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = digest.digest(text.getBytes("UTF-8"));
			hash = new String(Base64.getEncoder().encode(hashBytes));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hash;
		
	}

}
