package com.example.bioregproject.ui.Settings.AccountMangement;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.bioregproject.DAOs.AccountDAO;
import com.example.bioregproject.Repositories.AccountRepository;
import com.example.bioregproject.entities.Account;

import java.util.List;

public class MangeAccountViewModel extends ViewModel {

    public LiveData<PagedList<Account>> teamAllList;
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();
    public AccountDAO teamDao;

    public void initAllTeams(final AccountDAO teamDao) {
        this.teamDao = teamDao;
        final PagedList.Config config = (new PagedList.Config.Builder())
                .setPageSize(10)
                .build();

        teamAllList = Transformations.switchMap(filterTextAll, new Function<String, LiveData<PagedList<Account>>>() {
            @Override
            public LiveData<PagedList<Account>> apply(String input) {
                if (input == null || input.equals("") || input.equals("%%")) {
//check if the current value is empty load all data else search
                    return new LivePagedListBuilder<>(
                            teamDao.loadAllAccount(), config)
                            .build();
                } else {
                    System.out.println("CURRENTINPUT: " + input);
                    return new LivePagedListBuilder<>(
                            teamDao.loadAllAccountbyName(input), config)
                            .build();
                }

            }
        });

    }

}
