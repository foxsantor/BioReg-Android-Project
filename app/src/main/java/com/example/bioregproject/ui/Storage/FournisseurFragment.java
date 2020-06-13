package com.example.bioregproject.ui.Storage;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioregproject.Adapters.FournisseurAdapter;
import com.example.bioregproject.Adapters.FrsAdapter;
import com.example.bioregproject.Adapters.HistorySearchAdapter;
import com.example.bioregproject.DataBases.BioRegDB;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Fournisseur;
import com.example.bioregproject.entities.Storage;
import com.example.bioregproject.ui.History.DeviceHistoryViewModel;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.List;

public class FournisseurFragment extends Fragment {

    private static FournisseurViewModel mViewModel;
    private RecyclerView frsliste,searchTask;
    private Button addFrs;
    private FrsAdapter frsAdapter;
    private TextView Nodata;
    private NestedScrollView nestedfrs;
    private Button saveU , save , cancelU , cancel;
    private TextInputLayout name , nameU , categorie , categorieU , numero , numeroU , email , emailU ,adresse , adresseU ;
    private ConstraintLayout update , ajout , affichage ;
    private static DeviceHistoryViewModel deviceHistoryViewModel;
    private TextInputLayout search;
    private ConstraintLayout not,loading;
    private ImageButton reset;
    private MaterialCardView CardSearch;
    private TextView keyValue;
    private static int number;







    private ImageButton exit , exitU ;

