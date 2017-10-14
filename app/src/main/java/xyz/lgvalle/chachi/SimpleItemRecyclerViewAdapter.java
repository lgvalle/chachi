package xyz.lgvalle.chachi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.lgvalle.chachi.guardian.Article;

class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private List<Article> values;
    private ArticleListFragment.ArticleClickListener articleClickListener;


    SimpleItemRecyclerViewAdapter(List<Article> items,
                                  ArticleListFragment.ArticleClickListener articleClickListener) {
        values = items;
        this.articleClickListener = articleClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Article item = values.get(position);
        holder.mIdView.setText(item.getCreator());
        holder.mContentView.setText(item.getTitle());

        holder.itemView.setTag(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                articleClickListener.onArticleSelected(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    void setArticles(List<Article> articleList) {
        this.values = articleList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mIdView = view.findViewById(R.id.id_text);
            mContentView = view.findViewById(R.id.content);
        }
    }
}