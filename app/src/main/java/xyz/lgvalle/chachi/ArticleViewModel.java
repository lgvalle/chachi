package xyz.lgvalle.chachi;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import xyz.lgvalle.chachi.guardian.Article;
import xyz.lgvalle.chachi.guardian.Edition;
import xyz.lgvalle.chachi.guardian.TheGuardianDataSource;

public class ArticleViewModel extends ViewModel {

    private static final String TAG = ArticleViewModel.class.getSimpleName();
    private final LiveData<List<Article>> dummyItems;
    private LiveData<Article> selectedItem;
    private TheGuardianDataSource dataSource;

    public ArticleViewModel(TheGuardianDataSource dataSource) {
        this.dataSource = dataSource;

        Log.d(TAG, "Create ViewModel");
        dummyItems = Transformations.map(dataSource.fetch(), new Function<Edition, List<Article>>() {
            @Override
            public List<Article> apply(Edition edition) {
                Log.d(TAG, "Transform Result");
                return edition.getItems();
            }
        });
    }

    LiveData<List<Article>> dummyItems() {
        Log.d(TAG, "Get dummy items");
        return dummyItems;

    }

    void selectItem(String itemId) {
        selectedItem = dataSource.articleById(itemId);
    }

    LiveData<Article> getSelectedItem() {
        return selectedItem;
    }


}
