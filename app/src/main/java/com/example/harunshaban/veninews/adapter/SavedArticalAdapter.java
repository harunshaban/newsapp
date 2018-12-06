package com.example.harunshaban.veninews.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harunshaban.veninews.R;
import com.example.harunshaban.veninews.activity.MainActivity;
import com.example.harunshaban.veninews.activity.ReadLaterActivity;
import com.example.harunshaban.veninews.model.Article;
import com.example.harunshaban.veninews.utils.OnRecyclerViewItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SavedArticalAdapter extends RecyclerView.Adapter<SavedArticalAdapter.ViewHolder> {

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List savedArticles;

    public SavedArticalAdapter(List savedArticles) {
        this.savedArticles = savedArticles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_read_later, viewGroup, false);
        return new SavedArticalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final Article savedA = (Article) savedArticles.get(i);
        if(!TextUtils.isEmpty(savedA.getTitle())){
            viewHolder.RLtitleText.setText(savedA.getTitle());
        }
        if(!TextUtils.isEmpty(savedA.getDescription())) {
            viewHolder.RLdescriptionText.setText(savedA.getDescription());
        }
        if(!TextUtils.isEmpty(savedA.getUrlToImage())){
            Picasso.get().load(savedA.getUrlToImage())
                    .resize(700,500)
                    .centerInside()
                    .into(viewHolder.RLimgView);
        }
        viewHolder.RLartilceAdapterParentLinear.setTag(savedA);
        Button del_btn = viewHolder.RLbtn_delete;
        del_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeData(savedA);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return savedArticles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView RLtitleText, RLdescriptionText;
        private LinearLayout RLartilceAdapterParentLinear;
        private ImageView RLimgView;
        private Button RLbtn_delete;
        public ViewHolder(@NonNull View view) {
            super(view);
            RLbtn_delete = view.findViewById(R.id.button_delete);
            RLimgView = view.findViewById(R.id.article_read_later_image_view);
            RLtitleText = view.findViewById(R.id.article_read_later_tv_title);
            RLdescriptionText = view.findViewById(R.id.article_read_later_tv_description);
            RLartilceAdapterParentLinear = view.findViewById(R.id.article_read_later);
            RLartilceAdapterParentLinear.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(onRecyclerViewItemClickListener!=null){
                                onRecyclerViewItemClickListener.onItemClick(getAdapterPosition(),v);
                            }
                        }
                    }
            );
        }
    }
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
    private void removeData(Article sA) {
        int currPosition = savedArticles.indexOf(sA);
        sA.delete();
        savedArticles.remove(currPosition);
        notifyItemRemoved(currPosition);
        notifyDataSetChanged();
        //TODO here needs to show dialog when deleting last artical
        //if (savedArticles.size() <= 0)
    }
}
