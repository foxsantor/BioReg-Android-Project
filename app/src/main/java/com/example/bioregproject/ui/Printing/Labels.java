package com.example.bioregproject.ui.Printing;

import androidx.activity.OnBackPressedCallback;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.epson.epos2.Epos2Exception;
import com.epson.epos2.Log;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.ui.Printing.PrintingOverView.PrinterDiscovery;
import com.example.bioregproject.ui.Printing.PrintingOverView.ShowMsg;
import com.example.bioregproject.ui.Printing.PrintingOverView.SpnModelsItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.oned.EAN13Writer;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class Labels extends Fragment implements ReceiveListener {


    private Button next,back,clear,clearCode,span,title,submit,generate,line,gen2,back2,prints;
    private TextInputLayout content,code,barscode;
    private TextView mainContentPreview,discoverTextx3;
    private LabelsViewModel mViewModel;
    private ConstraintLayout mother;
    private ImageView barCode,QrCode,barCodeH,qrCodeH;
    private CheckBox barCodeCheck,qrCodeCheck;
    private static Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private CardView firstCard,secondCard;
    private EditText warn;
    private Spinner spinner,spinner2;
    private FloatingActionButton discovery;
    private static  String printerContent,barCodeInfo,targets;
    public static Printer  mPrinter = null;
    private Context mContext = null;

    private static final Pattern PASSWORD_CHECHER =  Pattern.compile("^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            //"(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            //"(?=\\S+$)" +           //no white spaces
            ".{12,}" +               //at least 8 characters
            "$");
    private static final int REQUEST_PERMISSION = 100;

    public static Labels newInstance() {
        return new Labels();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.labels_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();

        mViewModel = ViewModelProviders.of(this).get(LabelsViewModel.class);
        mother = view.findViewById(R.id.mother);
        StaticUse.backgroundAnimator(mother);
        firstCard = view.findViewById(R.id.firstCard);
        secondCard = view.findViewById(R.id.secondCard);
        secondCard.setVisibility(View.GONE);
        firstCard.setVisibility(View.VISIBLE);
        back2 = view.findViewById(R.id.back2);
        prints = view.findViewById(R.id.prints);
        next = view.findViewById(R.id.next);
        back = view.findViewById(R.id.back);
        qrCodeH= view.findViewById(R.id.qrcodeh);
        clear = view.findViewById(R.id.clear);
        clearCode = view.findViewById(R.id.clear2);
        span = view.findViewById(R.id.span);
        title = view.findViewById(R.id.title);
        submit = view.findViewById(R.id.button19);
        generate = view.findViewById(R.id.gen);
        line = view.findViewById(R.id.button21);
        content = view.findViewById(R.id.content);
        code = view.findViewById(R.id.code);
        mainContentPreview = view.findViewById(R.id.contentP);
        barCode = view.findViewById(R.id.abrcodeP);
        QrCode = view.findViewById(R.id.qrcodeP);
        barCodeCheck = view.findViewById(R.id.barcode);
        qrCodeCheck = view.findViewById(R.id.qrcode);
        gen2 =view.findViewById(R.id.gen2);
        barscode =view.findViewById(R.id.barscode);
        discoverTextx3 = view.findViewById(R.id.discoverTextx3);
        discovery = view.findViewById(R.id.discovery2);
        spinner = view.findViewById(R.id.series3);
        spinner2 = view.findViewById(R.id.lang3);
        warn = view.findViewById(R.id.warn3);

        warn.setEnabled(false);
        warn.setClickable(false);
        barscode.setClickable(false);
        barscode.setEnabled(false);
        gen2.setClickable(false);
        gen2.setEnabled(false);
        code.setClickable(false);
        code.setEnabled(false);
        generate.setClickable(false);
        generate.setEnabled(false);
        clearCode.setClickable(false);
        clearCode.setEnabled(false);

        qrCodeCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    code.setClickable(true);
                    code.setEnabled(true);
                    generate.setClickable(true);
                    generate.setEnabled(true);
                    clearCode.setClickable(true);
                    clearCode.setEnabled(true);
                }else
                {
                    code.setClickable(false);
                    code.setEnabled(false);
                    generate.setClickable(false);
                    generate.setEnabled(false);
                    clearCode.setClickable(false);
                    clearCode.setEnabled(false);
                    code.getEditText().setText("");

                    Glide.with(getActivity()).clear(QrCode);
                    Glide.with(getActivity()).clear(qrCodeH);
                }

            }
        });

        barCodeCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    barscode.setClickable(true);
                    barscode.setEnabled(true);
                    gen2.setClickable(true);
                    gen2.setEnabled(true);
                }else
                {
                    barscode.setClickable(false);
                    barscode.setEnabled(false);
                    gen2.setClickable(false);
                    gen2.setEnabled(false);
                    barscode.getEditText().setText("");
                   Glide.with(getActivity()).clear(barCode);
                   barCodeInfo="";
                }

            }
        });

        gen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !StaticUse.validCellPhone("BarCode",barscode)| !BarCodeChecker(barscode,"BarCode") )
                {
                    return;
                }else
                {
                    generateBarCode(barCode);
                }

            }
        });


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.mainMenu);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.mainMenu);
            }
        });

        span.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String span ="------------------------------";
                String current_comment = content.getEditText().getText().toString();
                current_comment = "<br>"+span+ "<br>"+ current_comment ;
                content.getEditText().append("<br>"+span+ "<br>");

            }
        });
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_comment = content.getEditText().getText().toString();
                current_comment = current_comment+"<br>";
                content.getEditText().append("<br>");
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_comment = content.getEditText().getText().toString();
                //current_comment = "<h3>"+current_comment+"</h3>";
                content.getEditText().getText().insert(current_comment.length(),"<h3>"+"</h3>");
                content.getEditText().setSelection(content.getEditText().getText().length()-5);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.getEditText().setText("");
            }
        });
        clearCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code.getEditText().setText("");
            }
        });
        content.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mainContentPreview.setText(Html.fromHtml(s.toString()));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content.getEditText().getText().toString().isEmpty())
                {
                    content.setError("Nothing to submit");
                    return;
                }else
                {
                    content.setError(null);
                    printerContent = stringPrcessor(content.getEditText().getText().toString());
                }
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code.setError(null);
                if(code.getEditText().getText().toString().isEmpty())
                {
                    code.setError("no data to process ");
                    return;
                }else
                {
                            generateQrCode(QrCode,qrCodeH);

                }
            }
        });

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.slide_left);
                secondCard.startAnimation(animFadeIn);
                secondCard.setVisibility(View.GONE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animation animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),R.anim.slide_right);
                        firstCard.startAnimation(animFadeIn);
                        firstCard.setVisibility(View.VISIBLE);
                    }
                }, 600);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(printerContent==null || printerContent.equals("") || printerContent.isEmpty())
                {
                    Toast.makeText(mContext, "Please submit content to print", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    requestRuntimePermission();
                    Animation animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_left);
                    firstCard.startAnimation(animFadeIn);
                    firstCard.setVisibility(View.GONE);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Animation animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_right);
                            secondCard.startAnimation(animFadeIn);
                            secondCard.setVisibility(View.VISIBLE);
                        }
                    }, 600);
                }  }
        });

    //SecondHalf

        prints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonState(false);
                if (!runPrintCouponSequence()) {
                    updateButtonState(true);
                }
            }
        });

        ArrayAdapter<SpnModelsItem> seriesAdapter = new ArrayAdapter<SpnModelsItem>(getActivity(), android.R.layout.simple_spinner_item);
        seriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_m10), Printer.TM_M10));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_m30), Printer.TM_M30));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_p20), Printer.TM_P20));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_p60), Printer.TM_P60));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_p60ii), Printer.TM_P60II));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_p80), Printer.TM_P80));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t20), Printer.TM_T20));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t60), Printer.TM_T60));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t70), Printer.TM_T70));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t81), Printer.TM_T81));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t82), Printer.TM_T82));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t83), Printer.TM_T83));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t83iii), Printer.TM_T83III));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t88), Printer.TM_T88));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t90), Printer.TM_T90));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t90kp), Printer.TM_T90KP));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_t100), Printer.TM_T100));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_u220), Printer.TM_U220));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_u330), Printer.TM_U330));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_l90), Printer.TM_L90));
        seriesAdapter.add(new SpnModelsItem(getString(R.string.printerseries_h6000), Printer.TM_H6000));
        spinner.setAdapter(seriesAdapter);
        spinner.setSelection(0);

        ArrayAdapter<SpnModelsItem> langAdapter = new ArrayAdapter<SpnModelsItem>(getActivity(), android.R.layout.simple_spinner_item);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langAdapter.add(new SpnModelsItem("ENGLISH", Printer.MODEL_ANK));
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_japanese), Printer.MODEL_JAPANESE));
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_chinese), Printer.MODEL_CHINESE));
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_taiwan), Printer.MODEL_TAIWAN));
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_korean), Printer.MODEL_KOREAN));
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_thai), Printer.MODEL_THAI));
        langAdapter.add(new SpnModelsItem(getString(R.string.lang_southasia), Printer.MODEL_SOUTHASIA));
        spinner2.setAdapter(langAdapter);
        spinner2.setSelection(0);

        try {
            Log.setLogSettings(getActivity(), Log.PERIOD_TEMPORARY, Log.OUTPUT_STORAGE, null, 0, 1, Log.LOGLEVEL_LOW);
        }
        catch (Exception e) {
            ShowMsg.showException(e, "setLogSettings", getActivity());
        }

        discovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PrinterDiscovery.class);
                startActivityForResult(intent, 0);
            }
        });





    }

    private void generateQrCode(ImageView preview,ImageView realDeal)
    {
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 1;
        qrgEncoder = new QRGEncoder(code.getEditText().getText().toString(), null, QRGContents.Type.TEXT, smallerDimension);
        try {
            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.getBitmap();
            // Setting Bitmap to ImageView
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_warning_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();
            Glide.with(getActivity()).asBitmap().load(bitmap).apply(options).into(preview);
            Glide.with(getActivity()).asBitmap().load(bitmap).into(realDeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void generateBarCode(ImageView preview)
    {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(barscode.getEditText().getText().toString(), BarcodeFormat.EAN_13, 500, 100);
            barCodeInfo=barscode.getEditText().getText().toString();
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_warning_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();
            Glide.with(getActivity()).asBitmap().load(bitmap).apply(options).into(preview);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    private String stringPrcessor(String string)
    {
        string = string.replace("<br>","\n");

        return string;
    }

    private boolean BarCodeChecker (TextInputLayout text , String type)
    {
        if(text.getError()!= null)
        {text.setError(null);}
        String textToCheck = text.getEditText().getText().toString().trim();
        if(textToCheck.isEmpty()){
            text.setError(type +" can't be empty");

            return false;}

        else if (!PASSWORD_CHECHER.matcher(textToCheck).matches()){
            text.setError(Html.fromHtml("*BarCode must be 12 digits"));
            return false;
        }else
        {
            text.setError(null);
            return true;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            String target = data.getStringExtra(getString(R.string.title_target));

            if (target != null) {
                discoverTextx3.setText(target);
                targets=target;
            }
        }}



    private void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        int permissionStorage = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> requestPermissions = new ArrayList<>();

        if (permissionStorage == PackageManager.PERMISSION_DENIED) {
            requestPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionLocation == PackageManager.PERMISSION_DENIED) {
            requestPermissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!requestPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), requestPermissions.toArray(new String[requestPermissions.size()]), REQUEST_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode != REQUEST_PERMISSION || grantResults.length == 0) {
            return;
        }

        List<String> requestPermissions = new ArrayList<>();

        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                requestPermissions.add(permissions[i]);
            }
            if (permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                requestPermissions.add(permissions[i]);
            }
        }

        if (!requestPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), requestPermissions.toArray(new String[requestPermissions.size()]), REQUEST_PERMISSION);
        }
    }


    private boolean runPrintCouponSequence() {

        if (!initializeObject()) {
            return false;
        }

        if (!createCouponDataPerso(qrCodeH,printerContent,barCodeInfo)) {
            finalizeObject();
            return false;
        }

        if (!printData()) {
            finalizeObject();
            return false;
        }

        return true;
    }

    private boolean createCouponDataPerso(ImageView qrCode,String content,String barCodeInfo) {
        String method = "";
        //Bitmap coffeeData = BitmapFactory.decodeResource(getResources(), R.drawable.accountadd);

        final int barcodeWidth = 4;
        final int barcodeHeight = 80;
        final int pageAreaHeight = 500;
        final int pageAreaWidth = 500;
        final int fontAHeight = 24;
        final int fontAWidth = 12;
        final int barcodeWidthPos = 110;
        final int barcodeHeightPos = 70;
        Bitmap logoData = BitmapFactory.decodeResource(getResources(), R.drawable.accountadd);
        StringBuilder textData = new StringBuilder();


        if (mPrinter == null) {
            return false;
        }

        try {
            Date now = new Date();
            final String newstring = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(now);
            method = "addTextAlign";
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);
            method = "addFeedLine";
            mPrinter.addFeedLine(1);
            textData.append(content);
//            textData.append("Object title: "+bundle.getString("name","")+"\n");
//            textData.append("------------------------------\n");
            textData.append("\nPrinted on the : "+newstring+"\n");
            textData.append("\n");
//            textData.append("Brand Name: "+bundle.getString("brand","") +"\n");
//            textData.append("\n");
//            textData.append("Creation Date: "+bundle.getString("created","") +"\n");
           textData.append("------------------------------\n");
            mPrinter.addText(textData.toString());

            if(bitmap == null) {

            }else
            {
                method = "addPageArea";
                mPrinter.addPageArea(0, 0, pageAreaWidth, pageAreaHeight);
                method = "addPageDirection";
                mPrinter.addPageDirection(Printer.DIRECTION_TOP_TO_BOTTOM);
                mPrinter.addPagePosition(0, bitmap.getHeight());
                Bitmap wmarkData = BitmapFactory.decodeByteArray(StaticUse.transformerImageBytes(qrCode), 0, StaticUse.transformerImageBytes(qrCode).length);
                method = "addPagePosition";
                method = "addImage";
                mPrinter.addImage(wmarkData, 0, 0,
                        wmarkData.getWidth(),
                        wmarkData.getHeight(),
                        Printer.PARAM_DEFAULT,
                        Printer.PARAM_DEFAULT,
                        Printer.PARAM_DEFAULT,
                        Printer.PARAM_DEFAULT,
                        Printer.PARAM_DEFAULT);
                method = "addPageEnd";
                mPrinter.addPageEnd();
            }

            //textData.append("7/01/07 16:58 6153 05 0191 134\n");
            //textData.append("ST# 21 OP# 001 TE# 01 TR# 747\n");

           /* method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("400 OHEIDA 3PK SPRINGF  9.99 R\n");
            textData.append("410 3 CUP BLK TEAPOT    9.99 R\n");
            textData.append("445 EMERIL GRIDDLE/PAN 17.99 R\n");
            textData.append("438 CANDYMAKER ASSORT   4.99 R\n");
            textData.append("474 TRIPOD              8.99 R\n");
            textData.append("433 BLK LOGO PRNTED ZO  7.99 R\n");
            textData.append("458 AQUA MICROTERRY SC  6.99 R\n");
            textData.append("493 30L BLK FF DRESS   16.99 R\n");
            textData.append("407 LEVITATING DESKTOP  7.99 R\n");
            textData.append("441 **Blue Overprint P  2.99 R\n");
            textData.append("476 REPOSE 4PCPM CHOC   5.49 R\n");
            textData.append("461 WESTGATE BLACK 25  59.99 R\n");
            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("SUBTOTAL                160.38\n");
            textData.append("TAX                      14.43\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());*/

//            method = "addTextSize";
//            mPrinter.addTextSize(2, 2);
//            method = "addText";
//            mPrinter.addText("Created at 2020    9887\n");
//            method = "addTextSize";
//            mPrinter.addTextSize(1, 1);
//            method = "addFeedLine";
//            mPrinter.addFeedLine(1);

           /* textData.append("CASH                    200.00\n");
            textData.append("CHANGE                   25.19\n");
            textData.append("------------------------------\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            textData.append("Purchased item total number\n");
            textData.append("Sign Up and Save !\n");
            textData.append("With Preferred Saving Card\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());
            method = "addFeedLine";
            mPrinter.addFeedLine(2);*/
           if(barCodeInfo== null || barCodeInfo.isEmpty()||barCodeInfo.equals(""))
           {

           }else
           {
               method = "addBarcode";
               mPrinter.addBarcode(barCodeInfo,
                       Printer.BARCODE_EAN13,
                       Printer.HRI_BELOW,
                       Printer.FONT_A,
                       barcodeWidth,
                       barcodeHeight);
           }
            method = "addCut";
            mPrinter.addCut(Printer.CUT_FEED);
        }
        catch (Exception e) {
            ShowMsg.showException(e, method, mContext);
            return false;
        }

        return true;
    }

    private boolean printData() {

        if (mPrinter == null) {
            return false;
        }

        if (!connectPrinter()) {
            return false;
        }

        PrinterStatusInfo status = mPrinter.getStatus();

        dispPrinterWarnings(status);

        if (!isPrintable(status)) {
            ShowMsg.showMsg(makeErrorMessage(status), mContext);
            try {
                mPrinter.disconnect();
            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        try {
            mPrinter.sendData(Printer.PARAM_DEFAULT);
        }
        catch (Exception e) {
            ShowMsg.showException(e, "sendData", mContext);
            try {
                mPrinter.disconnect();
            }
            catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        return true;
    }

    private boolean initializeObject() {

        try {
            mPrinter = new Printer(((SpnModelsItem) spinner.getSelectedItem()).getModelConstant(),
                    ((SpnModelsItem) spinner2.getSelectedItem()).getModelConstant(),
                    mContext);
        }
        catch (Exception e) {
            ShowMsg.showException(e, "Printer", mContext);
            return false;
        }

        mPrinter.setReceiveEventListener(this);


        return true;
    }

    private void finalizeObject() {
        if (mPrinter == null) {
            return;
        }

        mPrinter.clearCommandBuffer();

        mPrinter.setReceiveEventListener(null);

        mPrinter = null;
    }

    private boolean connectPrinter() {
        boolean isBeginTransaction = false;

        if (mPrinter == null) {
            return false;
        }

        try {

            mPrinter.connect(targets, Printer.PARAM_DEFAULT);
        }
        catch (Exception e) {
            ShowMsg.showException(e, "connect", mContext);
            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        }
        catch (Exception e) {
            ShowMsg.showException(e, "beginTransaction", mContext);
        }

        if (isBeginTransaction == false) {
            try {
                mPrinter.disconnect();
            }
            catch (Epos2Exception e) {
                // Do nothing
                return false;
            }
        }

        return true;
    }

    private void disconnectPrinter() {
        if (mPrinter == null) {
            return;
        }

        try {
            mPrinter.endTransaction();
        }
        catch (final Exception e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "endTransaction", mContext);
                }
            });
        }

        try {
            mPrinter.disconnect();
        }
        catch (final Exception e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "disconnect", mContext);
                }
            });
        }

        finalizeObject();
    }

    private boolean isPrintable(PrinterStatusInfo status) {
        if (status == null) {
            return false;
        }

        if (status.getConnection() == Printer.FALSE) {
            return false;
        }
        else if (status.getOnline() == Printer.FALSE) {
            return false;
        }
        else {
            ;//print available
        }

        return true;
    }

    private String makeErrorMessage(PrinterStatusInfo status) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += getString(R.string.handlingmsg_err_autocutter);
            msg += getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += getString(R.string.handlingmsg_err_battery_real_end);
        }

        return msg;
    }

    private void dispPrinterWarnings(PrinterStatusInfo status) {

        String warningsMsg = "";

        if (status == null) {
            return;
        }

        if (status.getPaper() == Printer.PAPER_NEAR_END) {
            warningsMsg += getString(R.string.handlingmsg_warn_receipt_near_end);
        }

        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_1) {
            warningsMsg += getString(R.string.handlingmsg_warn_battery_near_end);
        }

        warn.setText(warningsMsg);
    }

    private void updateButtonState(boolean state) {

        prints.setEnabled(state);
        prints.setClickable(state);
    }

    @Override
    public void onPtrReceive(final Printer printerObj, final int code, final PrinterStatusInfo status, final String printJobId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {
                ShowMsg.showResult(code, makeErrorMessage(status), mContext);

                dispPrinterWarnings(status);

                updateButtonState(true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disconnectPrinter();
                    }
                }).start();
            }
        });
    }


}
