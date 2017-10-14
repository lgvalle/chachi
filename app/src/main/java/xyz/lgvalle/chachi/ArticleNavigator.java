package xyz.lgvalle.chachi;

import android.support.v4.app.FragmentActivity;

public class ArticleNavigator {

    private final FragmentActivity mainActivity;


    public ArticleNavigator(FragmentActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void navigateToArticleDetail() {
        /*
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ArticleDetailFragment.ARG_ITEM_ID, item.id);
            ArticleDetailFragment fragment = new ArticleDetailFragment();
            fragment.setArguments(arguments);
            mParentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.article_detail_container, fragment)
                    .commit();
        } else {
            Context context = view.getContext();
            Intent intent = new Intent(context, ArticleDetailActivity.class);
            intent.putExtra(ArticleDetailFragment.ARG_ITEM_ID, item.id);

            context.startActivity(intent);
        }
        */

        ArticleDetailFragment fragment = new ArticleDetailFragment();
        mainActivity.getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, ArticleDetailFragment.TAG)
                .addToBackStack(ArticleDetailFragment.TAG)
                .commit();

    }
}
