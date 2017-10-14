package xyz.lgvalle.chachi.guardian;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TheGuardianAPI {

    @GET("fetchGuardian")
    Call<Edition> fetchGuardian();

}
