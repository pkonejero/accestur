package accestur.secom.core.model;



/*import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;*/

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

import accestur.secom.core.AppDatabase;


//@Table( database = AppDatabase.class , allFields = true)
@Table(name = "user")
public class User extends Model {//extends BaseModel{

    //@PrimaryKey
	//private Long id;

	//@Column(length = 40000)
	@Column
	private String pseudonym;


	private List<MCityPass> mCityPass(){
        return getMany(MCityPass.class, "user");
    }



	@Column(length = 40000)
	private String schnorr;

	@Column
	private String RU;

	public String getRU() {
		return RU;
	}

	public void setRU(String rU) {
		RU = rU;
	}

	public User(){}
	
	public User(long id, String pseudonym, String schnorr){
		//this.id = id;
		this.pseudonym = pseudonym;
		this.schnorr = schnorr;
	}

 /*   public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

    public String getSchnorr() {
		return schnorr;
	}

	public void setSchnorr(String schnorr) {
		this.schnorr = schnorr;
	}



	//public void setMCityPass(List<MCityPass> mCityPass){
	//	this.mCityPass = mCityPass;
	//}

	public String getPseudonym(){
		return pseudonym;
	}

	public void setPseudonym(String pseudonym){
		this.pseudonym = pseudonym;
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if ( pseudonym != other.pseudonym)
			return false;
		return true;
	}

	@Override
	public String toString(){
		return "User [pseudonym=" + pseudonym + "]";
	}
}