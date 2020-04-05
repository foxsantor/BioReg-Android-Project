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
import com.example.bioregproject.entities.Products;
import com.example.bioregproject.ui.Authentication.AccountBinderViewModel;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTracedAdapater extends ListAdapter<Products,DataTracedAdapater.AccountsHolder> {

    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;
    private AccountBinderViewModel model;

    public DataTracedAdapater(Context context,Activity activity) {
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
                    oldItem.getImage()== newItem.getImage() ;
        }
    };

    @NonNull
    @Override
    public AccountsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mangedata, parent, false);
        return new AccountsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsHolder holder, int position) {

        final Products currentItem = getItem(position);
        final byte[] image = currentItem.getImage();
        Glide.with(mContext).asBitmap().load(image).into(holder.mImageView);
       final String newstring = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(currentItem.getCreationDate());
        holder.mTextcreated.setText("Taken at: " + newstring);
        holder.mTextId.setText("Id: "+currentItem.getId());
        if(currentItem.getName() == null || currentItem.getName().equals("") )
        {
            holder.state.setText("Not Bound");
            holder.state.setBackgroundColor(mContext.getResources().getColor(R.color.yellowWarning));
        }else{
            holder.state.setText("Bound");
            holder.state.setBackgroundColor(mContext.getResources().getColor(R.color.greenJade));
        }

        holder.bind.setVisibility(View.GONE);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Bundle bundle = new Bundle();
                bundle.putLong("id",currentItem.getId());
                bundle.putByteArray("image",image);
                Navigation.findNavController(v).navigate(R.id.action_manageData_to_imageFlowAddImage,bundle);
                   /* Bundle account = new Bundle();
                    account.putString("firstname",currentItem.getFirstName());
                    account.putLong("id",currentItem.getId());
                    account.putByteArray("image",currentItem.getProfileImage());
                    account.putString("lastname",currentItem.getLastName());
                    account.putString("created",p.format(currentItem.getCreationDate()));
                    account.putString("password",currentItem.getPassword());
                    account.putString("email",currentItem.getEmail());
                    account.putString("phone",String.valueOf(currentItem.getPhoneNumber()));
                   */
            }
        });

        holder.qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle bundle = new Bundle();
                bundle.putString("id",""+currentItem.getId());
                bundle.putString("created",newstring);
                bundle.putByteArray("image",image);
                bundle.putInt("dest",1);
                Navigation.findNavController(v).navigate(R.id.action_manageData_to_iamgeFlowPrinting,bundle);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = MainActivity.AskOptionPro(mContext,currentItem,(LifecycleOwner)activity,"Visual Traceability");
                diaBox.show();

            }
        });
        holder.zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public Products getAccountAt(int postion) {
        return getItem(postion);
    }

    class AccountsHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public Button state;
        public TextView mTextcreated,mTextId;
        public ImageButton delete,edit,qr,bind,zoom;


        public AccountsHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.preview);
            mTextcreated = itemView.findViewById(R.id.created);
            mTextId=itemView.findViewById(R.id.id);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);
            qr = itemView.findViewById(R.id.Qrs);
            state = itemView.findViewById(R.id.state);
            zoom = itemView.findViewById(R.id.zoom);
            bind = itemView.findViewById(R.id.bind);


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
