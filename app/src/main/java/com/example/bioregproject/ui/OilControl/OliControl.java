package com.example.bioregproject.ui.OilControl;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bioregproject.Adapters.OilAdapter;
import com.example.bioregproject.Adapters.PostAdapter;
import com.example.bioregproject.Adapters.SpinnerCatAdapter;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Oil;
import com.example.bioregproject.entities.Post;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OliControl extends Fragment {

    private OliControlViewModel mViewModel;
    private Spinner spinner;
    private TextInputLayout textInputLayout ,textInputLayout4;
    private Button save,cancel,addOil;
    private ImageButton calender;
    private EditText date;
    private RecyclerView post1,OilRecycleView;
    private PostAdapter postAdapter;
    private String valeur;
    private ConstraintLayout ajout,affichage;
    private Button all,filtrageB,changenement,miseaniveau;
    private OilAdapter oilAdapter;


    public static OliControl newInstance() {
        return new OliControl();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.oli_control_fragment, container, false);

        mViewModel = ViewModelProviders.of(this).get(OliControlViewModel.class);


        spinner = view.findViewById(R.id.spinner);
        textInputLayout=view.findViewById(R.id.textInputLayout);
        textInputLayout4 = view.findViewById(R.id.textInputLayout4);
        save = view.findViewById(R.id.save);
        cancel=view.findViewById(R.id.cancel);
        calender = view.findViewById(R.id.calender);
        date = view.findViewById(R.id.dateedit);
        post1 = view.findViewById(R.id.post);
        ajout = view.findViewById(R.id.ajout);
        affichage=view.findViewById(R.id.affichage);
        addOil =view.findViewById(R.id.addOil);
        all =view.findViewById(R.id.All);
        miseaniveau =view.findViewById(R.id.miseaniveau);
        changenement =view.findViewById(R.id.Changement);
        filtrageB =view.findViewById(R.id.filtrageB);
        OilRecycleView =view.findViewById(R.id.OilRecycleView);






// affichage ajout

        addOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout.setVisibility(View.VISIBLE);
                affichage.setVisibility(View.INVISIBLE);
            }
        });

        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(date);
            }
        });



        SpinnerLoader(spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position>0)
                {
                    valeur = (String) parent.getItemAtPosition(position);
                    if (valeur.equals("mise à niveau")||valeur.equals("filtrage")) {
                        textInputLayout4.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//afichage Post
        postAdapter = new PostAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        post1.setLayoutManager(layoutManager);
        post1.setHasFixedSize(true);
        post1.setAdapter(postAdapter);
        //mViewModel.insert(new  Post("friteuse",new Date()));
        mViewModel.getAllPost().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                postAdapter.submitList(posts);
                postAdapter.notifyDataSetChanged();
            }
        });



        // ajout
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout.setVisibility(View.GONE);
                affichage.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Oil oil = new Oil();
                    try {
                        oil.setDateUtilisation(simpleDateFormat.parse(date.getText().toString()));
                        oil.setCreationDate(new Date());
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    oil.setPost("friteuse");
                    oil.setAction(valeur);
                    if (valeur.equals("changement")){
                        oil.setFiltrage(0);

                    }else{
                        //oil.setFiltrage(textInputLayout4.getEditText().getText());
                        oil.setFiltrage(1);


                    }

                        mViewModel.insert(oil);
                        Toast.makeText(getActivity(), "oil Added Successfully", Toast.LENGTH_SHORT).show();



                
            }
        });


// affichage Oil
//mViewModel.insert(new Oil("nejma",1,new Date(),new Date(),"changement","friteuse"));

        oilAdapter = new OilAdapter(getActivity());
        OilRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        OilRecycleView.setHasFixedSize(true);
        OilRecycleView.setAdapter(oilAdapter);
        mViewModel.getAllOil().observe(this, new Observer<List<Oil>>() {
            @Override
            public void onChanged(List<Oil> oils) {
                oilAdapter.submitList(oils);
                oilAdapter.notifyDataSetChanged();

            }
        });
        //Recherche
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getAllOil().observe(getActivity(), new Observer<List<Oil>>() {
                    @Override
                    public void onChanged(List<Oil> oils) {
                        oilAdapter.submitList(oils);
                        oilAdapter.notifyDataSetChanged();

                    }
                });
            }
        });


        miseaniveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mViewModel.getOilByAction("Mise à niveau").observe(getActivity(), new Observer<List<Oil>>() {
                   @Override
                   public void onChanged(List<Oil> oils) {
                       oilAdapter.submitList(oils);
                       oilAdapter.notifyDataSetChanged();

                   }
               });
            }
        });

        changenement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getOilByAction("Changement").observe(getActivity(), new Observer<List<Oil>>() {
                    @Override
                    public void onChanged(List<Oil> oils) {
                        oilAdapter.submitList(oils);
                        oilAdapter.notifyDataSetChanged();


                    }
                });

            }
        });
        filtrageB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getOilByAction("Filtrage").observe(getActivity(), new Observer<List<Oil>>() {
                    @Override
                    public void onChanged(List<Oil> oils) {
                        oilAdapter.submitList(oils);
                        oilAdapter.notifyDataSetChanged();


                    }
                });

            }
        });


// suppression oil and update
        oilAdapter.setOnItemClickListener(new OilAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Oil oil) {

            }

            @Override
            public void delete(Oil oil) {
             mViewModel.delete(oil);

            }

            @Override
            public void update(Oil oil) {
                ajout.setVisibility(View.VISIBLE);
                affichage.setVisibility(View.INVISIBLE);
                date.setText(oil.getDateUtilisation().toString());
                spinner.setSelection(getIndex(spinner, oil.getAction().toString()));
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        try {
                            oil.setDateUtilisation(simpleDateFormat.parse(date.getText().toString()));
                            oil.setCreationDate(new Date());
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        oil.setPost("friteuse");
                        oil.setAction(valeur);
                        if (valeur.equals("changement")){
                            oil.setFiltrage(0);

                        }else{
                            //oil.setFiltrage(textInputLayout4.getEditText().getText());
                            oil.setFiltrage(1);


                        }

                        mViewModel.update(oil);
                        Toast.makeText(getActivity(), "oil updated Successfully", Toast.LENGTH_SHORT).show();




                    }
                });
            }
        });


        return view;
    }

    private void SpinnerLoader(final Spinner spinner)
    {
        final ArrayList<String> spinnerArray =  new ArrayList<>();
        spinnerArray.add("Choose action *");
        spinnerArray.add("Changement");
        spinnerArray.add("Mise à niveau");
        spinnerArray.add("Filtrage");
        SpinnerCatAdapter adapter = new SpinnerCatAdapter(getActivity(),spinnerArray);
        spinner.setAdapter(adapter);

    }
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }


    private boolean validateSpinner(String spinner) {
        if ((spinner == null || spinner.equals("") || spinner.isEmpty())) {
            return false;
        } else {
            return true;

        }
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
