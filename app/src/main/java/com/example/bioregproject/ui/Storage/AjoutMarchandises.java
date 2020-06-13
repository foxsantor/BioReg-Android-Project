package com.example.bioregproject.ui.Storage;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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
import com.example.bioregproject.MainActivityViewModel;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Fournisseur;
import com.example.bioregproject.entities.Produit;
import com.example.bioregproject.entities.Storage;
import com.example.bioregproject.ui.History.DeviceHistoryViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AjoutMarchandises extends Fragment {



    private static AjoutMarchandisesViewModel mViewModel;
    private ConstraintLayout affichage,input1,input2,ajout,input1U,input2U,update;
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
    private Button plusT,moinsT,plusDT,moinsDT,plusQ,moinsQ;
    private TextView nbrTextViw,nbrQuantity;
    private float nbrT;
    private TextView information;
    private CardView frozen , ambient ,chilled;
    private int nbrQ,quantitynbr;
    private MainActivityViewModel mainActivityViewModel;
    private static DeviceHistoryViewModel deviceHistoryViewModel;
    private String description;
    private Button saveU , nextU , backU , cancelU  ,dateU;
    private ImageButton exitU ;
    private CardView step2CardU;
    private View barstepperU;
    private TextView step2U,num2U;
    private Button plusTU,moinsTU,plusDTU,moinsDTU,plusQU,moinsQU;
    private TextView nbrTextViwU,nbrQuantityU;
    private float nbrTU;
    private TextView informationU;
    private CardView frozenU , ambientU ,chilledU;
    private int nbrQU,quantitynbrU;
    private RecyclerView  RecycleFrsRecpU ,RecycleProduitRecpU;
    private CardView acceptCardU ,refusedCardU;








    public static AjoutMarchandises newInstance() {
        return new AjoutMarchandises();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = ViewModelProviders.of(this).get(AjoutMarchandisesViewModel.class);

        mainActivityViewModel  = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        deviceHistoryViewModel = ViewModelProviders.of(this).get(DeviceHistoryViewModel.class);

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


        plusQ=view.findViewById(R.id.plusQ);
        moinsQ=view.findViewById(R.id.moinsQ);
        nbrQuantity=view.findViewById(R.id.nbrQuantity);


        //update

        input1U = view.findViewById(R.id.input1U);
        input2U = view.findViewById(R.id.input2U);
        update = view.findViewById(R.id.editReception);
        nextU = view.findViewById(R.id.nextRecpU);
        backU = view.findViewById(R.id.backBU);
        cancelU = view.findViewById(R.id.cancelRecpU);
        saveU = view.findViewById(R.id.saveRecpU);
        exitU = view.findViewById(R.id.imageButton10U);
        barstepperU = view.findViewById(R.id.barStepperU);
        step2U = view.findViewById(R.id.step2U);
        num2U = view.findViewById(R.id.num2U);
        step2CardU = view.findViewById(R.id.step2CardU);
        dateU=view.findViewById(R.id.selectDateU);


        plusTU=view.findViewById(R.id.plusTU);
        moinsTU=view.findViewById(R.id.moinsTU);
        plusDTU=view.findViewById(R.id.plusDTU);
        moinsDTU=view.findViewById(R.id.moinsDTU);
        nbrTextViwU=view.findViewById(R.id.nbrTU);

        frozenU=view.findViewById(R.id.frozenU);
        chilledU=view.findViewById(R.id.chilledU);
        ambientU=view.findViewById(R.id.ambientU);
        informationU=view.findViewById(R.id.informationU);


        plusQU=view.findViewById(R.id.plusQU);
        moinsQU=view.findViewById(R.id.moinsQU);
        nbrQuantityU=view.findViewById(R.id.nbrQuantityU);

        RecycleFrsRecpU = view.findViewById(R.id.RecycleFrsRecpU);
        RecycleProduitRecpU = view.findViewById(R.id.RecyclePrdRecpU);

        acceptCardU=view.findViewById(R.id.acceptCardU);
        refusedCardU=view.findViewById(R.id.refusedCardU);








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



nbrT=0;
nbrQ=0;

        plusQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nbrQ=nbrQ+1;
                nbrQuantity.setText(String.valueOf(nbrQ));
            }
        });
        moinsQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nbrQ=nbrQ-1;
                nbrQuantity.setText(String.valueOf(nbrQ));

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

                ambientU.setCardBackgroundColor(Color.parseColor("#F8C471"));

                chilledU.setCardBackgroundColor(Color.parseColor("#D6EAF8"));

            }
        });


        ambient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information.setText("Note: Temperature should be around +14°C to +21");
                ambient.setCardBackgroundColor(Color.parseColor("#e6e6e6"));
                frozenU.setCardBackgroundColor(Color.parseColor("#2E86C1"));
                chilledU.setCardBackgroundColor(Color.parseColor("#D6EAF8"));

            }
        });


        chilled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information.setText("Note: Temperature should be around +4°C to 7°C");

                chilled.setCardBackgroundColor(Color.parseColor("#e6e6e6"));
                frozenU.setCardBackgroundColor(Color.parseColor("#2E86C1"));

                ambientU.setCardBackgroundColor(Color.parseColor("#F8C471"));



            }
        });



        nbrT=0;
        nbrQ=0;

        plusQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nbrQ=nbrQ+1;
                nbrQuantity.setText(String.valueOf(nbrQ));
            }
        });
        moinsQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nbrQ=nbrQ-1;
                nbrQuantity.setText(String.valueOf(nbrQ));

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





