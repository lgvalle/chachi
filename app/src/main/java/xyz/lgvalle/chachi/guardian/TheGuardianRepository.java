package xyz.lgvalle.chachi.guardian;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import java.util.List;

public class TheGuardianRepository {

    private final TheGuardianDataSource dataSource;

    private MutableLiveData<Edition> edition = new MutableLiveData<>();
    private final LiveData<List<Article>> articles;

    public TheGuardianRepository(TheGuardianDataSource dataSource) {
        this.dataSource = dataSource;

        articles = Transformations.switchMap(dataSource.fetch(), new Function<Edition, LiveData<List<Article>>>() {
            @Override
            public LiveData<List<Article>> apply(Edition edition) {
                MutableLiveData<List<Article>> objectLiveData = new MutableLiveData<List<Article>>();
                objectLiveData.setValue(edition.items);
                return objectLiveData;
            }
        });
    }

    public LiveData<List<Article>> articles() {
        return articles;
    }
}
