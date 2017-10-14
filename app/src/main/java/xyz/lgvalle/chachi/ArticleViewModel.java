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
    private LiveData<List<Article>> articles;
    private LiveData<Article> selectedItem;
    private TheGuardianDataSource dataSource;
    private String selectedItemId;

    public ArticleViewModel(TheGuardianDataSource dataSource) {
        this.dataSource = dataSource;

        Log.d(TAG, "Create ViewModel");

    }

    LiveData<List<Article>> articles() {
        articles = Transformations.map(dataSource.edition(), new Function<Edition, List<Article>>() {
            @Override
            public List<Article> apply(Edition edition) {
                Log.d(TAG, "Transform Result");
                return edition.getItems();
            }
        });

        return articles;

    }

    void selectItem(final String selectedItemId) {
        this.selectedItemId = selectedItemId;
    }

    LiveData<Article> selectedItem() {
        selectedItem = Transformations.map(articles, new Function<List<Article>, Article>() {
            @Override
            public Article apply(List<Article> articles) {
                for (Article article : articles) {
                    if (article.getId().equals(selectedItemId)) {
                        return article;
                    }
                }
                return null;
            }
        });
        return selectedItem;
    }


}
