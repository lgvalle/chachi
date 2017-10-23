package xyz.lgvalle.chachi.guardian;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.List;

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

    public LiveData<Edition> edition() {
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

    public LiveData<List<Article>> articles() {
        Log.d(TAG, "get articles");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("feed/guardian/items");


        final MutableLiveData<List<Article>> articles = new MutableLiveData<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Article> items = dataSnapshot.getValue(new GenericTypeIndicator<List<Article>>() {
                });
                Log.d(TAG, "get articles: post value");
                articles.postValue(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        myRef.addListenerForSingleValueEvent(valueEventListener);

        return articles;
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
