package secom.accestur.mcoupon.activity.content;

public class MCouponItem
{
	private String info_1;
	private String info_2;
	private String info_3;

	public MCouponItem(String de, String asunto, String texto){
		this.info_1 = de;
		this.info_2 = asunto;
		this.info_3 = texto;
	}
	
	public String getDe(){
		return info_1;
	}
	
	public String getAsunto(){
		return info_2;
	}
	
	public String getTexto(){
		return info_3;
	}
}
