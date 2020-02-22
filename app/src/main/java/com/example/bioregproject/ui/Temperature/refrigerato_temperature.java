package com.example.bioregproject.ui.Temperature;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.bioregproject.R;

public class refrigerato_temperature extends Fragment {

    private RefrigeratoTemperatureViewModel mViewModel;

    private NumberPicker number1,number2;
    String[] numsLeft = {"-2","-1","0","1","2"};
    String[] numRight = {"-9","-8","-7","-6","-5","-4","-3","-2","-1","0","1","2","3","4","5","6","7","8","9"};

    public static refrigerato_temperature newInstance() {
        return new refrigerato_temperature();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.refrigerato_temperature_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        number1 = view.findViewById(R.id.picker_left);
        number2 = view.findViewById(R.id.picker_right);
        number1.setDisplayedValues(numsLeft);
        number2.setDisplayedValues(numRight);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RefrigeratoTemperatureViewModel.class);
        // TODO: Use the ViewModel
    }

}
