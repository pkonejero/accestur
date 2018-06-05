/**
 * 
 */
package secom.accestur.core.model;


import javax.persistence.*;
/**
 * @author Sebastià
 *
 */
@Entity
@Table(name="secretvalueEntity")
public class SecretValue extends DomainObjectModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "PROVIDER_ID")
	private Provider provider;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MCITYPASS_ID")
	private MCityPass mCityPass;
	
	private String secret;
	
	public SecretValue(){}

	
	public SecretValue(MCityPass mCityPass, Provider provider, String secret){
		this.mCityPass = mCityPass;
		this.provider = provider;
		this.secret = secret;
	}


	public Provider getProvider() {
		return provider;
	}


	public void setProvider(Provider provider) {
		this.provider = provider;
	}


	public MCityPass getmCityPass() {
		return mCityPass;
	}


	public void setmCityPass(MCityPass mCityPass) {
		this.mCityPass = mCityPass;
	}


	public String getSecret() {
		return secret;
	}


	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	
	

}
