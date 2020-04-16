package com.example.bioregproject.ui.History;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bioregproject.Adapters.HistoryAdapter;
import com.example.bioregproject.Adapters.InnerHistoryAdapter;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.ExternalHistory;
import com.example.bioregproject.entities.History;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeviceHistory extends Fragment {

    private DeviceHistoryViewModel mViewModel;
    private RecyclerView history;
    private HistoryAdapter innerHistoryAdapter;

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
        mViewModel = ViewModelProviders.of(this).get(DeviceHistoryViewModel.class);
        history = view.findViewById(R.id.history);
        history.setLayoutManager(new LinearLayoutManager(getActivity()));
        innerHistoryAdapter = new HistoryAdapter(getActivity(),getActivity());
        history.setAdapter(innerHistoryAdapter);
        mViewModel.getAllHistorys().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                //Toast.makeText(getActivity(), ""+histories.get(0).getId(), Toast.LENGTH_SHORT).show();
                List<ExternalHistory> externalHistories = new ArrayList<>();
                List<History> history = new ArrayList<>();
                ExternalHistory object = new ExternalHistory();
                object.setCreation(histories.get(0).getCreation());
                object.setList(history);
                externalHistories.add(0,object);
                for(int i = 0 ; i<histories.size();i++){
                    int j=0;
                        if( !(new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(histories.get(i).getCreation()).equals(new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(externalHistories.get(j).getCreation()))))
                        {
                            List<History> historyx = new ArrayList<>();
                            object.setCreation(histories.get(i).getCreation());
                            //historyx.add(histories.get(i));
                            object.setList(historyx);
                            externalHistories.add(object);
                            //Toast.makeText(getActivity(), "was here", Toast.LENGTH_SHORT).show();
                            j++;
                        }else
                        //if(histories.get(i).getCreation().compareTo(externalHistories.get(j).getCreation()) == 0)
                        {
                            History objects = histories.get(i);
                            externalHistories.get(j).getList().add(objects);
                            //Toast.makeText(getActivity(), "was here 2", Toast.LENGTH_SHORT).show();
                        }
                    }


                        innerHistoryAdapter.submitList(externalHistories);
            }
        });




    }
}
