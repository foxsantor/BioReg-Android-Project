package com.example.bioregproject.Adapters;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.ui.Authentication.AccountBinderViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;


public class AccountPopUp extends ListAdapter<Account,AccountPopUp.AccountsHolder> {

    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;
    private AccountBinderViewModel model;

    public AccountPopUp(Context context,Activity activity) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.activity = activity;
    }

    private static final DiffUtil.ItemCallback<Account> DIFF_CALLBACK = new DiffUtil.ItemCallback<Account>() {
        @Override
        public boolean areItemsTheSame(@NonNull Account oldItem, @NonNull Account newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Account oldItem, @NonNull Account newItem) {
            return  oldItem.getFirstName().equals(newItem.getFirstName()) &&
                    oldItem.getLastName().equals(newItem.getLastName())&&
                    oldItem.getProfileImage()== newItem.getProfileImage() &&
                    oldItem.getLastLoggedIn().equals(newItem.getLastLoggedIn());
        }
    };

    @NonNull
    @Override
    public AccountsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_pop, parent, false);
        return new AccountsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsHolder holder, int position) {

        final Account currentItem = getItem(position);
        PrettyTime p = new PrettyTime();

        final String firstName = currentItem.getFirstName();
        String lastName = currentItem.getLastName();
        Date lastLoggedIn = currentItem.getLastLoggedIn();
        final String fullName = StaticUse.capitalize(firstName)+" "+StaticUse.capitalize(lastName);
        final byte[] image = currentItem.getProfileImage();
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_warning_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
        Glide.with(mContext).asBitmap().load(image).apply(options).into(holder.mImageView);
        holder.mTextViewName.setText(fullName);
        holder.mTextViewLoggedIn.setText("Last Logged in "+p.format(lastLoggedIn));

    }


    public Account getAccountAt(int postion) {
        return getItem(postion);
    }

    class AccountsHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextViewName,mTextViewLoggedIn;


        public AccountsHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imagepopup);
            mTextViewName = itemView.findViewById(R.id.namePopup);
            mTextViewLoggedIn=itemView.findViewById(R.id.loggedpopoup);

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
        void OnItemClick(Account account);
    }

    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }



}
