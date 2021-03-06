package xyz.lgvalle.chachi.guardian;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.lgvalle.chachi.BuildConfig;

public class TheGuardianDataSource {

    private static final String TAG = TheGuardianDataSource.class.getSimpleName();
    private MutableLiveData<Edition> edition = new MutableLiveData<>();

    public MutableLiveData<Edition> edition() {
        Log.d(TAG, "Fetching");
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
//                .client(client)

                .build();

        TheGuardianAPI theGuardianAPI = retrofit.create(TheGuardianAPI.class);
        Call<Edition> call = theGuardianAPI.fetchGuardian();
        call.enqueue(callback());

        return edition;
    }


    private Callback<Edition> callback() {
        return new Callback<Edition>() {
            @Override
            public void onResponse(Call<Edition> call, Response<Edition> response) {
                if (response.isSuccessful()) {
                    Edition body = response.body();
                    edition.setValue(body);
                }
            }

            @Override
            public void onFailure(Call<Edition> call, Throwable throwable) {
                Log.d(TAG, "Failure", throwable);
            }
        };
    }

}
