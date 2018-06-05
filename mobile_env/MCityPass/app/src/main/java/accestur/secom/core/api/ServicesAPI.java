package accestur.secom.core.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by Sebastià on 1/6/2017.
 */

public interface ServicesAPI {

    @GET("services/getServices")
    Call<String> getServices();

}
