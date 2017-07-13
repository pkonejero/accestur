package secom.accestur.core.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gornals on 13/07/2017.
 */

public class MerchantAPI {

    @POST("/merchant/getChallengeRedeem")
    Call<String> getChallengeRedeem(@Body String json) {
        return null;
    }

    @GET("/merchant/getParamsRedeem")
    Call<String> getParamsRedeem() {
        return null;
    }

}
