package com.example.bioregproject.ui.Storage;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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


        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.mainMenu);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);



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
