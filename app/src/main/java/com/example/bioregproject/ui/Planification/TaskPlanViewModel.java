package com.example.bioregproject.ui.Planification;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.bioregproject.DAOs.PersoTaskDAO;
import com.example.bioregproject.DAOs.ProductDAO;
import com.example.bioregproject.Repositories.PersoTaskRepository;
import com.example.bioregproject.entities.PersoTask;

import java.util.Date;
import java.util.List;

public class TaskPlanViewModel extends AndroidViewModel {
    private PersoTaskRepository repository;
    private LiveData<List<PersoTask>> allPersoTask;
    public TaskPlanViewModel(@NonNull Application application) {
        super(application);
        repository = new PersoTaskRepository(application);
        allPersoTask = repository.getAllPersoTask();
    }

    public void insert(PersoTask product)
    {
        repository.insert(product);
    }
    public LiveData<List<PersoTask>> loadPersoTaskOne(Long id) {return repository.loadPersoTaskOne(id);}
    public LiveData<List<PersoTask>> getAssgineTAsks(Long id) {return repository.getPersoTaskAssgine(id);}
    public LiveData<List<PersoTask>> getAssgineTAskSpec(Long id, String name) {return repository.getPersoTaskSpec(id,name);}
    public LiveData<List<PersoTask>> getAssgineTAsksSatte(Long id,Boolean state) {return repository.getPersoTaskByNameAndBrand(id,state);}
    public void update(PersoTask product)
    {
        repository.update(product);
    }
    public void delete(PersoTask product)
    {
        repository.delete(product);
    }
    public void deleteAll()
    {
        repository.deleteAllPersoTask();
    }
    public LiveData<List<PersoTask>> getAllPersoTaskAdmin()
    {
        return allPersoTask;
    }


    public LiveData<PagedList<PersoTask>> teamAllList;
    public MutableLiveData<String> filterTextAll = new MutableLiveData<>();

    public LiveData<PagedList<PersoTask>> teamAllListAdmin;
    public MutableLiveData<String> filterTextAllAdmin = new MutableLiveData<>();

    public PersoTaskDAO teamDao;

    public void initAllTeams(final PersoTaskDAO teamDao,long id,String state,String state2) {
        this.teamDao = teamDao;
        final PagedList.Config config = (new PagedList.Config.Builder())
                .setPageSize(10)
                .build();
        teamAllList = Transformations.switchMap(filterTextAll, new Function<String, LiveData<PagedList<PersoTask>>>() {
            @Override
            public LiveData<PagedList<PersoTask>> apply(String input) {
                if (input == null || input.equals("") || input.equals("%%")) {

                    return new LivePagedListBuilder<>(
                            teamDao.loadAllTaskByAssgnie(id), config)
                            .build();
                } else  {
                    if(input.equals("Open,Invalid"))
                    {
                        return new LivePagedListBuilder<>(
                                teamDao.loadAllPersoTaskState2Admin("Open","Invalid",id), config)
                                .build();
                    }else if(input.equals("Done,"))
                    {
                        return new LivePagedListBuilder<>(
                                teamDao.loadAllPersoTaskStateAdmin("Done",id), config)
                                .build();
                    }else
                    {
                        return new LivePagedListBuilder<>(
                                teamDao.loadAllProductbyNameAdmin(input,id), config)
                                .build();
                    }}

            }
        });

    }



    public void initAllTeamsAdmin(final PersoTaskDAO teamDao,long id,String state,String state2) {
        this.teamDao = teamDao;
        final PagedList.Config config = (new PagedList.Config.Builder())
                .setPageSize(10)
                .build();
        teamAllListAdmin = Transformations.switchMap(filterTextAllAdmin, new Function<String, LiveData<PagedList<PersoTask>>>() {
            @Override
            public LiveData<PagedList<PersoTask>> apply(String input) {


                if (input == null || input.equals("") || input.equals("%%")) {

                    return new LivePagedListBuilder<>(
                            teamDao.loadAllPersoTask(), config)
                            .build();
                } else  {
                    if(input.equals("Open,Invalid"))
                    {
                        return new LivePagedListBuilder<>(
                                teamDao.loadAllPersoTaskState2("Open","Invalid"), config)
                                .build();
                    }else if(input.equals("Done,"))
                    {
                        return new LivePagedListBuilder<>(
                                teamDao.loadAllPersoTaskState("Done"), config)
                                .build();
                    }else
                    {
                        return new LivePagedListBuilder<>(
                                teamDao.loadAllProductbyName(input), config)
                            .build();
                }}

            }
        });

    }
}
