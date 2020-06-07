package com.example.bioregproject.ui.Traceability.ImageFlow;

import androidx.activity.OnBackPressedCallback;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Products;
import com.example.bioregproject.ui.others.CamerQrPreviewViewModel;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QRInputImageFlow extends Fragment {

    private QrinputImageFlowViewModel mViewModel;
    private Button cancel,add,reset;
    private ConstraintLayout mother,msgHolder,loading,found,refV,catV,fabV,ExpV,creationv,Brandv,nameV,imageQr;
    private TextView nameT,BrandT,creationT,ExpT,fabT,catT,refT,exp,typeT,msg;
    private CardView die;
    private AnimatorSet mAnimationSet;
    private Button inddictor;
    private CameraSource cameraSource;
    private ImageView warn,preview;
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private static String value;
    private Bundle bundle;
    private long timeLeft;
    private static boolean checker = false;
    private LifecycleOwner lifecycleOwner;

    public static QRInputImageFlow newInstance() {
        return new QRInputImageFlow();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.qrinput_image_flow_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(QrinputImageFlowViewModel.class);
        lifecycleOwner = this;
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_QRInputImageFlow_to_imageFlowHome);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        checker = false;
        ExpT = view.findViewById(R.id.ExpT);
        warn= view.findViewById(R.id.warn);
        preview= view.findViewById(R.id.preview);
        imageQr = view.findViewById(R.id.imageQr);
        fabT = view.findViewById(R.id.fabT);
        catT = view.findViewById(R.id.catT);
        refT = view.findViewById(R.id.refT);
        exp = view.findViewById(R.id.exp);
        typeT = view.findViewById(R.id.typeT);
        msg = view.findViewById(R.id.msg);
        catV = view.findViewById(R.id.catV);
        refV = view.findViewById(R.id.refV);
        fabV = view.findViewById(R.id.fabV);
        ExpV = view.findViewById(R.id.ExpV);
        creationv = view.findViewById(R.id.creationv);
        Brandv = view.findViewById(R.id.Brandv);
        nameV = view.findViewById(R.id.nameV);
        nameT = view.findViewById(R.id.nameT);
        BrandT = view.findViewById(R.id.BrandT);
        creationT = view.findViewById(R.id.creationT);
        cancel = view.findViewById(R.id.cancel);
        add = view.findViewById(R.id.yes);
        reset = view.findViewById(R.id.reset);
        mother = view.findViewById(R.id.mother);
        StaticUse.backgroundAnimator(mother);
        msgHolder = view.findViewById(R.id.msgHolder);
        loading = view.findViewById(R.id.loading);
        found = view.findViewById(R.id.found);
        die = view.findViewById(R.id.relative_layout_id);
        inddictor = view.findViewById(R.id.indictor);
        //visibility
        found.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        msgHolder.setVisibility(View.VISIBLE);
        msg.setText("Waiting for Input ...");
        cancel.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        reset.setVisibility(View.GONE);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.QRInputImageFlow);
            }
        });
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(die, "alpha", .5f, .1f);
        fadeOut.setDuration(300);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(die, "alpha", .1f, .5f);
        fadeIn.setDuration(300);
        mAnimationSet = new AnimatorSet();
        mAnimationSet.play(fadeIn).after(fadeOut);
        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimationSet.start();
            }
        });
        mAnimationSet.start();
        surfaceView = view.findViewById(R.id.surfaceView2);
        barcodeDetector = new BarcodeDetector.Builder(getActivity()).setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(getActivity(),barcodeDetector).setRequestedPreviewSize(640,400).build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCode= detections.getDetectedItems();
                if(qrCode.size()!=0)
                {
                    inddictor.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator =(Vibrator)getActivity().getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(500);
                            inddictor.setText("Processing Data");
                            msg.setText("Fetching ...");
                            value = qrCode.valueAt(0).displayValue;
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    imageQr.setVisibility(View.GONE);
                                    loading.setVisibility(View.VISIBLE);
                                    msgHolder.setVisibility(View.GONE);
                                    reset.setVisibility(View.VISIBLE);
                                    final Handler handler1 = new Handler();
                                    handler1.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(value.contains(",") && value.contains("BioReg")) {
                                                String[] items = value.split(",");
                                                if(!StaticUse.validNumber(items[0]))
                                                {
                                                    loading.setVisibility(View.GONE);
                                                    msgHolder.setVisibility(View.VISIBLE);
                                                    msg.setText("Corrupted Data");
                                                    return;
                                                }else
                                                {
                                                    mViewModel.getProductByNameAndBrandAndId(items[1],items[2],Long.parseLong(items[0])).observe(lifecycleOwner, new Observer<List<Products>>() {
                                                        @Override
                                                        public void onChanged(List<Products> products) {
                                                            if(products.isEmpty())
                                                            {
                                                                checker = true;
                                                            }else {

                                                                if (items.length == 6 && items[4].equals("ImageT")) {
                                                                    refV.setVisibility(View.GONE);
                                                                    ExpV.setVisibility(View.GONE);
                                                                    fabV.setVisibility(View.GONE);
                                                                    exp.setVisibility(View.GONE);
                                                                    warn.setVisibility(View.GONE);
                                                                    catV.setVisibility(View.GONE);
                                                                    Brandv.setVisibility(View.VISIBLE);
                                                                    nameV.setVisibility(View.VISIBLE);
                                                                    typeT.setVisibility(View.VISIBLE);
                                                                    creationv.setVisibility(View.VISIBLE);
                                                                    creationT.setText("Created on : "+products.get(0).getCreationDate());
                                                                    typeT.setText("Type : Visual Traceability");
                                                                    BrandT.setText("Brand :   "+products.get(0).getBrandName());
                                                                    nameT.setText("Title    : "+products.get(0).getName());
                                                                    RequestOptions options = new RequestOptions()
                                                                            .centerCrop()
                                                                            .placeholder(R.drawable.progress_animation)
                                                                            .error(R.drawable.ic_warning_black_24dp)
                                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                                            .priority(Priority.HIGH)
                                                                            .dontAnimate()
                                                                            .dontTransform();
                                                                    Glide.with(getActivity()).load(products.get(0).getImage()).apply(options).into(preview);
                                                                    msgHolder.setVisibility(View.GONE);
                                                                    found.setVisibility(View.VISIBLE);


                                                                }
                                                                else if(items.length > 6)
                                                                {
                                                                    refV.setVisibility(View.VISIBLE);
                                                                    ExpV.setVisibility(View.VISIBLE);
                                                                    fabV.setVisibility(View.VISIBLE);
                                                                    exp.setVisibility(View.VISIBLE);
                                                                    Date now = new Date();
                                                                    if(products.get(0).getExpirationDate().compareTo(now) <= 0)
                                                                    {
                                                                        warn.setVisibility(View.VISIBLE);
                                                                        exp.setText("Expired");

                                                                    }else {
                                                                        timeLeft = products.get(0).getExpirationDate().getTime() - new Date().getTime();
                                                                        if (timeLeft > 0) {
                                                                            StartTimer(exp);
                                                                            exp.setTextColor(getActivity().getResources().getColor(R.color.White));
                                                                        } else {
                                                                            exp.setText("Expired");
                                                                            warn.setVisibility(View.VISIBLE);
                                                                            exp.setTextColor(getActivity().getResources().getColor(R.color.redErrorDeep));
                                                                        }
                                                                    }
                                                                    catV.setVisibility(View.VISIBLE);
                                                                    Brandv.setVisibility(View.VISIBLE);
                                                                    nameV.setVisibility(View.VISIBLE);
                                                                    typeT.setVisibility(View.VISIBLE);
                                                                    creationv.setVisibility(View.VISIBLE);
                                                                    refT.setText("Reference : "+products.get(0).getRefrence());
                                                                    ExpT.setText("Expiration Date : "+new SimpleDateFormat("dd/MM/yyyy HH:mm").format(products.get(0).getExpirationDate()));
                                                                    fabT.setText("Fabrication Date :   "+new SimpleDateFormat("dd/MM/yyyy HH:mm").format(products.get(0).getFabricationDate()));
                                                                    catT.setText("Category : "+products.get(0).getCategoryName());
                                                                    creationT.setText("Created on : "+new SimpleDateFormat("dd/MM/yyyy HH:mm").format(products.get(0).getCreationDate()));
                                                                    typeT.setText("Type : Manual Traceability");
                                                                    BrandT.setText("Brand :   "+products.get(0).getBrandName());
                                                                    nameT.setText("Title    : "+products.get(0).getName());
                                                                    RequestOptions options = new RequestOptions()
                                                                            .centerCrop()
                                                                            .placeholder(R.drawable.progress_animation)
                                                                            .error(R.drawable.ic_warning_black_24dp)
                                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                                            .priority(Priority.HIGH)
                                                                            .dontAnimate()
                                                                            .dontTransform();
                                                                    Glide.with(getActivity()).load(products.get(0).getImage()).apply(options).into(preview);
                                                                    msgHolder.setVisibility(View.GONE);
                                                                    found.setVisibility(View.VISIBLE);
                                                                }
                                                            }
                                                            mViewModel.getProductByNameAndBrandAndType(items[1],items[2],items[5]).removeObservers(lifecycleOwner);
                                                            loading.setVisibility(View.GONE);
                                                        }
                                                    });


                                                }
                                                if(checker)
                                                {
                                                    mViewModel.getProductByNameAndBrandAndType(items[1],items[2],items[5]).observe(lifecycleOwner, new Observer<List<Products>>() {
                                                        @Override
                                                        public void onChanged(List<Products> products) {
                                                            if(products.isEmpty())
                                                            {
                                                                msgHolder.setVisibility(View.VISIBLE);
                                                                msg.setText("No DATA found on this Device would you like to add a new Traced Product based on this information ?");
                                                                add.setVisibility(View.VISIBLE);
                                                                cancel.setVisibility(View.VISIBLE);
                                                                bundle = new Bundle();
                                                                bundle.putString("created",items[3]);
                                                                bundle.putString("name",items[1]);
                                                                bundle.putString("brand",items[2]);
                                                                bundle.putString("Type",items[4]);
                                                                bundle.putString("id",""+items[0]);
                                                                if (items.length > 6) {
                                                                    bundle.putString("expirationString", items[8]);
                                                                    bundle.putString("ref", items[5]);
                                                                    bundle.putString("fabricationString", items[7]);
                                                                    bundle.putString("CategoryName",items[6]);
                                                                    bundle.putInt("dest",15 );
                                                                }else
                                                                {
                                                                    bundle.putInt("dest", 4);
                                                                }

                                                                checker = false;
                                                            }else {

                                                                if (items.length == 6 && items[4].equals("ImageT")) {
                                                                    refV.setVisibility(View.GONE);
                                                                    ExpV.setVisibility(View.GONE);
                                                                    fabV.setVisibility(View.GONE);
                                                                    exp.setVisibility(View.GONE);
                                                                    warn.setVisibility(View.GONE);
                                                                    catV.setVisibility(View.GONE);
                                                                    Brandv.setVisibility(View.VISIBLE);
                                                                    nameV.setVisibility(View.VISIBLE);
                                                                    typeT.setVisibility(View.VISIBLE);
                                                                    creationv.setVisibility(View.VISIBLE);
                                                                    creationT.setText("Created on : "+products.get(0).getCreationDate());
                                                                    typeT.setText("Type : Visual Traceability");
                                                                    BrandT.setText("Brand :   "+products.get(0).getBrandName());
                                                                    nameT.setText("Title    : "+products.get(0).getName());
                                                                    RequestOptions options = new RequestOptions()
                                                                            .centerCrop()
                                                                            .placeholder(R.drawable.progress_animation)
                                                                            .error(R.drawable.ic_warning_black_24dp)
                                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                                            .priority(Priority.HIGH)
                                                                            .dontAnimate()
                                                                            .dontTransform();
                                                                    Glide.with(getActivity()).load(products.get(0).getImage()).apply(options).into(preview);
                                                                    msgHolder.setVisibility(View.GONE);
                                                                    found.setVisibility(View.VISIBLE);


                                                                }
                                                                else if(items.length > 6)
                                                                {
                                                                    refV.setVisibility(View.VISIBLE);
                                                                    ExpV.setVisibility(View.VISIBLE);
                                                                    fabV.setVisibility(View.VISIBLE);
                                                                    exp.setVisibility(View.VISIBLE);
                                                                    Date now = new Date();
                                                                    if(products.get(0).getExpirationDate().compareTo(now) <= 0)
                                                                    {
                                                                        warn.setVisibility(View.VISIBLE);
                                                                        exp.setText("Expired");

                                                                    }else {
                                                                        timeLeft = products.get(0).getExpirationDate().getTime() - new Date().getTime();
                                                                        warn.setVisibility(View.GONE);
                                                                        if (timeLeft > 0) {
                                                                            StartTimer(exp);
                                                                            exp.setTextColor(getActivity().getResources().getColor(R.color.White));
                                                                        } else {
                                                                            exp.setText("Expired");
                                                                            warn.setVisibility(View.VISIBLE);
                                                                            exp.setTextColor(getActivity().getResources().getColor(R.color.redErrorDeep));
                                                                        }
                                                                    }
                                                                    catV.setVisibility(View.VISIBLE);
                                                                    Brandv.setVisibility(View.VISIBLE);
                                                                    nameV.setVisibility(View.VISIBLE);
                                                                    typeT.setVisibility(View.VISIBLE);
                                                                    creationv.setVisibility(View.VISIBLE);
                                                                    refT.setText("Reference : "+products.get(0).getRefrence());
                                                                    ExpT.setText("Expiration Date : "+new SimpleDateFormat("dd/MM/yyyy HH:mm").format(products.get(0).getExpirationDate()));
                                                                    fabT.setText("Fabrication Date :   "+new SimpleDateFormat("dd/MM/yyyy HH:mm").format(products.get(0).getFabricationDate()));
                                                                    catT.setText("Category : "+products.get(0).getCategoryName());
                                                                    creationT.setText("Created on : "+new SimpleDateFormat("dd/MM/yyyy HH:mm").format(products.get(0).getCreationDate()));
                                                                    typeT.setText("Type : Manual Traceability");
                                                                    BrandT.setText("Brand :   "+products.get(0).getBrandName());
                                                                    nameT.setText("Title    : "+products.get(0).getName());
                                                                    RequestOptions options = new RequestOptions()
                                                                            .centerCrop()
                                                                            .placeholder(R.drawable.progress_animation)
                                                                            .error(R.drawable.ic_warning_black_24dp)
                                                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                                            .priority(Priority.HIGH)
                                                                            .dontAnimate()
                                                                            .dontTransform();
                                                                    Glide.with(getActivity()).load(products.get(0).getImage()).apply(options).into(preview);
                                                                    loading.setVisibility(View.GONE);
                                                                    msgHolder.setVisibility(View.GONE);
                                                                    found.setVisibility(View.VISIBLE);
                                                                }
                                                            }
                                                            mViewModel.getProductByNameAndBrandAndId(items[1],items[2],Long.parseLong(items[0])).removeObservers(lifecycleOwner);
                                                            loading.setVisibility(View.GONE);
                                                        }
                                                    });
                                                }
                                            }else
                                            {
                                                loading.setVisibility(View.GONE);
                                                msgHolder.setVisibility(View.VISIBLE);
                                                msg.setText("Mismatching Data this feature is only available for the traced products,please ensure that the QRcode is generate through the traceability module");
                                            }

                                            handler1.removeCallbacksAndMessages(null);
                                        }

                                    }, 1000);
                                    handler.removeCallbacksAndMessages(null);
                                }
                            }, 1500);


                        }
                    });
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle==null)
                {
                    Toast.makeText(getActivity(), "Something went Wrong please try again", Toast.LENGTH_SHORT).show();
                    return;
                }else
                {
                   if(bundle.getInt("dest")==4)
                   {
                       cameraSource.stop();
                       Navigation.findNavController(view).navigate(R.id.action_QRInputImageFlow_to_imageFlowAddImage,bundle);

                   }else
                   {

                       Navigation.findNavController(view).navigate(R.id.action_QRInputImageFlow_to_formManual2,bundle);
                   }

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset.performClick();
            }
        });




    }

    @Override
    public void onResume() {
        super.onResume();
        surfaceView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        cameraSource.stop();
        surfaceView.setVisibility(View.GONE);
    }

    public void StartTimer(TextView textView)
    {
        CountDownTimer countDownTimer =new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft=millisUntilFinished;
                UpateTimer(textView);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
    private void UpateTimer(TextView textView)
    {
        int hours = (int) (timeLeft / 1000) / 3600;
        int minutes = (int) ((timeLeft / 1000) % 3600) / 60;
        int seconds = (int) (timeLeft / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        textView.setText("Time left before Expiration: "+timeLeftFormatted);
    }


}
