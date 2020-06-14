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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bioregproject.Adapters.ProduitAdapter;
import com.example.bioregproject.Adapters.ProduitRecpDetails;
import com.example.bioregproject.Adapters.SpinnerActionAdapter;
import com.example.bioregproject.Adapters.SpinnerCatAdapter;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Produit;
import com.example.bioregproject.entities.Storage;
import com.example.bioregproject.ui.History.DeviceHistoryViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductFragment extends Fragment {

    private static ProductViewModel mViewModel;
    private Button  saveU , save , cancelU , cancel ,addProduit , updatenbr;
    private ImageButton exit , exitU , exitQ;
    private ConstraintLayout update , ajout , affichage , quantite ;
    private TextInputLayout name , nameU ;
    private Spinner spinner,spinner4;
    private String valeur , valeur2 ,valeur3 ,valeur4,valeur5;
    private Spinner spinner2,natureSpiner,natureSpiner2;
    private RecyclerView recycle1;
    private ProduitAdapter produitAdapter;
    private TextView nameProduit , categorieProduit ;
    private RecyclerView recycleViewReception;
    private ProduitRecpDetails produitRecpDetails;
    public static AlertDialog alerti;
    private static DeviceHistoryViewModel deviceHistoryViewModel;
    private TextView tvShow , tvShow1 , nbr, kg;
    private ConstraintLayout StorageForm , ConstRecep ;







    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        deviceHistoryViewModel = ViewModelProviders.of(this).get(DeviceHistoryViewModel.class);



        exit=view.findViewById(R.id.exit);
        exitU=view.findViewById(R.id.exitU);
        save=view.findViewById(R.id.save);
        saveU=view.findViewById(R.id.saveU);
        cancel=view.findViewById(R.id.cancel);
        cancelU=view.findViewById(R.id.cancelU);
        name=view.findViewById(R.id.namePDT);
        nameU=view.findViewById(R.id.namePDTU);
        addProduit=view.findViewById(R.id.addProduit);
        ajout=view.findViewById(R.id.ajout);
        update=view.findViewById(R.id.update);
        quantite=view.findViewById(R.id.quantite);
        affichage=view.findViewById(R.id.affichage);
        spinner=view.findViewById(R.id.spinner2);
        spinner2 = view.findViewById(R.id.spinner3);
        natureSpiner = view.findViewById(R.id.spinnerNature);
        natureSpiner2 = view.findViewById(R.id.spinnerNatureU);
        recycle1 = view.findViewById(R.id.recycle1);
        nameProduit = view.findViewById(R.id.namePrd);
        exitQ = view.findViewById(R.id.imageButton8);
        updatenbr=view.findViewById(R.id.updatenbr);
        spinner4 = view.findViewById(R.id.spinner4);
        categorieProduit = view.findViewById(R.id.textView60);



        //Details:
        recycleViewReception = view.findViewById(R.id.recycleViewReception);
        tvShow = view.findViewById(R.id.tvShow);
        tvShow1 = view.findViewById(R.id.tvShow1);
        nbr = view.findViewById(R.id.textView81);
        kg = view.findViewById(R.id.textView82);
        StorageForm = view.findViewById(R.id.StorageForm);
        ConstRecep = view.findViewById(R.id.constraintLayout11);



        if (tvShow.getText().equals("show")){

        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               StorageForm.setVisibility(View.VISIBLE);
               tvShow.setText("Hide");
            }
        });}else{
            tvShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StorageForm.setVisibility(View.GONE);
                    tvShow.setText("show");
                }
            });
        }


        if (tvShow1.getText().equals("show")) {
            tvShow1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConstRecep.setVisibility(View.VISIBLE);
                    tvShow1.setText("Hide");

                }
            });
        }else{
            tvShow1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConstRecep.setVisibility(View.GONE);
                    tvShow1.setText("show");

                }
            });

        }












        produitAdapter = new ProduitAdapter(getActivity());
        recycle1.setLayoutManager(new GridLayoutManager(getContext(),5));
        recycle1.setHasFixedSize(true);
        recycle1.setAdapter(produitAdapter);




