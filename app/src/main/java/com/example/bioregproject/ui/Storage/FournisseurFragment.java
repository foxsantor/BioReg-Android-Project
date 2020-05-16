package com.example.bioregproject.ui.Storage;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioregproject.Adapters.FrsAdapter;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Fournisseur;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.List;

public class FournisseurFragment extends Fragment {

    private FournisseurViewModel mViewModel;
    private RecyclerView frsliste;
    private Button addFrs;
    private FrsAdapter frsAdapter;
    private TextView Nodata;
    private NestedScrollView nestedfrs;
    private Button saveU , save , cancelU , cancel;
    private TextInputLayout name , nameU , categorie , categorieU , numero , numeroU , email , emailU ,adresse , adresseU ;
    private ConstraintLayout update , ajout , affichage ;
    private EditText search;

    private ImageButton exit , exitU ;

    public static FournisseurFragment newInstance() {
        return new FournisseurFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fournisseur_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(FournisseurViewModel.class);

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








        //affichage
        frsAdapter = new FrsAdapter(getActivity());
        frsliste.setLayoutManager(new LinearLayoutManager(getContext()));
        frsliste.setHasFixedSize(true);
        frsliste.setAdapter(frsAdapter);
        mViewModel.getAllFrs().observe(this, new Observer<List<Fournisseur>>() {
            @Override
            public void onChanged(List<Fournisseur> fournisseurs) {
               // if (fournisseurs.size()>0){
                 //   Nodata.setVisibility(View.GONE);
                   // nestedfrs.setVisibility(View.VISIBLE);
                    frsAdapter.submitList(fournisseurs);
                    frsAdapter.notifyDataSetChanged();}
                //else{
                 //   Nodata.setVisibility(View.VISIBLE);
                   // nestedfrs.setVisibility(View.GONE);
                //}
            //}
        });


        // delete and update

        frsAdapter.setOnItemClickListener(new FrsAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Fournisseur fournisseur) {
                affichage.setVisibility(View.GONE);

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
                        affichage.setVisibility(View.VISIBLE);

            }

                });

            }
        });


        addFrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout.setVisibility(View.VISIBLE);
                affichage.setVisibility(View.GONE);

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
                affichage.setVisibility(View.VISIBLE);



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
                mViewModel.delete(frsAdapter.getFrsAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Contact deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(frsliste);



        exitU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setVisibility(View.GONE);
                affichage.setVisibility(View.VISIBLE);



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

        cancelU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setVisibility(View.GONE);
                affichage.setVisibility(View.VISIBLE);


            }
        });


        return view;

    }

    private void filter(String text) {

    }


}
