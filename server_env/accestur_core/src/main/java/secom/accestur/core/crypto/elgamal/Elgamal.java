package secom.accestur.core.crypto.elgamal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import secom.accestur.core.utils.Constants;


@Component("elGamal")
public class Elgamal{
	private int nbits;
	private Elgamal_KeySet kset;

	public Elgamal(){
		this.kset=new Elgamal_KeySet(Constants.ELGAMAL_DEFAULT_BITS);
	}

	public Elgamal(int nb_bits){
		this.nbits=nb_bits;
		Elgamal_Parameters params=new Elgamal_Parameters(this.nbits,new SecureRandom());
		this.kset=new Elgamal_KeySet(this.nbits);
	}

	public Elgamal(Elgamal_KeySet _keys,int nbb){
		this.kset=_keys;
		this.nbits=nbb;
	}
	 public Elgamal(String[] params) {
	    this.kset = Elgamal_KeySet.newKset(params);
	    }
	    
	 public Elgamal(String s) {
	     this.kset = new Elgamal_KeySet(s);
	 }

	public static BigInteger getPrime(int nb_bits, Random rng){
		BigInteger fois=new BigInteger("2");
		BigInteger p_prime,p;
		do{
			p_prime=BigInteger.probablePrime(nb_bits,rng);
			p=p_prime.multiply(fois).add(BigInteger.ONE);
		}while(!p.isProbablePrime(100));
		return p;
	}

	public static BigInteger getPrime_cert(int nb_bits, Random rng,int cert){
		BigInteger fois=new BigInteger("2");
		BigInteger p_prime,p;
		do{
			p_prime=BigInteger.probablePrime(nb_bits-1,rng);
			p=p_prime.multiply(fois).add(BigInteger.ONE);
		}while(!p.isProbablePrime(cert));

		return p;
	}

	public Elgamal_CipherText encrypt(Elgamal_PlainText pt){
		BigInteger modulo=kset.getPk().getP();
		Elgamal_CipherText ct;
		BigInteger mhr[]=new BigInteger[pt.getPt().length];
		BigInteger r;
		do{
			r = new BigInteger(modulo.bitCount()-1, new SecureRandom());
		}while(kset.getPk().getP().compareTo(r)==-1);

		for(int i=0;i<pt.getPt().length;i++){
			if(pt.getPt()[i].compareTo(modulo)==1){
				//System.out.println("mod "+ modulo+" bytes  "+modulo.bitCount()+" "+pt.getPt()[i]);
				System.out.println("Plain text superieure a N");
				System.exit(1);
			} 
			//System.out.println("mod "+ modulo+" bytes  "+modulo.bitCount()+" "+pt.getPt()[i]);
			mhr[i]=(pt.getPt()[i].multiply(kset.getPk().getH().modPow(r, modulo))).mod(modulo);
		}
		BigInteger gr=kset.getPk().getG().modPow(r,modulo);
		ct=new Elgamal_CipherText(mhr,gr);
		return ct;
	}

	public Elgamal_CipherText encrypt(String s){
		Elgamal_CipherText cipherT;
		byte bytes[]=null;
		try {
			bytes = s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(Elgamal.class.getName()).log(Level.SEVERE, null, ex);
		}
		//System.out.print(bytes.length+"\n");
		byte[][] chuncked =divideArray(bytes, 31);
		//System.out.println("remainder is "+ s.length()%127);
		//last byte[]. we have to delete zeros in the end!
		byte lastchunck[]=new byte[s.length()%31];
		int j=0;
		for(int i=0;i<chuncked[0].length;i++){
			if(chuncked[chuncked.length-1][i]!=(byte)0){
				lastchunck[j]=(byte)(chuncked[chuncked.length-1][i]);
				j++;
			}
		}
		// convert to biginteger!
		BigInteger[] chuncks=new BigInteger[chuncked.length];
		for(int w=0;w<chuncks.length-1;w++){
			chuncks[w]=new BigInteger(chuncked[w]);
			//System.out.print(new String(chuncks[w].toByteArray()));
		}
		//convert last chunk
		chuncks[chuncks.length-1]=new BigInteger(lastchunck);
		// System.out.println(new String(chuncks[chuncks.length-1].toByteArray()));
		//encrypt
		cipherT=encrypt(new Elgamal_PlainText(chuncks));
		return cipherT;
	}

