package secom.accestur.core.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
/**
 * Created by gornals on 13/07/2017.
 */

public interface ManufacturerAPI {

    @POST("manufacturer/getChallengeRegister")
    Call<String> getChallengeRegister(@Body String json);

    @GET("manufacturer/getParamsCoupon")
    Call<String> getParamsCoupon();

    @POST("manufacturer/login")
    Call<String> logIn(@Body String json);
}
