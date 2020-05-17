package com.example.bioregproject.ui.Cleaning;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bioregproject.Adapters.CategorieCleanAdapter;
import com.example.bioregproject.Adapters.Category2Adapter;
import com.example.bioregproject.Adapters.SurfaceforCategrory2Adapter;
import com.example.bioregproject.Adapters.TacheAdapter;
import com.example.bioregproject.Adapters.UsersAdapter2;
import com.example.bioregproject.Adapters.UsersAdapter2T;
import com.example.bioregproject.MainActivityViewModel;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.CategorieTache;
import com.example.bioregproject.entities.Realtions.CategorywithSurfaces;
import com.example.bioregproject.entities.Surface;
import com.example.bioregproject.entities.Tache;
import com.example.bioregproject.ui.History.DeviceHistoryViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Cleaning extends Fragment {


    private List<Tache> selectedTasks = new ArrayList<>();
    private TacheAdapter adaptertache = new TacheAdapter(getActivity());
    private CategorieCleanAdapter adapter = new CategorieCleanAdapter(getActivity());
    private ListPlanningCleanViewModel mViewModel;
    private ConstraintLayout input1;
    private Button all, effectue, noneffectue;
    private CardView allCategorie;
    private TextView titre;
    private Button buttonaddTask;
    private RecyclerView categorieRecycle, surfaceRecycle;
    private Category2Adapter adapterCategorie;
    private SurfaceforCategrory2Adapter adapterSurface;
    private TextView titleSurface;
    private NestedScrollView surfaces;
    private Button calender;
    private Button addTaskBtn ,b,canelAdd;
    private RecyclerView recyclerView,recyclerViewtache;
    private String idSurface,idCategorie;
    private UsersAdapter2T usersAdapter2;
    private List<Account> newList;
    private RecyclerView users;
    private LifecycleOwner lifecycleOwner;
    private static long idChosen  ;
    private String fullName ;
    private Button cancelU ,UpdateU;
    private ConstraintLayout input2;
    private RecyclerView categorieRecycleU, surfaceRecycleU;
    private RecyclerView usersU;
    private Button calenderU;
    private TextView titleSurfaceU;
    private NestedScrollView surfacesU;
    private Category2Adapter adapterCategorie1;
    private SurfaceforCategrory2Adapter adapterSurface1;










    private MainActivityViewModel mainActivityViewModel;
    private DeviceHistoryViewModel deviceHistoryViewModel;

    public static Cleaning newInstance() {
        return new Cleaning();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.cleaning_fragment, container, false);


        mViewModel = ViewModelProviders.of(this).get(ListPlanningCleanViewModel.class);

        mainActivityViewModel  = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        deviceHistoryViewModel = ViewModelProviders.of(this).get(DeviceHistoryViewModel.class);
        lifecycleOwner = this;



        b = view.findViewById(R.id.done);
        input1 = view.findViewById(R.id.input1);
        allCategorie = view.findViewById(R.id.allCategorie);
        titre = view.findViewById(R.id.textView11);
        all = view.findViewById(R.id.AllStatus);
        effectue = view.findViewById(R.id.effectue);
        noneffectue = view.findViewById(R.id.noneffectue);
        recyclerView = view.findViewById(R.id.recycle1);
        recyclerViewtache = view.findViewById(R.id.recycleTaches);
        surfaces = view.findViewById(R.id.surfaces);
        titleSurface = view.findViewById(R.id.textView15);
        categorieRecycle = view.findViewById(R.id.categoriesRecycle);
        surfaceRecycle = view.findViewById(R.id.surfaceRecycle);
        buttonaddTask = view.findViewById(R.id.buttonaddTask);
        calender = view.findViewById(R.id.calender);
        canelAdd = view.findViewById(R.id.cancelBtnTask);
        addTaskBtn = view.findViewById(R.id.buttonAjout);
        users = view.findViewById(R.id.users);
        input2 = view.findViewById(R.id.input2);
        cancelU = view.findViewById(R.id.cancelU);
        UpdateU = view.findViewById(R.id.buttonUpdate);
        titleSurfaceU = view.findViewById(R.id.textView15U);
        surfaceRecycleU = view.findViewById(R.id.surfaceRecycleU);
        categorieRecycleU = view.findViewById(R.id.categoriesRecycleU);
        usersU = view.findViewById(R.id.usersU);
        calenderU = view.findViewById(R.id.calenderU);
        surfacesU = view.findViewById(R.id.surfacesU);









        //Section categorie affichage

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        mViewModel.getAllCategories().observe(this, new Observer<List<CategorieTache>>() {
            @Override
            public void onChanged(List<CategorieTache> categorieTaches) {
                adapter.setCategories(categorieTaches);

            }
        });

        //Recherche par statuts
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getAllTache().observe(getActivity(), new Observer<List<Tache>>() {
                    @Override
                    public void onChanged(List<Tache> taches) {
                        adaptertache.submitList(taches);
                        adaptertache.notifyDataSetChanged();
                    }
                });
            }
        });


        effectue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getTacheByStatut(true).observe(getActivity(), new Observer<List<Tache>>() {
                    @Override
                    public void onChanged(List<Tache> taches) {
                        adaptertache.submitList(taches);
                        adaptertache.notifyDataSetChanged();

                    }
                });
            }
        });

        noneffectue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getTacheByStatut(false).observe(getActivity(), new Observer<List<Tache>>() {
                    @Override
                    public void onChanged(List<Tache> taches) {
                        adaptertache.submitList(taches);
                        adaptertache.notifyDataSetChanged();



                    }
                });
            }
        });

        //REcherche par categorie
        allCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getAllTache().observe(getActivity(), new Observer<List<Tache>>() {
                    @Override
                    public void onChanged(List<Tache> tacheWithSurfaceAndCategoryTaches) {
                        adaptertache.submitList(tacheWithSurfaceAndCategoryTaches);
                        adaptertache.notifyDataSetChanged();
                        titre.setText("All Categories");



                    }
                });
            }
        });

        adapter.setOnIteemClickListener(new CategorieCleanAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(CategorieTache categorie_tache) {

                mViewModel.getTacheById(categorie_tache.getName()).observe(getActivity(), new Observer<List<Tache>>() {
                    @Override
                    public void onChanged(List<Tache> tacheWithSurfaceAndCategoryTaches) {
                        titre.setText(categorie_tache.getName());
                        adaptertache.submitList(tacheWithSurfaceAndCategoryTaches);
                        adaptertache.notifyDataSetChanged();
                    }
                });
            }

        });



        //Section views taches

        recyclerViewtache.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewtache.setHasFixedSize(true);
        recyclerViewtache.setAdapter(adaptertache);
        mViewModel.getAllTache().observe(this, new Observer<List<Tache>>() {
            @Override
            public void onChanged(List<Tache> taches) {
                adaptertache.submitList(taches);
                adaptertache.notifyDataSetChanged();

            }
        });


        //delete task

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerViewtache, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.delete(adaptertache.getTacheAt(viewHolder.getAdapterPosition()));

                final Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        StaticUse.SaveHistory(lifecycleOwner,deviceHistoryViewModel,getActivity(),"Cleaning and disinfection",
                                "has deleted a Task",
                                "",0,"Task Cleaning");
                        handler.removeCallbacksAndMessages(null);
                    }
                }, 500);




                Toast.makeText(getActivity(), "Task delete", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerViewtache);



        //Update

        adaptertache.setOnItemClickListener(new TacheAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Tache tache) {
                if (selectedTasks.contains(tache))
                   selectedTasks.remove(tache);
                else
                   selectedTasks.add(tache);

            }

            @Override
            public void onItemUpdateClick(Tache tache) {
                input2.setVisibility(View.VISIBLE);

                UpdateU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        try {
                            tache.setDate(simpleDateFormat.parse(calender.getText().toString()));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        tache.setUser(fullName);
                        tache.setIdCategorie(idCategorie);
                        tache.setIdSurface(idSurface);
                        tache.setStatus(false);

                        mViewModel.update(tache);
                        input2.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), " Updated Task Successfully", Toast.LENGTH_SHORT).show();



                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                StaticUse.SaveHistory(getActivity(), deviceHistoryViewModel, getActivity(), "Cleaning Task",
                                        "has updated a cleaning task assigned to"+fullName, null, 0, "Cleaning Task");


                                handler.removeCallbacksAndMessages(null);
                            }
                        }, 500);

                    }
                });

            }

        });


        //Validate status change
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (Tache t : selectedTasks) {
                    t.setStatus(true);
                    mViewModel.update(t);
                    adaptertache.notifyDataSetChanged();
                }

            }
        });




        //affichagechoix ( ajout task)



        adapterCategorie = new Category2Adapter(getActivity());
        adapterSurface = new SurfaceforCategrory2Adapter(getActivity());

        categorieRecycle.setLayoutManager(new GridLayoutManager(getActivity(),3));
        categorieRecycle.setHasFixedSize(true);
        categorieRecycle.setAdapter(adapterCategorie);
        surfaceRecycle.setLayoutManager(new GridLayoutManager(getActivity(),3));
        surfaceRecycle.setHasFixedSize(true);
        surfaceRecycle.setAdapter(adapterSurface);

        mViewModel.getCategories().observe(this, new Observer<List<CategorywithSurfaces>>() {
            @Override
            public void onChanged(List<CategorywithSurfaces> categorywithSurfaces) {
                adapterCategorie.submitList(categorywithSurfaces);
            }
        });

        adapterCategorie.setOnItemClickListener(new Category2Adapter.OnItemClickLisnter() {
            @SuppressLint("ShowToast")
            @Override
            public void onItemClick(CategorywithSurfaces categorywithSurfaces) {
                if (categorywithSurfaces.surfaces.size() > 0) {
                    surfaces.setVisibility(View.VISIBLE);
                    titleSurface.setVisibility(View.VISIBLE);
                    adapterSurface.submitList(categorywithSurfaces.surfaces);
                } else {
                    titleSurface.setVisibility(View.GONE);
                    surfaces.setVisibility(View.GONE);
                }

                mViewModel.getCategorieById(categorywithSurfaces.categorieTache.getIdCat()).observe(getActivity(), new Observer<List<CategorieTache>>() {
                    @Override
                    public void onChanged(List<CategorieTache> categorieTaches) {
                        idCategorie = categorieTaches.get(0).getName();
                    }
                });
            }
        });


        adapterSurface.setOnItemClickListener(new SurfaceforCategrory2Adapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Surface Surface) {
                mViewModel.getSurfaceById(Surface.getIdSurface()).observe(getActivity(), new Observer<List<Surface>>() {
                    @Override
                    public void onChanged(List<Surface> surfaces) {
                        idSurface=surfaces.get(0).getNameSurface();
                    }
                });

            }
        });




        //assgined to :Ajout

        users.setLayoutManager(new GridLayoutManager(getActivity(),2));
        usersAdapter2 = new UsersAdapter2T(getActivity(),getActivity());
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

        usersAdapter2.setOnIteemClickListener(new UsersAdapter2T.OnItemClickLisnter() {
            @Override
            public void OnItemClick(Account account) {
                idChosen = account.getId();
               // Glide.with(getActivity()).load(account.getProfileImage()).into(container);
                fullName = account.getFirstName()+" "+account.getLastName();
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


//assigned to :update
        usersU.setLayoutManager(new GridLayoutManager(getActivity(),3));
        usersU.setAdapter(usersAdapter2);






        //datePiker
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(calender);
            }
        });


        calenderU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(calenderU);
            }
        });

        //add Button

        buttonaddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input1.setVisibility(View.VISIBLE);
            }
        });



        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Tache tache = new Tache();
                try {
                    tache.setDate(simpleDateFormat.parse(calender.getText().toString()));

                } catch (Exception e) {
                e.printStackTrace();
            }

                tache.setCreatedAt(new Date());
                tache.setUser(fullName);
                tache.setIdCategorie(idCategorie);
                tache.setIdSurface(idSurface);
                tache.setStatus(false);

                mViewModel.insert(tache);
                input1.setVisibility(View.GONE);
                Toast.makeText(getActivity(), " Created Task Successfully", Toast.LENGTH_SHORT).show();



                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        StaticUse.SaveHistory(getActivity(), deviceHistoryViewModel, getActivity(), "Cleaning Task",
                                "has created a new cleaning task assigned to"+fullName, null, 0, "Cleaning Task");


                        handler.removeCallbacksAndMessages(null);
                    }
                }, 500);



            }
        });
