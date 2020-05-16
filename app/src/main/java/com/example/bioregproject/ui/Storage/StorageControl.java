package com.example.bioregproject.ui.Storage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bioregproject.Adapters.SectionPagerAdapter;
import com.example.bioregproject.R;
import com.google.android.material.tabs.TabLayout;

public class StorageControl extends Fragment {

    private StorageControlViewModel mViewModel;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static StorageControl newInstance() {
        return new StorageControl();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.storage_control_fragment, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);



        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });






        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ProductFragment(), "Product");
        adapter.addFragment(new FournisseurFragment(), "Supplier");
        adapter.addFragment(new AjoutMarchandises(), "Receipt of goods");



        viewPager.setAdapter(adapter);
    }



}
