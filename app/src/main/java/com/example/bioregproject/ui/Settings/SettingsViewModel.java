package com.example.bioregproject.ui.Settings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Settings;

import java.util.ArrayList;

public class SettingsViewModel extends ViewModel {

    MutableLiveData<ArrayList<Settings>> settingLiveData;
    ArrayList<Settings> settingsArrayList;

    public SettingsViewModel() {
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

        Settings account_management = new Settings(R.drawable.ic_supervisor_account_seetings_50dp,"Account Management",false,R.id.accountMangmentFragment,R.drawable.ic_accountmangment_selected);
        Settings general_settings= new Settings(R.drawable.ic_settings_gray_24dp,"General Settings",true,R.id.generalSettings,R.drawable.notselectedgeneral);
        Settings cloud_settings= new Settings(R.drawable.ic_cloud_unselected_24dp,"Cloud Synchronisation",false,R.id.cloudFragment,R.drawable.ic_cloud_selected_24dp);
        settingsArrayList = new ArrayList<>();
        settingsArrayList.add(general_settings);
        settingsArrayList.add(account_management);
        settingsArrayList.add(cloud_settings);
    }
}
