package xyz.lgvalle.chachi;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import xyz.lgvalle.chachi.guardian.Article;
import xyz.lgvalle.chachi.guardian.TheGuardianDataSource;

public class ArticleViewModel extends ViewModel {

    private static final String TAG = ArticleViewModel.class.getSimpleName();
    private LiveData<List<Article>> articles = new MutableLiveData<>();
    private LiveData<Article> selectedItem;
    private TheGuardianDataSource dataSource;
    private String selectedItemId;
    private DatabaseReference myRef;
    private ValueEventListener valueEventListener;

    public ArticleViewModel(TheGuardianDataSource dataSource) {
        this.dataSource = dataSource;
    }

    LiveData<List<Article>> articles() {

        /*
        articles = Transformations.map(dataSource.edition(), new Function<Edition, List<Article>>() {
            @Override
            public List<Article> apply(Edition edition) {
                return edition.getItems();
            }
        });
        */

        Log.d(TAG, "get articles");
        articles = Transformations.map(dataSource.articles(), new Function<List<Article>, List<Article>>() {
            @Override
            public List<Article> apply(List<Article> articles) {
                Log.d(TAG, "get articles: post value");

                return articles;
            }
        });

//        if (articles.getValue() == null) {
//
//            Log.d(TAG, "get articles");
//            final FirebaseDatabase database = FirebaseDatabase.getInstance();
//            myRef = database.getReference("feed/guardian/items");
//
//
//            valueEventListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//
//                    List<Article> items = dataSnapshot.getValue(new GenericTypeIndicator<List<Article>>() {
//                    });
//                    Log.d(TAG, "get articles: post value");
//                    articles.postValue(items);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            };
//
//            myRef.addListenerForSingleValueEvent(valueEventListener);
//        }

        return articles;

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        myRef.removeEventListener(valueEventListener);

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


    /**
     * Sentiment value goes from -1.0 to 1.0
     *
     * @param sentimentValue
     */
    public void sentiment(final Integer sentimentValue) {
        List<Article> filteredArticles = new ArrayList<>();
        for (Article article : articles.getValue()) {
            if (article.getScore() >= sentimentValue) {
                filteredArticles.add(article);
            }
        }

//        articles.setValue(filteredArticles);
    }
}