mViewModel.getAllproduit().observe(this, new Observer<List<Produit>>() {
    @Override
    public void onChanged(List<Produit> produits) {
        produitAdapter.submitList(produits);
        produitAdapter.notifyDataSetChanged();

    }
});



        produitAdapter.setOnItemClickListener(new ProduitAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Produit Produit) {
                quantite.setVisibility(View.VISIBLE);
                affichage.setVisibility(View.GONE);
                nameProduit.setText(Produit.getName());
                categorieProduit.setText(Produit.getCategorie()+",");
                nbr.setText(String.valueOf(Produit.getQuantite()));
                if (Produit.getNature().equals("Solid")){
                    kg.setText("kg");
                }else{
                    kg.setText('L');
                }
                produitRecpDetails = new ProduitRecpDetails(getActivity());
                recycleViewReception.setLayoutManager(new LinearLayoutManager(getContext()));
                recycleViewReception.setHasFixedSize(true);
                recycleViewReception.setAdapter(produitRecpDetails);


                mViewModel.getAllRecp(Produit.getName()).observe(getActivity(), new Observer<List<Storage>>() {
                        @Override
                        public void onChanged(List<Storage> storages) {

                            produitRecpDetails.submitList(storages);
                            produitRecpDetails.notifyDataSetChanged();

                        }
                    });



                updatenbr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        affichage.setClickable(true);
                        quantite.setVisibility(View.GONE);
                        affichage.setVisibility(View.VISIBLE);

                    }
                });

            }

            @Override
            public void delete(Produit Produit) {
                AskOptionPro(getContext(),Produit,getActivity());

            }

            @Override
            public void update(Produit Produit) {
                update.setVisibility(View.VISIBLE);
                affichage.setClickable(false);
                nameU.getEditText().setText(Produit.getName());
                spinner2.setSelection(getIndex(spinner2, Produit.getCategorie().toString()));
                natureSpiner2.setSelection(getIndex(natureSpiner2, Produit.getNature().toString()));

                saveU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Produit.setName(nameU.getEditText().getText().toString());
                        Produit.setCategorie(valeur2);
                        Produit.setNature(valeur4);
                        Produit.setUpdatedAT(new Date());
                        mViewModel.update(Produit);
                        Toast.makeText(getActivity(), "Product updated Successfully", Toast.LENGTH_SHORT).show();
                        affichage.setClickable(true);
                        update.setVisibility(View.GONE);
                    }
                });

            }
        });





        addProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout.setVisibility(View.VISIBLE);
                affichage.setClickable(true);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produit produit = new Produit();
                produit.setName(name.getEditText().getText().toString());
                produit.setCategorie(valeur);
                produit.setCreation(new Date());
                produit.setQuantite(0);
                produit.setNature(valeur3);
                produit.setUpdatedAT(new Date());
                mViewModel.insert(produit);
                ajout.setVisibility(View.GONE);
                affichage.setClickable(true);
            }
        });




        exitU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.setVisibility(View.GONE);
                affichage.setClickable(true);



            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout.setVisibility(View.GONE);
                affichage.setClickable(true);


            }
        });

        exitQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                affichage.setClickable(true);
                quantite.setVisibility(View.GONE);
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

        SpinnerLoader(spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position>0)
            {
                valeur = (String) parent.getItemAtPosition(position);

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });


        SpinnerLoader(spinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                {
                    valeur2 = (String) parent.getItemAtPosition(position);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SpinnerLoader2(natureSpiner);
        natureSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                {
                    valeur3 = (String) parent.getItemAtPosition(position);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SpinnerLoader2(natureSpiner2);
        natureSpiner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                {
                    valeur4 = (String) parent.getItemAtPosition(position);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinnerLoader3(spinner4);
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0)
                {
                    valeur5 = (String) parent.getItemAtPosition(position);
                    mViewModel.getProduitByCategorie(valeur5).observe(getActivity(), new Observer<List<Produit>>() {
                        @Override
                        public void onChanged(List<Produit> produits) {
                            produitAdapter.submitList(produits);
                            produitAdapter.notifyDataSetChanged();

                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }


    private void SpinnerLoader3(final Spinner spinner)
    {
        final ArrayList<String> spinnerArray =  new ArrayList<>();
        spinnerArray.add("All");
        spinnerArray.add("Dairy products");
        spinnerArray.add("Meat, fish and eggs");
        spinnerArray.add("vegetables and fruits");
        spinnerArray.add("Cereals");
        spinnerArray.add("High-fat products");
        spinnerArray.add("Sweetened products");
        spinnerArray.add("Drinks");
        SpinnerActionAdapter adapter = new SpinnerActionAdapter(getActivity(),spinnerArray);
        spinner.setAdapter(adapter);

    }

    private void SpinnerLoader2(final Spinner spinner)
    {
        final ArrayList<String> spinnerArray =  new ArrayList<>();
        spinnerArray.add("Choose Nature Product *");
        spinnerArray.add("Liquid");
        spinnerArray.add("Solid");
   ;
        SpinnerActionAdapter adapter = new SpinnerActionAdapter(getActivity(),spinnerArray);
        spinner.setAdapter(adapter);

    }
    private void SpinnerLoader(final Spinner spinner)
    {
        final ArrayList<String> spinnerArray =  new ArrayList<>();
        spinnerArray.add("Choose Category *");
        spinnerArray.add("Dairy products");
        spinnerArray.add("Meat, fish and eggs");
        spinnerArray.add("Fruits and vegetables");
        spinnerArray.add("Cereals");
        spinnerArray.add("High-fat products");
        spinnerArray.add("Sweetened products");
        spinnerArray.add("Drinks");
        SpinnerActionAdapter adapter = new SpinnerActionAdapter(getActivity(),spinnerArray);
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

    public static void dismissMessage() {
        alerti.dismiss();
    }

    public static AlertDialog AskOptionPro(final Context context, final Produit account, final LifecycleOwner activity)
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
