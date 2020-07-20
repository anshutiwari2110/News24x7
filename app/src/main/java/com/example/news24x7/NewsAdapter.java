package com.example.news24x7;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> implements Filterable {

    Context context;
    ArrayList<Articles> articles;
    ArrayList<Articles> displayArticles;
    ArrayList<Articles> suggestions;

    private NewsFilter filter ;

    public NewsAdapter(Context context, ArrayList<Articles> articles) {
        this.articles = articles;
        this.context = context;
        this.displayArticles = (ArrayList<Articles>) articles.clone();
        this.suggestions = new ArrayList<>();
        filter = new NewsFilter();
    }



    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(context).inflate(R.layout.cell_for_news,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        Articles currentArticle = displayArticles.get(position);

        if(currentArticle!=null){
            holder.mTvPublishedAt.setText(currentArticle.publishedAt);
            holder.mTvTitle.setText(currentArticle.title);
            holder.mTvDescription.setText(currentArticle.description);
            holder.mTvURL.setText(currentArticle.url);
            Glide.with(context).load(currentArticle.urlToImage).placeholder(R.drawable.no_image_found).into(holder.mIvNewsImage);

        }


    }

    @Override
    public int getItemCount() {
        return displayArticles!=null?displayArticles.size():0;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        private TextView mTvPublishedAt;
        private TextView mTvTitle;
        private TextView mTvDescription;
        private ImageView mIvNewsImage;
        private TextView mTvURL;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            mTvPublishedAt = itemView.findViewById(R.id.tv_publishedAt);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvDescription = itemView.findViewById(R.id.tv_description);
            mIvNewsImage = itemView.findViewById(R.id.iv_news_image);
            mTvURL = itemView.findViewById(R.id.tv_url);


        }
    }

    public class NewsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            suggestions.clear();
            if(constraint != null){
                for (Articles article : articles ){
                    if (article.title.toLowerCase().contains(constraint.toString().toLowerCase())){
                        suggestions.add(article);
                    }else if (article.description.toLowerCase().contains(constraint.toString().toLowerCase())){
                        suggestions.add(article);
                    }

                }
            }

            results.values = suggestions ;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if(results != null){
                if(results.count > 0){
                    displayArticles = (ArrayList<Articles>) results.values;
                    notifyDataSetChanged();
                }
            }
        }
    }

    public void clearSearch(){
        displayArticles.clear();
        displayArticles = (ArrayList<Articles>) articles.clone();
        notifyDataSetChanged();
    }


}
