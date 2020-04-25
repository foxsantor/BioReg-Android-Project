package com.example.bioregproject.ui.History;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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

import com.example.bioregproject.Adapters.LogsAdapter;
import com.example.bioregproject.Adapters.UserLogsAdapter;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.MainActivityViewModel;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.History;
import com.example.bioregproject.entities.TestSubject;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class LogsAndAnalysisFragment extends Fragment {

    private LogsAndAnalysisViewModel mViewModel;
    private Button newest,oldest;
    private ImageButton clear,csv;
    private TextInputLayout search;
    private RecyclerView logs,users;
    private ConstraintLayout upperConst;
    private UserLogsAdapter userLogsAdapter;
    private TextView textView29;
    private LogsAdapter logsAdapter;
    private PieChart pie;
    private HorizontalBarChart barChart;
    static List<PieEntry> pieEntries ;
    static List<TestSubject> testSubjects ;
    static List<BarEntry> barEntries ;
    static List<TestSubject> testSubjects2 ;
    private MainActivityViewModel mainActivityViewModel;
    static int counterofModules = 1;
    static int numberOfWorkers =0;
    static int numberOfObjects=0;
    static int counterOfActions=1;
    static int totalTasks=0;
    static boolean condition =false;
    static boolean conditionP =false;



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

        mViewModel = ViewModelProviders.of(this).get(LogsAndAnalysisViewModel.class);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        Fragment parent = (Fragment) navHostFragment.getParentFragment();
        upperConst = parent.getView().findViewById(R.id.upperConst);
        upperConst.setVisibility(View.GONE);
        textView29 = view.findViewById(R.id.textView29);
        pie = view.findViewById(R.id.pie);
        barChart = view.findViewById(R.id.barChart);

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


        mViewModel.initAllTeams(BioRegDB.getInstance(getActivity()).historyDAO());
        logs.setLayoutManager(new LinearLayoutManager(getActivity()));
        logsAdapter = new LogsAdapter(getActivity(),getActivity());
        logs.setAdapter(logsAdapter);
        mViewModel.teamAllList.observe(
                getActivity(), new Observer<PagedList<History>>() {
                    @Override
                    public void onChanged(PagedList<History> pagedList) {
                        try {
                            //Toast.makeText(getActivity(), ""+pagedList.get(0).getId(), Toast.LENGTH_SHORT).show();
                            Log.e("Paging ", "PageAll" + pagedList.size());
                            //refresh current list
                            logsAdapter.submitList(pagedList);
                            //adapter.submitList(pagedList);
                        } catch (Exception e) {
                        }
                    }
                });


        oldest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.filterTextAll.setValue("OldestX");
                oldest.setBackgroundColor(getActivity().getResources().getColor(R.color.White));
                oldest.setTextColor(getActivity().getResources().getColor(R.color.blueDeep));
                clearUp("Newest");
                clear.setVisibility(View.VISIBLE);
            }
        });



        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getAllHistorys().observe(getActivity(), new Observer<List<History>>() {
                    @Override
                    public void onChanged(List<History> list) {
                     StaticUse.exportCsvFilesNotification(list,getActivity());
                    }
                });
            }
        });

        newest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.filterTextAll.setValue("NewestX");
                newest.setBackgroundColor(getActivity().getResources().getColor(R.color.White));
                newest.setTextColor(getActivity().getResources().getColor(R.color.blueDeep));
                clearUp("Oldest");
                clear.setVisibility(View.VISIBLE);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.filterTextAll.setValue("");
                clear.setVisibility(View.INVISIBLE);
                clearUp("ALL");
                search.getEditText().setText("");
                search.getEditText().setFocusable(false);
                search.getEditText().setFocusable(true);

            }
        });

        userLogsAdapter.setOnIteemClickListener(new UserLogsAdapter.OnItemClickLisnter() {
            @Override
            public void OnItemClick(Account account) {
                if(account.getFirstName().equals("Administrator"))
                {
                    mViewModel.filterTextAll.setValue("OwnerX"+account.getFirstName()+";"+"");
                }else
                {
                    mViewModel.filterTextAll.setValue("OwnerX"+account.getFirstName()+";"+account.getLastName());

                }
                clear.setVisibility(View.VISIBLE);

            }
        });


        GetTotalNum(mViewModel);
        GetModules(mViewModel);

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
                if (editable.toString().isEmpty()) {
                    clear.setVisibility(View.INVISIBLE);
                } else {
                    clear.setVisibility(View.VISIBLE);

                }
            }  });


    }


    private void clearUp(String key)
    {

        if(key.equals("Newest"))
        {
            newest.setBackgroundColor(getActivity().getResources().getColor(R.color.blueDeep));
            newest.setTextColor(getActivity().getResources().getColor(R.color.White));
        }else if(key.equals("Oldest"))
        {
            oldest.setBackgroundColor(getActivity().getResources().getColor(R.color.blueDeep));
            oldest.setTextColor(getActivity().getResources().getColor(R.color.White));
        }else
        {
            oldest.setBackgroundColor(getActivity().getResources().getColor(R.color.blueDeep));
            oldest.setTextColor(getActivity().getResources().getColor(R.color.White));
            newest.setBackgroundColor(getActivity().getResources().getColor(R.color.blueDeep));
            newest.setTextColor(getActivity().getResources().getColor(R.color.White));
        }


    }


    //pieChat
    private void GetTotalNum(LogsAndAnalysisViewModel mViewModel)
    {

        testSubjects= new ArrayList<>();
        pieEntries = new ArrayList<>();
        mViewModel.getAllHistorys().observe(getActivity(), new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
        if(!histories.isEmpty()){
            int j=0;
                TestSubject testSubject = new TestSubject();
                testSubject.setIdSubject(histories.get(0).getOwnerLinking());
                testSubject.setLastName(histories.get(0).getOwnerLastName());
                testSubject.setNumberOfTasks(counterOfActions);
                testSubject.setName(histories.get(0).getOwnerFirstName());
                testSubjects.add(testSubject);
            //pieEntries.add(new PieEntry(testSubjects.get(j).getNumberOfTasks(),testSubjects.get(j).getName()+" "+testSubjects.get(j).getLastName()));


                for(int i=0;i<histories.size();i++)
                {

                    if(i==histories.size()-1)
                    {
                        return;
                    }
                    //Toast.makeText(getActivity(), ""+testSubjects.get(j).getName()+testSubjects.get(j).getLastName()+testSubjects.get(j).getNumberOfTasks(), Toast.LENGTH_SHORT).show();
                    if(histories.get(i).getOwnerLinking() !=histories.get(i+1).getOwnerLinking() )
                    {



                        for (TestSubject t:testSubjects) {

                            if(t.getIdSubject()== histories.get(i+1).getOwnerLinking())
                            {
                                conditionP=true;

                            }

                        }
                        if(condition==true)
                        {
                            counterOfActions ++;
                            testSubjects.get(j).setNumberOfTasks(counterOfActions);
                            conditionP =false;
                        }else
                        {
                            TestSubject testSubject2 = new TestSubject();
                            testSubject2.setIdSubject(histories.get(i+1).getOwnerLinking());
                            testSubject2.setLastName(histories.get(i+1).getOwnerLastName());
                            testSubject2.setName(histories.get(i+1).getOwnerFirstName());
                            testSubject.setNumberOfTasks(counterOfActions);
                            testSubjects.add(testSubject2);
                            j++;

                        }


                    }else
                    {

                        counterOfActions ++;
                        testSubjects.get(j).setNumberOfTasks(counterOfActions);


                    }


                }



        }else
        {
            return;
        }



        }}
        );

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacksAndMessages(null);
                for (TestSubject t:testSubjects) {
                    pieEntries.add(new PieEntry(t.getNumberOfTasks(),t.getName()+" "+t.getLastName()));
                }
                PieDataSet  dataSet =  new PieDataSet(pieEntries, "Number of Actions per User");
                dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                Description description = new Description();
                description.setText("Number of actions per user");
                pie.setDescription(description);
                PieData data = new PieData(dataSet);
                pie.setData(data);
                pie.animateY(1000);
                pie.invalidate();


            }

        }, 1000);
    }
    //H-BarChart
    private void GetModules(LogsAndAnalysisViewModel mViewModel)
    {
        testSubjects2= new ArrayList<>();
        barEntries = new ArrayList<>();
        mViewModel.getAllHistorys().observe(getActivity(), new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                if(!histories.isEmpty()){
                    int j=0;

                    totalTasks= histories.size();
                    TestSubject testSubject = new TestSubject();
                    testSubject.setModuleNAme(histories.get(0).getCategoryName());
                    testSubject.setNumberOfTasksPerModule(counterofModules);
                    testSubjects2.add(testSubject);
                    //pieEntries.add(new PieEntry(testSubjects.get(j).getNumberOfTasks(),testSubjects.get(j).getName()+" "+testSubjects.get(j).getLastName()));


                    for(int i=0;i<histories.size();i++)
                    {

                        if(i==histories.size()-1)
                        {
                            return;
                        }
                        //Toast.makeText(getActivity(), ""+testSubjects.get(j).getName()+testSubjects.get(j).getLastName()+testSubjects.get(j).getNumberOfTasks(), Toast.LENGTH_SHORT).show();
                        if(!histories.get(i).getCategoryName().equals(histories.get(i+1).getCategoryName()) )
                        {
                            for (TestSubject t:testSubjects2) {

                                if(t.getModuleNAme().equals(histories.get(i+1).getCategoryName()))
                                {
                                    condition=true;

                                }

                            }
                            if(condition==true)
                            {
                                counterofModules ++;
                                testSubjects2.get(j).setNumberOfTasksPerModule(counterofModules);
                                condition =false;
                            }else
                            {
                                TestSubject testSubject2 = new TestSubject();
                                testSubject2.setModuleNAme(histories.get(i+1).getCategoryName());
                                testSubject2.setNumberOfTasksPerModule(counterofModules);
                                testSubjects2.add(testSubject2);
                                j++;

                            }

                        }else
                        {

                            counterofModules ++;
                            testSubjects2.get(j).setNumberOfTasksPerModule(counterofModules);


                        }


                    }



                }else
                {
                    return;
                }



            }}
        );



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                handler.removeCallbacksAndMessages(null);
                int i=testSubjects2.size()-1;
                final ArrayList<String> xAxisLabel = new ArrayList<>();
                for (TestSubject t:testSubjects2) {
                    BarEntry barEntry = new BarEntry(i,t.getNumberOfTasksPerModule());
                    xAxisLabel.add(t.getModuleNAme());
                    barEntries.add(barEntry);
                    i--;

                }



                BarDataSet dataSetx =  new BarDataSet(barEntries, "Number of Actions per Module");
                dataSetx.setColors(ColorTemplate.VORDIPLOM_COLORS);
                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(dataSetx);
                BarData data = new BarData(dataSets);
                data.setValueTextSize(10f);
                barChart.setData(data);
                data.setBarWidth(0.5f);
                Description description = new Description();
                description.setText("Number of actions per module");
                barChart.setDescription(description);
                YAxis left = barChart.getAxisLeft();
                barChart.setFitBars(true);
                left.setDrawLabels(false);
                barChart.setPinchZoom(true);
                barChart.setDrawGridBackground(false);
                barChart.setHighlightFullBarEnabled(false);
                barChart.setHighlightPerDragEnabled(false);
                barChart.getXAxis().setDrawGridLines(false);
                barChart.getAxisLeft().setDrawGridLines(false);
                barChart.animateY(1000);
                barChart.getLegend().setEnabled(true);
                XAxis xAxis = barChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
                xAxis.setDrawGridLines(false);
                xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
                xAxis.setGranularity(1f);
                xAxis.setLabelRotationAngle(-45);
                barChart.getLegend().setWordWrapEnabled(true);





            }

        }, 1000);



    }







}
