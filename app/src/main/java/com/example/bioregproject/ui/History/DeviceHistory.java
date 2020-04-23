package com.example.bioregproject.ui.History;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioregproject.Adapters.AccountSettingsAdapter;
import com.example.bioregproject.Adapters.HistoryAdapter;
import com.example.bioregproject.Adapters.HistoryAdapter;
import com.example.bioregproject.Adapters.HistorySearchAdapter;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.ExternalHistory;
import com.example.bioregproject.entities.History;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeviceHistory extends Fragment {

    public static DeviceHistoryViewModel mViewModel;
    private RecyclerView history,historySearch;
    private HistoryAdapter HistoryAdapter;
    private MaterialCardView CardSearch;
    private TextView keyValue, noSearch;
    private TextInputLayout searchView;
    private ConstraintLayout upperConst;
    private static int number;

    private  static int j;

    public static DeviceHistory newInstance() {
        return new DeviceHistory();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.device_history_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.mainMenu);
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        Fragment parent = (Fragment) navHostFragment.getParentFragment();
        searchView = parent.getView().findViewById(R.id.search);
        noSearch = parent.getView().findViewById(R.id.noSearch);
        upperConst = parent.getView().findViewById(R.id.upperConst);
        upperConst.setVisibility(View.VISIBLE);
        mViewModel = ViewModelProviders.of(this).get(DeviceHistoryViewModel.class);
        history = view.findViewById(R.id.history);
        history.setVisibility(View.VISIBLE);
        CardSearch = view.findViewById(R.id.CardSearch);
        keyValue = view.findViewById(R.id.keyvalue);
        CardSearch.setVisibility(View.GONE);
        historySearch = view.findViewById(R.id.historySearch);
        history.setLayoutManager(new LinearLayoutManager(getActivity()));
        HistoryAdapter = new HistoryAdapter(getActivity(),getActivity(),this);
        history.setAdapter(HistoryAdapter);
        mViewModel.getAllHistorys().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                //Toast.makeText(getActivity(), ""+histories.get(0).getId(), Toast.LENGTH_SHORT).show();
                if(histories.isEmpty())
                {
                    return;
                }else
                {
                List<ExternalHistory> externalHistories = new ArrayList<>();
                List<History> history = new ArrayList<>();
                ExternalHistory object = new ExternalHistory();
                object.setCreation(histories.get(0).getCreation());
                object.setList(history);
                externalHistories.add(0,object);
                j=0;
                for(int i = 0 ; i<histories.size();i++){
                        if( !(new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(histories.get(i).getCreation()).equals(new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(externalHistories.get(j).getCreation()))))
                        {
                            List<History> historyx = new ArrayList<>();
                            ExternalHistory objecto = new ExternalHistory();
                            objecto.setCreation(histories.get(i).getCreation());
                            objecto.setList(historyx);
                             j = j+1;
                            externalHistories.add(j,objecto);
                        }
                        if((new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(histories.get(i).getCreation()).equals(new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(externalHistories.get(j).getCreation()))))
                        {
                            History objects = histories.get(i);
                            externalHistories.get(j).getList().add(objects);
                        }
                    }
                        HistoryAdapter.submitList(externalHistories);
            }}
        });

        mViewModel.initAllTeams(BioRegDB.getInstance(getActivity()).historyDAO());
        final HistorySearchAdapter adapter = new HistorySearchAdapter(getActivity(),getActivity());
        historySearch.setLayoutManager(new LinearLayoutManager(getContext()));
        historySearch.setAdapter(adapter);
        mViewModel.teamAllList.observe(
                getActivity(), new Observer<PagedList<History>>() {
                    @Override
                    public void onChanged(PagedList<History> pagedList) {
                        try {
                            //Toast.makeText(getActivity(), ""+pagedList.get(0).getId(), Toast.LENGTH_SHORT).show();
                            Log.e("Paging ", "PageAll" + pagedList.size());
                            //refresh current list
                            adapter.submitList(pagedList);
                            number = pagedList.size();
                            if(number == 0)
                            {
                                noSearch.setVisibility(View.VISIBLE);
                                CardSearch.setVisibility(View.GONE);
                            }else {

                                if(history.getVisibility() == View.GONE)
                                CardSearch.setVisibility(View.VISIBLE);
                            }
                            //adapter.submitList(pagedList);
                        } catch (Exception e) {
                        }
                    }
                });

        //first time set an empty value to get all data
        mViewModel.filterTextAll.setValue("");


        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                //just set the current value to search.
                mViewModel.filterTextAll.setValue("%" + editable.toString() + "%");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        keyValue.setText("Found "+number+" search results for"+" "+"'"+editable.toString()+"'");
                    }
                }, 500);


                if(editable.toString().isEmpty())
                {
                    history.setVisibility(View.VISIBLE);
                    CardSearch.setVisibility(View.GONE);
                }else
                {
                    history.setVisibility(View.GONE);
                }

            }
        });





    }

    public static void deleteHistory(long id)
    {
        History deletable = new History();
        deletable.setId(id);
        mViewModel.delete(deletable);
    }
}
