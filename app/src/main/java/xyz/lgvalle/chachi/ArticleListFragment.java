package xyz.lgvalle.chachi;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.util.Collections;
import java.util.List;

import xyz.lgvalle.chachi.guardian.Article;
import xyz.lgvalle.chachi.guardian.TheGuardianDataSource;

public class ArticleListFragment extends Fragment {

    public static final String TAG = ArticleListFragment.class.getSimpleName();
    private SimpleItemRecyclerViewAdapter adapter;
    private ArticleNavigator articleNavigator;
    private ArticleViewModel articleViewModel;
    private SeekBar moodSeekbar;
    private View moodContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);

        moodSeekbar = view.findViewById(R.id.mood_seekbar);
        moodContainer = view.findViewById(R.id.mood_container);


        setupToolbar(view);

        articleNavigator = new ArticleNavigator(getActivity());
        adapter = new SimpleItemRecyclerViewAdapter(Collections.<Article>emptyList(), articleClickListener);

        RecyclerView recyclerView = view.findViewById(R.id.article_list);
        recyclerView.setAdapter(adapter);

        return view;
    }


    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setTitle("Chachi");
    }

    @Override
    public void onResume() {
        super.onResume();

        ViewModelFactory viewModelFactory = new ViewModelFactory(new TheGuardianDataSource());
        articleViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(ArticleViewModel.class);

        FirebaseDatabaseLiveData firebaseDatabaseLiveData = new FirebaseDatabaseLiveData();
        firebaseDatabaseLiveData.observe(getActivity(), new Observer<List<? extends Article>>() {
            @Override
            public void onChanged(@Nullable List<? extends Article> articles) {

                if (articles != null) {
                    adapter.setArticles((List<Article>) articles);
                }
            }
        });
//        new FirebaseDatabaseLiveData().observe(getActivity(), new Observer<List<Article>>() {
//            @Override
//            public void onChanged(@Nullable List<Article> articles) {
//                if (articles != null) {
//                    adapter.setArticles(articles);
//                }
//            }
//        });

        articleViewModel.articles().observe(getActivity(), new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles != null) {
                    adapter.setArticles(articles);
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ViewModelFactory viewModelFactory = new ViewModelFactory(new TheGuardianDataSource());

        final MoodSeekbarViewModel seekbarViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(MoodSeekbarViewModel.class);
        moodSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    double filter = (progress - 100) / 100d;
                    Log.d(TAG, "progress: " + filter);
                    adapter.filter(filter);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekbarViewModel.seekbarValue.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                articleViewModel.sentiment(integer);
            }
        });
    }

    private ArticleClickListener articleClickListener = new ArticleClickListener() {
        @Override
        public void onArticleSelected(Article article) {
            articleViewModel.selectItem(article.getId());
            articleNavigator.navigateToArticleDetail();
        }
    };

    interface ArticleClickListener {
        void onArticleSelected(Article dummyItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_mood) {
            if (moodContainer.getVisibility() == View.VISIBLE) {
                moodContainer.setVisibility(View.GONE);
            } else {
                moodContainer.setVisibility(View.VISIBLE);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
