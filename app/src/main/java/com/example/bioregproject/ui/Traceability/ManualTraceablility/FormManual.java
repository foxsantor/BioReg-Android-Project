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
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
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
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bioregproject.Adapters.SpinnerCatAdapter;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.MainActivityViewModel;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.Category;
import com.example.bioregproject.entities.Notification;
import com.example.bioregproject.entities.Products;
import com.example.bioregproject.ui.History.DeviceHistoryViewModel;
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
    private MainActivityViewModel mainActivityViewModel;
    private DeviceHistoryViewModel deviceHistoryViewModel;
    private Button back,save;
    private TextView imageNote,textView20;
    private ImageButton addCat,addImage,addImage2,cancel,calender1,calender2;
    private ImageView preiview,container;
    private TextInputLayout dateTimeExipartion,fref,fabrication,Category,brandName,fname;
    private ConstraintLayout constraintLayout,layoutPreview;
    private Spinner spinner;
    private static final int GALLERY_REQUEST = 1;
    private static final int  MY_CAMERA_PERMISSION_CODE = 2;
    private static final int CAMERA_REQUEST=3;
    private Bitmap bitmapContainer;
    private String category,expirationString,fabricationString,name,brand,ref,creationString;
    private static  int CODE_ADD = 0;
    private static  int CODE_UPDATE = 1;
    private static  int CURRENT_RUNNING_CODE = 2;
    private Bundle bundle ;
    boolean fired = false;
    private long id;
    private byte[] image;

    public static FormManual newInstance() {
        return new FormManual();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form_manual_fragment, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
         bundle = getArguments();

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FormManualViewModel.class);
        mainActivityViewModel  = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        deviceHistoryViewModel = ViewModelProviders.of(this).get(DeviceHistoryViewModel.class);
        cancel = view.findViewById(R.id.cancel);
        back = view.findViewById(R.id.Back);
        save = view.findViewById(R.id.Save);
        container = view.findViewById(R.id.container);
        addCat = view.findViewById(R.id.addCat);
        calender1 = view.findViewById(R.id.calender1);
        calender2 = view.findViewById(R.id.calender2);
        addImage = view.findViewById(R.id.addImage);
        addImage2 = view.findViewById(R.id.addImage2);
        preiview = view.findViewById(R.id.preiview);
        fref = view.findViewById(R.id.fref);

        imageNote= view.findViewById(R.id.imagNote);
        fabrication = view.findViewById(R.id.fabrication);
        Category = view.findViewById(R.id.Category);
        brandName = view.findViewById(R.id.brandName);
        textView20 = view.findViewById(R.id.textView20);
        fname = view.findViewById(R.id.fname);
        layoutPreview = view.findViewById(R.id.layoutingPreview);
        spinner = view.findViewById(R.id.spinner);
        cancel.setVisibility(View.GONE);
        SpinnerLoader(spinner);
        constraintLayout = view.findViewById(R.id.constraintlayout);
        StaticUse.backgroundAnimator(constraintLayout);
        dateTimeExipartion = view.findViewById(R.id.expiration);
        if(bundle !=null)
        {
            if(bundle.getInt("dest")>0)
            {
                expirationString= bundle.getString("expirationString");
                fabricationString=bundle.getString("fabricationString");
                name=bundle.getString("name");
                ref=bundle.getString("ref");
                brand=bundle.getString("brand");
                category=bundle.getString("CategoryName");
                CURRENT_RUNNING_CODE = CODE_ADD;
                fabrication.getEditText().setText(fabricationString);
                dateTimeExipartion.getEditText().setText(expirationString);
                calender2.setEnabled(true);
                calender2.setClickable(true);
                calender2.setImageResource(R.drawable.ic_date_range_blue_24dp);
                fref.getEditText().setText(ref);
                fname.getEditText().setText(name);
                brandName.getEditText().setText(brand);

            }else {
                id = bundle.getLong("id");
                image = bundle.getByteArray("image");
                expirationString = bundle.getString("expirationString");
                fabricationString = bundle.getString("fabricationString");
                creationString = bundle.getString("creationString");
                name = bundle.getString("name");
                ref = bundle.getString("ref");
                brand = bundle.getString("brand");
                category = bundle.getString("CategoryName");
                CURRENT_RUNNING_CODE = CODE_UPDATE;
            }
        }else
        {
            CURRENT_RUNNING_CODE = CODE_ADD;
        }
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(bundle == null || bundle.getInt("dest",0)==0)
                {
                        Navigation.findNavController(view).navigate(R.id.action_formManual_to_manualHome);
                }
                else
                {
                    Navigation.findNavController(view).navigate(R.id.action_formManual2_to_imageFlowHome);
                }

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        dateTimeExipartion.getEditText().setEnabled(false);
        fabrication.getEditText().setEnabled(false);
        calender2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(dateTimeExipartion.getEditText());
            }
        });
        calender2.setEnabled(false);
        calender2.setClickable(false);
        calender2.setImageResource(R.drawable.ic_date_range_gray_35dp);

        calender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(fabrication.getEditText());
            }
        });
        if(CURRENT_RUNNING_CODE == 1 )
        {
            save.setText("Update");
            textView20.setText("Update a product");
            fabrication.getEditText().setText(fabricationString);
            dateTimeExipartion.getEditText().setText(expirationString);
            calender2.setEnabled(true);
            calender2.setClickable(true);
            calender2.setImageResource(R.drawable.ic_date_range_blue_24dp);
            fref.getEditText().setText(ref);
            fname.getEditText().setText(name);
            brandName.getEditText().setText(brand);
           // Toast.makeText(getActivity(), ""+category, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), ""+getIndex(spinner,category), Toast.LENGTH_SHORT).show();
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_warning_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();
            Glide.with(getActivity()).asBitmap().load(image).apply(options).into(preiview);
            //Glide.with(getActivity()).asBitmap().load(image).into(container);
            layoutPreview.setVisibility(View.GONE);
            cancel.setVisibility(View.VISIBLE);
            bitmapContainer = BitmapFactory.decodeByteArray(image, 0, image.length);
            container.setImageBitmap(bitmapContainer);

        }

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
                    calender2.setEnabled(true);
                    calender2.setClickable(true);
                    calender2.setImageResource(R.drawable.ic_date_range_blue_24dp);
                }else
                {
                    calender2.setEnabled(false);
                    calender2.setClickable(false);
                    calender2.setImageResource(R.drawable.ic_date_range_gray_35dp);
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
                if(bundle == null || bundle.getInt("dest",0)==0)
                {
                    Navigation.findNavController(view).navigate(R.id.action_formManual_to_manualHome);
                }
                else
                {
                    Navigation.findNavController(view).navigate(R.id.imageFlowHome);
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!StaticUse.validateEmpty(fname,"Name") | !StaticUse.validateEmpty(brandName,"Brand Name")
                        | !StaticUse.validateEmpty(dateTimeExipartion,"Expiration Date") |!StaticUse.validateEmpty(fabrication,"Fabrication Date")
                        |!StaticUse.validateEmpty(fref,"Reference")   | !validateSpinner(category) | !validateImage(bitmapContainer) |!validateFabrication(fabrication,dateTimeExipartion)
                ){return;}
                else {
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
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
                    product.setBrandName(brandName.getEditText().getText().toString());
                    product.setCategoryName(category);
                    product.setRefrence(fref.getEditText().getText().toString());
                    if(CURRENT_RUNNING_CODE == 0){
                    product.setCreationDate(new Date());
                    mViewModel.insert(product);
                    Toast.makeText(getActivity(), "Product Added Successfully", Toast.LENGTH_SHORT).show();

                        StaticUse.SaveNotification(getActivity(),mainActivityViewModel,getActivity(),"Traceability Module"
                                ,"has added a new Traced Product by the name of "+fname.getEditText().getText().toString()+" from "
                                ,"Manuel Traceability",null,container,R.drawable.ic_add_circle_blue_24dp);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                StaticUse.SaveHistory(getActivity(),deviceHistoryViewModel,getActivity(),"Traceability Module",
                                        "has added a new Traced Product by the name of ",
                                        fname.getEditText().getText().toString(),0,"Manuel Traceability");
                                handler.removeCallbacksAndMessages(null);
                            }
                        }, 500);



