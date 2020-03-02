package com.example.bioregproject.ui.Traceability.ImageFlow;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class IamgeFlowPrinting extends Fragment {

    private IamgeFlowPrintingViewModel mViewModel;
    private Button print;
    private ImageView qrCode,preview;
    private TextView id,created;
    private ConstraintLayout mother;
    private Bundle bundle;
    String inputValue;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    public static IamgeFlowPrinting newInstance() {
        return new IamgeFlowPrinting();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.iamge_flow_printing_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bundle = getArguments();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(IamgeFlowPrintingViewModel.class);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_iamgeFlowPrinting_to_imageFlowQrCode);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        print = view.findViewById(R.id.print);
        qrCode = view.findViewById(R.id.Qrcode);
        mother = view.findViewById(R.id.mother);
        StaticUse.backgroundAnimator(mother);
        id  = view.findViewById(R.id.idText);
        created = view.findViewById(R.id.CreatedText);
        preview = view.findViewById(R.id.preview);
        if(bundle != null)
        {
            id.setText(bundle.getString("id",""));
            created.setText(bundle.getString("created",""));
            Glide.with(getActivity()).asBitmap().load(bundle.getByteArray("image")).into(preview);
            inputValue = bundle.getString("id","");
            Toast.makeText(getActivity(), ""+inputValue, Toast.LENGTH_SHORT).show();

        }
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;
        qrgEncoder = new QRGEncoder("hello", null, QRGContents.Type.TEXT, smallerDimension);
        try {
            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.getBitmap();
            // Setting Bitmap to ImageView
            Glide.with(getActivity()).asBitmap().load(bitmap).into(qrCode);
        } catch (Exception e) {
            e.printStackTrace();
        }









    }
}
