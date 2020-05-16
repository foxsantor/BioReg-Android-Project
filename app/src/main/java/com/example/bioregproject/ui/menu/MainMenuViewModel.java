package com.example.bioregproject.ui.menu;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.ExportFiles;
import com.example.bioregproject.entities.ExportFiles;

import java.util.ArrayList;

public class MainMenuViewModel extends ViewModel {
    MutableLiveData<ArrayList<ExportFiles>> settingLiveData;
    ArrayList<ExportFiles> ExportFilesArrayList;

    public MainMenuViewModel() {
        settingLiveData = new MutableLiveData<>();
        // call your Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<ExportFiles>> getSettingMutableLiveData() {
        return settingLiveData;
    }

    public void init() {
        populateList();
        settingLiveData.setValue(ExportFilesArrayList);
    }

    public void populateList() {

        ExportFiles account_management = new ExportFiles("account_table","Export Account Table to CSV");
        ExportFiles history_ExportFiles= new ExportFiles("history_table","Export History Table to CSV");
        ExportFiles Suppliers_ExportFiles= new ExportFiles("fournisseur_table","Export Suppliers Table to CSV");
        ExportFiles oil_ExportFiles= new ExportFiles("oil_table","Export Oil Control Table to CSV");
        ExportFiles persoTAsk_ExportFiles= new ExportFiles("parsotask_table","Export Planifcation Tasks Table to CSV");
        ExportFiles post_ExportFiles= new ExportFiles("post_table","Export Post Table to CSV");
        ExportFiles tracability_ExportFiles= new ExportFiles("product_table","Export Traceablity Table to CSV");
        ExportFiles produi_ExportFiles= new ExportFiles("produit_table","Export Products Table to CSV");
        ExportFiles SettingsOl_ExportFiles= new ExportFiles("settingOil_table","Export Oil Settings Table to CSV");
        ExportFiles Storage_ExportFiles= new ExportFiles("storage_table","Export Storage Table to CSV");
        ExportFiles Surface_ExportFiles= new ExportFiles("Surface","Export Cleaning Surface Table to CSV");
        ExportFiles Tache_ExportFiles= new ExportFiles("Tache","Export Cleaning Tasks Table to CSV");
        ExportFiles CategorieTache_ExportFiles= new ExportFiles("CategorieTache","Export Cleaning Task Categories Table to CSV");

        ExportFilesArrayList = new ArrayList<>();
        ExportFilesArrayList.add(account_management);
        ExportFilesArrayList.add(Tache_ExportFiles);
        ExportFilesArrayList.add(CategorieTache_ExportFiles);
        ExportFilesArrayList.add(Surface_ExportFiles);
        ExportFilesArrayList.add(history_ExportFiles);
        ExportFilesArrayList.add(post_ExportFiles);
        ExportFilesArrayList.add(oil_ExportFiles);
        ExportFilesArrayList.add(SettingsOl_ExportFiles);
        ExportFilesArrayList.add(produi_ExportFiles);
        ExportFilesArrayList.add(Storage_ExportFiles);
        ExportFilesArrayList.add(persoTAsk_ExportFiles);
        ExportFilesArrayList.add(Suppliers_ExportFiles);
        ExportFilesArrayList.add(tracability_ExportFiles);


    }
}
