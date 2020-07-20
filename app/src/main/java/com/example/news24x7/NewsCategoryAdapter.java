package com.example.news24x7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

public class NewsCategoryAdapter extends RecyclerView.Adapter<NewsCategoryAdapter.NewsCategoryHolder> {

    private Context context;
    private String[] newsCategories;
    private NewsCategoryClickListener listener;
    private int selectedPosition = -1;

    public void setListener(NewsCategoryClickListener listener){
        this.listener = listener ;
    }

    public NewsCategoryAdapter(Context context) {
        this.context = context;
        this.newsCategories = context.getResources().getStringArray(R.array.news_category);
    }

    @NonNull
    @Override
    public NewsCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsCategoryHolder(LayoutInflater.from(context).inflate(R.layout.cell_for_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsCategoryHolder holder, final int position) {

        final String currentCategory = newsCategories[position];
        holder.mTvNewsCategory.setText(currentCategory);
        holder.mLlNewsCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onNewsCategoryClicked(currentCategory);
                    selectedPosition = position;
                    notifyDataSetChanged();
                }
            }
        });
        if(selectedPosition == position){
            holder.mLlNewsCategory.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.bg_crect_selected,null));
            holder.mTvNewsCategory.setTextColor(ResourcesCompat.getColor(context.getResources(),R.color.white,null));

        }else {
            holder.mLlNewsCategory.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.bg_crect_unselected,null));
            holder.mTvNewsCategory.setTextColor(ResourcesCompat.getColor(context.getResources(),R.color.black,null));
        }
    }

    @Override
    public int getItemCount() {
        return newsCategories != null ? newsCategories.length : 0;
    }

    public class NewsCategoryHolder extends RecyclerView.ViewHolder {
        private TextView mTvNewsCategory;
        private LinearLayout mLlNewsCategory;

        public NewsCategoryHolder(@NonNull View itemView) {
            super(itemView);
            mTvNewsCategory = itemView.findViewById(R.id.tv_news_category);
            mLlNewsCategory = itemView.findViewById(R.id.ll_news_category);


        }
    }
    public interface NewsCategoryClickListener{
        void onNewsCategoryClicked(String category);
    }
}
