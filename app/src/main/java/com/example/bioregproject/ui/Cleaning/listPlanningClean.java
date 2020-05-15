package com.example.bioregproject.ui.Cleaning;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.Adapters.CategorieCleanAdapter;
import com.example.bioregproject.Adapters.Category2Adapter;
import com.example.bioregproject.Adapters.SurfaceforCategrory2Adapter;
import com.example.bioregproject.Adapters.TacheAdapter;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.CategorieTache;
import com.example.bioregproject.entities.Realtions.CategorywithSurfaces;
import com.example.bioregproject.entities.Realtions.TacheWithSurfaceAndCategoryTache;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class listPlanningClean extends Fragment {

    private List<TacheWithSurfaceAndCategoryTache> selectedTasks = new ArrayList<>();
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
    private EditText editText4;
    private Button addTaskBtn ,b,canelAdd;
    private RecyclerView recyclerView,recyclerViewtache;


    public static listPlanningClean newInstance() {
        return new listPlanningClean();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ajout_task, container, false);
        mViewModel = ViewModelProviders.of(this).get(ListPlanningCleanViewModel.class);



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
        editText4=view.findViewById(R.id.editTextDate);
        calender = view.findViewById(R.id.calender);
        canelAdd = view.findViewById(R.id.cancelBtnTask);
        addTaskBtn = view.findViewById(R.id.buttonAjout);









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
                mViewModel.getAllTache().observe(getActivity(), new Observer<List<TacheWithSurfaceAndCategoryTache>>() {
                    @Override
                    public void onChanged(List<TacheWithSurfaceAndCategoryTache> taches) {
                        adaptertache.submitList(taches);
                        adaptertache.notifyDataSetChanged();
                    }
                });
            }
        });

        effectue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getTacheByStatut(true).observe(getActivity(), new Observer<List<TacheWithSurfaceAndCategoryTache>>() {
                    @Override
                    public void onChanged(List<TacheWithSurfaceAndCategoryTache> taches) {
                        adaptertache.submitList(taches);
                        adaptertache.notifyDataSetChanged();

                    }
                });
            }
        });

        noneffectue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getTacheByStatut(false).observe(getActivity(), new Observer<List<TacheWithSurfaceAndCategoryTache>>() {
                    @Override
                    public void onChanged(List<TacheWithSurfaceAndCategoryTache> taches) {
                        adaptertache.submitList(taches);
                        adaptertache.notifyDataSetChanged();

                    }
                });
            }
        });

        //Recherche par categorie

        allCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getAllTache().observe(getActivity(), new Observer<List<TacheWithSurfaceAndCategoryTache>>() {
                    @Override
                    public void onChanged(List<TacheWithSurfaceAndCategoryTache> tacheWithSurfaceAndCategoryTaches) {
                        adaptertache.submitList(tacheWithSurfaceAndCategoryTaches);
                        adaptertache.notifyDataSetChanged();

                    }
                });
            }
        });

        adapter.setOnIteemClickListener(new CategorieCleanAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(CategorieTache categorie_tache) {

                mViewModel.getTacheById(categorie_tache.getIdCat()).observe(getActivity(), new Observer<List<TacheWithSurfaceAndCategoryTache>>() {
                    @Override
                    public void onChanged(List<TacheWithSurfaceAndCategoryTache> tacheWithSurfaceAndCategoryTaches) {
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
        mViewModel.getAllTache().observe(this, new Observer<List<TacheWithSurfaceAndCategoryTache>>() {
            @Override
            public void onChanged(List<TacheWithSurfaceAndCategoryTache> taches) {
                adaptertache.submitList(taches);
                adaptertache.notifyDataSetChanged();

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerViewtache, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.delete(adaptertache.getTacheAt(viewHolder.getAdapterPosition()).tache);
                Toast.makeText(getActivity(), "Task delete", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerViewtache);

        adaptertache.setOnItemClickListener(new TacheAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(TacheWithSurfaceAndCategoryTache tache) {
                if (selectedTasks.contains(tache))
                    selectedTasks.remove(tache);
                else
                    selectedTasks.add(tache);

            }

            @Override
            public void onItemUpdateClick(TacheWithSurfaceAndCategoryTache tache) {

            }

            @Override
            public void Select(View v, long position, long id) {

            }

        });

        //status change
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (TacheWithSurfaceAndCategoryTache t : selectedTasks) {
                    t.tache.setStatus(true);
                    mViewModel.update(t.tache);
                    adaptertache.notifyDataSetChanged();
                }

            }
        });


        //section Add Task


        //affichagechoix ( ajout task)




        adapterCategorie = new Category2Adapter(getActivity());
        adapterSurface = new SurfaceforCategrory2Adapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        categorieRecycle.setLayoutManager(layoutManager);
        categorieRecycle.setHasFixedSize(true);
        categorieRecycle.setAdapter(adapterCategorie);
        surfaceRecycle.setLayoutManager(layoutManager2);
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
            }
        });



        //datePiker
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(editText4);
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

            }
        });


// cancel ajout
        canelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_left);
                input1.startAnimation(animFadeIn);
                input1.setVisibility(View.GONE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        input1.setVisibility(View.GONE);
                    }
                }, 550);

            }
        });


        return view;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ListPlanningCleanViewModel.class);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }




    private void showDateTimeDialog (EditText date_time_in) {
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





