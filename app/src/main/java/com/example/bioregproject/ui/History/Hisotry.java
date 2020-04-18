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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bioregproject.Adapters.SettingsItemAdapter;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Settings;
import com.example.bioregproject.ui.Settings.SettingsViewModel;

import java.util.ArrayList;

public class Hisotry extends Fragment {

    private HisotryViewModel mViewModel;
    private RecyclerView settingsRecycle;
    private SettingsItemAdapter adapter;
    private ArrayList<Settings> settingsArrayList;
    private Button cancel,delete;
    private ImageButton clear;
    private ConstraintLayout bar;
    private TextView slected;

    public static Hisotry newInstance() {
        return new Hisotry();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.hisotry_fragment, container, false);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.menu);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        mViewModel = ViewModelProviders.of(this).get(HisotryViewModel.class);
        cancel = root.findViewById(R.id.Cancel);
        delete = root.findViewById(R.id.Delete);
        clear = root.findViewById(R.id.clear);
        bar = root.findViewById(R.id.upperConst);
        slected = root.findViewById(R.id.selected);
        settingsRecycle = root.findViewById(R.id.settingsRecycle);
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
                Navigation.findNavController(getActivity(),R.id.fragmentHistory).navigate(setting.getDestination());

            }
        });

        return  root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}
