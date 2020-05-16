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
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Fournisseur;
import com.example.bioregproject.entities.Produit;
import com.example.bioregproject.entities.Storage;
import com.google.android.material.textfield.TextInputLayout;

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
    private CardView acceptCard ,refusedCard;
    private Boolean state;
    private Button plusT,moinsT,plusDT,moinsDT;
    private TextView nbrTextViw;
    private float nbrT;
    private TextView information;
    private CardView frozen , ambient ,chilled;


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
        acceptCard=view.findViewById(R.id.acceptCard);
        refusedCard=view.findViewById(R.id.refusedCard);

        plusT=view.findViewById(R.id.plusT);
        moinsT=view.findViewById(R.id.moinsT);
        plusDT=view.findViewById(R.id.plusDT);
        moinsDT=view.findViewById(R.id.moinsDT);
        nbrTextViw=view.findViewById(R.id.nbrT);

        frozen=view.findViewById(R.id.frozen);
        chilled=view.findViewById(R.id.chilled);
        ambient=view.findViewById(R.id.ambient);
        information=view.findViewById(R.id.information);







//temperature
        information.setText("");

            frozen.setCardBackgroundColor(Color.parseColor("#2E86C1"));


            ambient.setCardBackgroundColor(Color.parseColor("#F8C471"));

            chilled.setCardBackgroundColor(Color.parseColor("#D6EAF8"));





        frozen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information.setText("Note: Temperature should be -18°C");

                frozen.setCardBackgroundColor(Color.parseColor("#e6e6e6"));

            }
        });


        ambient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information.setText("Note: Temperature should be around +14°C to +21");
                ambient.setCardBackgroundColor(Color.parseColor("#e6e6e6"));


            }
        });


        chilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information.setText("Note: Temperature should be around +4°C to 7°C");

                chilled.setCardBackgroundColor(Color.parseColor("#e6e6e6"));


            }
        });




        plusT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               nbrT=nbrT+1;
                nbrTextViw.setText(String.valueOf(nbrT));
            }
        });
        moinsT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nbrT=nbrT-1;
                nbrTextViw.setText(String.valueOf(nbrT));

            }
        });

        plusDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nbrT = (float) (nbrT + 0.1);
                nbrTextViw.setText(String.valueOf(nbrT));
            }
        });
        moinsDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nbrT= (float) (nbrT-0.1);
                nbrTextViw.setText(String.valueOf(nbrT));

            }
        });








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

        storageAdapter.setOnItemClickListener(new StorageAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Storage Storage) {

            }

            @Override
            public void ondeleteClick(Storage Storage) {
                  mViewModel.delete(Storage);
                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onUpdateClick(Storage Storage) {

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

        fournisseurAdapter.setOnItemClickListener(new FournisseurAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Fournisseur Fournisseur) {
                mViewModel.getFrsById(Fournisseur.getId()).observe(getActivity(), new Observer<List<Fournisseur>>() {
                    @Override
                    public void onChanged(List<Fournisseur> fournisseurs) {
                        nameFrsR=fournisseurs.get(0).getName();
                    }
                });
            }
        });



        produitRecpAdapter = new ProduitRecpAdapter(getActivity());
        RecycleProduitRecp.setLayoutManager(new GridLayoutManager(getContext(),3));
        RecycleProduitRecp.setAdapter(produitRecpAdapter);
        mViewModel.getAllproduit().observe(this, new Observer<List<Produit>>() {
            @Override
            public void onChanged(List<Produit> produits) {
                produitRecpAdapter.submitList(produits);
            }
        });


        produitRecpAdapter.setOnItemClickListener(new ProduitRecpAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Produit Produit) {

                mViewModel.getProduitById(Produit.getId()).observe(getActivity(), new Observer<List<Produit>>() {
                    @Override
                    public void onChanged(List<Produit> produits) {
                        namePrd=produits.get(0).getName();
                        naturePr=produits.get(0).getNature();
                        categoriePr=produits.get(0).getCategorie();
                    }
                });
            }
        });
acceptCard.setCardBackgroundColor(Color.parseColor("#00A86B"));
        refusedCard.setCardBackgroundColor(Color.parseColor("#fdc7c7"));


        refusedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refusedCard.setCardBackgroundColor(Color.parseColor("#e6e6e6"));
                state=false;
            }
        });
        acceptCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptCard.setCardBackgroundColor(Color.parseColor("#e6e6e6"));
                state=true;
            }
        });


        //Steppers

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameFrsR.equals("")){
                    Toast.makeText(getActivity(), "Please choose a supplier", Toast.LENGTH_SHORT).show();

                }
                else if (namePrd.equals("")){
                    Toast.makeText(getActivity(), "Please choose a product", Toast.LENGTH_SHORT).show();

                }
                else {

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


                 if (date.getText().equals("Select Date")){
                    Toast.makeText(getActivity(), "Please Choose Date", Toast.LENGTH_SHORT).show();

                }
                else {


                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    Storage recp = new Storage();
                    try {
                        recp.setDateReception(simpleDateFormat.parse(date.getText().toString()));
                        recp.setUpdatedAT(new Date());
                        recp.setCreation(new Date());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    recp.setOwner(StaticUse.loadSession(getActivity()).getFirstName());
                    recp.setCategorie(categoriePr);
                    recp.setProduit(namePrd);
                    recp.setFournisseur(nameFrsR);
                    recp.setNatureProduit(naturePr);
                    recp.setQuantite(0);
                    recp.setTemperature(nbrT);
                    recp.setStatus(state);
                    mViewModel.insert(recp);
                    Toast.makeText(getActivity(), " Successfully", Toast.LENGTH_SHORT).show();
                    ajout.setVisibility(View.GONE);
                    affichage.setVisibility(View.VISIBLE);
                }


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
