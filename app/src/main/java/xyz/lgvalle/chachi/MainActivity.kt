package xyz.lgvalle.chachi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu

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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)
        return true
    }


}
