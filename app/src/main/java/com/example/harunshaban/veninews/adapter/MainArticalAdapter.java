package com.example.harunshaban.veninews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harunshaban.veninews.model.Article;
import com.example.harunshaban.veninews.utils.OnRecyclerViewItemClickListener;
import com.example.harunshaban.veninews.R;
import com.orm.SugarContext;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainArticalAdapter extends RecyclerView.Adapter<MainArticalAdapter.ViewHolder> {

    private List<Article> articleArrayList;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public MainArticalAdapter(List<Article> articleArrayList){
        this.articleArrayList = articleArrayList;
    }

    @NonNull
    @Override
    public MainArticalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_main_article_adapter, viewGroup, false);
        return new MainArticalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainArticalAdapter.ViewHolder viewHolder, int position) {
        final Article articleModel = articleArrayList.get(position);
        if(!TextUtils.isEmpty(articleModel.getTitle())){
            viewHolder.titleText.setText(articleModel.getTitle());
        }
        if(!TextUtils.isEmpty(articleModel.getDescription())) {
            viewHolder.descriptionText.setText(articleModel.getDescription());
        }
        if(!TextUtils.isEmpty(articleModel.getUrlToImage())){
            Picasso.get().load(articleModel.getUrlToImage())
                    .resize(700,500)
                    .centerInside()
                    .into(viewHolder.imgView);
        }
        viewHolder.artilceAdapterParentLinear.setTag(articleModel);
        Button btn = viewHolder.btn_read_later;
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) { articleModel.save(); }
                }
        );

    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText, descriptionText;
        private LinearLayout artilceAdapterParentLinear;
        private ImageView imgView;
        private Button btn_read_later;
        public ViewHolder(@NonNull View view) {
            super(view);
            btn_read_later = view.findViewById(R.id.button_read_later);
            imgView = view.findViewById(R.id.article_adapter_image_view);
            titleText = view.findViewById(R.id.article_adapter_tv_title);
            descriptionText = view.findViewById(R.id.article_adapter_tv_description);
            artilceAdapterParentLinear = view.findViewById(R.id.article_adapter_ll_parent);
            artilceAdapterParentLinear.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(onRecyclerViewItemClickListener != null){
                                onRecyclerViewItemClickListener.onItemClick(getAdapterPosition(),view);
                            }
                        }
                    }
            );
        }
    }
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener){
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
