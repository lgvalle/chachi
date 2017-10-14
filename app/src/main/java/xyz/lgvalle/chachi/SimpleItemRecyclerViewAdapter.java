package xyz.lgvalle.chachi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        holder.title.setText(item.getTitle());

        loadThumbnail(holder, item);

        holder.itemView.setTag(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                articleClickListener.onArticleSelected(item);
            }
        });
    }

    private void loadThumbnail(ViewHolder holder, Article item) {
        Picasso.with(holder.thumbnail.getContext())
                .load(item.getThumbnail())
                .into(holder.thumbnail);
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
        final TextView title;
        final ImageView thumbnail;

        ViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.article_thumbnail);
            title = view.findViewById(R.id.article_title);
        }
    }
}