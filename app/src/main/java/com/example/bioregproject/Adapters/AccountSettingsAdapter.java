package com.example.bioregproject.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.ui.Authentication.AccountBinderViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class AccountSettingsAdapter extends ListAdapter<Account,AccountSettingsAdapter.AccountsHolder> {

    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;
    private AccountBinderViewModel model;

    public AccountSettingsAdapter(Context context,Activity activity) {
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
                .inflate(R.layout.item_account_card, parent, false);
        return new AccountsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsHolder holder, int position) {

        final Account currentItem = getItem(position);
        final PrettyTime p = new PrettyTime();
        final String firstName = currentItem.getFirstName();
        String lastName = currentItem.getLastName();
        Date lastLoggedIn = currentItem.getCreationDate();
        final String fullName = StaticUse.capitalize(firstName)+" "+StaticUse.capitalize(lastName);
        final byte[] image = currentItem.getProfileImage();
        if(currentItem.getFirstName().equals("Administrator"))
        {
            holder.delete.setVisibility(View.GONE);
        }
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
        holder.mTextViewLoggedIn.setText("Last Updated "+p.format(lastLoggedIn));

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentItem.getFirstName().equals("Administrator"))
                {
                    Toast.makeText(mContext, "Access Denied", Toast.LENGTH_SHORT).show();
                   return;
                }else
                {
                    Bundle account = new Bundle();
                    account.putString("firstname",currentItem.getFirstName());
                    account.putLong("id",currentItem.getId());
                    account.putByteArray("image",currentItem.getProfileImage());
                    account.putString("lastname",currentItem.getLastName());
                    account.putString("created",p.format(currentItem.getCreationDate()));
                    account.putString("password",currentItem.getPassword());
                    account.putString("email",currentItem.getEmail());
                    account.putString("phone",String.valueOf(currentItem.getPhoneNumber()));
                    Navigation.findNavController(v).navigate(R.id.action_mangeAccount_to_updateAccount,account);
                }

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog diaBox = MainActivity.AskOption(mContext,currentItem);
                diaBox.show();

            }
        });

    }


    public Account getAccountAt(int postion) {
        return getItem(postion);
    }

    class AccountsHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextViewName,mTextViewLoggedIn;
        public ImageButton delete,edit;


        public AccountsHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageView5);
            mTextViewName = itemView.findViewById(R.id.Name);
            mTextViewLoggedIn=itemView.findViewById(R.id.created);
            delete = itemView.findViewById(R.id.remove);
            edit = itemView.findViewById(R.id.edit);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.OnItemClick(getItem(position));
                }
            });*/
        }

    }

    public interface OnItemClickLisnter {
        void OnItemClick(Account account);
    }

    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}
