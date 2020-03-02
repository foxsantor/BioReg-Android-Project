package com.example.bioregproject.ui.Traceability;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bioregproject.Adapters.TracePager;
import com.example.bioregproject.R;
import com.google.android.material.tabs.TabLayout;

public class Products extends Fragment {

    private ProductsViewModel mViewModel;
    private ViewPager viewer;
    private TabLayout tabs;

    public static Products newInstance() {
        return new Products();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.products_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TracePager sectionsPagerAdapter = new TracePager(getActivity(), getChildFragmentManager());
        viewer =  view.findViewById(R.id.pager);
        viewer.setAdapter(sectionsPagerAdapter);
        tabs =  view.findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewer);
    }
}
