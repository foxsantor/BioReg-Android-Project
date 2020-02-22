package com.example.bioregproject.ui.Settings.AccountMangement;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.google.android.material.textfield.TextInputLayout;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

public class AccountCreation extends Fragment {

    private static final int GALLERY_REQUEST = 1;
    private static final int  MY_CAMERA_PERMISSION_CODE = 2;
    private static final int CAMERA_REQUEST=3;
    private Bitmap bitmapContainer;
    private Bundle message;
    private AccountCreationViewModel mViewModel;
    private TextView imagenote,imageHinter;
    private CardView buttons;
    private ConstraintLayout loading,imageViews;
    private Button create,cancel,button7,button9;
    private ImageButton close,profileAdd,addImage,seeAlone;
    private TextInputLayout firstName,lastName,email,password,confirm,phone;
    private ImageView profile,imageView10;
    private RoundedImageView imageContainer;
    private static final Pattern PASSWORD_CHECHER =  Pattern.compile("^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            //"(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            //"(?=\\S+$)" +           //no white spaces
            ".{6,}" +               //at least 8 characters
            "$");

    public static AccountCreation newInstance() {
        return new AccountCreation();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_creation_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AccountCreationViewModel.class);

        loading = view.findViewById(R.id.loading_1);
        buttons = view.findViewById(R.id.buttons);
        imageHinter = view.findViewById(R.id.buttontext);
        imageViews = view.findViewById(R.id.imageViews);
        create = view.findViewById(R.id.button5);
        cancel = view.findViewById(R.id.button6);
        button7= view.findViewById(R.id.button7);
        button9 = view.findViewById(R.id.button9);
        close = view.findViewById(R.id.closeimage);
        confirm= view.findViewById(R.id.confirm);
        imageContainer = view.findViewById(R.id.image_container);
        profile= view.findViewById(R.id.imageView4);
        imageView10= view.findViewById(R.id.imageView10);
        firstName= view.findViewById(R.id.fname);
        lastName= view.findViewById(R.id.lname);
        phone= view.findViewById(R.id.phone);
        email= view.findViewById(R.id.email);
        password= view.findViewById(R.id.password);
        profileAdd= view.findViewById(R.id.imageButton);
        addImage= view.findViewById(R.id.addImage);
        seeAlone= view.findViewById(R.id.seealone);
        imagenote = view.findViewById(R.id.imagenote);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageContainer.setVisibility(View.GONE);
                imageHinter.setVisibility(View.VISIBLE);
                close.setVisibility(View.GONE);
                buttons.setVisibility(View.VISIBLE);
                bitmapContainer=null;

            }
        });

        profileAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViews.setVisibility(View.VISIBLE);
            }
        });




        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViews.setVisibility(View.GONE);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if(!StaticUse.validateEmpty(firstName,"First name") | !StaticUse.validateEmpty(lastName,"Last name")
                | !Password(password,"Password") |!passwordChecker(password,confirm)
                |!StaticUse.validateEmpty(email,"Email")   | !StaticUse.validateEmail(email)
                ){return;}
                else {
                    loading.setVisibility(View.VISIBLE);
                    addNewAccount(password,firstName,lastName,phone,email,profile);
                    message = new Bundle();
                    message.putString("message","Account Created successfully ");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading.setVisibility(View.GONE);
                            Navigation.findNavController(v).navigate(R.id.accountMangmentFragment,message);
                        }
                    }, 1500);

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

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();
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


    private void addNewAccount(TextInputLayout password,TextInputLayout firstName, TextInputLayout lastName,TextInputLayout phone ,TextInputLayout email,ImageView profile)
    {
        Account account = new Account();
        account.setEmail(StaticUse.loadEmail(getActivity()));
        account.setFirstName(firstName.getEditText().getText().toString().trim());
        account.setLastName(lastName.getEditText().getText().toString().trim());
        account.setPassword(password.getEditText().getText().toString());
        account.setCreationDate(new Date());
        account.setLastLoggedIn(new Date());
        account.setEmail(email.getEditText().getText().toString().trim());
        long phoneNumber = 0;
        if(!phone.getEditText().getText().toString().trim().isEmpty())
            phoneNumber =Long.parseLong(phone.getEditText().getText().toString().trim());
        account.setPhoneNumber(phoneNumber);
        account.setProfileImage(StaticUse.imageGetter(profile));
        mViewModel.insert(account);
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

                        Glide.with(getActivity()).asBitmap().load(bitmap).into(imageContainer);
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
            //container.setImageBitmap(photo);
            Glide.with(getActivity()).asBitmap().load(photo).into(imageContainer);
            //Glide.with(getActivity()).asBitmap().load(photo).into(ImageView10);
            imageView10.setImageBitmap(photo);
            bitmapContainer = photo;



        }

    }



}
