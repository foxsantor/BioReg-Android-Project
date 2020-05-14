package com.example.bioregproject.ui.Planification;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
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

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioregproject.Adapters.HistoryAdapter;
import com.example.bioregproject.Adapters.HistorySearchAdapter;
import com.example.bioregproject.Adapters.MyTaskAdapter;
import com.example.bioregproject.Adapters.PersoTaskAdapter;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.ExternalPersoTask;
import com.example.bioregproject.entities.PersoTask;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class taskPlan extends Fragment {

    private TaskPlanViewModel mViewModel;
    private RecyclerView task,searchTask;
    private TextView label,count;
    private ConstraintLayout background,not;
    private Button manage,done,undone,markDone;
    private ImageButton reset,clear;
    private TextInputLayout search;
    private PersoTaskAdapter persoTask;
    private MyTaskAdapter taskAdapter;
    private int j,counterSaver;
    private List<Long> listsOfDeletableItems ;
    private static int behvior = 0,counter;


    public static taskPlan newInstance() {
        return new taskPlan();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_plan_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listsOfDeletableItems = new ArrayList<>();
        mViewModel = ViewModelProviders.of(this).get(TaskPlanViewModel.class);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.mainMenu);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        task = view.findViewById(R.id.task);
        not = view.findViewById(R.id.not);
        label = view.findViewById(R.id.label);
        background = view.findViewById(R.id.frameLayout6);
        manage = view.findViewById(R.id.manage);
        done = view.findViewById(R.id.done);
        undone = view.findViewById(R.id.undone);
        reset = view.findViewById(R.id.reset);
        searchTask = view.findViewById(R.id.searchTask);
        search = view.findViewById(R.id.search);
        reset.setVisibility(View.INVISIBLE);
        markDone = view.findViewById(R.id.markdone);
        clear = view.findViewById(R.id.cancel);
        count = view.findViewById(R.id.indi);
        StaticUse.backgroundAnimator(background);



        manage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(v).navigate(R.id.action_taskPlan_to_taskPlanManage);
                }
            });

        if(StaticUse.loggedInInternalAdmin(getActivity()))
        {
         manage.setVisibility(View.VISIBLE);
         label.setText("Task Planification");
            behvior = 1;
        }else
        {
         manage.setVisibility(View.GONE);
         label.setText("My Tasks");

        }

    if(behvior != 1 ) {


        task.setLayoutManager(new LinearLayoutManager(getActivity()));
        persoTask = new PersoTaskAdapter(getActivity(), getActivity(), view);
        task.setAdapter(persoTask);
        mViewModel.getAssgineTAsks(StaticUse.loadSession(getActivity()).getId()).observe(this, new Observer<List<PersoTask>>() {
            @Override
            public void onChanged(List<PersoTask> tasks) {
                //Toast.makeText(getActivity(), ""+tasks.get(0).getId(), Toast.LENGTH_SHORT).show();
                if (tasks.isEmpty()) {
                    return;
                } else {
                    List<ExternalPersoTask> externalHistories = new ArrayList<>();
                    List<PersoTask> task = new ArrayList<>();
                    ExternalPersoTask object = new ExternalPersoTask();
                    object.setCreation(tasks.get(0).getCreation());
                    object.setList(task);
                    externalHistories.add(0, object);
                    j = 0;
                    for (int i = 0; i < tasks.size(); i++) {
                        if (!(new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(tasks.get(i).getCreation()).equals(new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(externalHistories.get(j).getCreation())))) {
                            List<PersoTask> taskx = new ArrayList<>();
                            ExternalPersoTask objecto = new ExternalPersoTask();
                            objecto.setCreation(tasks.get(i).getCreation());
                            objecto.setList(taskx);
                            j = j + 1;
                            externalHistories.add(j, objecto);
                        }
                        if ((new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(tasks.get(i).getCreation()).equals(new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(externalHistories.get(j).getCreation())))) {
                            PersoTask objects = tasks.get(i);
                            externalHistories.get(j).getList().add(objects);
                        }
                    }
                    persoTask.submitList(externalHistories);
                }
            }
        });

        mViewModel.initAllTeams(BioRegDB.getInstance(getActivity()).persoTaskDAO(),StaticUse.loadSession(getActivity()).getId(),"","");
        final MyTaskAdapter adapter = new MyTaskAdapter(getActivity(), getActivity(),2);
        task.setLayoutManager(new LinearLayoutManager(getContext()));
        task.setAdapter(adapter);
        mViewModel.teamAllList.observe(
                getActivity(), new Observer<PagedList<PersoTask>>() {
                    @Override
                    public void onChanged(PagedList<PersoTask> pagedList) {
                        try {
                            //Toast.makeText(getActivity(), ""+pagedList.get(0).getId(), Toast.LENGTH_SHORT).show();
                            Log.e("Paging ", "PageAll" + pagedList.size());
                            //refresh current list

                            //adapter.submitList(pagedList);
                        } catch (Exception e) {
                        }
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

//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }, 500);
            }
        });

    }else
    {
        markDone.setVisibility(View.INVISIBLE);
        count.setVisibility(View.INVISIBLE);
        clear.setVisibility(View.INVISIBLE);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.filterTextAllAdmin.setValue("");
                done.setTextColor(getActivity().getResources().getColor(R.color.White));
                done.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                undone.setTextColor(getActivity().getResources().getColor(R.color.White));
                undone.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                reset.setVisibility(View.INVISIBLE);
            }
        });
        undone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.filterTextAllAdmin.setValue("Open,Invalid");
                done.setTextColor(getActivity().getResources().getColor(R.color.White));
                done.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                undone.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                undone.setBackgroundColor(getActivity().getResources().getColor(R.color.White));
                reset.setVisibility(View.VISIBLE);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.filterTextAllAdmin.setValue("Done,");
                done.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                done.setBackgroundColor(getActivity().getResources().getColor(R.color.White));
                undone.setTextColor(getActivity().getResources().getColor(R.color.White));
                undone.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                reset.setVisibility(View.VISIBLE);
            }
        });
        //continue ADMIN VIEW
        mViewModel.initAllTeamsAdmin(BioRegDB.getInstance(getActivity()).persoTaskDAO(),StaticUse.loadSession(getActivity()).getId(),"","");
        final MyTaskAdapter adapter = new MyTaskAdapter(getActivity(), getActivity(),2);
        task.setLayoutManager(new LinearLayoutManager(getContext()));
        task.setAdapter(adapter);
        mViewModel.teamAllListAdmin.observe(
                getActivity(), new Observer<PagedList<PersoTask>>() {
                    @Override
                    public void onChanged(PagedList<PersoTask> pagedList) {
                        try {
                            //Toast.makeText(getActivity(), ""+pagedList.get(0).getId(), Toast.LENGTH_SHORT).show();
                            Log.e("Paging ", "PageAll" + pagedList.size());
                            if(pagedList.size() ==0)
                            {
                                not.setVisibility(View.VISIBLE);

                            }else
                            {
                                not.setVisibility(View.GONE);
                                adapter.submitList(pagedList);
                            }
                            //refresh current list

                            //adapter.submitList(pagedList);
                        } catch (Exception e) {
                        }
                    }
                });

        //first time set an empty value to get all data
        mViewModel.filterTextAllAdmin.setValue("");

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter=0;
                Navigation.findNavController(v).navigate(R.id.taskPlan);
                count.setVisibility(View.GONE);
                clear.setVisibility(View.GONE);

            }
        });

        markDone.setText("Delete");
        markDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterSaver=counter;
                for (Long l : listsOfDeletableItems) {
                    PersoTask persoTask = new PersoTask();
                    persoTask.setId(l);
                    mViewModel.delete(persoTask);
                    counter--;
                }
                String text;
                if (counterSaver == 1) {
                text = ""+counterSaver+" item deleted successfully";
                } else{
                    text = ""+counterSaver+" items deleted successfully";
                }
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                count.setVisibility(View.INVISIBLE);
                clear.setVisibility(View.INVISIBLE);
                markDone.setVisibility(View.INVISIBLE);
            }
        });
        adapter.setOnIteemClickListener(new MyTaskAdapter.OnItemClickLisnter() {
            @Override
            public void OnItemClick(PersoTask PersoTask) {
            }

            @Override
            public void Select(View v, long position, long id) {
                if(position == 0){
                    counter = counter -1;
                    listsOfDeletableItems.remove(id);
                    count.setText(""+counter+" Selected");
                    if(counter == 0){
                        count.setVisibility(View.INVISIBLE);
                        clear.setVisibility(View.INVISIBLE);
                        markDone.setVisibility(View.INVISIBLE);
                    }

                }
                else {
                    counter++;
                    listsOfDeletableItems.add(id);
                    markDone.setVisibility(View.VISIBLE);
                    count.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                    count.setText(""+counter+" Selected");}

            }
        });

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

                mViewModel.filterTextAllAdmin.setValue("%" + editable.toString() + "%");

//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }, 500);
            }
        });
    }

    }
}
