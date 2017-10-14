package xyz.lgvalle.chachi;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import xyz.lgvalle.chachi.guardian.Article;
import xyz.lgvalle.chachi.guardian.TheGuardianDataSource;

public class ArticleListFragment extends Fragment {

    public static final String TAG = ArticleListFragment.class.getSimpleName();
    private SimpleItemRecyclerViewAdapter adapter;
    private ArticleNavigator articleNavigator;
    private ArticleViewModel articleViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);

        articleNavigator = new ArticleNavigator(getActivity());
        adapter = new SimpleItemRecyclerViewAdapter(Collections.<Article>emptyList(), articleClickListener);

        RecyclerView recyclerView = view.findViewById(R.id.article_list);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelFactory viewModelFactory = new ViewModelFactory(new TheGuardianDataSource());
        articleViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(ArticleViewModel.class);

        articleViewModel.dummyItems().observe(getActivity(), new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> dummyItems) {
                if (dummyItems != null) {
                    adapter.setArticleList(dummyItems);
                }
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

}
