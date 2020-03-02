package com.example.bioregproject.ui.Settings.AccountMangement;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.bioregproject.Adapters.AccountSettingsAdapter;
import com.example.bioregproject.DataBases.AccountDB;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.google.android.material.textfield.TextInputLayout;

public class MangeAccount extends Fragment {

    private MangeAccountViewModel mViewModel;
    private RecyclerView recyclerView;
    private TextInputLayout searchView;


    public static MangeAccount newInstance() {
        return new MangeAccount();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mange_account_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(MangeAccountViewModel.class);
        searchView = view.findViewById(R.id.search);
        recyclerView= view.findViewById(R.id.reec);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.accountMangmentFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        mViewModel.initAllTeams(AccountDB.getInstance(getActivity()).accountDao());
        final AccountSettingsAdapter adapter = new AccountSettingsAdapter(getActivity(),getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mViewModel.teamAllList.observe(
                getActivity(), new Observer<PagedList<Account>>() {
                    @Override
                    public void onChanged(PagedList<Account> pagedList) {
                        try {
                            //Toast.makeText(getActivity(), ""+pagedList.get(0).getId(), Toast.LENGTH_SHORT).show();
                            Log.e("Paging ", "PageAll" + pagedList.size());
                            //refresh current list
                            adapter.submitList(pagedList);
                            //adapter.submitList(pagedList);

                        } catch (Exception e) {
                        }
                    }
                });

//first time set an empty value to get all data
        mViewModel.filterTextAll.setValue("");
        searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                //just set the current value to search.
                mViewModel.filterTextAll.
                        setValue("%" + editable.toString() + "%");
            }
        });





    }
}
