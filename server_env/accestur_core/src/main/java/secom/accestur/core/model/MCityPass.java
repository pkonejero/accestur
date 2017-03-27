package secom.accestur.core.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mcitypassEntity")
public class MCityPass extends DomainObjectModel{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
//	//Type
	
	private Long lifeTime;
	private String category;
	private String termsAndContions;
//	
//	//User
//	private String PSEUu;
//	
//	//Dates
//	private Date purDate;
//	private Date expDate;
//	
//	
//	//Hash of All Secret numbers
//	private List<String> hRI;
//		
//	//Hash of the ownership of the pass
//	private String hRU;
//
//	
//	private List<List<String>> reusableServices;
//	private List<String> nonReusableServices;
//	private List<String> infiniteReusableServices;
 	

	public MCityPass(){}


	public Long getLifeTime() {
		return lifeTime;
	}


	public void setLifeTime(Long lifeTime) {
		this.lifeTime = lifeTime;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getTermsAndContions() {
		return termsAndContions;
	}


	public void setTermsAndContions(String termsAndContions) {
		this.termsAndContions = termsAndContions;
	}
	
	
	
}

