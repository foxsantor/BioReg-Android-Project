package com.example.bioregproject.ui.Settings.AccountMangement;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.google.android.material.textfield.TextInputLayout;
import com.makeramen.roundedimageview.RoundedImageView;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class UpdateAccount extends Fragment {

    private UpdateAccountViewModel mViewModel;
    private TextInputLayout FirstName,password,LastName,email,phone;
    private TextView created,FirstNameText,passwordText,lastNameText,emailText,phoneText,imagenote,imageHinter;
    private ImageButton addProfile,editFirstName,editPassword,editLastName,editEmailText,editPhoneText,addImage,seeAlone,close;
    private ConstraintLayout imageViews,nocon,firstnameLayout,lastnameLayout,passwordLayout,addressLayout,mobileLayout,firstnameView,lastnameView,passwordView,addressView,mobileView;
    private ImageView profile,imageView10,imageContainer;
    private Bundle account;
    private CardView buttons,blue;
    private Button button7,button9;
    private static  Boolean changed = false;
    private static final int GALLERY_REQUEST = 1;
    private static final int  MY_CAMERA_PERMISSION_CODE = 2;
    private static final int CAMERA_REQUEST=3;
    private Bitmap bitmapContainer;

    private LifecycleOwner lifecycleOwner;


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
        lifecycleOwner = this;
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
        profile= view.findViewById(R.id.preview);
        addProfile= view.findViewById(R.id.imageButton2);
        firstnameLayout = view.findViewById(R.id.namelayout);
        lastnameLayout= view.findViewById(R.id.lastnamelayout);
        passwordLayout= view.findViewById(R.id.Passwordlayout);
        blue= view.findViewById(R.id.blue);
        addressLayout= view.findViewById(R.id.emaillayout);
        mobileLayout= view.findViewById(R.id.mobilelayout);
        firstnameView= view.findViewById(R.id.firstnameView);
        lastnameView= view.findViewById(R.id.lastnameView);
        passwordView= view.findViewById(R.id.PasswordView);
        addressView= view.findViewById(R.id.emailView);
        addProfile =view.findViewById(R.id.imageButton2);
        mobileView= view.findViewById(R.id.mobileView);

        buttons = view.findViewById(R.id.buttons);
        imageHinter = view.findViewById(R.id.buttontext);
        imageViews = view.findViewById(R.id.imageViewsC);
        button7= view.findViewById(R.id.button7);
        button9 = view.findViewById(R.id.button9);
        close = view.findViewById(R.id.closeimage);
        imageContainer = view.findViewById(R.id.image_container);
        imageView10= view.findViewById(R.id.imageView10);
        addImage= view.findViewById(R.id.addImage);
        seeAlone= view.findViewById(R.id.seealone);
        imagenote = view.findViewById(R.id.imagenote);

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

            created.setText("Last Modified: "+account.getString("created"));
            FirstName.getEditText().append(account.getString("firstname"));
            password.getEditText().append(account.getString("password"));
            LastName.getEditText().append(account.getString("lastname"));

            email.getEditText().append(account.getString("email"));

            if(account.getString("phone").equals("0")){
                phoneText.setText("");}
            else{
            phoneText.setText(account.getString("phone"));
                phone.getEditText().append(account.getString("phone"));}



        }

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmapContainer == null )
                {
                    Toast.makeText(getActivity(), "You didn't choose an image", Toast.LENGTH_SHORT).show();
                }else
                {
                    UpadateUser("","",null, StaticUse.transformerImageBytes(imageView10));
                    profile.setImageBitmap(bitmapContainer);
                    imageViews.setVisibility(View.GONE);

                }
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViews.setVisibility(View.GONE);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageContainer.setVisibility(View.GONE);
                imageHinter.setVisibility(View.VISIBLE);
                close.setVisibility(View.GONE);
                buttons.setVisibility(View.VISIBLE);
                blue.setVisibility(View.VISIBLE);
                bitmapContainer=null;

            }
        });
        addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViews.setVisibility(View.VISIBLE);
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmapContainer == null )
                {
                    Toast.makeText(getActivity(), "You didn't choose an image", Toast.LENGTH_SHORT).show();
                }else
                {
                    profile.setImageBitmap(bitmapContainer);
                    imageViews.setVisibility(View.GONE);

                }
            }
        });
        seeAlone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {

                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }

            }
        });



        editFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(LastName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(LastName,lastnameLayout,1,0);
                }
                if(password.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);
                }
                if(email.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(email,addressLayout,1,0);
                }
                if(phone.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(phone,mobileLayout,1,0);
                }
                FirstName.getEditText().requestFocus();
                firstnameView.setVisibility(View.GONE);
                Animator(FirstName,firstnameLayout,0,0);
            }else {
                    if(LastName.getVisibility() == View.VISIBLE)
                    {

                        shiftingViews(lastnameView,0);
                        shiftingViews(LastName,1);
                    }
                    if(password.getVisibility() == View.VISIBLE)
                    {
                        shiftingViews(passwordView,0);
                        shiftingViews(password,1);
                    }
                    if(email.getVisibility() == View.VISIBLE)
                    {

                        shiftingViews(addressView,0);
                        shiftingViews(email,1);
                    }
                    if(phone.getVisibility() == View.VISIBLE)
                    {
                        shiftingViews(mobileView,0);
                        shiftingViews(phone,1);

                    }
                    FirstName.getEditText().requestFocus();
                    firstnameView.setVisibility(View.GONE);
                    shiftingViews(FirstName,0);
                }}

        });
        FirstName.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    //UserMenuFragment.checker=0;
                    UpadateUser(FirstName.getEditText().getText().toString(),"firstname",FirstNameText,null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(FirstName,firstnameLayout,1,0);}else {
                        shiftingViews(firstnameView,0);
                        shiftingViews(FirstName,1);
                    }

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
                    //Toast.makeText(getActivity(), "password here ", Toast.LENGTH_SHORT).show();
                    //UserMenuFragment.checker=0;
                    UpadateUser(password.getEditText().getText().toString(),"password",passwordText,null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);}else {
                        shiftingViews(passwordView,0);
                        shiftingViews(password,1);
                    }
                    return true;
                }
                return false;
            }
        });


        email.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    //Toast.makeText(getActivity(), "password here ", Toast.LENGTH_SHORT).show();
                    //UserMenuFragment.checker=0;
                    UpadateUser(email.getEditText().getText().toString(),"email",emailText,null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(email,addressLayout,1,0);}else {
                        shiftingViews(addressView,0);
                        shiftingViews(email,1);
                    }
                    return true;
                }
                return false;
            }
        });
        LastName.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    //UserMenuFragment.checker=0;
                    UpadateUser(LastName.getEditText().getText().toString(),"lastname",lastNameText,null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(LastName,lastnameLayout,1,0);}else {
                        shiftingViews(lastnameView,0);
                        shiftingViews(LastName,1);
                    }
                    return true;
                }
                return false;
            }
        });
        phone.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    //UserMenuFragment.checker=0;
                    UpadateUser(phone.getEditText().getText().toString(),"phone",phoneText,null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(phone,mobileLayout,1,0);}else {
                        shiftingViews(mobileView,0);
                        shiftingViews(phone,1);
                    }
                    return true;
                }
                return false;
            }
        });


        editLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(FirstName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(FirstName,firstnameLayout,1,0);

                }
                if(password.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);
                }
                if(email.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(email,addressLayout,1,0);
                }
                if(phone.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(phone,mobileLayout,1,0);
                }
                LastName.getEditText().requestFocus();
                lastnameView.setVisibility(View.GONE);
                Animator(LastName,lastnameLayout,0,0);
            }else {
                    if(FirstName.getVisibility() == View.VISIBLE)
                    {
                        shiftingViews(firstnameView,0);
                        shiftingViews(FirstName,1);
                    }
                    if(password.getVisibility() == View.VISIBLE)
                    {
                        shiftingViews(passwordView,0);
                        shiftingViews(password,1);

                    }
                    if(email.getVisibility() == View.VISIBLE)
                    {
                        shiftingViews(addressView,0);
                        shiftingViews(email,1);
                    }
                    if(phone.getVisibility() == View.VISIBLE)
                    {
                        shiftingViews(mobileView,0);
                        shiftingViews(phone,1);

                    }
                    LastName.getEditText().requestFocus();
                    lastnameView.setVisibility(View.GONE);
                    shiftingViews(LastName,0);
                }}
        });


        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(LastName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(LastName,lastnameLayout,1,0);
                }
                if(FirstName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(FirstName,firstnameLayout,1,0);
                }
                if(email.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(email,addressLayout,1,0);
                }
                if(phone.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(phone,mobileLayout,1,0);
                }
                passwordView.setVisibility(View.GONE);
                Animator(password,passwordLayout,0,0);
            }else {
                    if(LastName.getVisibility() == View.VISIBLE)
                    {

                        shiftingViews(lastnameView,0);
                        shiftingViews(LastName,1);
                    }
                    if(FirstName.getVisibility() == View.VISIBLE)
                    {

                        shiftingViews(firstnameView,0);
                        shiftingViews(FirstName,1);
                    }
                    if(email.getVisibility() == View.VISIBLE)
                    {

                        shiftingViews(addressView,0);
                        shiftingViews(email,1);
                    }
                    if(phone.getVisibility() == View.VISIBLE)
                    {

                        shiftingViews(mobileView,0);
                        shiftingViews(phone,1);
                    }
                    password.getEditText().requestFocus();
                    passwordView.setVisibility(View.GONE);
                    shiftingViews(password,0);
                }}
        });


        editEmailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(LastName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(LastName,lastnameLayout,1,0);
                }
                if(password.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);
                }
                if(FirstName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(FirstName,firstnameLayout,1,0);
                }
                if(phone.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(phone,mobileLayout,1,0);
                }
                email.getEditText().requestFocus();
                addressView.setVisibility(View.GONE);
                Animator(email,addressLayout,0,0);
            }else {
                    if(LastName.getVisibility() == View.VISIBLE)
                    {
                        shiftingViews(lastnameView,0);
                        shiftingViews(LastName,1);
                    }
                    if(password.getVisibility() == View.VISIBLE)
                    {

                        shiftingViews(passwordView,0);
                        shiftingViews(password,1);
                    }
                    if(FirstName.getVisibility() == View.VISIBLE)
                    {

                        shiftingViews(firstnameView,0);
                        shiftingViews(FirstName,1);
                    }
                    if(phone.getVisibility() == View.VISIBLE)
                    {
                        shiftingViews(mobileView,0);
                        shiftingViews(phone,1);
                    }
                    email.getEditText().requestFocus();
                    addressView.setVisibility(View.GONE);
                    shiftingViews(email,0);
                }}
        });


        editPhoneText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(LastName.getVisibility() == View.VISIBLE)
                {

                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(LastName,lastnameLayout,1,0);
                }
                if(password.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);
                }
                if(email.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(email,addressLayout,1,0);
                }
                if(FirstName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(FirstName,firstnameLayout,1,0);
                }
                phone.getEditText().requestFocus();
                mobileView.setVisibility(View.GONE);
                Animator(phone,mobileLayout,0,0);

            }else {
                    if(LastName.getVisibility() == View.VISIBLE)
                    {

                        shiftingViews(lastnameView,0);
                        shiftingViews(LastName,1);
                    }
                    if(password.getVisibility() == View.VISIBLE)
                    {
                        shiftingViews(passwordView,0);
                        shiftingViews(password,1);
                    }
                    if(email.getVisibility() == View.VISIBLE)
                    {
                        shiftingViews(addressView,0);
                        shiftingViews(email,1);
                    }
                    if(FirstName.getVisibility() == View.VISIBLE)
                    {
                        shiftingViews(firstnameView,0);
                        shiftingViews(FirstName,1);
                    }
                    phone.getEditText().requestFocus();
                    mobileView.setVisibility(View.GONE);
                    shiftingViews(phone,0);
                }}
        });



        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.mangeAccount);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);





    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void AnimatorView(ViewGroup object, ViewGroup Layout, int state ,int direction )
    {
        Transition transition2;
        if(direction==0)
        {
            transition2 = new Slide(Gravity.RIGHT);
        }else
        {
            transition2 = new Slide(Gravity.RIGHT);
        }
        transition2.setDuration(300);
        transition2.addTarget(object.getId());
        TransitionManager.beginDelayedTransition(Layout, transition2);
        if(state ==0){
            object.setVisibility(View.VISIBLE);
        }else {
            object.setVisibility(View.GONE);
        }}
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void Animator(TextInputLayout object, ViewGroup Layout, int state ,int direction )
    {
        Transition transition2;
        if(direction==0)
        {
            transition2 = new Slide(Gravity.RIGHT);
        }else
        {
            transition2 = new Slide(Gravity.RIGHT);
        }
        transition2.setDuration(300);
        transition2.addTarget(object.getId());
        TransitionManager.beginDelayedTransition(Layout, transition2);
        if(state ==0){
            object.setVisibility(View.VISIBLE);
        }else {
            object.setVisibility(View.GONE);
        }}

    private void UpadateUser( final String target , String label, final TextView targetView,byte[] imageView)
    {
        mViewModel.getAccount(account.getLong("id")).observe(lifecycleOwner, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                Account model = new Account();
                model.setCreationDate(new Date());
                model.setLastName(accounts.get(0).getLastName());
                model.setPassword(accounts.get(0).getPassword());
                model.setEmail(accounts.get(0).getEmail());
                model.setFirstName(accounts.get(0).getFirstName());
                model.setLastLoggedIn(accounts.get(0).getLastLoggedIn());
                model.setPhoneNumber(accounts.get(0).getPhoneNumber());
                model.setId(accounts.get(0).getId());

                if(imageView==null)
                {
                    model.setProfileImage(accounts.get(0).getProfileImage());
                }else
                {
                    model.setProfileImage(imageView);
                }

            switch (label){

                   case "firstname" :
                       if(target.isEmpty()|| target.equals(""))
                       {

                       }else{
                    model.setFirstName(target);
                    targetView.setText(target);}
                       break;
                   case  "lastname":
                       if(target.isEmpty()|| target.equals(""))
                       {

                       }else{
                      model.setLastName(target);
                       targetView.setText(target);}
                      break;
                   case "email":
                       if(target.isEmpty()|| target.equals(""))
                       {

                       }else{
                       model.setEmail(target);
                       targetView.setText(target);}
                       break;
                   case "phone":
                       if(target.isEmpty()|| target.equals(""))
                       {

                       }else{
                       model.setPhoneNumber(Long.parseLong(target));
                       targetView.setText(target);
                       }
                       break;
                   case "password":
                       if(target.isEmpty()|| target.equals(""))
                       {

                       }else{
                      model.setPassword(target);
                       targetView.setText(target);
                       }
                       break;
                    default:
                        break;
               }

                mViewModel.getAccount(account.getLong("id")).removeObservers(lifecycleOwner);
                mViewModel.update(model);
                PrettyTime p = new PrettyTime();
                created.setText("Last Modified: "+ p.format(new Date()));
                Toast.makeText(getActivity(), "Data has been Updated", Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Bundle account = new Bundle();
                        account.putString("firstname",model.getFirstName());
                        account.putLong("id",model.getId());
                        account.putByteArray("image",model.getProfileImage());
                        account.putString("lastname",model.getLastName());
                        account.putString("created",p.format(model.getCreationDate()));
                        account.putString("password",model.getPassword());
                        account.putString("email",model.getEmail());
                        account.putString("phone",String.valueOf(model.getPhoneNumber()));
                        Navigation.findNavController(getActivity(),R.id.fragment).navigate(R.id.updateAccount,account);
                        handler.removeCallbacksAndMessages(null);
                    }
                }, 1000);

            }
        });

    }

    private void shiftingViews(ViewGroup object,int state)
    {
        if(state ==0){
            Animation animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.slide_right);
            object.startAnimation(animFadeIn);
            object.setVisibility(View.VISIBLE);

        }else {
            Animation animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.slide_left);
            object.startAnimation(animFadeIn);
            object.setVisibility(View.GONE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        if(lengthbmp >= 1000000*10)
                        {
                            imagenote.setText(Html.fromHtml("<font color=red>"+imagenote.getText().toString()+"</font>"));
                            return;
                        }

                        imageContainer.setVisibility(View.VISIBLE);
                        imageHinter.setVisibility(View.GONE);
                        close.setVisibility(View.VISIBLE);
                        buttons.setVisibility(View.GONE);
                        blue.setVisibility(View.GONE);
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.ic_warning_black_24dp)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();
                        Glide.with(getActivity()).asBitmap().load(bitmap).apply(options).into(imageContainer);
                        //container.setImageBitmap(bitmap);
                        // Glide.with(getActivity()).asBitmap().load(bitmap).into(ImageView10);
                        imageView10.setImageBitmap(bitmap);
                        bitmapContainer = bitmap;


                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageContainer.setVisibility(View.VISIBLE);
            imageHinter.setVisibility(View.GONE);
            close.setVisibility(View.VISIBLE);
            buttons.setVisibility(View.GONE);
            blue.setVisibility(View.GONE);
            //container.setImageBitmap(photo);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_warning_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();
            Glide.with(getActivity()).asBitmap().load(photo).apply(options).into(imageContainer);
            //Glide.with(getActivity()).asBitmap().load(photo).into(ImageView10);
            imageView10.setImageBitmap(photo);
            bitmapContainer = photo;



        }

    }
    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

    }
}
