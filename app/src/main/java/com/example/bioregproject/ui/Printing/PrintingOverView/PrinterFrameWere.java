package com.example.bioregproject.ui.Printing.PrintingOverView;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bioregproject.R;

public class PrinterFrameWere extends Fragment {

    private PrinterFrameWereViewModel mViewModel;

    public static PrinterFrameWere newInstance() {
        return new PrinterFrameWere();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.printer_frame_were_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PrinterFrameWereViewModel.class);
        // TODO: Use the ViewModel
    }

}