//temperature
        informationU.setText("");

        frozenU.setCardBackgroundColor(Color.parseColor("#2E86C1"));

        ambientU.setCardBackgroundColor(Color.parseColor("#F8C471"));

        chilledU.setCardBackgroundColor(Color.parseColor("#D6EAF8"));





        frozenU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                informationU.setText("Note: Temperature should be -18°C");

                frozenU.setCardBackgroundColor(Color.parseColor("#e6e6e6"));
                ambientU.setCardBackgroundColor(Color.parseColor("#F8C471"));

                chilledU.setCardBackgroundColor(Color.parseColor("#D6EAF8"));

            }
        });


        ambientU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                informationU.setText("Note: Temperature should be around +14°C to +21");
                ambientU.setCardBackgroundColor(Color.parseColor("#e6e6e6"));
                frozenU.setCardBackgroundColor(Color.parseColor("#2E86C1"));


                chilledU.setCardBackgroundColor(Color.parseColor("#D6EAF8"));


            }
        });


        chilledU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                informationU.setText("Note: Temperature should be around +4°C to 7°C");

                chilledU.setCardBackgroundColor(Color.parseColor("#e6e6e6"));
                frozenU.setCardBackgroundColor(Color.parseColor("#2E86C1"));

                ambientU.setCardBackgroundColor(Color.parseColor("#F8C471"));


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
                AskOptionPro(getContext(),Storage,getActivity());

            }

            @Override
            public void onUpdateClick(Storage Storage) {
                update.setVisibility(View.VISIBLE);
                affichage.setVisibility(View.GONE);


                refusedCardU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        refusedCardU.setCardBackgroundColor(Color.parseColor("#e6e6e6"));
                        acceptCardU.setCardBackgroundColor(Color.parseColor("#00A86B"));

                        state=false;
                    }
                });
                acceptCardU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        acceptCardU.setCardBackgroundColor(Color.parseColor("#e6e6e6"));
                        refusedCardU.setCardBackgroundColor(Color.parseColor("#fdc7c7"));

                        state=true;
                    }
                });