//                         mainActivityViewModel.getAccount(StaticUse.loadSession(getContext()).getId()).observe(getActivity(), new Observer<List<Account>>() {
//                             @Override
//                             public void onChanged(List<Account> accounts) {
//                                 final Account user = accounts.get(0);
//                                 Notification notification = new Notification();
//                                 notification.setCreation(new Date());
//                                 notification.setOwner(user.getFirstName());
//                                 notification.setCategoryName("Traceability Module");
//                                 notification.setSeen(false);
//                                 notification.setName("Manuel Traceability");
//                                 notification.setDescription("has added a new Traced Product by the name of "+fname.getEditText().getText().toString()+" from ");
//                                 notification.setObjectImageBase64(StaticUse.transformerImageBase64(container));
//                                 notification.setImageBase64(StaticUse.transformerImageBase64frombytes(user.getProfileImage()));
//                                 MainActivity.insertNotification(notification);
//                                 StaticUse.createNotificationChannel(notification,getActivity());
//                                 StaticUse.displayNotification(getActivity(),R.drawable.ic_add_circle_blue_24dp,notification);
//                             }
//                         });

                    }
                    else
                    {
                        product.setId(id);
                        try {
                            product.setCreationDate(simpleDateFormat.parse(creationString));
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        mViewModel.update(product);


                        StaticUse.SaveNotification(getActivity(),mainActivityViewModel,getActivity(),"Traceability Module"
                                ,"has updated a Traced Product by the name of "+fname.getEditText().getText().toString()+" from "
                                ,"Manuel Traceability",null,container,R.drawable.ic_edit_blue_24dp);


                                StaticUse.SaveHistory(getActivity(),deviceHistoryViewModel,getActivity(),"Traceability Module",
                                        "has updated a Traced Product by the name of ",
                                        fname.getEditText().getText().toString(),product.getId(),"Manuel Traceability");

                        Toast.makeText(getActivity(), "Product Updated Successfully", Toast.LENGTH_SHORT).show();
//                        mainActivityViewModel.getAccount(StaticUse.loadSession(getContext()).getId()).observe(getActivity(), new Observer<List<Account>>() {
//                            @Override
//                            public void onChanged(List<Account> accounts) {
//                                final Account user = accounts.get(0);
//                                Notification notification = new Notification();
//                                notification.setCreation(new Date());
//                                notification.setOwner(user.getFirstName());
//                                notification.setCategoryName("Traceability Module");
//                                notification.setSeen(false);
//                                notification.setName("Manuel Traceability");
//                                notification.setDescription("has updated a Traced Product by the name of "+fname.getEditText().getText().toString()+" from ");
//                                notification.setObjectImageBase64(StaticUse.transformerImageBase64(container));
//                                notification.setImageBase64(StaticUse.transformerImageBase64frombytes(user.getProfileImage()));
//                                MainActivity.insertNotification(notification);
//                                StaticUse.createNotificationChannel(notification,getActivity());
//                                StaticUse.displayNotification(getActivity(),R.drawable.ic_edit_blue_24dp,notification);
//                            }
//                        });

                    }

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

                        Log.i("PEW PEW", "Double fire check");

                            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                            calendar.set(Calendar.MINUTE,minute);
                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                            fired = true;
                    }
                };

                new TimePickerDialog(getActivity(),timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(getActivity(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    private void SpinnerLoader(final Spinner spinner)
    {
        final ArrayList<String> spinnerArray =  new ArrayList<>();
        spinnerArray.add("Choose a Category *");
                    spinnerArray.add("Raw materials");
                    spinnerArray.add("Food");
                    spinnerArray.add("Beverages");
                    spinnerArray.add("Tools");
                    spinnerArray.add("Herbs");
                    spinnerArray.add("Industrial Items");
        SpinnerCatAdapter adapter = new SpinnerCatAdapter(getActivity(),spinnerArray);
        spinner.setAdapter(adapter);
        if(CURRENT_RUNNING_CODE == 1 )
        {
            spinner.setSelection(getIndex(spinner,category));
        }
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
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.ic_warning_black_24dp)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();
                        Glide.with(getActivity()).asBitmap().load(bitmap).apply(options).into(preiview);
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
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_warning_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();
            Glide.with(getActivity()).asBitmap().load(photo).apply(options).into(preiview);
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


    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    private boolean validateFabrication(TextInputLayout fabrication,TextInputLayout expiration)
    {
        Date expirationD,fabricationD;
        if(StaticUse.validateEmpty(fabrication,"Fabrication Date") && StaticUse.validateEmpty(expiration,"Expiration Date"))
        {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            expirationD= simpleDateFormat.parse(fabrication.getEditText().getText().toString());
            fabricationD= simpleDateFormat.parse(expiration.getEditText().getText().toString());
            if( fabricationD.compareTo(expirationD) <= 0)
            {
                fabrication.setError("Invalid Fabrication Date");
                return false;
            }else
            {
                fabrication.setError(null);
                return true;

            }
        }catch (Exception e)
        {
            e.printStackTrace();
            fabrication.setError("Something Went Wrong While Processing the dates please Submit a bug");
            return  false;
        }}else
        {
            return false;
        }


    }


}