	public Elgamal_PlainText decrypt(Elgamal_CipherText ct){
		Elgamal_PlainText pt;
		BigInteger mod=kset.getPk().getP();
		BigInteger tmp;
		BigInteger plain[]=new BigInteger[ct.getCt().length];
		for(int i=0;i<plain.length;i++){
			tmp=ct.getGr().modPow(kset.getSk().getX(), mod);
			plain[i]=ct.getCt()[i].multiply(tmp.modInverse(mod)).mod(mod);
		}
		//BigInteger s=ct.getGr().modPow(kset.getSk().getX(), kset.getPk().getP());
		//BigInteger decrypt=ct.getCt().multiply(s.modInverse(kset.getPk().getP())).mod(kset.getPk().getP());
		pt=new Elgamal_PlainText(plain);
		return pt;
	}

	public static byte[][] divideArray(byte[] source, int chunksize) {
		byte[][] ret = new byte[(int)Math.ceil(source.length / (double)chunksize)][chunksize];
		int start = 0;
		for(int i = 0; i < ret.length; i++) {
			ret[i] = Arrays.copyOfRange(source,start, start + chunksize);
			start += chunksize ;
		}
		return ret;
	}

	public String Elgamal_PtToString(Elgamal_PlainText pt){
		String res="";
		for(int i=0;i<pt.getPt().length;i++){
			res+=new String(pt.getPt()[i].toByteArray());
		}
		return res;
	}

	public static ArrayList<BigInteger> ordre(BigInteger p){
		BigInteger factor1=new BigInteger("2");
		ArrayList<BigInteger> list = new ArrayList<BigInteger>();
		BigInteger factor2=(p.subtract(BigInteger.ONE)).divide(factor1);
		list.add(null);
		System.out.println("here  "+factor1+"  "+factor2);
		BigInteger i=BigInteger.ONE;
		BigInteger ordre;
		boolean found=false;
		while(i.compareTo(p)==-1){
			if(i.mod(factor1)==BigInteger.ZERO ||i.mod(factor2)==BigInteger.ZERO){
				list.add(null);
			}else{
				ordre=BigInteger.ONE;
				found=false;
				while(!found){
					if(i.modPow(ordre,p).compareTo(BigInteger.ONE)==0){
						found=true;
						list.add(ordre);
					}else{
						ordre=ordre.add(BigInteger.ONE);
					}
				}
			}     
			i=i.add(BigInteger.ONE);
		}
		return list;
	}
	
	
	public void createPublicCertificate() {
        try {
            //Whatever the file path is.
            File statText = new File("publicCertificate.txt");
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(kset.getPk().toJson());
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file statsTest.txt");
        }

    }

    public void createPrivateCertificate() {
        try {
            //Whatever the file path is.
            File statText = new File("privateCertificate.txt");
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(kset.getPk().toJson()+"\n");
            w.write(kset.getSk().toJson()+"\n");
            w.write("params"+"\n");
            w.write("" + kset.getNb_bits()+"\n");
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file statsTest.txt");
        }
    }
    
    
    public void modifyPublicKey(String s) {
    	kset = new Elgamal_KeySet(s);
    }
    
    public void modifyKset(String[] params){
    	kset = Elgamal_KeySet.newKset(params);
    }

    public static String readPublicCertificate()  {
        String params = "";
        try (BufferedReader br = new BufferedReader(new FileReader("publicCertificate.txt"))) {
            params = br.readLine();
    
        } catch (Exception ex) {
            Logger.getLogger(Elgamal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }
        return params;
    }
    
      public static String[] readPrivateCertificate()  {
        String params[] = new String[4];
        try (BufferedReader br = new BufferedReader(new FileReader("privateCertificate.txt"))) {
            String line = br.readLine();
            int i = 0;
            while (line != null && i <= 3) {
                params[i] = line;
                line = br.readLine();
                System.out.println(params[i]);
                i++;
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Elgamal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }
        return params;
    }
}