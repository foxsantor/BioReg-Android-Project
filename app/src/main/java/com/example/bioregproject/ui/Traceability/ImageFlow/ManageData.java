package com.example.bioregproject.ui.Traceability.ImageFlow;

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

import com.example.bioregproject.Adapters.AccountSettingsAdapter;
import com.example.bioregproject.Adapters.DataTracedAdapater;
import com.example.bioregproject.DAOs.ProductDAO;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.Products;
import com.example.bioregproject.ui.Settings.AccountMangement.MangeAccountViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class ManageData extends Fragment {

    private ManageDataViewModel mViewModel;
    private RecyclerView recyclerView;
    private TextInputLayout searchView;

    public static ManageData newInstance() {
        return new ManageData();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manage_data_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ManageDataViewModel.class);
        //searchView = view.findViewById(R.id.search);
        recyclerView= view.findViewById(R.id.ManageData);


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.imageFlowHome);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        mViewModel.initAllTeams(BioRegDB.getInstance(getActivity()).productDAO());
        final DataTracedAdapater adapter = new DataTracedAdapater(getActivity(),getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mViewModel.teamAllList.observe(
                getActivity(), new Observer<PagedList<Products>>() {
                    @Override
                    public void onChanged(PagedList<Products> pagedList) {
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
        mViewModel.filterTextAll.
                setValue("ImageT");
//first time set an empty value to get all data
        /*searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                //just set the current value to search.
                mViewModel.filterTextAll.
                        setValue("ImageT");
            }
        });*/
    }
}
