package com.example.bioregproject.ui.History;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Settings;

import java.util.ArrayList;

public class HisotryViewModel extends ViewModel {
    MutableLiveData<ArrayList<Settings>> settingLiveData;
    ArrayList<Settings> settingsArrayList;

    public HisotryViewModel() {
        settingLiveData = new MutableLiveData<>();
        // call your Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<Settings>> getSettingMutableLiveData() {
        return settingLiveData;
    }

    public void init() {
        populateList();
        settingLiveData.setValue(settingsArrayList);
    }

    public void populateList() {

        Settings device_history = new Settings(R.drawable.ic_tablet_android_gray_24dp,"Device History",true,R.id.deviceHistory,R.drawable.ic_tablet_android_blue_24dp);
        Settings logs= new Settings(R.drawable.ic_description_gray_24dp,"Logs & Analysis ",false,R.id.logsAndAnalysisFragment,R.drawable.ic_description_blue_24dp);
        //Settings cloud_settings= new Settings(R.drawable.ic_cloud_unselected_24dp,"Cloud Synchronisation",false,R.id.cloudFragment,R.drawable.ic_cloud_selected_24dp);
        settingsArrayList = new ArrayList<>();
        settingsArrayList.add(device_history);
        settingsArrayList.add(logs);
        //settingsArrayList.add(cloud_settings);
    }
}
