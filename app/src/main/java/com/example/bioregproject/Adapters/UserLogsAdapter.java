package com.example.bioregproject.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.ui.Authentication.AccountBinderViewModel;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserLogsAdapter extends ListAdapter<Account,UserLogsAdapter.AccountsHolder> {

    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;
    private AccountBinderViewModel model;

    public UserLogsAdapter(Context context,Activity activity) {
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
            return  oldItem.getCreationDate().equals(newItem.getCreationDate()) &&
                    oldItem.getFirstName().equals (newItem.getFirstName()) &&
                    oldItem.getLastName().equals (newItem.getLastName()) ;
        }
    };

    @NonNull
    @Override
    public AccountsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_card, parent, false);
        return new AccountsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsHolder holder, int position) {

        final Account currentItem = getItem(position);
        final byte[] image = currentItem.getProfileImage();
        Glide.with(mContext).asBitmap().load(image).into(holder.mImageView);
        final String fullName = StaticUse.capitalize(currentItem.getFirstName())+" "+StaticUse.capitalize(currentItem.getLastName());
        //final String newstring = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentItem.getCreationDate());
        holder.mTextcreated.setText(fullName);

//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final Bundle bundle = new Bundle();
//                bundle.putLong("id",currentItem.getId());
//                bundle.putByteArray("image",image);
//                Navigation.findNavController(v).navigate(R.id.action_manageData_to_imageFlowAddImage,bundle);
//                   /* Bundle account = new Bundle();
//                    account.putString("firstname",currentItem.getFirstName());
//                    account.putLong("id",currentItem.getId());
//                    account.putByteArray("image",currentItem.getProfileImage());
//                    account.putString("lastname",currentItem.getLastName());
//                    account.putString("created",p.format(currentItem.getCreationDate()));
//                    account.putString("password",currentItem.getPassword());
//                    account.putString("email",currentItem.getEmail());
//                    account.putString("phone",String.valueOf(currentItem.getPhoneNumber()));
//
//            }
//        });


    }


    public Account getAccountAt(int postion) {
        return getItem(postion);
    }

    class AccountsHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextcreated;



        public AccountsHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.categoryimage);
            mTextcreated = itemView.findViewById(R.id.categoryname);



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
