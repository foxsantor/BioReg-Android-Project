package com.example.bioregproject.ui.History;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.bioregproject.Adapters.LogsAdapter;
import com.example.bioregproject.Adapters.UserLogsAdapter;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.MainActivityViewModel;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.History;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LogsAndAnalysisFragment extends Fragment {

    private DeviceHistoryViewModel mViewModel;
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

        mViewModel = ViewModelProviders.of(this).get(DeviceHistoryViewModel.class);
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



        logs.setLayoutManager(new LinearLayoutManager(getActivity()));
        logsAdapter = new LogsAdapter(getActivity(),getActivity());
        logs.setAdapter(logsAdapter);
        mViewModel.getAllHistorys().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> accounts) {
                logsAdapter.submitList(accounts);
            }
        });













    }
}
