package xyz.lgvalle.chachi

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.database.*
import xyz.lgvalle.chachi.guardian.Article
import xyz.lgvalle.chachi.guardian.TheGuardianDataSource
import java.util.*

class ArticleViewModel(private val dataSource: TheGuardianDataSource) : ViewModel() {
    private var articles: MutableLiveData<List<Article>> = MutableLiveData()
    private var selectedItem: LiveData<Article>? = null
    private var selectedItemId: String? = null
    private val myRef: DatabaseReference? = null
    private val valueEventListener: ValueEventListener? = null

    fun articles(): LiveData<List<Article>> {

        if (articles.value == null) {
            FirebaseDatabase.getInstance()
                    .getReference("feed/articles")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                val value = dataSnapshot.getValue(object : GenericTypeIndicator<List<Article>>() {

                                })
                                articles.postValue(value)
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
        }

        return articles

    }

    override fun onCleared() {
        super.onCleared()
        myRef!!.removeEventListener(valueEventListener!!)

    }

    internal fun selectItem(selectedItemId: String) {
        this.selectedItemId = selectedItemId
    }

    internal fun selectedItem(): LiveData<Article> {
        selectedItem = Transformations.map(articles, Function { articles ->
            for (article in articles) {
                if (article.id == selectedItemId) {
                    return@Function article
                }
            }
            null
        })
        return selectedItem
    }


    /**
     * Sentiment value goes from -1.0 to 1.0
     *
     * @param sentimentValue
     */
    fun sentiment(sentimentValue: Int?) {
        val filteredArticles = ArrayList<Article>()
        for (article in articles.value!!) {
            if (article.getScore() >= sentimentValue) {
                filteredArticles.add(article)
            }
        }

        //        articles.setValue(filteredArticles);
    }

    companion object {

        private val TAG = ArticleViewModel::class.java.simpleName
    }
}
