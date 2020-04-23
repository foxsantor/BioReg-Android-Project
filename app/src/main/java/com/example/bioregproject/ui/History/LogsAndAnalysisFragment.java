package com.example.bioregproject.ui.History;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.lifecycle.LifecycleOwner;
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
import android.widget.Button;
import android.widget.ImageButton;

import com.example.bioregproject.Adapters.LogsAdapter;
import com.example.bioregproject.Adapters.UserLogsAdapter;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.MainActivityViewModel;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.History;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LogsAndAnalysisFragment extends Fragment {

    private LogsAndAnalysisViewModel mViewModel;
    private Button newest,oldest;
    private ImageButton clear,csv;
    private TextInputLayout search;
    private RecyclerView logs,users;
    private ConstraintLayout upperConst;
    private UserLogsAdapter userLogsAdapter;
    private LogsAdapter logsAdapter;
    private MainActivityViewModel mainActivityViewModel;



    public static LogsAndAnalysisFragment newInstance() {
        return new LogsAndAnalysisFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.logs_and_analysis_fragment, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(LogsAndAnalysisViewModel.class);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        Fragment parent = (Fragment) navHostFragment.getParentFragment();
        upperConst = parent.getView().findViewById(R.id.upperConst);
        upperConst.setVisibility(View.GONE);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.mainMenu);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        newest = view.findViewById(R.id.news);
        oldest = view.findViewById(R.id.old);
        clear = view.findViewById(R.id.clear);
        clear.setVisibility(View.INVISIBLE);
        csv = view.findViewById(R.id.csv);
        search = view.findViewById(R.id.search);
        logs = view.findViewById(R.id.logs);
        users = view.findViewById(R.id.users);

        users.setLayoutManager(new LinearLayoutManager(getActivity()));
        userLogsAdapter = new UserLogsAdapter(getActivity(),getActivity());
        users.setAdapter(userLogsAdapter);
        mainActivityViewModel.getAllAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                userLogsAdapter.submitList(accounts);
            }
        });


        mViewModel.initAllTeams(BioRegDB.getInstance(getActivity()).historyDAO());
        logs.setLayoutManager(new LinearLayoutManager(getActivity()));
        logsAdapter = new LogsAdapter(getActivity(),getActivity());
        logs.setAdapter(logsAdapter);
        mViewModel.teamAllList.observe(
                getActivity(), new Observer<PagedList<History>>() {
                    @Override
                    public void onChanged(PagedList<History> pagedList) {
                        try {
                            //Toast.makeText(getActivity(), ""+pagedList.get(0).getId(), Toast.LENGTH_SHORT).show();
                            Log.e("Paging ", "PageAll" + pagedList.size());
                            //refresh current list
                            logsAdapter.submitList(pagedList);
                            //adapter.submitList(pagedList);
                        } catch (Exception e) {
                        }
                    }
                });


        oldest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.filterTextAll.setValue("OldestX");
                oldest.setBackgroundColor(getActivity().getResources().getColor(R.color.White));
                oldest.setTextColor(getActivity().getResources().getColor(R.color.blueDeep));
                clearUp("Newest");
                clear.setVisibility(View.VISIBLE);
            }
        });



        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getAllHistorys().observe(getActivity(), new Observer<List<History>>() {
                    @Override
                    public void onChanged(List<History> list) {
                     StaticUse.exportCsvFilesNotification(list,getActivity());
                    }
                });
            }
        });

        newest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.filterTextAll.setValue("NewestX");
                newest.setBackgroundColor(getActivity().getResources().getColor(R.color.White));
                newest.setTextColor(getActivity().getResources().getColor(R.color.blueDeep));
                clearUp("Oldest");
                clear.setVisibility(View.VISIBLE);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.filterTextAll.setValue("");
                clear.setVisibility(View.INVISIBLE);
                clearUp("ALL");
                search.getEditText().setText("");
                search.getEditText().setFocusable(false);
                search.getEditText().setFocusable(true);

            }
        });

        userLogsAdapter.setOnIteemClickListener(new UserLogsAdapter.OnItemClickLisnter() {
            @Override
            public void OnItemClick(Account account) {
                if(account.getFirstName().equals("Administrator"))
                {
                    mViewModel.filterTextAll.setValue("OwnerX"+account.getFirstName()+";"+"");
                }else
                {
                    mViewModel.filterTextAll.setValue("OwnerX"+account.getFirstName()+";"+account.getLastName());

                }
                clear.setVisibility(View.VISIBLE);

            }
        });

        //first time set an empty value to get all data
        mViewModel.filterTextAll.setValue("");


        search.getEditText().addTextChangedListener(new TextWatcher() {
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
                if (editable.toString().isEmpty()) {
                    clear.setVisibility(View.INVISIBLE);
                } else {
                    clear.setVisibility(View.VISIBLE);

                }
            }  });


    }


    private void clearUp(String key)
    {

        if(key.equals("Newest"))
        {
            newest.setBackgroundColor(getActivity().getResources().getColor(R.color.blueDeep));
            newest.setTextColor(getActivity().getResources().getColor(R.color.White));
        }else if(key.equals("Oldest"))
        {
            oldest.setBackgroundColor(getActivity().getResources().getColor(R.color.blueDeep));
            oldest.setTextColor(getActivity().getResources().getColor(R.color.White));
        }else
        {
            oldest.setBackgroundColor(getActivity().getResources().getColor(R.color.blueDeep));
            oldest.setTextColor(getActivity().getResources().getColor(R.color.White));
            newest.setBackgroundColor(getActivity().getResources().getColor(R.color.blueDeep));
            newest.setTextColor(getActivity().getResources().getColor(R.color.White));
        }


    }
}
