package accestur.secom.core.api;

/**
 * Created by Sebastià on 24/4/2017.
 */
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProviderAPI {
    /*
        @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<User> createUser(@Body User user);

     */

    @POST("provider/verifyPass")
    Call<String> verifyPass(@Body String json);

    @POST("provider/verifyPass2")
    Call<String> verifyPass2(@Body String json);

    @POST("provider/verifyProof")
    Call<String> verifyProof(@Body String json);

    @POST("provider/verifyMPass")
    Call<String> verifyMPass(@Body String json);

    @POST("provider/verifyMPass2")
    Call<String> verifyMPass2(@Body String json);

    @POST("provider/verifyMProof")
    Call<String> verifyMProof(@Body String json);

    @POST("provider/verifyInfinitePass")
    Call<String> verifyInfPass(@Body String json);

    @POST("provider/verifyInfinitePass2")
    Call<String> verifyInfPass2(@Body String json);

    @POST("provider/verifyInfiniteProof")
    Call<String> verifyInfProof(@Body String json);




}
