package com.example.bioregproject.ui.Planification;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bioregproject.Adapters.HistoryAdapter;
import com.example.bioregproject.Adapters.HistorySearchAdapter;
import com.example.bioregproject.Adapters.MyTaskAdapter;
import com.example.bioregproject.Adapters.PersoTaskAdapter;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.MainActivityViewModel;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.ExternalPersoTask;
import com.example.bioregproject.entities.History;
import com.example.bioregproject.entities.PersoTask;
import com.example.bioregproject.ui.History.DeviceHistoryViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class taskPlan extends Fragment {

    public static TaskPlanViewModel mViewModel;
    private RecyclerView task,searchTask;
    private TextView label,count,indexport,keyvalue;
    private CardView CardSearch;
    private ConstraintLayout background,not,loading;
    private Button manage,done,undone,markDone;
    private static  MyTaskAdapter adapter;
    private ImageButton reset,clear,export;
    private TextInputLayout search;
    private PersoTaskAdapter persoTask;
    private MyTaskAdapter taskAdapter;
    private static LifecycleOwner lifecycleOwner;
    private int j,counterSaver;
    private List<Long> listsOfDeletableItems ;
    private static int behvior = 0,counter,number;
    private static Activity activity;
    private static MainActivityViewModel mainActivityViewModel;
    private static  DeviceHistoryViewModel deviceHistoryViewModel;
    private static final int GALLERY_REQUEST = 1;
    private static final int  MY_CAMERA_PERMISSION_CODE = 2;
    private static final int CAMERA_REQUEST=3;
    private static Button cancel,attachs;
    private static TextInputLayout comments;
    private static TextView indicatorT,imageT,imgIndi;
    private static ImageView contaienr,balster;
    private static Context context;
    private static ImageButton cancelD,gallary,photo;
    private static Bitmap imageBytes ;


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
        lifecycleOwner = this;
        activity = getActivity();

        mViewModel = ViewModelProviders.of(this).get(TaskPlanViewModel.class);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        deviceHistoryViewModel = ViewModelProviders.of(this).get(DeviceHistoryViewModel.class);
        //mViewModel.deleteAll();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.mainMenu);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        export= view.findViewById(R.id.export);
        task = view.findViewById(R.id.task);
        not = view.findViewById(R.id.not);
        loading = view.findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);
        indexport = view.findViewById(R.id.indexport);
        label = view.findViewById(R.id.label);
        CardSearch = view.findViewById(R.id.CardSearch);
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
        keyvalue = view.findViewById(R.id.keyvalue);
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
            behvior = 2;

        }

    if(behvior != 1 ) {
        export.setVisibility(View.INVISIBLE);
        indexport.setVisibility(View.INVISIBLE);
        count.setVisibility(View.INVISIBLE);
        clear.setVisibility(View.INVISIBLE);
        markDone.setVisibility(View.INVISIBLE);
        task.setLayoutManager(new LinearLayoutManager(getActivity()));
        CardSearch.setVisibility(View.GONE);
        persoTask = new PersoTaskAdapter(getActivity(), getActivity(), view,this);
        task.setAdapter(persoTask);
        mViewModel.getAssgineTAsks(StaticUse.loadSession(getActivity()).getId()).observe(this, new Observer<List<PersoTask>>() {
            @Override
            public void onChanged(List<PersoTask> tasks) {
                //Toast.makeText(getActivity(), ""+tasks.get(0).getId(), Toast.LENGTH_SHORT).show();
                if (tasks.isEmpty()) {
                    not.setVisibility(View.VISIBLE);
                    return;
                } else {
                    Collections.sort(tasks, new Comparator<PersoTask>() {
                        @Override
                        public int compare(PersoTask o1, PersoTask o2) {
                            return o1.getDue().compareTo(o2.getDue());
                        }
                    });
                    not.setVisibility(View.GONE);
                    List<ExternalPersoTask> externalHistories = new ArrayList<>();
                    List<PersoTask> task = new ArrayList<>();
                    ExternalPersoTask object = new ExternalPersoTask();
                    object.setCreation(tasks.get(0).getDue());
                    object.setList(task);
                    externalHistories.add(0, object);
                    j = 0;
                    for (int i = 0; i < tasks.size(); i++) {
                        if (!(new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(tasks.get(i).getDue()).equals(new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(externalHistories.get(j).getCreation())))) {
                            List<PersoTask> taskx = new ArrayList<>();
                            ExternalPersoTask objecto = new ExternalPersoTask();
                            objecto.setCreation(tasks.get(i).getDue());
                            objecto.setList(taskx);
                            j = j + 1;
                            externalHistories.add(j, objecto);
                        }
                        if ((new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(tasks.get(i).getDue()).equals(new SimpleDateFormat("EEEE,MMMMM d, yyyy").format(externalHistories.get(j).getCreation())))) {
                            PersoTask objects = tasks.get(i);
                            externalHistories.get(j).getList().add(objects);
                        }
                    }


                    persoTask.submitList(externalHistories);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading.setVisibility(View.GONE);
                        }
                    }, 1000);
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.filterTextAll.setValue("");
                done.setTextColor(getActivity().getResources().getColor(R.color.White));
                done.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                undone.setTextColor(getActivity().getResources().getColor(R.color.White));
                undone.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                reset.setVisibility(View.INVISIBLE);
                task.setVisibility(View.VISIBLE);
                CardSearch.setVisibility(View.GONE);
            }
        });
        undone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setVisibility(View.GONE);
                CardSearch.setVisibility(View.VISIBLE);
                mViewModel.filterTextAll.setValue("Open,Invalid");
                done.setTextColor(getActivity().getResources().getColor(R.color.White));
                done.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                undone.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                undone.setBackgroundColor(getActivity().getResources().getColor(R.color.White));
                reset.setVisibility(View.VISIBLE);
                keyvalue.setText("Found "+number+" search results ");
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setVisibility(View.GONE);
                CardSearch.setVisibility(View.VISIBLE);
                mViewModel.filterTextAll.setValue("Done,");
                done.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                done.setBackgroundColor(getActivity().getResources().getColor(R.color.White));
                undone.setTextColor(getActivity().getResources().getColor(R.color.White));
                undone.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                reset.setVisibility(View.VISIBLE);
                keyvalue.setText("Found "+number+" search results ");
            }
        });

        mViewModel.initAllTeams(BioRegDB.getInstance(getActivity()).persoTaskDAO(),StaticUse.loadSession(getActivity()).getId(),"","");
        adapter = new MyTaskAdapter(getActivity(), getActivity(),2,this);
        searchTask.setLayoutManager(new LinearLayoutManager(getContext()));
        searchTask.setAdapter(adapter);
        mViewModel.teamAllList.observe(
                getActivity(), new Observer<PagedList<PersoTask>>() {
                    @Override
                    public void onChanged(PagedList<PersoTask> pagedList) {
                        try {
                            if(pagedList.size()==0 && task.getVisibility()== View.GONE)
                            {
                                not.setVisibility(View.VISIBLE);
                            }else
                            {
                                adapter.submitList(pagedList);
                                number = pagedList.size();
                                not.setVisibility(View.GONE);
                                if(number == 0)
                                {
                                    not.setVisibility(View.VISIBLE);
                                    CardSearch.setVisibility(View.GONE);
                                }else {

                                    if(task.getVisibility() == View.GONE)
                                        CardSearch.setVisibility(View.VISIBLE);
                                }
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loading.setVisibility(View.GONE);
                                    }
                                }, 500);
                                //Toast.makeText(getActivity(), ""+pagedList.get(0).getId(), Toast.LENGTH_SHORT).show();
                                Log.e("Paging ", "PageAll" + pagedList.size());
                                //refresh current list
                            }
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
                CardSearch.setVisibility(View.VISIBLE);
                task.setVisibility(View.GONE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        keyvalue.setText("Found "+number+" search results for"+" "+"'"+editable.toString()+"'");
                    }
                }, 500);


                if(editable.toString().isEmpty())
                {
                    task.setVisibility(View.VISIBLE);
                    CardSearch.setVisibility(View.GONE);
                }
            }
        });

    }else
    {
        export.setVisibility(View.VISIBLE);
        indexport.setVisibility(View.VISIBLE);
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getAllPersoTaskAdmin().observe(lifecycleOwner, new Observer<List<PersoTask>>() {
                    @Override
                    public void onChanged(List<PersoTask> list) {
                        if(list.isEmpty())
                        {
                            Toast.makeText(getActivity(), "Nothing to Export", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        StaticUse.exportCsvFilesTasks(list,getActivity());
                    }
                });
            }
        });
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
        final MyTaskAdapter adapter = new MyTaskAdapter(getActivity(), getActivity(),2,this);
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
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loading.setVisibility(View.GONE);
                                }
                            }, 500);

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
                    StaticUse.SaveNotification(lifecycleOwner,mainActivityViewModel,activity,"Task Planification Module"
                            ,"has deleted a Task of ID "+persoTask.getId()+" from "
                            ,"Task Management",null,null,R.drawable.ic_add_circle_blue_24dp);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            StaticUse.SaveHistory(lifecycleOwner,deviceHistoryViewModel,activity,"Task Planification Module",
                                    "has deleted a Task",
                                    "",persoTask.getId(),"Task Management");
                            handler.removeCallbacksAndMessages(null);
                        }
                    }, 500);
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


    public static  void ProfileImageGetter(ImageView imageView,long id)
    {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.admin_user)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
        mainActivityViewModel.getAccount(id).observe(lifecycleOwner, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                Glide.with(activity).load(accounts.get(0).getProfileImage()).apply(options).into(imageView);
                mainActivityViewModel.getAccount(id).removeObservers(lifecycleOwner);
            }
        });


    }
    public static void UpdateTask(List<Long> id)
    {
        for (Long l:id) {

        mViewModel.loadPersoTaskOne(l).observe(lifecycleOwner, new Observer<List<PersoTask>>() {
            @Override
            public void onChanged(List<PersoTask> persoTasks) {
                PersoTask updatable = persoTasks.get(0);
                updatable.setState("Done");
                updatable.setValidationDate(new Date());
                mViewModel.update(updatable);
                mViewModel.loadPersoTaskOne(l).removeObservers(lifecycleOwner);
            }

        });
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    StaticUse.SaveNotification(lifecycleOwner,mainActivityViewModel,activity,"Task Planification Module"
                            ,"has Completed a Task of ID"+l+" from "
                            ,"Task Management",null,null,R.drawable.ic_add_circle_blue_24dp);

                    StaticUse.SaveHistory(lifecycleOwner,deviceHistoryViewModel,activity,"Task Planification Module",
                            "has Completed a Task of ID ",
                            "",l,"Task Management");
                    handler.removeCallbacksAndMessages(null);
                }
            }, 500);
    }
    }


    public static void DeleteTaskOne(long id)
    {

        mViewModel.loadPersoTaskOne(id).observe(lifecycleOwner, new Observer<List<PersoTask>>() {
            @Override
            public void onChanged(List<PersoTask> persoTasks) {
                PersoTask updatable = persoTasks.get(0);
                updatable.setState("Done");
                updatable.setValidationDate(new Date());
                mViewModel.update(updatable);
                mViewModel.loadPersoTaskOne(id).removeObservers(lifecycleOwner);
            }

        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StaticUse.SaveNotification(lifecycleOwner,mainActivityViewModel,activity,"Task Planification Module"
                        ,"has Completed a Task of ID"+id+" from "
                        ,"Task Management",null,null,R.drawable.ic_add_circle_blue_24dp);

                StaticUse.SaveHistory(lifecycleOwner,deviceHistoryViewModel,activity,"Task Planification Module",
                        "has Completed a Task of ID ",
                        "",id,"Task Management");
                handler.removeCallbacksAndMessages(null);
            }
        }, 500);

    }

    public static void UpdateTaskOne(long id,String status,String nature)
    {

            mViewModel.loadPersoTaskOne(id).observe(lifecycleOwner, new Observer<List<PersoTask>>() {
                @Override
                public void onChanged(List<PersoTask> persoTasks) {
                    PersoTask updatable = persoTasks.get(0);
                    updatable.setState(status);
                    updatable.setValidationDate(new Date());
                    mViewModel.update(updatable);
                    mViewModel.loadPersoTaskOne(id).removeObservers(lifecycleOwner);
                }

            });
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    StaticUse.SaveNotification(lifecycleOwner,mainActivityViewModel,activity,"Task Planification Module"
                            ,"has "+nature+" a Task of ID"+id+" from "
                            ,"Task Management",null,null,R.drawable.ic_add_circle_blue_24dp);

                    StaticUse.SaveHistory(lifecycleOwner,deviceHistoryViewModel,activity,"Task Planification Module",
                            "has "+nature+" a Task of ID ",
                            "",id,"Task Management");
                    handler.removeCallbacksAndMessages(null);
                }
            }, 500);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.progress_animation)
                                .error(R.drawable.ic_warning_black_24dp)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .priority(Priority.HIGH)
                                .dontAnimate()
                                .dontTransform();
                        //imageContainer.setVisibility(View.VISIBLE);
                        //imageHinter.setVisibility(View.GONE);
                        cancelD.setVisibility(View.VISIBLE);
                        Glide.with(getActivity()).asBitmap().load(bitmap).apply(options).into(contaienr);
                        //container.setImageBitmap(bitmap);
                        // Glide.with(getActivity()).asBitmap().load(bitmap).into(ImageView10);
                        balster.setImageBitmap(bitmap);
                        imgIndi.setVisibility(View.GONE);
                        imageBytes = bitmap;


                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //imageContainer.setVisibility(View.VISIBLE);
            //imageHinter.setVisibility(View.GONE);
            //container.setImageBitmap(photo);
            cancelD.setVisibility(View.VISIBLE);
            imgIndi.setVisibility(View.GONE);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_warning_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();
            Glide.with(getActivity()).asBitmap().load(photo).apply(options).into(contaienr);
            //Glide.with(getActivity()).asBitmap().load(photo).into(ImageView10);
            balster.setImageBitmap(photo);
            imageBytes = photo;
        }

    }


    public static void DilaogueAttach(PersoTask currentItem, taskPlan activity)
    {
        final AlertDialog.Builder alerts = new AlertDialog.Builder(activity.getActivity());
        LayoutInflater layoutInflaters =  (LayoutInflater) activity.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogueViews =layoutInflaters.inflate(R.layout.attach_dialogue,null);
        alerts.setView(dialogueViews);
        cancel= dialogueViews.findViewById(R.id.cancel);
        balster =dialogueViews.findViewById(R.id.blaster);
        contaienr = dialogueViews.findViewById(R.id.container);
        attachs= dialogueViews.findViewById(R.id.atttach);
        comments= dialogueViews.findViewById(R.id.comments);
        imgIndi= dialogueViews.findViewById(R.id.textView41);
        indicatorT= dialogueViews.findViewById(R.id.indicator);
        imageT= dialogueViews.findViewById(R.id.Image);
        cancelD= dialogueViews.findViewById(R.id.cancelD);
        gallary= dialogueViews.findViewById(R.id.gallary);
        photo= dialogueViews.findViewById(R.id.photo);
        alerts.setTitle("Join Attachments to Task N° "+currentItem.getId());
        if(currentItem.getImageBase64()==null)
        {
            imgIndi.setVisibility(View.VISIBLE);
            imageT.setText("Attach an Image :");
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_warning_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();
            Glide.with(activity).load(R.drawable.black_back).apply(options).into(contaienr);
        }else
        {
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_warning_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();
            imgIndi.setVisibility(View.GONE);
            imageT.setText("Change the Image :");
            Glide.with(activity).load(currentItem.getImageBase64()).apply(options).into(contaienr);
        }
        if(currentItem.getComment()==null)
        {
            indicatorT.setText("Attach a Comment :");
        }else
        {
            indicatorT.setText("Change a Comment :");
            comments.getEditText().setText(currentItem.getComment());
        }

        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                activity.startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(activity.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {

                    activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }

            }
        });
        cancelD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgIndi.setVisibility(View.VISIBLE);
                imageT.setText("Attach an Image :");
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.ic_warning_black_24dp)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH)
                        .dontAnimate()
                        .dontTransform();
                Glide.with(activity).load(R.drawable.black_back).apply(options).into(contaienr);
            }
        });


        final AlertDialog alertix =alerts.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertix.dismiss();
            }
        });

     attachs.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             mViewModel.loadPersoTaskOne(currentItem.getId()).observe(lifecycleOwner, new Observer<List<PersoTask>>() {
                 @Override
                 public void onChanged(List<PersoTask> persoTasks) {

                     if(imageBytes==null &&(comments.getEditText().getText().toString() == null || comments.getEditText().getText().toString().isEmpty() || comments.getEditText().getText().toString().equals("")))
                     {
                         Toast.makeText(activity.getActivity(), "Nothing to Join", Toast.LENGTH_SHORT).show();
                 }else
                     {
                         String imageBase64,Comment;
                         if(imageBytes ==null)
                         {

                         }else
                         {
                             imageBase64= StaticUse.transformerImageBase64(balster);
                             persoTasks.get(0).setImageBase64(imageBase64);
                         }

                         if(comments.getEditText().getText().toString() == null || comments.getEditText().getText().toString().isEmpty() || comments.getEditText().getText().equals(""))
                         {

                         }else
                         {
                             Comment = comments.getEditText().getText().toString();
                             persoTasks.get(0).setComment(Comment);
                         }
                         mViewModel.update(persoTasks.get(0));
                         mViewModel.loadPersoTaskOne(currentItem.getId()).removeObservers(lifecycleOwner);
                         alertix.dismiss();
                         Toast.makeText(activity.getActivity(), "Task's Attachment(s) has been added", Toast.LENGTH_SHORT).show();
                         Navigation.findNavController(activity.getActivity(),R.id.nav_host_fragment).navigate(R.id.taskPlan);
                     }

                 }
             });

         }
     });
    }

}

