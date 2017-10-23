package xyz.lgvalle.chachi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import xyz.lgvalle.chachi.guardian.Article;

class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private List<Article> values;
    private ArticleListFragment.ArticleClickListener articleClickListener;
    // TODO ÑAPA PARA EL FILTER!
    private List<Article> original;


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


//        updateBgColour(holder, item);

    }

    private void updateBgColour(ViewHolder holder, Article item) {
        if (item.getScore() < -0.5d) {
            setBgColour(holder, R.color.color_bg0);
        } else if (item.getScore() < 0) {
            setBgColour(holder, R.color.color_bg1);
        } else if (item.getScore() < 0.5d) {
            setBgColour(holder, R.color.color_bg2);
        } else {
            setBgColour(holder, R.color.color_bg3);
        }
    }

    private void setBgColour(ViewHolder holder, int colorAccent) {
        holder.container.setBackgroundColor(holder.container.getResources().getColor(colorAccent));
    }

    private void loadThumbnail(ViewHolder holder, Article item) {
        Picasso.with(holder.thumbnail.getContext())
                .load(item.getImage())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void filter(double filter) {
        List<Article> newValues = new ArrayList<>();
        for (Article value : original) {
            if (value.getScore() >= filter) {
                newValues.add(value);
            }
        }
        values.clear();
        values.addAll(newValues);

        super.notifyDataSetChanged();
    }


    void setArticles(List<Article> articleList) {
        this.values = articleList;
        this.original = new ArrayList<>(articleList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final ImageView thumbnail;
        final LinearLayout container;

        ViewHolder(View view) {
            super(view);
            container = view.findViewById(R.id.article_container);
            thumbnail = view.findViewById(R.id.article_thumbnail);
            title = view.findViewById(R.id.article_title);
        }
    }
}