    public static FournisseurFragment newInstance() {
        return new FournisseurFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fournisseur_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(FournisseurViewModel.class);
        deviceHistoryViewModel = ViewModelProviders.of(this).get(DeviceHistoryViewModel.class);


        frsliste=view.findViewById(R.id.frsliste);
        addFrs=view.findViewById(R.id.addFrs);
        ajout=view.findViewById(R.id.ajout);
        update=view.findViewById(R.id.update);
        affichage=view.findViewById(R.id.affichage);
        exit=view.findViewById(R.id.exit);
        exitU=view.findViewById(R.id.exitU);
        save=view.findViewById(R.id.save);
        saveU=view.findViewById(R.id.saveU);
        cancel=view.findViewById(R.id.cancel);
        cancelU=view.findViewById(R.id.cancelU);
        name=view.findViewById(R.id.nameFrs);
        categorie=view.findViewById(R.id.categorieFrs);
        email=view.findViewById(R.id.emailFrs);
        adresse=view.findViewById(R.id.adressefrs);
        numero=view.findViewById(R.id.numeroFrs);
        nameU=view.findViewById(R.id.nameFrsU);
        categorieU=view.findViewById(R.id.categorieFrsU);
        emailU=view.findViewById(R.id.emailFrsU);
        adresseU=view.findViewById(R.id.adressefrsU);
        numeroU=view.findViewById(R.id.numeroFrsU);
        Nodata=view.findViewById(R.id.Nodata);
        nestedfrs=view.findViewById(R.id.nestedfrs);





        search = view.findViewById(R.id.search);
        not = view.findViewById(R.id.not);
        loading = view.findViewById(R.id.loading);
        reset = view.findViewById(R.id.reset);
        searchTask = view.findViewById(R.id.searchTask);
        CardSearch = view.findViewById(R.id.CardSearch);
        keyValue = view.findViewById(R.id.keyvalue);




        //recherche

        mViewModel.initAllTeams(BioRegDB.getInstance(getActivity()).FournisseurDAO());
        final FrsAdapter adapter = new FrsAdapter(getActivity());
        searchTask.setLayoutManager(new LinearLayoutManager(getContext()));
        searchTask.setAdapter(adapter);
        mViewModel.teamAllList.observe(
                getActivity(), new Observer<PagedList<Fournisseur>>() {
                    @Override
                    public void onChanged(PagedList<Fournisseur> pagedList) {
                        try {
                            //Toast.makeText(getActivity(), ""+pagedList.get(0).getId(), Toast.LENGTH_SHORT).show();
                            Log.e("Paging ", "PageAll" + pagedList.size());
                            //refresh current list
                            adapter.submitList(pagedList);
                            number = pagedList.size();
                            if(number == 0)
                            {
                                not.setVisibility(View.VISIBLE);
                                CardSearch.setVisibility(View.GONE);
                            }else {

                                if(frsliste.getVisibility() == View.GONE)
                                    CardSearch.setVisibility(View.VISIBLE);
                            }
                            //adapter.submitList(pagedList);
                        } catch (Exception e) {
                        }
                    }
                });

        //first time set an empty value to get all data
        mViewModel.filterTextAll.setValue("");
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.filterTextAll.setValue("");
                reset.setVisibility(View.INVISIBLE);
                frsliste.setVisibility(View.VISIBLE);
                CardSearch.setVisibility(View.GONE);
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
                mViewModel.filterTextAll.setValue("%" + editable.toString() + "%");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        keyValue.setText("Found "+number+" search results for"+" "+"'"+editable.toString()+"'");
                    }
                }, 500);


                if(editable.toString().isEmpty())
                {
                    frsliste.setVisibility(View.VISIBLE);
                    CardSearch.setVisibility(View.GONE);
                }else
                {
                    frsliste.setVisibility(View.GONE);
                }

            }
        });









        //affichage
        frsAdapter = new FrsAdapter(getActivity());
        frsliste.setLayoutManager(new LinearLayoutManager(getContext()));
        frsliste.setHasFixedSize(true);
        frsliste.setAdapter(frsAdapter);
        mViewModel.getAllFrs().observe(this, new Observer<List<Fournisseur>>() {
            @Override
            public void onChanged(List<Fournisseur> fournisseurs) {
                if(fournisseurs.isEmpty())
                {
                    Nodata.setVisibility(View.VISIBLE);
                    nestedfrs.setVisibility(View.GONE);
                }else
                {
                    Nodata.setVisibility(View.GONE);
                    nestedfrs.setVisibility(View.VISIBLE);


                    frsAdapter.submitList(fournisseurs);
                    frsAdapter.notifyDataSetChanged();}
             }
        });


        // delete and update

        frsAdapter.setOnItemClickListener(new FrsAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Fournisseur fournisseur) {
                affichage.setClickable(false);
                update.setVisibility(View.VISIBLE);
                nameU.getEditText().setText(fournisseur.getName());
                categorieU.getEditText().setText(fournisseur.getCategorieCommerciale());
                adresseU.getEditText().setText(fournisseur.getAdresse());
                numeroU.getEditText().setText(fournisseur.getNumero());
                emailU.getEditText().setText(fournisseur.getEmail());


                saveU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fournisseur.setName(nameU.getEditText().getText().toString());
                        fournisseur.setAdresse(adresseU.getEditText().getText().toString());
                        fournisseur.setNumero(numeroU.getEditText().getText().toString());
                        fournisseur.setCategorieCommerciale(categorieU.getEditText().getText().toString());
                        fournisseur.setEmail(emailU.getEditText().getText().toString());
                        fournisseur.setUpdatedAT(new Date());
                        mViewModel.update(fournisseur);
                        update.setVisibility(View.GONE);
                        affichage.setClickable(true);

            }

                });

            }
        });


        addFrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout.setVisibility(View.VISIBLE);
                affichage.setClickable(false);

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fournisseur fournisseur= new Fournisseur();
                fournisseur.setName(name.getEditText().getText().toString());
                fournisseur.setAdresse(adresse.getEditText().getText().toString());
                fournisseur.setNumero(numero.getEditText().getText().toString());
                fournisseur.setCategorieCommerciale(categorie.getEditText().getText().toString());
                fournisseur.setEmail(email.getEditText().getText().toString());
                fournisseur.setUpdatedAT(new Date());
                fournisseur.setCreation(new Date());
                mViewModel.insert(fournisseur);
                ajout.setVisibility(View.GONE);
                affichage.setClickable(true);



            }
        });
        //delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView frsliste, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AskOptionPro(getContext(),frsAdapter.getFrsAt(viewHolder.getAdapterPosition()),getActivity());

            }
        }).attachToRecyclerView(frsliste);



        exitU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setVisibility(View.GONE);
                affichage.setClickable(false);



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

        cancelU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setVisibility(View.GONE);
                affichage.setClickable(true);


            }
        });


        return view;

    }

    private void filter(String text) {


    }


    public static AlertDialog AskOptionPro(final Context context, final Fournisseur account, final LifecycleOwner activity)
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
                        "has deleted a contact",
                        "",account.getId(),"");

                alertio.dismiss();
                Toast.makeText(context, "The Trace of "+account.getId()+" has been deleted", Toast.LENGTH_SHORT).show();
                alertio.dismiss();
            }
        });

        return alertio;
    }



}
