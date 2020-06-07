package com.example.bioregproject.ui.Settings.AccountMangement;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bioregproject.R;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateAccount extends Fragment {

    private UpdateAccountViewModel mViewModel;
    private TextInputLayout FirstName,password,LastName,email,phone;
    private TextView created,FirstNameText,passwordText,lastNameText,emailText,phoneText;
    private ImageButton addProfile,editFirstName,editPassword,editLastName,editEmailText,editPhoneText;
    private ConstraintLayout passwordView;
    private ImageView profile;
    private Bundle account;


    public static UpdateAccount newInstance() {
        return new UpdateAccount();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.update_account_fragment, container, false);

    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UpdateAccountViewModel.class);
        editEmailText= view.findViewById(R.id.edit4);
        editFirstName= view.findViewById(R.id.edit1);
        editPhoneText= view.findViewById(R.id.edit7);
        editPassword =view.findViewById(R.id.edit3);
        editLastName= view.findViewById(R.id.edit2);
        FirstName = view.findViewById(R.id.firstnameedit);
        password = view.findViewById(R.id.Passwordedit);
        LastName =  view.findViewById(R.id.lastnameedit);
        email = view.findViewById(R.id.emailedit);
        phone = view.findViewById(R.id.mobiledit);
        created = view.findViewById(R.id.modified);
        FirstNameText = view.findViewById(R.id.firstname);
        passwordText= view.findViewById(R.id.Password);
        lastNameText = view.findViewById(R.id.lastname);
        emailText= view.findViewById(R.id.email);
        phoneText= view.findViewById(R.id.Mobile);
        profile= view.findViewById(R.id.imageView6);
        addProfile= view.findViewById(R.id.imageButton2);
        account = getArguments();
        if(account!=null)
        {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_warning_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();
            Glide.with(getActivity()).asBitmap().load(account.getByteArray("image")).apply(options).into(profile);
            FirstNameText.setText(account.getString("firstname"));
            passwordText.setText(account.getString("password"));
            lastNameText.setText(account.getString("lastname"));
            emailText.setText(account.getString("email"));
            created.setText("Created: "+account.getString("created"));

            if(account.getString("phone").equals("0"))
                phoneText.setText("");
            else
            phoneText.setText(account.getString("phone"));



        }
/*
        editFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(LastName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(lastName,lastnameLayout,1,0);
                }
                if(password.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);
                }
                if(address.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(address,addressLayout,1,0);
                }
                if(mobilePhone.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(mobilePhone,mobileLayout,1,0);
                }
                firstName.getEditText().requestFocus();
                firstnameView.setVisibility(View.GONE);
                Animator(firstName,firstnameLayout,0,0);
            }

        });
        firstName.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    UserMenuFragment.checker=0;
                    UpadateUser(username=loadUsername(),firstName.getEditText().getText().toString(),"firstname",firstname_x);
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(firstName,firstnameLayout,1,0);

                    return true;
                }
                return false;
            }
        });
        address.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    UserMenuFragment.checker=0;
                    UpadateUser(username=loadUsername(),address.getEditText().getText().toString(),"address",address_x);
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(address,addressLayout,1,0);
                    return true;
                }
                return false;
            }
        });
        password.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    Toast.makeText(getActivity(), "password here ", Toast.LENGTH_SHORT).show();
                    UserMenuFragment.checker=0;
                    UpadateUser(username=loadUsername(),firstName.getEditText().getText().toString(),"firstname",firstname_x);
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(password,firstnameLayout,1,0);
                    return true;
                }
                return false;
            }
        });
        lastName.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    UserMenuFragment.checker=0;
                    UpadateUser(username=loadUsername(),lastName.getEditText().getText().toString(),"lastname",lasstname_x);
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(lastName,lastnameLayout,1,0);
                    return true;
                }
                return false;
            }
        });
        mobilePhone.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    UserMenuFragment.checker=0;
                    UpadateUser(username=loadUsername(),mobilePhone.getEditText().getText().toString(),"phone",mobile_x);
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(mobilePhone,mobileLayout,1,0);
                    return true;
                }
                return false;
            }
        });

        edit2_lastname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetworkConnection())
                {
                    nocon.setVisibility(View.VISIBLE);
                    editFirstName.setEnabled(false);
                    edit2_lastname.setEnabled(false);
                    edit3_password.setEnabled(false);
                    edit6_address.setEnabled(false);
                    edit7_mobile.setEnabled(false);
                    return;
                }
                if(firstName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(firstName,firstnameLayout,1,0);
                }
                if(password.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);
                }
                if(address.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(address,addressLayout,1,0);
                }
                if(mobilePhone.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(mobilePhone,mobileLayout,1,0);
                }
                lastName.getEditText().requestFocus();
                lastnameView.setVisibility(View.GONE);
                Animator(lastName,lastnameLayout,0,0);
            }
        });


        edit3_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetworkConnection())
                {
                    nocon.setVisibility(View.VISIBLE);
                    editFirstName.setEnabled(false);
                    edit2_lastname.setEnabled(false);
                    edit3_password.setEnabled(false);
                    edit6_address.setEnabled(false);
                    edit7_mobile.setEnabled(false);
                    return;
                }
                if(lastName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(lastName,lastnameLayout,1,0);
                }
                if(firstName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(firstName,firstnameLayout,1,0);
                }
                if(address.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(address,addressLayout,1,0);
                }
                if(mobilePhone.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(mobilePhone,mobileLayout,1,0);
                }
                passwordView.setVisibility(View.GONE);
                Animator(password,passwordLayout,0,0);
            }
        });


        edit6_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetworkConnection())
                {
                    nocon.setVisibility(View.VISIBLE);
                    editFirstName.setEnabled(false);
                    edit2_lastname.setEnabled(false);
                    edit3_password.setEnabled(false);
                    edit6_address.setEnabled(false);
                    edit7_mobile.setEnabled(false);
                    return;
                }
                if(lastName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(lastName,lastnameLayout,1,0);
                }
                if(password.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);
                }
                if(firstName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(firstName,firstnameLayout,1,0);
                }
                if(mobilePhone.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(mobilePhone,mobileLayout,1,0);
                }
                address.getEditText().requestFocus();
                addressView.setVisibility(View.GONE);
                Animator(address,addressLayout,0,0);
            }
        });


        edit7_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetworkConnection())
                {
                    nocon.setVisibility(View.VISIBLE);
                    editFirstName.setEnabled(false);
                    edit2_lastname.setEnabled(false);
                    edit3_password.setEnabled(false);
                    edit6_address.setEnabled(false);
                    edit7_mobile.setEnabled(false);
                    return;
                }
                if(lastName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(lastName,lastnameLayout,1,0);
                }
                if(password.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);
                }
                if(address.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(address,addressLayout,1,0);
                }
                if(firstName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(firstName,firstnameLayout,1,0);
                }
                mobilePhone.getEditText().requestFocus();
                mobileView.setVisibility(View.GONE);
                Animator(mobilePhone,mobileLayout,0,0);

            }
        });
*/


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.mangeAccount);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);





    }
}
