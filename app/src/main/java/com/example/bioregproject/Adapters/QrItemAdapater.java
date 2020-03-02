package com.example.bioregproject.Adapters;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.Products;
import com.example.bioregproject.ui.Authentication.AccountBinderViewModel;
import com.google.android.material.button.MaterialButton;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;


public class QrItemAdapater extends ListAdapter<Products, QrItemAdapater.AccountsHolder> {

    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;
    private AccountBinderViewModel model;

    public QrItemAdapater(Context context, Activity activity) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<Products> DIFF_CALLBACK = new DiffUtil.ItemCallback<Products>() {
        @Override
        public boolean areItemsTheSame(@NonNull Products oldItem, @NonNull Products newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Products oldItem, @NonNull Products newItem) {
            return  oldItem.getImage() == newItem.getImage() ;
        }
    };

    @NonNull
    @Override
    public AccountsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.print_item, parent, false);
        return new AccountsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsHolder holder, int position) {

        final Products currentItem = getItem(position);
        final String id = String.valueOf(currentItem.getId());
        final byte[] image = currentItem.getImage();
        Glide.with(mContext).asBitmap().load(image).into(holder.mImageView);
        holder.mTextViewName.setText("Id : "+id);
        holder.mTextViewName.setClickable(false);
        holder.mTextViewName.setEnabled(false);

    }


    public Products getAccountAt(int postion) {
        return getItem(postion);
    }

    class AccountsHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public Button mTextViewName;


        public AccountsHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.image);
            mTextViewName = itemView.findViewById(R.id.id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.OnItemClick(getItem(position));
                }
            });
        }

    }

    public interface OnItemClickLisnter {
        void OnItemClick(Products account);
    }

    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }



}
