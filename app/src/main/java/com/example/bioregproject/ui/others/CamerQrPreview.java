package com.example.bioregproject.ui.others;

import androidx.cardview.widget.CardView;
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

import android.os.Vibrator;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.bioregproject.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class CamerQrPreview extends Fragment {

    private CamerQrPreviewViewModel mViewModel;
    private CardView die;
    private AnimatorSet mAnimationSet;
    private Button inddictor;
    private CameraSource cameraSource;
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;

    public static CamerQrPreview newInstance() {
        return new CamerQrPreview();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.camer_qr_preview_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        die = view.findViewById(R.id.relative_layout_id);
        inddictor = view.findViewById(R.id.indictor);
        mViewModel = ViewModelProviders.of(this).get(CamerQrPreviewViewModel.class);
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
        surfaceView = view.findViewById(R.id.surfaceView);
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
                            vibrator.vibrate(1000);
                            inddictor.setText(qrCode.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });


    }
}