// cancel ajout
        canelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input1.setVisibility(View.GONE);


            }
        });


//Update affichage




        adapterCategorie1 = new Category2Adapter(getActivity());
        adapterSurface1 = new SurfaceforCategrory2Adapter(getActivity());

        categorieRecycleU.setLayoutManager(new GridLayoutManager(getActivity(),3));
        categorieRecycleU.setHasFixedSize(true);
        categorieRecycleU.setAdapter(adapterCategorie1);
        surfaceRecycleU.setLayoutManager(new GridLayoutManager(getActivity(),3));
        surfaceRecycleU.setHasFixedSize(true);
        surfaceRecycleU.setAdapter(adapterSurface1);

        mViewModel.getCategories().observe(this, new Observer<List<CategorywithSurfaces>>() {
            @Override
            public void onChanged(List<CategorywithSurfaces> categorywithSurfaces) {
                adapterCategorie1.submitList(categorywithSurfaces);
            }
        });

        adapterCategorie1.setOnItemClickListener(new Category2Adapter.OnItemClickLisnter() {
            @SuppressLint("ShowToast")
            @Override
            public void onItemClick(CategorywithSurfaces categorywithSurfaces) {
                if (categorywithSurfaces.surfaces.size() > 0) {
                    surfacesU.setVisibility(View.VISIBLE);
                    titleSurfaceU.setVisibility(View.VISIBLE);
                    adapterSurface1.submitList(categorywithSurfaces.surfaces);
                } else {
                    titleSurfaceU.setVisibility(View.GONE);
                    surfacesU.setVisibility(View.GONE);
                }

                mViewModel.getCategorieById(categorywithSurfaces.categorieTache.getIdCat()).observe(getActivity(), new Observer<List<CategorieTache>>() {
                    @Override
                    public void onChanged(List<CategorieTache> categorieTaches) {
                        idCategorie = categorieTaches.get(0).getName();
                    }
                });
            }
        });


        adapterSurface1.setOnItemClickListener(new SurfaceforCategrory2Adapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Surface Surface) {
                mViewModel.getSurfaceById(Surface.getIdSurface()).observe(getActivity(), new Observer<List<Surface>>() {
                    @Override
                    public void onChanged(List<Surface> surfaces) {
                        idSurface=surfaces.get(0).getNameSurface();
                    }
                });

            }
        });















        cancelU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input2.setVisibility(View.GONE);
            }
        });




        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idChosen =0;
        fullName = "";
        mViewModel = ViewModelProviders.of(this).get(ListPlanningCleanViewModel.class);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }




    private void showDateTimeDialog (Button date_time_in) {
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
                    }
                };

                new TimePickerDialog(getActivity(),timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(getActivity(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }


}
