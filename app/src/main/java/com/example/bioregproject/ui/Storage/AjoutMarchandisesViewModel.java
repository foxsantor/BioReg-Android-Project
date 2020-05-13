package com.example.bioregproject.ui.Storage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bioregproject.Repositories.FournisseurRepository;
import com.example.bioregproject.Repositories.ProduitRepository;
import com.example.bioregproject.Repositories.StorageRepository;
import com.example.bioregproject.entities.Fournisseur;
import com.example.bioregproject.entities.Produit;
import com.example.bioregproject.entities.Storage;

import java.util.List;

public class AjoutMarchandisesViewModel extends AndroidViewModel {
    private FournisseurRepository frsRepository;
    private LiveData<List<Fournisseur>> allFrs;
    private StorageRepository RecpRepository;
    private LiveData<List<Storage>> allRecp;
    private ProduitRepository produitRepository;
    private LiveData<List<Produit>> allproduit;
    public AjoutMarchandisesViewModel(@NonNull Application application) {
        super(application);
        frsRepository = new FournisseurRepository(application);
        allFrs = frsRepository.getAllFrs();
        RecpRepository = new StorageRepository(application) ;
        allRecp = RecpRepository.getAllStorage();
        produitRepository = new ProduitRepository(application);
        allproduit = produitRepository.getAllProduit();


    }
    public LiveData<List<Fournisseur>> getAllFrs() {
        return allFrs;
    }
    public LiveData<List<Storage>> getAllRecp() {
        return allRecp;
    }
    public LiveData<List<Produit>> getAllproduit() {
        return allproduit;
    }
    public void insert(Storage Storage) {
        RecpRepository.insert(Storage);
    }
    public void update(Storage Storage){RecpRepository.update(Storage);}
    public void delete(Storage Storage) {
        RecpRepository.delete(Storage);
    }

}
