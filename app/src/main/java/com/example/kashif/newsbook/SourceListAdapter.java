package com.example.kashif.newsbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kashif.newsbook.ApiClasses.NewsSourceLists;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;

/**
 * Created by KASHIF on 3/1/2017.
 */

public class SourceListAdapter extends RecyclerView.Adapter<SourceListAdapter.SourceListViewHolder> {


    ArrayList<NewsSourceLists> newsSourceLists = new ArrayList<NewsSourceLists>();

    @Override
    public SourceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.source_list_display, parent, false);
        return new SourceListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SourceListViewHolder holder, int position) {
        holder.setSourceListData(newsSourceLists.get(position));
    }

    @Override
    public int getItemCount() {
        return newsSourceLists.size();
    }

    public void setList(ArrayList<NewsSourceLists> newsSourceLists) {
        this.newsSourceLists = newsSourceLists;
        notifyDataSetChanged();
    }

    public class SourceListViewHolder extends RecyclerView.ViewHolder {

        TextView sourceListTextView;
        //  ImageView sourceLogoImageView;
        NewsSourceLists newsSourceLists;

        AvatarView avatarView;
        IImageLoader imageLoader;

        public SourceListViewHolder(final View itemView) {
            super(itemView);


            sourceListTextView = (TextView) itemView.findViewById(R.id.sourceList_tv);
            // sourceLogoImageView = (ImageView) itemView.findViewById(R.id.sourcelogo_iView);

            avatarView = (AvatarView) itemView.findViewById(R.id.avatar_view);
            imageLoader = new PicassoLoader();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), ArticleListActivity.class);
                    intent.putExtra("sourceId", newsSourceLists.getId());
                    intent.putExtra("sourceName", newsSourceLists.getName());
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public void setSourceListData(NewsSourceLists newsSourceLists) {
            sourceListTextView.setText(newsSourceLists.getName());

            // picasso libary is used to load image/logo URL
            // Picasso.with(itemView.getContext()).load(newsSourceLists.getSourceLogo()).into(sourceLogoImageView);

            imageLoader.loadImage(avatarView, "http:/example.com/user/someUserAvatar.png", newsSourceLists.getName());
            this.newsSourceLists = newsSourceLists;
        }
    }
}
