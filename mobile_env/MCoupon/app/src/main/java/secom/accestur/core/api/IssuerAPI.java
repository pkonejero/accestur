package secom.accestur.core.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gornals on 13/07/2017.
 */

public class IssuerAPI {

    @POST("issuer/setUserCoupon")
    Call<String> setUserCoupon(@Body String json) {
        return null;
    }

}
