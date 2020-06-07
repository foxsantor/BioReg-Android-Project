package com.example.bioregproject.ui.Authentication;

import androidx.activity.OnBackPressedCallback;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.UserHandle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bioregproject.Adapters.AccountAdapter;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class AccountBinder extends Fragment {

    private AccountBinderViewModel mViewModel;
    private Button save;
    private boolean exit = false;
    private ImageView admin;
    private RecyclerView accounts;
    private CardView cardFirst;
    private ImageButton notifcation;
    private ConstraintLayout first;
    private TextInputLayout password,confirmPassword;
    private static final Pattern PASSWORD_CHECHER =  Pattern.compile("^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            //"(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            //"(?=\\S+$)" +           //no white spaces
            ".{6,}" +               //at least 8 characters
            "$");


    public static AccountBinder newInstance() {
        return new AccountBinder();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.account_bindiner_fragment, container, false);

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) requireActivity()).getSupportActionBar().show();
        notifcation = getActivity().findViewById(R.id.notification);
        notifcation.setVisibility(View.VISIBLE);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(exit)
                {
                    getActivity().finish();
                    System.exit(0);
                }

                exit = true;
                Toast.makeText(getActivity(), "Click BACK again to exit", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        exit =false;
                    }
                }, 2000);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

         mViewModel = ViewModelProviders.of(this).get(AccountBinderViewModel.class);
        save = view.findViewById(R.id.save);
        first =  view.findViewById(R.id.first);
        first.setVisibility(View.GONE);
        admin = view.findViewById(R.id.admin);
        accounts = view.findViewById(R.id.accounts);
        cardFirst = view.findViewById(R.id.cardFirst);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirm);
        //testing
        //mViewModel.deleteAll();
        //StaticUse.removeData(StaticUse.SHARED_NAME_ADMIN,"password",getActivity());



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Password(password,"Password") | !passwordChecker(password,confirmPassword)){return;}
                else {
                    createAdmin();
                    Animation animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.slide_left);
                    cardFirst.startAnimation(animFadeIn);
                    cardFirst.setVisibility(View.GONE);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                    first.setVisibility(View.GONE);
                        }
                    }, 550);
                }
            }
        });

        accounts.setLayoutManager(new LinearLayoutManager(getContext()));
        final AccountAdapter adapter = new AccountAdapter(getActivity(),getActivity());
        accounts.setAdapter(adapter);
        mViewModel.getAllAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                if(accounts.isEmpty())
                {
                    if(!StaticUse.hasAdmin(getActivity())){first.setVisibility(View.VISIBLE);}
                }else
                {
                    adapter.submitList(accounts);

                }
            }
        });
    }


    private  boolean passwordChecker (TextInputLayout password ,TextInputLayout confirm)
    {
        if(confirm.getError()!= null)
        {confirm.setError(null);}
        String passwordToCheck = password.getEditText().getText().toString().trim();
        String confirmPasswordToCheck = confirm.getEditText().getText().toString().trim();
        if(confirmPasswordToCheck.isEmpty()){
            confirm.setError("This field can't be empty");
            return false;

        }else if(!passwordToCheck.equals(confirmPasswordToCheck)){
            confirm.setError("Password does not match ");
            return false;
        }else if(checkPassword(password))
        {
            confirm.setError("Password must be valid first");
            return false;

        }
        else
        {
            confirm.setError(null);
            return true;
        }
    }
    private  boolean checkPassword(TextInputLayout password)
    {

        return (password.getError() != null);
    }
    private boolean Password (TextInputLayout text , String type)
    {
        if(text.getError()!= null)
        {text.setError(null);}
        String textToCheck = text.getEditText().getText().toString().trim();
        if(textToCheck.isEmpty()){
            text.setError(type +" can't be empty");

            return false;}

        else if (!PASSWORD_CHECHER.matcher(textToCheck).matches()){
                text.setError(Html.fromHtml("*Password must be at least 6 characters"));
                return false;
        }else
        {
            text.setError(null);
            return true;
        }

    }
    private void createAdmin()
    {

        Account admin = new Account();
        admin.setEmail(StaticUse.loadEmail(getActivity()));
        admin.setFirstName("Administrator");
        admin.setLastName("");
        admin.setPassword(password.getEditText().getText().toString());
        admin.setCreationDate(new Date());
        admin.setLastLoggedIn(new Date());
        this.admin.setImageResource(R.drawable.admin_user);
        byte[] bitmapdata = StaticUse.transformerImageBytes(this.admin);
        admin.setProfileImage(bitmapdata);
        mViewModel.insert(admin);
        StaticUse.saveAdmin(getActivity(),password.getEditText().getText().toString());

    }

}
