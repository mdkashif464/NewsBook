package com.example.kashif.newsbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kashif.newsbook.ApiClasses.ArticleLists;
import com.example.kashif.newsbook.ApiClasses.NewsApi;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by KASHIF on 3/1/2017.
 */

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleListViewHolder> {

    ArrayList<ArticleLists> articleLists = new ArrayList<ArticleLists>();
    private String SOURCE_NAME = "sourceName";


    @Override
    public ArticleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.article_list_display, parent, false);
        return new ArticleListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ArticleListViewHolder holder, int position) {
        holder.setArticleListData(articleLists.get(position));

    }

    @Override
    public int getItemCount() {
        return articleLists.size();
    }

    public void setList(ArrayList<ArticleLists> articleLists) {
        this.articleLists = articleLists;
        notifyDataSetChanged();
    }

    public class ArticleListViewHolder extends RecyclerView.ViewHolder {

        TextView articleListTextView;
        ArticleLists articleLists;
        ImageView articleListImageView;

        public ArticleListViewHolder(final View itemView) {
            super(itemView);

            articleListTextView = (TextView) itemView.findViewById(R.id.showArticles_tv);
            articleListImageView = (ImageView) itemView.findViewById(R.id.articleList_iView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), ShowFullNewsActivity.class);
                    Gson gson = new Gson();
                    String parsedArticlesList = gson.toJson(articleLists);
                    intent.putExtra("kashif", parsedArticlesList);
                    intent.putExtra("sourceName", SOURCE_NAME);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public void setArticleListData(ArticleLists articleLists) {

            articleListTextView.setText(articleLists.getTitle());
            Picasso.with(itemView.getContext()).load(articleLists.getArticleImageUrl()).into(articleListImageView);
            this.articleLists = articleLists;
        }
    }
}
