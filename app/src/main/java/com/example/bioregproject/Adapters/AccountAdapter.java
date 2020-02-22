package com.example.bioregproject.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;


public class AccountAdapter extends ListAdapter<Account,AccountAdapter.AccountsHolder> {

    private OnItemClickLisnter listener;
    private Context mContext;
    private Activity activity;

    public AccountAdapter(Context context,Activity activity) {
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
                .inflate(R.layout.user_card, parent, false);
        return new AccountsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsHolder holder, int position) {

        final Account currentItem = getItem(position);
        PrettyTime p = new PrettyTime();

        final   ConstraintLayout layout = activity.findViewById(R.id.connetcd);
        final ImageView profileImage= activity.findViewById(R.id.image);
        final TextView nameProfile= activity.findViewById(R.id.name);

        final String firstName = currentItem.getFirstName();
        String lastName = currentItem.getLastName();
        Date lastLoggedIn = currentItem.getLastLoggedIn();
        final String fullName = StaticUse.capitalize(firstName)+" "+StaticUse.capitalize(lastName);
        final byte[] image = currentItem.getProfileImage();
        Glide.with(mContext).asBitmap().load(image).into(holder.mImageView);
        holder.mTextViewName.setText(fullName);
        holder.mTextViewLoggedIn.setText(p.format(lastLoggedIn));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                LayoutInflater layoutInflater =  (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogueView =layoutInflater.inflate(R.layout.password_confirm_dialogue,null);
                alert.setView(dialogueView);
                final TextInputLayout textInputLayout = dialogueView.findViewById(R.id.argax);
                final TextView fullNameView = dialogueView.findViewById(R.id.fullName);
                final Button logIn = dialogueView.findViewById(R.id.log);
                final Button back = dialogueView.findViewById(R.id.back);

                alert.setTitle("Sign In");
                fullNameView.setText("Logging in as "+fullName);
                //TextInputLayout textInputLayout =
                // Set an EditText view to get user input

                final AlertDialog alerti =alert.show();
                //Button save = alerti.getButton(AlertDialog.BUTTON_POSITIVE);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alerti.dismiss();
                    }
                });
                logIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View vi) {
                        if(!textInputLayout.getEditText().getText().toString().isEmpty() && !textInputLayout.getEditText().getText().toString().equals("") && textInputLayout.getEditText().getText().toString()!=null)
                        {
                            if(textInputLayout.getEditText().getText().toString().equals(currentItem.getPassword())){
                            textInputLayout.setError(null);
                            StaticUse.saveSession(mContext,currentItem.getPassword(),fullName,currentItem.getId());
                            layout.setVisibility(View.VISIBLE);
                            nameProfile.setText(fullName);
                            Glide.with(mContext).asBitmap().load(image).into(profileImage);
                            Navigation.findNavController(v).navigate(R.id.mainMenu);
                            alerti.dismiss();
                            }else
                            {
                                textInputLayout.setError("Password is wrong ");
                            }

                        }

                    }
                });
            }
        });

    }


    public Account getAccountAt(int postion) {
        return getItem(postion);
    }

    class AccountsHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextViewName,mTextViewLoggedIn;
        public FloatingActionButton button;

        public AccountsHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageProfile);
            mTextViewName = itemView.findViewById(R.id.fullName);
            mTextViewLoggedIn=itemView.findViewById(R.id.lastLoggedIn);
            button = itemView.findViewById(R.id.logIn);



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
