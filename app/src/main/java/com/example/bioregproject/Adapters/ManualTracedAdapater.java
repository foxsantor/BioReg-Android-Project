package com.example.bioregproject.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Path;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Products;
import com.example.bioregproject.ui.Authentication.AccountBinderViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ManualTracedAdapater extends ListAdapter<Products,ManualTracedAdapater.AccountsHolder> {


    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;
    private AccountBinderViewModel model;

    public ManualTracedAdapater(Context context,Activity activity) {
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
            return  oldItem.getCreationDate().equals(newItem.getCreationDate()) &&
                    oldItem.getImage()== newItem.getImage()&&
                    oldItem.getBrandName().equals(newItem.getBrandName()) &&
                    oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getExpirationDate().equals(newItem.getExpirationDate()) &&
                    oldItem.getFabricationDate().equals(newItem.getFabricationDate()) &&
                    oldItem.getCategoryName().equals(newItem.getCategoryName());
        }
    };

    @NonNull
    @Override
    public AccountsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manuel, parent, false);
        return new AccountsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsHolder holder, int position) {

        final Products currentItem = getItem(position);
        holder.warn.setVisibility(View.GONE);
        holder.exp.setVisibility(View.GONE);
        final byte[] image = currentItem.getImage();
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_warning_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
        Glide.with(mContext).asBitmap().load(image).apply(options).into(holder.mImageView);
        final String creationString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentItem.getCreationDate());
        final String fabricationString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentItem.getFabricationDate());
        final String expirationString = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentItem.getExpirationDate());
        Date now = new Date();
        if(currentItem.getExpirationDate().compareTo(now) <= 0)
        {
            holder.warn.setVisibility(View.VISIBLE);
            holder.exp.setVisibility(View.VISIBLE);

        }
        holder.text_creation.setText(creationString);
        holder.text_expiration.setText(expirationString);
        holder.text_fab.setText(fabricationString);
        holder.text_name.setText(currentItem.getName());
        holder.text_brand.setText(currentItem.getBrandName());
        holder.text_cat.setText(currentItem.getCategoryName());
        if(currentItem.getRefrence().isEmpty())
        {
            holder.ref_c.setVisibility(View.GONE);

        }else {
            holder.text_ref.setText(currentItem.getRefrence());
        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Bundle bundle = new Bundle();
                bundle.putLong("id",currentItem.getId());
                bundle.putByteArray("image",image);
                bundle.putString("expirationString",expirationString);
                bundle.putString("ref",currentItem.getRefrence());
                bundle.putString("fabricationString",fabricationString);
                bundle.putString("creationString",creationString);
                bundle.putString("name",currentItem.getName());
                bundle.putString("brand",currentItem.getBrandName());
                bundle.putString("CategoryName",currentItem.getCategoryName());
                Navigation.findNavController(v).navigate(R.id.action_manualHome_to_formManual,bundle);

            }
        });

        holder.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final Bundle bundle = new Bundle();
                bundle.putByteArray("image",image);
                bundle.putString("id",""+currentItem.getId());
                bundle.putString("expirationString",expirationString);
                bundle.putString("ref",currentItem.getRefrence());
                bundle.putString("fabricationString",fabricationString);
                bundle.putString("created",creationString);
                bundle.putString("name",currentItem.getName());
                bundle.putString("brand",currentItem.getBrandName());
                bundle.putString("CategoryName",currentItem.getCategoryName());
                bundle.putString("Type",currentItem.getType());
                String bigString = ""+currentItem.getId()+","+currentItem.getName()+","+currentItem.getBrandName()+","+
                        creationString+","+currentItem.getType()+","+currentItem.getRefrence()+","+currentItem.getCategoryName()
                        +","+fabricationString+","+expirationString;
                bundle.putString("bigString",bigString);
                bundle.putInt("dest",3);
                Navigation.findNavController(v).navigate(R.id.printingConfig2,bundle);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = MainActivity.AskOptionPro(mContext,currentItem,(LifecycleOwner)activity,"Manual Traceability");
                diaBox.show();

            }
        });


    }


    public Products getAccountAt(int postion) {
        return getItem(postion);
    }

    class AccountsHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView,warn;
        private ConstraintLayout name_c,menu,brand_c,cat,creation_c,ref_c,fab_c,expiration_c;
        public TextView text_creation,text_brand,exp,text_cat,text_ref,text_name,text_fab,text_expiration;
        public ImageButton delete,edit,print;


        public AccountsHolder(View itemView) {
            super(itemView);
            exp=itemView.findViewById(R.id.exp);
            mImageView = itemView.findViewById(R.id.preview);
            text_ref=itemView.findViewById(R.id.text_ref);
            text_name=itemView.findViewById(R.id.text_name);
            text_cat=itemView.findViewById(R.id.text_cat);
            text_fab=itemView.findViewById(R.id.text_fab);
            text_expiration=itemView.findViewById(R.id.text_expiration);
            text_creation = itemView.findViewById(R.id.text_creation);
            text_brand=itemView.findViewById(R.id.text_brand);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
            print = itemView.findViewById(R.id.next);
            warn = itemView.findViewById(R.id.warn);
            name_c = itemView.findViewById(R.id.name_c);
            menu = itemView.findViewById(R.id.menu);
            cat = itemView.findViewById(R.id.cat);
            brand_c = itemView.findViewById(R.id.brand_c);
            creation_c = itemView.findViewById(R.id.creation_c);
            ref_c = itemView.findViewById(R.id.ref_c);
            fab_c = itemView.findViewById(R.id.fab_c);
            expiration_c = itemView.findViewById(R.id.expiration_c);
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
