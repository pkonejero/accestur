package accestur.secom.core.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Sebastià on 1/6/2017.
 */

public interface TTPAPI {

    @POST("ttp/generatePseudonym")
    Call<String> generatePseudonym(@Body String json);
}
