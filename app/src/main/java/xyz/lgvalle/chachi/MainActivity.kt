package xyz.lgvalle.chachi

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu

import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

import xyz.lgvalle.chachi.guardian.Article

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)



        if (savedInstanceState == null) {
            val fragment = ArticleListFragment()

            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, ArticleListFragment.TAG)
                    .addToBackStack(ArticleListFragment.TAG)
                    .commit()
        }


        val firebaseApp = FirebaseApp.initializeApp(this)

    }

    override fun onResume() {
        super.onResume()


        val viewModel = ArticleViewModel()
        viewModel.articles().observe(this, Observer { articles ->
            adapter.setArticles(articles)
        })


        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("feed/guardian/items")

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val items = dataSnapshot.value as List<Article>?
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)
        return true
    }


}