//Quntity temperature
                nbrTU=Storage.getTemperature();
                nbrQU=Storage.getQuantite();
                nbrQuantityU.setText(String.valueOf(nbrQU));
                nbrTextViwU.setText(String.valueOf(nbrTU));



                plusQU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nbrQU=nbrQU+1;
                        nbrQuantityU.setText(String.valueOf(nbrQU));
                    }
                });
                moinsQU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nbrQU=nbrQU-1;
                        nbrQuantityU.setText(String.valueOf(nbrQU));

                    }
                });



                plusTU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nbrTU=nbrTU+1;
                        nbrTextViwU.setText(String.valueOf(nbrTU));
                    }
                });
                moinsTU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nbrTU=nbrTU-1;
                        nbrTextViwU.setText(String.valueOf(nbrTU));

                    }
                });

                plusDTU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nbrTU = (float) (nbrTU + 0.1);
                        nbrTextViwU.setText(String.valueOf(nbrTU));
                    }
                });
                moinsDTU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nbrTU= (float) (nbrTU-0.1);
                        nbrTextViwU.setText(String.valueOf(nbrTU));

                    }
                });





                saveU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                       dateU.setText(Storage.getDateReception().toString());

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                            try {
                                Storage.setDateReception(simpleDateFormat.parse(dateU.getText().toString()));
                                Storage.setUpdatedAT(new Date());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }



                            Storage.setOwner(StaticUse.loadSession(getActivity()).getFirstName());
                            Storage.setCategorie(categoriePr);
                            Storage.setProduit(namePrd);
                            Storage.setFournisseur(nameFrsR);
                            Storage.setNatureProduit(naturePr);
                            Storage.setQuantite(nbrQ);
                            Storage.setTemperature(nbrT);
                            Storage.setStatus(state);
                            update.setVisibility(View.GONE);
                            mViewModel.update(Storage);
                            affichage.setClickable(true);

                            if (state) {
                                description = "accepted";
                            } else {
                                description = "refused";
                            }


                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    StaticUse.SaveHistory(getActivity(), deviceHistoryViewModel, getActivity(), "Storage",
                                            "has " + description + "a new delevery from", nameFrsR, 0, "Storage");


                                    handler.removeCallbacksAndMessages(null);
                                }
                            }, 500);


                        }
                });












            }
        });


        //Ajout
        ajoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout.setVisibility(View.VISIBLE);
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
        dateU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(dateU);
            }
        });

        //affichage RecycleView Produit et fournisseur
        fournisseurAdapter = new FournisseurAdapter(getActivity());
        RecycleFrsRecp.setLayoutManager(new GridLayoutManager(getContext(),3));
        RecycleFrsRecp.setAdapter(fournisseurAdapter);
        RecycleFrsRecpU.setLayoutManager(new GridLayoutManager(getContext(),3));
        RecycleFrsRecpU.setAdapter(fournisseurAdapter);
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
        RecycleProduitRecpU.setLayoutManager(new GridLayoutManager(getContext(),3));
        RecycleProduitRecpU.setAdapter(produitRecpAdapter);
        mViewModel.getAllproduit().observe(this, new Observer<List<Produit>>() {
            @Override
            public void onChanged(List<Produit> produits) {
                produitRecpAdapter.submitList(produits);
            }
        });




        refusedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refusedCard.setCardBackgroundColor(Color.parseColor("#e6e6e6"));
                acceptCard.setCardBackgroundColor(Color.parseColor("#00A86B"));

                state=false;
            }
        });
        acceptCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptCard.setCardBackgroundColor(Color.parseColor("#e6e6e6"));
                refusedCard.setCardBackgroundColor(Color.parseColor("#fdc7c7"));

                state=true;
            }
        });
        produitRecpAdapter.setOnItemClickListener(new ProduitRecpAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Produit Produit) {

                mViewModel.getProduitById(Produit.getId()).observe(getActivity(), new Observer<List<Produit>>() {
                    @Override
                    public void onChanged(List<Produit> produits) {
                        namePrd = produits.get(0).getName();
                        naturePr = produits.get(0).getNature();
                        categoriePr = produits.get(0).getCategorie();
                        quantitynbr = produits.get(0).getQuantite();
                        quantitynbr = quantitynbr + nbrQ;
                        produits.get(0).setQuantite(quantitynbr);
                    }
                });
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
                affichage.setClickable(true);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout.setVisibility(View.GONE);
                affichage.setClickable(true);

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
                     recp.setQuantite(nbrQ);
                     recp.setTemperature(nbrT);
                     recp.setStatus(state);
                     mViewModel.insert(recp);
                     Toast.makeText(getActivity(), " Successfully", Toast.LENGTH_SHORT).show();
                     ajout.setVisibility(View.GONE);
                     affichage.setClickable(true);

                     if (state) {
                         description = "accepted";
                     } else {
                         description = "refused";
                     }


                     final Handler handler = new Handler();
                     handler.postDelayed(new Runnable() {
                         @Override
                         public void run() {

                             StaticUse.SaveHistory(getActivity(), deviceHistoryViewModel, getActivity(), "Storage",
                                     "has " + description + "a new delevery from", nameFrsR, 0, "Storage");


                             handler.removeCallbacksAndMessages(null);
                         }
                     }, 500);


                 }}
            });




        //Steppers edit

        nextU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameFrsR.equals("")){
                    Toast.makeText(getActivity(), "Please choose a supplier", Toast.LENGTH_SHORT).show();

                }
                else if (namePrd.equals("")){
                    Toast.makeText(getActivity(), "Please choose a product", Toast.LENGTH_SHORT).show();

                }
                else {

                    input1U.setVisibility(View.GONE);
                    input2U.setVisibility(View.VISIBLE);
                    saveU.setVisibility(View.VISIBLE);
                    backU.setVisibility(View.VISIBLE);
                    nextU.setVisibility(View.GONE);
                    num2U.setTextColor(Color.parseColor("#3797DD"));
                    step2CardU.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    barstepperU.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    step2U.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });
        backU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input1U.setVisibility(View.VISIBLE);
                input2U.setVisibility(View.GONE);
                saveU.setVisibility(View.GONE);
                backU.setVisibility(View.GONE);
                nextU.setVisibility(View.VISIBLE);
                num2U.setTextColor(Color.parseColor("#FFFFFF"));
                step2CardU.setCardBackgroundColor(Color.parseColor("#3187c6"));
                barstepperU.setBackgroundColor(Color.parseColor("#3187c6"));
                step2U.setTextColor(Color.parseColor("#3187c6"));


            }
        });

        exitU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setVisibility(View.GONE);
                affichage.setClickable(true);
            }
        });

        cancelU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setVisibility(View.GONE);
                affichage.setClickable(true);

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





    public static AlertDialog AskOptionPro(final Context context, final Storage account, final LifecycleOwner activity)
    {
        final AlertDialog.Builder alerto = new AlertDialog.Builder(context);
        LayoutInflater layoutInflatero =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogueViewo =layoutInflatero.inflate(R.layout.delete_dialogue,null);
        alerto.setView(dialogueViewo);
        Button delete,cancelo;
        alerto.setTitle("Delete Traced Product N° "+account.getId());
        delete = dialogueViewo.findViewById(R.id.delete);
        cancelo= dialogueViewo.findViewById(R.id.cancel);
        final AlertDialog alertio =alerto.show();
        cancelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertio.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.delete(account);


                StaticUse.SaveHistory(activity,deviceHistoryViewModel,(Activity)context,"Storage Module",
                        "has deleted a Product",
                        "",account.getId(),"");

//                        mViewModel.getAccount(StaticUse.loadSession(context).getId()).observe(activity, new Observer<List<Account>>() {
//                            @Override
//                            public void onChanged(List<Account> accounts) {
//                                final Account user = accounts.get(0);
//                                Notification notification = new Notification();
//                                notification.setCreation(new Date());
//                                notification.setOwner(user.getFirstName());
//                                notification.setCategoryName("Traceability Module");
//                                notification.setSeen(false);
//                                notification.setName(type);
//                                notification.setDescription("has deleted a Traced Product by the ID of "+account.getId()+" from ");
//                                notification.setObjectImageBase64(StaticUse.transformerImageBase64frombytes(account.getImage()));
//                                notification.setImageBase64(StaticUse.transformerImageBase64frombytes(user.getProfileImage()));
//                                MainActivity.insertNotification(notification);
//                                StaticUse.createNotificationChannel(notification,(Activity)context);
//                                StaticUse.displayNotification((Activity)context,R.drawable.ic_delete_blue_24dp,notification);
//                            }
//                        });
                alertio.dismiss();
                Toast.makeText(context, "The Trace of "+account.getId()+" has been deleted", Toast.LENGTH_SHORT).show();
                alertio.dismiss();
            }
        });

        return alertio;
    }



}
