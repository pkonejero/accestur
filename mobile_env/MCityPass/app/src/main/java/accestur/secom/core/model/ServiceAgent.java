package accestur.secom.core.model;


/*import com.raizlabs.android.dbflow.annotation.OneToMany;
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
@Table(name = "serviceAgent")
public class ServiceAgent extends Model {//  extends BaseModel{

	//@PrimaryKey
	//private Long id;

	@Column
	private String provider;


    public List<Counter> counters (){
        return getMany(Counter.class, "service");
    }


	//@OneToMany(methods = {OneToMany.Method.ALL}, variableName = "counters")
/*	public List<Counter> getCounters(){
		if (counters == null || counters.isEmpty()) {
			//counters = SQLite.select().from(Counter.class).where(Counter_Table.service_id.eq(id)).queryList();
		}

		return counters;
	}*/

	@Column
	private String name;

	@Column
	private int m;

	@Column
	private String indexHash;
	
	public ServiceAgent( String indexHash, int m, String name){
		//this.sN = sN;
		this.indexHash = indexHash;
		this.m = m;
		this.name = name;
	}

	public ServiceAgent(String name, int m, String indexHash, String provider){
		super();
		this.name = name;
		this.m = m;
		this.indexHash = indexHash;
		this.provider = provider;
		//this.counters = counters;
	}

	public ServiceAgent(){}

	//public void setId(Long id) {
	//	this.sN = id;
	//}

	public String getProvider(){
		return provider;
	}

	public void setProvider(String provider){
		this.provider = provider;
	}

//	public void setCounters(List<Counter> counters){
	//	this.counters = counters;
	//}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public int getM(){
		return m;
	}

	public void setM(int m){
		this.m = m;
	}

	public String getIndexHash(){
		return indexHash;
	}

	public void setIndexHash(String indexHash){
		this.indexHash = indexHash;
	}

	//public Long getId() {
		//return id;
	//}
}