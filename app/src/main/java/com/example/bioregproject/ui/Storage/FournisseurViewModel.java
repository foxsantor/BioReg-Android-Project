package com.example.bioregproject.ui.Storage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bioregproject.Repositories.FournisseurRepository;
import com.example.bioregproject.entities.Fournisseur;


import java.util.List;

public class FournisseurViewModel extends AndroidViewModel {

    private FournisseurRepository frsRepository;
    private LiveData<List<Fournisseur>> allFrs;

    public FournisseurViewModel(@NonNull Application application) {
        super(application);
        frsRepository = new FournisseurRepository(application);
        allFrs = frsRepository.getAllFrs();
    }

    public LiveData<List<Fournisseur>> getAllFrs() {
        return allFrs;
    }
    public void insert(Fournisseur frs) {
        frsRepository.insert(frs);
    }
    public void update(Fournisseur frs){frsRepository.update(frs);}
    public void delete(Fournisseur frs) {
        frsRepository.delete(frs);
    }


}
