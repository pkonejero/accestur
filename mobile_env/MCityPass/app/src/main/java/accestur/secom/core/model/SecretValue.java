/**
 * 
 */
package accestur.secom.core.model;



/*import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;*/

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import accestur.secom.core.AppDatabase;

/**
 * @author Sebastià
 *
 */

//@Table( database = AppDatabase.class , allFields = true)
	@Table(name = "secretValue")
public class SecretValue extends Model {//extends BaseModel{

//    @PrimaryKey
	//private Long id;

	@Column
	private String provider;

  //  @ForeignKey
    @Column
	private MCityPass mCityPass;

	@Column
	private String secret;
	
	public SecretValue(){}

	
	public SecretValue(MCityPass mCityPass, String provider, String secret){
		this.mCityPass = mCityPass;
		this.provider = provider;
		this.secret = secret;
	}

    /*public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

    public String getProvider() {
		return provider;
	}


	public void setProvider(String provider) {
		this.provider = provider;
	}


	public MCityPass getMCityPass() {
		return mCityPass;
	}


	public void setMCityPass(MCityPass mCityPass) {
		this.mCityPass = mCityPass;
	}


	public String getSecret() {
		return secret;
	}


	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	
	

}
