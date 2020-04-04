package com.example.bioregproject.ui.Traceability.ManualTraceablility;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bioregproject.Adapters.SpinnerCatAdapter;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Category;
import com.example.bioregproject.entities.Products;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FormManual extends Fragment {

    private FormManualViewModel mViewModel;
    private Button back,save;
    private TextView imageNote;
    private ImageButton addCat,addImage,addImage2,cancel;
    private ImageView preiview,container;
    private TextInputLayout dateTimeExipartion,fref,fabrication,Category,brandName,fname;
    private ConstraintLayout constraintLayout,layoutPreview;
    private Spinner spinner;
    private static final int GALLERY_REQUEST = 1;
    private static final int  MY_CAMERA_PERMISSION_CODE = 2;
    private static final int CAMERA_REQUEST=3;
    private Bitmap bitmapContainer;
    private String category;

    public static FormManual newInstance() {
        return new FormManual();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form_manual_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FormManualViewModel.class);
        cancel = view.findViewById(R.id.cancel);
        back = view.findViewById(R.id.Back);
        save = view.findViewById(R.id.Save);
        container = view.findViewById(R.id.container);
        addCat = view.findViewById(R.id.addCat);
        addImage = view.findViewById(R.id.addImage);
        addImage2 = view.findViewById(R.id.addImage2);
        preiview = view.findViewById(R.id.preiview);
        fref = view.findViewById(R.id.fref);
        imageNote= view.findViewById(R.id.imagNote);
        fabrication = view.findViewById(R.id.fabrication);
        Category = view.findViewById(R.id.Category);
        brandName = view.findViewById(R.id.brandName);
        fname = view.findViewById(R.id.fname);
        layoutPreview = view.findViewById(R.id.layoutingPreview);
        spinner = view.findViewById(R.id.spinner);
        cancel.setVisibility(View.GONE);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_formManual_to_manualHome);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        SpinnerLoader(spinner);
        constraintLayout = view.findViewById(R.id.constraintlayout);
        StaticUse.backgroundAnimator(constraintLayout);
        dateTimeExipartion = view.findViewById(R.id.expiration);


        dateTimeExipartion.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(dateTimeExipartion.getEditText());
            }
        });
        dateTimeExipartion.setEnabled(false);
        dateTimeExipartion.setClickable(false);

        fabrication.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(fabrication.getEditText());
            }
        });

        fabrication.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!fabrication.getEditText().getText().toString().isEmpty())
                {
                    dateTimeExipartion.setEnabled(true);
                    dateTimeExipartion.setClickable(true);
                }else
                {
                    dateTimeExipartion.setEnabled(false);
                    dateTimeExipartion.setClickable(false);
                }

            }
        });
        addImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position>0)
                {
                    category = (String) parent.getItemAtPosition(position);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
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
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutPreview.setVisibility(View.VISIBLE);
                preiview.setImageResource(R.color.grayNotSelected);
                bitmapContainer = null;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!StaticUse.validateEmpty(fname,"Name") | !StaticUse.validateEmpty(brandName,"Brand Name")
                        | !StaticUse.validateEmpty(dateTimeExipartion,"Expiration Date") |!StaticUse.validateEmpty(fabrication,"Fabrication Date")
                        |!StaticUse.validateEmpty(fref,"Reference")   | !validateSpinner(category) | !validateImage(bitmapContainer)
                ){return;}
                else {
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Products product = new Products();
                    product.setType("ManuelT");
                    try {
                        product.setFabricationDate(simpleDateFormat.parse(fabrication.getEditText().getText().toString()));
                        product.setExpirationDate(simpleDateFormat.parse(dateTimeExipartion.getEditText().getText().toString()));
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    product.setImage(StaticUse.imageGetter(container));
                    product.setName(fname.getEditText().getText().toString());
                    product.setCreationDate(new Date());
                    product.setBrandName(brandName.getEditText().getText().toString());
                    product.setCategoryName(category);
                    product.setRefrence(fref.getEditText().getText().toString());
                    mViewModel.insert(product);
                    Toast.makeText(getActivity(), "Product Added Successfully", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            }
        });




    }


    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(getActivity(),timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(getActivity(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    private void SpinnerLoader(Spinner spinner)
    {
        final ArrayList<String> spinnerArray =  new ArrayList<>();
        spinnerArray.add("Choose a Category *");
        mViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                for (Category cat: categories
                     ) {
                  spinnerArray.add(cat.getName());
                }
            }
        });
        SpinnerCatAdapter adapter = new SpinnerCatAdapter(getActivity(),spinnerArray);
        spinner.setAdapter(adapter);
    }

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
                            imageNote.setText(Html.fromHtml("<font color=red>"+imageNote.getText().toString()+"</font>"));
                            return;
                        }

                        //imageContainer.setVisibility(View.VISIBLE);
                        //imageHinter.setVisibility(View.GONE);
                        cancel.setVisibility(View.VISIBLE);
                        layoutPreview.setVisibility(View.GONE);

                        Glide.with(getActivity()).asBitmap().load(bitmap).into(preiview);
                        //container.setImageBitmap(bitmap);
                        // Glide.with(getActivity()).asBitmap().load(bitmap).into(ImageView10);
                        container.setImageBitmap(bitmap);
                        bitmapContainer = bitmap;


                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //imageContainer.setVisibility(View.VISIBLE);
            //imageHinter.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            layoutPreview.setVisibility(View.GONE);
            //container.setImageBitmap(photo);
            Glide.with(getActivity()).asBitmap().load(photo).into(preiview);
            //Glide.with(getActivity()).asBitmap().load(photo).into(ImageView10);
            container.setImageBitmap(photo);
            bitmapContainer = photo;



        }

    }

    private boolean validateSpinner(String spinner) {
        if ((spinner == null || spinner.equals("") || spinner.isEmpty())) {
            Category.setError("Must Choose a Category !");
            return false;
        } else {
            Category.setError(null);
            return true;

        }
    }
    private boolean validateImage(Bitmap image)
    {
        if( image == null)
        {
            imageNote.setText(Html.fromHtml("<font color=red>"+imageNote.getText().toString()+"</font>"));
            return false;
        }else
        {
            imageNote.setText(Html.fromHtml("<font color=white>"+imageNote.getText().toString()+"</font>"));
            return true;

        }


    }


}
