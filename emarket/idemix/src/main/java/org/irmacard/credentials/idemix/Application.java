package org.irmacard.credentials.idemix;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.irmacard.credentials.CredentialsException;
import org.irmacard.credentials.idemix.info.IdemixKeyStore;
import org.irmacard.credentials.idemix.info.IdemixKeyStoreDeserializer;
import org.irmacard.credentials.idemix.messages.IssueCommitmentMessage;
import org.irmacard.credentials.idemix.messages.IssueSignatureMessage;
import org.irmacard.credentials.idemix.proofs.ProofD;
import org.irmacard.credentials.info.DescriptionStore;
import org.irmacard.credentials.info.DescriptionStoreDeserializer;
import org.irmacard.credentials.info.InfoException;
import org.irmacard.credentials.info.IssuerIdentifier;
import org.irmacard.credentials.info.KeyException;

public class Application {

	public static void main(String[] args) throws CredentialsException {		
		// Issue
		List<BigInteger> attributes = Arrays.asList(new BigInteger(1, "ATT_1".getBytes()),
				new BigInteger(1, "ATT_2".getBytes()), new BigInteger(1, "ATT_3".getBytes()),
				new BigInteger(1, "ATT_4".getBytes()));
		
		IdemixSecretKey sk = null;
		IdemixPublicKey pk = null;

		try {
			URI core = Application.class.getClassLoader().getResource("irma_configuration/").toURI();
			DescriptionStore.initialize(new DescriptionStoreDeserializer(core));
			IdemixKeyStore.initialize(new IdemixKeyStoreDeserializer(core));

			IssuerIdentifier mo = new IssuerIdentifier("irma-demo.MijnOverheid");
			sk = IdemixKeyStore.getInstance().getSecretKey(mo, 1);
			pk = IdemixKeyStore.getInstance().getPublicKey(mo, 1);
		} catch (InfoException | KeyException | URISyntaxException e) {
			e.printStackTrace();
		}
		
		// R_1
		IdemixSystemParameters params = pk.getSystemParameters();
		BigInteger context = new BigInteger(params.get_l_h(), new Random());
		BigInteger n_1 = new BigInteger(params.get_l_statzk(), new Random());
		CredentialBuilder cb = new CredentialBuilder(pk, attributes, context);

		// R_2
		BigInteger secret = new BigInteger(params.get_l_m(), new Random());
		IssueCommitmentMessage commit_msg = cb.commitToSecretAndProve(secret, n_1);

		// R_3
		IdemixIssuer issuer = new IdemixIssuer(pk, sk, context);
		IssueSignatureMessage msg;
		msg = issuer.issueSignature(commit_msg, attributes, n_1);
		
		IdemixCredential cred = cb.constructCredential(msg);

		// Prove
		List<Integer> disclosed = Arrays.asList(1,2,3,4);
		ProofD proof = cred.createDisclosureProof(disclosed, context, n_1);
		
		// Verify
		if(proof.verify(pk, context, n_1)) {
			System.out.println("Proof VALID !!!");
			Iterator<Entry<Integer, BigInteger>> entries = proof.getDisclosedAttributes().entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
			    BigInteger value = (BigInteger)entry.getValue();
			    System.out.println(new String(value.toByteArray()));
			}	
		}
		else {
			System.out.println("Proof NOT VALID !!!");
		}
	}
	
}