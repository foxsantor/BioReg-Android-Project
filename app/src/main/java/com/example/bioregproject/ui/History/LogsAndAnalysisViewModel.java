package com.example.bioregproject.ui.History;

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

import com.example.bioregproject.DAOs.HistoryDAO;
import com.example.bioregproject.Repositories.AccountRepository;
import com.example.bioregproject.Repositories.HistoryRepository;
import com.example.bioregproject.entities.History;

import java.util.List;

public class LogsAndAnalysisViewModel extends AndroidViewModel {

    private LiveData<List<History>> allHistory;
    private HistoryRepository HistoryRepository;

    public LogsAndAnalysisViewModel(@NonNull Application application) {
        super(application);
        HistoryRepository = new HistoryRepository(application);
        allHistory = HistoryRepository.getAllHistory();
    }

    public LiveData<PagedList<History>> teamAllList;
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();
    public HistoryDAO teamDao;
    public LiveData<List<History>> getAllHistorys()
    {
        return allHistory;
    }

    public void initAllTeams(final HistoryDAO teamDao) {
        this.teamDao = teamDao;
        final PagedList.Config config = (new PagedList.Config.Builder())
                .setPageSize(10)
                .build();
        teamAllList = Transformations.switchMap(filterTextAll, new Function<String, LiveData<PagedList<History>>>() {
            @Override
            public LiveData<PagedList<History>> apply(String input) {
                if (input == null || input.equals("") || input.equals("%%")) {
//check if the current value is empty load all data else search
                    return new LivePagedListBuilder<>(
                            teamDao.loadAllHistory(), config)
                            .build();

                }else if(input.contains("OwnerX")) {

                    String[] arrOfStr = input.split("X", 2);
                    String[] arrOfStrName = arrOfStr[1].split(";",2);
                    return new LivePagedListBuilder<>(
                            teamDao.loadAllHistorybyOwner(arrOfStrName[0],arrOfStrName[1]), config)
                            .build();

                }else if(input.equals("NewestX")) {

                    return new LivePagedListBuilder<>(
                            teamDao.loadAllHistoryNewst(), config)
                            .build();

                }else if(input.equals("OldestX")) {

                    return new LivePagedListBuilder<>(
                            teamDao.loadAllHistoryOldst(), config)
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
