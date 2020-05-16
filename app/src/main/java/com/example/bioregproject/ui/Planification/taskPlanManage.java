package com.example.bioregproject.ui.Planification;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bioregproject.Adapters.SpinnerCatAdapter;
import com.example.bioregproject.Adapters.UsersAdapter2;
import com.example.bioregproject.MainActivityViewModel;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.Category;
import com.example.bioregproject.entities.PersoTask;
import com.example.bioregproject.entities.Products;
import com.example.bioregproject.ui.History.DeviceHistoryViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class taskPlanManage extends Fragment {

    private TaskPlanViewModel mViewModel;
    private MainActivityViewModel mainActivityViewModel;
    private DeviceHistoryViewModel deviceHistoryViewModel;
    private ConstraintLayout background;
    private RecyclerView users;
    private ImageButton calender;
    private ImageView container;
    private TextInputLayout stats,due,priority,title,description;
    private Button back,save;
    private Spinner prio,status;
    private TextView indi,textView31;
    private UsersAdapter2 usersAdapter2;
    private List<Account> newList;
    private LifecycleOwner lifecycleOwner;
    private Boolean fired;
    private String prioS,StatS,fullName;
    private static long idChosen  ;
    private static int mode =0;

    public static taskPlanManage newInstance() {
        return new taskPlanManage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_plan_manage_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idChosen =0;
        fullName = "";
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TaskPlanViewModel.class);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        deviceHistoryViewModel = ViewModelProviders.of(this).get(DeviceHistoryViewModel.class);
        lifecycleOwner = this;
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_taskPlanManage_to_taskPlan);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        Bundle bundle = getArguments();
        if(bundle == null)
        {
            mode =0;
        }else
        {
            mode =1;
        }
        users = view.findViewById(R.id.users);
        calender = view.findViewById(R.id.calender);
        background = view.findViewById(R.id.backround);
        stats = view.findViewById(R.id.stats);
        due = view.findViewById(R.id.due);
        priority = view.findViewById(R.id.priority);
        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.descrption);
        back = view.findViewById(R.id.back);
        save = view.findViewById(R.id.save);
        indi = view.findViewById(R.id.indi);
        prio = view.findViewById(R.id.spinner);
        container = view.findViewById(R.id.imageView12);
        status= view.findViewById(R.id.spinnerState);
        textView31 = view.findViewById(R.id.textView31);
        SpinnerLoader(status,prio);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        StaticUse.backgroundAnimator(background);

        if(mode == 1)
        {
            indi.setText("Update Task N°"+bundle.getLong("id"));
            title.getEditText().setText(bundle.getString("title"));
            description.getEditText().setText(bundle.getString("description",""));
            idChosen = bundle.getLong("assgineId");
            save.setText("Update");
            due.getEditText().setText( bundle.getString("due"));
            prioS = bundle.getString("priority");
            StatS =  bundle.getString("status");
            prio.setSelection(getIndex(prio,prioS));
            status.setSelection(getIndex(status,StatS));
            fullName =bundle.getString("AssgineName");
            textView31.setText("Assigned To: "+bundle.getString("AssgineName"));
        }
        users.setLayoutManager(new GridLayoutManager(getActivity(),3));
        usersAdapter2 = new UsersAdapter2(getActivity(),getActivity());
        users.setAdapter(usersAdapter2);
        mainActivityViewModel.getAllAccounts().observe(lifecycleOwner, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                newList = new ArrayList<>();
                for (Account a:accounts) {
                    if(!a.getFirstName().equals("Administrator"))
                    {
                        a.setSelected(0);
                        newList.add(a);

                    }
                }
                usersAdapter2.submitList(newList);
            }
        });

        usersAdapter2.setOnIteemClickListener(new UsersAdapter2.OnItemClickLisnter() {
            @Override
            public void OnItemClick(Account account) {
                idChosen = account.getId();
                Glide.with(getActivity()).load(account.getProfileImage()).into(container);
                fullName = account.getFirstName()+" "+account.getLastName();
                textView31.setText("Assigned To: "+fullName);
                mainActivityViewModel.getAllAccounts().observe(lifecycleOwner, new Observer<List<Account>>() {
                    @Override
                    public void onChanged(List<Account> accounts) {
                        newList = new ArrayList<>();
                        for (Account a:accounts) {
                            if(!a.getFirstName().equals("Administrator") )
                            {
                                if(account.getId() == a.getId())
                                {
                                    account.setSelected(1);
                                    newList.add(account);
                                }else
                                {
                                    a.setSelected(0);
                                    newList.add(a);
                                }
                            }
                        }
                        usersAdapter2.submitList(newList);
                        usersAdapter2.notifyDataSetChanged();
                    }
                });

            }
        });

        prio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                {
                    prioS = (String) parent.getItemAtPosition(position);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                {
                    StatS = (String) parent.getItemAtPosition(position);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        due.setEnabled(false);
        due.setClickable(false);
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(due.getEditText());
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!StaticUse.validateEmpty(title,"Title") | !StaticUse.validateEmpty(due,"Due Date")
                        |!StaticUse.validateSpinner(prioS,priority,"Priority") | !StaticUse.validateSpinner(StatS,stats,"Status")
                ){return;}
                else if(idChosen==0 || fullName.equals("")){
                    Toast.makeText(getActivity(), "Please assign a User", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if(mode ==0)
                    {
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    PersoTask persoTask = new PersoTask();
                    persoTask.setValidationDate(new Date());
                    persoTask.setAssginedId(idChosen);
                    persoTask.setPiority(prioS);
                    persoTask.setCreation(new Date());
                    persoTask.setName(title.getEditText().getText().toString());
                    if(description.getEditText().getText().toString()!=null)
                    persoTask.setDescription(description.getEditText().getText().toString());
                    else
                        persoTask.setDescription("");
                    persoTask.setAssignedName(fullName);
                    persoTask.setOwnerName(StaticUse.loadSession(getActivity()).getFirstName());
                    try {
                        persoTask.setDue(simpleDateFormat.parse(due.getEditText().getText().toString()));
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    persoTask.setState(StatS);

                    mViewModel.insert(persoTask);
                    Toast.makeText(getActivity(), "Task Added Successfully", Toast.LENGTH_SHORT).show();

                    StaticUse.SaveNotification(getActivity(),mainActivityViewModel,getActivity(),"Task Planification Module"
                            ,"has added a new Task by the name of "+title.getEditText().getText().toString()+" assigned to "+fullName+" from "
                            ,"Task Management",null,null,R.drawable.ic_add_circle_blue_24dp);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mViewModel.getAssgineTAskSpec(persoTask.getAssginedId(),persoTask.getName()).observe(lifecycleOwner, new Observer<List<PersoTask>>() {
                                @Override
                                public void onChanged(List<PersoTask> persoTasks) {
                                    StaticUse.SaveHistory(getActivity(),deviceHistoryViewModel,getActivity(),"Task Planification Module",
                                            "has added a new Task  by the name of ",
                                            title.getEditText().getText().toString()+" assigned to "+fullName,persoTasks.get(0).getId(),"Task Management");
                                                return;
                                }
                            });
                            handler.removeCallbacksAndMessages(null);
                        }
                    }, 500);
                    getActivity().onBackPressed();
                    }else
                    {

                        mViewModel.loadPersoTaskOne(bundle.getLong("id")).observe(lifecycleOwner, new Observer<List<PersoTask>>() {
                            @Override
                            public void onChanged(List<PersoTask> persoTasks) {

                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                PersoTask persoTask = persoTasks.get(0);
                                persoTask.setAssginedId(idChosen);
                                persoTask.setPiority(prioS);
                                persoTask.setName(title.getEditText().getText().toString());
                                if(description.getEditText().getText().toString()!=null)
                                    persoTask.setDescription(description.getEditText().getText().toString());
                                else
                                    persoTask.setDescription("");
                                persoTask.setAssignedName(fullName);
                                persoTask.setOwnerName(StaticUse.loadSession(getActivity()).getFirstName());
                                try {
                                    persoTask.setDue(simpleDateFormat.parse(due.getEditText().getText().toString()));
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                persoTask.setState(StatS);
                                mViewModel.update(persoTask);
                                StaticUse.SaveNotification(getActivity(),mainActivityViewModel,getActivity(),"Task Planification Module"
                                        ,"has Updated a Task by the name of "+title.getEditText().getText().toString()+" assigned to "+fullName+" from "
                                        ,"Task Management",null,null,R.drawable.ic_add_circle_blue_24dp);
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mViewModel.getAssgineTAskSpec(persoTask.getAssginedId(),persoTask.getName()).observe(lifecycleOwner, new Observer<List<PersoTask>>() {
                                            @Override
                                            public void onChanged(List<PersoTask> persoTasks) {
                                                StaticUse.SaveHistory(getActivity(),deviceHistoryViewModel,getActivity(),"Task Planification Module",
                                                        "has Updated a Task  by the name of ",
                                                        title.getEditText().getText().toString()+" assigned to "+fullName,persoTasks.get(0).getId(),"Task Management");
                                                return;
                                            }
                                        });
                                        handler.removeCallbacksAndMessages(null);
                                    }
                                }, 500);
                                mViewModel.loadPersoTaskOne(bundle.getLong("id")).removeObservers(lifecycleOwner);
                                getActivity().onBackPressed();
                            }
                        });


                    }
                }
            }
        });










    }

    private void SpinnerLoader(final Spinner spinnerStat,final Spinner spinnerPrio)
    {
        final ArrayList<String> spinnerArrayStat =  new ArrayList<>();
        final ArrayList<String> spinnerArrayPrio =  new ArrayList<>();
        spinnerArrayStat.add("Choose a Status *");
        spinnerArrayStat.add("Open");
        spinnerArrayStat.add("Invalid");
        spinnerArrayStat.add("Done");
        spinnerArrayPrio.add("Choose a Priority *");
        spinnerArrayPrio.add("Critic");
        spinnerArrayPrio.add("High");
        spinnerArrayPrio.add("Normal");
        spinnerArrayPrio.add("Low");
        SpinnerCatAdapter adapter = new SpinnerCatAdapter(getActivity(),spinnerArrayStat);
        SpinnerCatAdapter adapter2 = new SpinnerCatAdapter(getActivity(),spinnerArrayPrio);
        spinnerStat.setAdapter(adapter);
        spinnerPrio.setAdapter(adapter2);
    }

    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar=Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Log.i("PEW PEW", "Double fire check");

                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                        fired = true;
                    }
                };

                new TimePickerDialog(getActivity(),timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

       DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();

    }
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }


}
