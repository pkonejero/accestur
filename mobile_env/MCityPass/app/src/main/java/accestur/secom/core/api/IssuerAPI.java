package accestur.secom.core.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Sebastià on 1/6/2017.
 */

public interface IssuerAPI {



    @POST("issuer/getChallenge")
    Call<String> getChallenge(@Body String json);

    @POST("issuer/getPASS")
    Call<String> getPASS(@Body String json);

    @POST("issuer/verifyTicket")
    Call<String> verifyTicket(@Body String json);
}
