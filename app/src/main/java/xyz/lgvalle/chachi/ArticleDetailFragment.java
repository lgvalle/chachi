package xyz.lgvalle.chachi;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.lgvalle.chachi.guardian.Article;

public class ArticleDetailFragment extends Fragment {
    public static final String TAG = ArticleDetailFragment.class.getSimpleName();

    public ArticleDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_article_detail, container, false);

        setupToolbar(rootView);
        setupFabButton(rootView);
        setupContent(rootView);

        return rootView;
    }

    private void setupToolbar(View rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.detail_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupFabButton(View rootView) {
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupContent(final View rootView) {
        final CollapsingToolbarLayout appBarLayout = rootView.findViewById(R.id.toolbar_layout);

        ArticleViewModel viewModel = ViewModelProviders.of(getActivity()).get(ArticleViewModel.class);
        viewModel.getSelectedItem().observe(getActivity(), new Observer<Article>() {
            @Override
            public void onChanged(@Nullable Article article) {
                if (article != null) {
                    appBarLayout.setTitle(article.getTitle());
                    ((TextView) rootView.findViewById(R.id.article_detail)).setText(article.getDescription());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
