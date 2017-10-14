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
    private final LiveData<List<Article>> articles;
    private LiveData<Article> selectedItem;

    public ArticleViewModel(TheGuardianDataSource dataSource) {

        Log.d(TAG, "Create ViewModel");
        articles = Transformations.map(dataSource.edition(), new Function<Edition, List<Article>>() {
            @Override
            public List<Article> apply(Edition edition) {
                Log.d(TAG, "Transform Result");
                return edition.getItems();
            }
        });
    }

    LiveData<List<Article>> articles() {
        Log.d(TAG, "Get dummy items");
        return articles;

    }

    void selectItem(final String itemId) {
        selectedItem = Transformations.map(articles, new Function<List<Article>, Article>() {
            @Override
            public Article apply(List<Article> articles) {
                for (Article article : articles) {
                    if (article.getId().equals(itemId)) {
                        return article;
                    }
                }
                return null;
            }
        });
    }

    LiveData<Article> selectedItem() {
        return selectedItem;
    }


}
