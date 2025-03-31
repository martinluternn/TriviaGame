package martinlutern.triviagame.util.api;

import martinlutern.triviagame.util.api.model.CategoryResponseModel;
import martinlutern.triviagame.util.api.model.ContentCategoryResponseModel;
import martinlutern.triviagame.util.api.model.RequestSessionResponseModel;
import martinlutern.triviagame.util.api.model.ResetSessionResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by martinluternainggolan on 10/1/17.
 */

public interface APIService {
    @GET("api_token.php?command=request")
    Call<RequestSessionResponseModel> requestSession();

    @GET("api_token.php?command=reset")
    Call<ResetSessionResponseModel> resetSession(@Query("token") String token);

    @GET("api_category.php")
    Call<CategoryResponseModel> getCategory();

    @GET("api.php")
    Call<ContentCategoryResponseModel> getContentCategory(@Query("amount") String amount,
                                                          @Query("category") String category,
                                                          @Query("difficulty") String difficulty,
                                                          @Query("type") String type,
                                                          @Query("token") String token);
}
