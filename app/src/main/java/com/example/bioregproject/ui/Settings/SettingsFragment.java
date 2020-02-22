package com.example.bioregproject.ui.Settings;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bioregproject.Adapters.SettingsItemAdapter;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Settings;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private RecyclerView settingsRecycle;
    private SettingsItemAdapter adapter;
    private ArrayList<Settings> settingsArrayList;


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root= inflater.inflate(R.layout.settings_fragment, container, false);
        settingsRecycle = root.findViewById(R.id.settingsRecycle);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        adapter = new SettingsItemAdapter(getActivity());
        settingsRecycle.setLayoutManager(new LinearLayoutManager(root.getContext()));
        settingsRecycle.setAdapter(adapter);
        mViewModel.getSettingMutableLiveData().observe(getActivity(), new Observer<ArrayList<Settings>>() {
            @Override
            public void onChanged(ArrayList<Settings> settings) {
                settingsArrayList = settings;
                adapter.submitList(settings);
            }});

        adapter.setOnIteemClickListener(new SettingsItemAdapter.OnItemClickLisnter() {
            @Override
            public void OnItemClick(Settings setting) {

                for(int i=0; i< settingsArrayList.size();i++){
                    settingsArrayList.get(i).setSelected(false);
                    adapter.notifyItemChanged(i);
                }

                settingsArrayList.get(settingsArrayList.indexOf(setting)).setSelected(true);
                adapter.notifyItemChanged(settingsArrayList.indexOf(setting));
                Navigation.findNavController(getActivity(),R.id.fragment).navigate(setting.getDestination());

            }
        });

        return  root;

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
