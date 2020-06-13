package com.example.bioregproject.ui.Storage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.bioregproject.DAOs.FournisseurDAO;
import com.example.bioregproject.DAOs.HistoryDAO;
import com.example.bioregproject.Repositories.FournisseurRepository;
import com.example.bioregproject.entities.Fournisseur;
import com.example.bioregproject.entities.History;


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

    public void update(Fournisseur frs) {
        frsRepository.update(frs);
    }

    public void delete(Fournisseur frs) {
        frsRepository.delete(frs);
    }


    public LiveData<PagedList<Fournisseur>> teamAllList;
    public FournisseurDAO teamDao;
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();


    public void initAllTeams(final FournisseurDAO teamDao) {
        this.teamDao = teamDao;
        final PagedList.Config config = (new PagedList.Config.Builder())
                .setPageSize(10)
                .build();
        teamAllList = Transformations.switchMap(filterTextAll, new Function<String, LiveData<PagedList<Fournisseur>>>() {
            @Override
            public LiveData<PagedList<Fournisseur>> apply(String input) {
                if (input == null || input.equals("") || input.equals("%%")) {
//check if the current value is empty load all data else search
                    return new LivePagedListBuilder<>(
                            teamDao.loadAllHistory(), config)
                            .build();
                } else {
                    //System.out.println("CURRENTINPUT: " + input);
                    return new LivePagedListBuilder<>(
                            teamDao.loadAllHistorybyName(input), config)
                            .build();
                }
            }
        });

    }

}
