package com.example.bioregproject.ui.Storage;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bioregproject.Adapters.FournisseurAdapter;
import com.example.bioregproject.Adapters.ProduitRecpAdapter;
import com.example.bioregproject.Adapters.StorageAdapter;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Fournisseur;
import com.example.bioregproject.entities.Produit;
import com.example.bioregproject.entities.Storage;
import com.google.android.material.textfield.TextInputLayout;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AjoutMarchandises extends Fragment {



    private AjoutMarchandisesViewModel mViewModel;
    private ConstraintLayout affichage,input1,input2,ajout;
    private RecyclerView RecpRecycle ,RecycleFrsRecp ,RecycleProduitRecp;
    private StorageAdapter storageAdapter ;
    private FournisseurAdapter fournisseurAdapter;
    private ProduitRecpAdapter produitRecpAdapter;
    private Button save , next , back , cancel ,ajoutB ,date;
    private ImageButton exit ;
    private CardView step2Card;
    private View barstepper;
    private TextView step2,num2;
    private String nameFrsR,namePrd,naturePr,categoriePr;
    private TextInputLayout temperqture , quantites;

    public static AjoutMarchandises newInstance() {
        return new AjoutMarchandises();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(AjoutMarchandisesViewModel.class);

        View view = inflater.inflate(R.layout.ajout_marchandises_fragment, container, false);





        affichage = view.findViewById(R.id.affichageReception);
        RecpRecycle = view.findViewById(R.id.StorageRecycle);
        RecycleFrsRecp = view.findViewById(R.id.RecycleFrsRecp);
        RecycleProduitRecp = view.findViewById(R.id.RecyclePrdRecp);
        input1 = view.findViewById(R.id.input1);
        input2 = view.findViewById(R.id.input2);
        ajout = view.findViewById(R.id.ajoutReception);
        next = view.findViewById(R.id.nextRecp);
        back = view.findViewById(R.id.backB);
        cancel = view.findViewById(R.id.cancelRecp);
        save = view.findViewById(R.id.saveRecp);
        exit = view.findViewById(R.id.imageButton10);
        barstepper = view.findViewById(R.id.barStepper);
        step2 = view.findViewById(R.id.step2);
        num2 = view.findViewById(R.id.num2);
        step2Card = view.findViewById(R.id.step2Card);
        ajoutB=view.findViewById(R.id.button14);
        date=view.findViewById(R.id.selectDate);
        quantites=view.findViewById(R.id.quantity);
        temperqture=view.findViewById(R.id.temperature);









        //affichage Reception marchandises
        storageAdapter = new StorageAdapter(getActivity());
        RecpRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        RecpRecycle.setHasFixedSize(true);
        RecpRecycle.setAdapter(storageAdapter);
        mViewModel.getAllRecp().observe(this, new Observer<List<Storage>>() {
            @Override
            public void onChanged(List<Storage> storages) {
                storageAdapter.submitList(storages);
                storageAdapter.notifyDataSetChanged();
            }
        });


        //Ajout
        ajoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout.setVisibility(View.VISIBLE);
                affichage.setVisibility(View.INVISIBLE);
                affichage.isEnabled();
                affichage.setClickable(false);
            }
        });

        //DatePiker

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(date);
            }
        });

        //affichage RecycleView Produit et fournisseur
        fournisseurAdapter = new FournisseurAdapter(getActivity());
        RecycleFrsRecp.setLayoutManager(new GridLayoutManager(getContext(),3));
        RecycleFrsRecp.setAdapter(fournisseurAdapter);
        mViewModel.getAllFrs().observe(this, new Observer<List<Fournisseur>>() {
            @Override
            public void onChanged(List<Fournisseur> fournisseurs) {
                fournisseurAdapter.submitList(fournisseurs);
            }
        });


        produitRecpAdapter = new ProduitRecpAdapter(getActivity());
        RecycleProduitRecp.setLayoutManager(new GridLayoutManager(getContext(),3));
        RecycleProduitRecp.setAdapter(produitRecpAdapter);
        mViewModel.getAllproduit().observe(this, new Observer<List<Produit>>() {
            @Override
            public void onChanged(List<Produit> produits) {
                produitRecpAdapter.submitList(produits);
                produitRecpAdapter.notifyDataSetChanged();
            }
        });



        //Steppers

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    input1.setVisibility(View.GONE);
                    input2.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                    back.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                    num2.setTextColor(Color.parseColor("#3797DD"));
                    step2Card.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    barstepper.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    step2.setTextColor(Color.parseColor("#FFFFFF"));

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input1.setVisibility(View.VISIBLE);
                input2.setVisibility(View.GONE);
                save.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                num2.setTextColor(Color.parseColor("#FFFFFF"));
                step2Card.setCardBackgroundColor(Color.parseColor("#3187c6"));
                barstepper.setBackgroundColor(Color.parseColor("#3187c6"));
                step2.setTextColor(Color.parseColor("#3187c6"));


            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout.setVisibility(View.GONE);
                affichage.setVisibility(View.VISIBLE);
            }
        });

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

                Storage recp = new Storage();
                try {
                    recp.setDateReception(simpleDateFormat.parse(date.getText().toString()));
                    recp.setUpdatedAT(new Date());
                    recp.setCreation(new Date());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                fournisseurAdapter.setOnItemClickListener(new FournisseurAdapter.OnItemClickLisnter() {
                    @Override
                    public void onItemClick(Fournisseur Fournisseur) {
                        recp.setFournisseur(Fournisseur.getName());
                    }
                });
                produitRecpAdapter.setOnItemClickListener(new ProduitRecpAdapter.OnItemClickLisnter() {
                    @Override
                    public void onItemClick(Produit Produit) {


                        recp.setCategorie(Produit.getName());
                        recp.setCategorie(Produit.getCategorie());
                        recp.setProduit(Produit.getNature());

                    }
                });

                recp.setNatureProduit(naturePr);
                recp.setQuantite(Integer.parseInt(quantites.getEditText().getText().toString()));
                recp.setTemperature(Float.parseFloat(temperqture.getEditText().getText().toString()));
                recp.setStatus(true);
                mViewModel.insert(recp);
                Toast.makeText(getActivity(), " Successfully", Toast.LENGTH_SHORT).show();
ajout.setVisibility(View.GONE);
affichage.setVisibility(View.VISIBLE);



            }
        });













        return view;
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
