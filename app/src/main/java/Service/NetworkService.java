package Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "http://89.223.67.163/";
    private Retrofit mRetrofit;
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public static String getBaseUrl(){
        return BASE_URL;
    }
    public JSONPlaceHolderApi getJSONApi() {

        return mRetrofit.create(JSONPlaceHolderApi.class);
    }
}