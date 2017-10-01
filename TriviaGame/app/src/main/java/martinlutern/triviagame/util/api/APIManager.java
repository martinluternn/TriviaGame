package martinlutern.triviagame.util.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import martinlutern.triviagame.util.api.model.CategoryResponseModel;
import martinlutern.triviagame.util.api.model.ContentCategoryResponseModel;
import martinlutern.triviagame.util.api.model.RequestSessionResponseModel;
import martinlutern.triviagame.util.api.model.ResetSessionResponseModel;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by martinluternainggolan on 10/1/17.
 */

public class APIManager {
    public enum APIRoute {
        GET_CATEGORY, REQUEST_SESSION, RESET_SESSION, GET_CONTENT_CATEGORY
    }

    private static APIManager instance;
    private Retrofit restAdapter;

    /**
     * Returns singleton class instance
     */
    public static APIManager getInstance() {
        if (instance == null) {
            synchronized (APIManager.class) {
                if (instance == null) {
                    instance = new APIManager();
                }
            }
        }
        return instance;
    }

    public APIManager() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        restAdapter = new Retrofit.Builder()
                .baseUrl(APIRoot.HOST)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public boolean getCategory(Callback<CategoryResponseModel> callback) {
        APIService apiService = restAdapter.create(APIService.class);
        Call<CategoryResponseModel> callAPIService = apiService.getCategory();
        callAPIService.enqueue(callback);
        return true;
    }

    public boolean requestSession(Callback<RequestSessionResponseModel> callback) {
        APIService apiService = restAdapter.create(APIService.class);
        Call<RequestSessionResponseModel> callAPIService = apiService.requestSession();
        callAPIService.enqueue(callback);
        return true;
    }

    public boolean resetSession(String token, Callback<ResetSessionResponseModel> callback) {
        APIService apiService = restAdapter.create(APIService.class);
        Call<ResetSessionResponseModel> callAPIService = apiService.resetSession(token);
        callAPIService.enqueue(callback);
        return true;
    }

    public boolean getContentCategory(String amount, String category, String difficulty, String type, String token,
                                      Callback<ContentCategoryResponseModel> callback) {
        APIService apiService = restAdapter.create(APIService.class);
        Call<ContentCategoryResponseModel> callAPIService = apiService.getContentCategory(amount, category, difficulty, type, token);
        callAPIService.enqueue(callback);
        return true;
    }
}
