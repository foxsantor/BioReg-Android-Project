package com.example.bioregproject.ui.OilControl;

import androidx.activity.OnBackPressedCallback;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bioregproject.Adapters.OilAdapter;
import com.example.bioregproject.Adapters.PostAdapter;
import com.example.bioregproject.Adapters.SpinnerCatAdapter;
import com.example.bioregproject.MainActivityViewModel;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Oil;
import com.example.bioregproject.entities.Post;
import com.example.bioregproject.ui.History.DeviceHistoryViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OliControl extends Fragment {

    private static OliControlViewModel mViewModel;

    private RecyclerView OilRecycleView;
    private String valeur;
    private Button all,filtrageB,changenement,miseaniveau,addOil;
    private OilAdapter oilAdapter;
    private String namePost;
    private MainActivityViewModel mainActivityViewModel;
    private static DeviceHistoryViewModel deviceHistoryViewModel;
    private CardView affichage;
    private TextView noData;
    private NestedScrollView nestedScrollView8;
    public static AlertDialog alerti;
    private Spinner spinner;
    private TextInputLayout textInputLayout ,textInputLayout4;
    private Button save,cancel;
    private Button calender;
    private EditText date;
    private PostAdapter postAdapter;
    private RecyclerView post1;
    private CardView update , error;
    private ImageButton close;



    public static OliControl newInstance() {
        return new OliControl();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.oli_control_fragment, container, false);

        mViewModel = ViewModelProviders.of(this).get(OliControlViewModel.class);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.mainMenu);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);




        affichage=view.findViewById(R.id.affichage);
        all =view.findViewById(R.id.All);
        miseaniveau =view.findViewById(R.id.miseaniveau);
        changenement =view.findViewById(R.id.Changement);
        filtrageB =view.findViewById(R.id.filtrageB);
        OilRecycleView =view.findViewById(R.id.OilRecycleView);
        mainActivityViewModel  = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        deviceHistoryViewModel = ViewModelProviders.of(this).get(DeviceHistoryViewModel.class);
        noData =view.findViewById(R.id.noData);
        nestedScrollView8=view.findViewById(R.id.nestedScrollView8);
        addOil =view.findViewById(R.id.addOil);

        spinner = view.findViewById(R.id.spinner);
        textInputLayout=view.findViewById(R.id.textInputLayout);
        textInputLayout4 = view.findViewById(R.id.textInputLayout4);
        save = view.findViewById(R.id.save);
        cancel=view.findViewById(R.id.cancel);
        calender = view.findViewById(R.id.calender);
        post1 = view.findViewById(R.id.post);
        update = view.findViewById(R.id.updateoil);
        error = view.findViewById(R.id.errors);
        close = view.findViewById(R.id.close);






        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setVisibility(View.GONE);
            }
        });


// affichage ajout



        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(calender);
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
//afichage Post
        //get Post
        postAdapter = new PostAdapter(getActivity());
        post1.setLayoutManager(new GridLayoutManager(getContext(),8));
        post1.setHasFixedSize(true);
        post1.setAdapter(postAdapter);
        mViewModel.getAllPost().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                postAdapter.submitList(posts);
            }
        });


        postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Post Post) {
                mViewModel.getPostById(Post.getId()).observe(getActivity(), new Observer<List<Post>>() {
                    @Override
                    public void onChanged(List<Post> posts) {
                        namePost=posts.get(0).getName();

                    }
                });

            }
        });







        addOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewModel.getAllPost().observe(getActivity(), new Observer<List<Post>>() {
                    @Override
                    public void onChanged(List<Post> posts) {

                        if (posts.isEmpty()){
                            error.setVisibility(View.VISIBLE);
                        } else{
                            Navigation.findNavController(v).navigate(R.id.oilAjout);
                    }}
                });

            }
        });



// affichage Oil
//mViewModel.insert(new Oil("nejma",1,new Date(),new Date(),"changement","friteuse"));

        oilAdapter = new OilAdapter(getActivity(),this);
        OilRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        OilRecycleView.setHasFixedSize(true);
        OilRecycleView.setAdapter(oilAdapter);
        mViewModel.getAllOil().observe(this, new Observer<List<Oil>>() {
            @Override
            public void onChanged(List<Oil> oils) {
                if (oils.isEmpty()){
                    noData.setVisibility(View.VISIBLE);
                    nestedScrollView8.setVisibility(View.GONE);

                }else {
                    noData.setVisibility(View.GONE);
                    nestedScrollView8.setVisibility(View.VISIBLE);
                    oilAdapter.submitList(oils);
                    oilAdapter.notifyDataSetChanged();
                }
            }
        });
        //Recherche
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miseaniveau.setTextColor(getActivity().getResources().getColor(R.color.White));
                miseaniveau.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                filtrageB.setTextColor(getActivity().getResources().getColor(R.color.White));
                filtrageB.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                changenement.setTextColor(getActivity().getResources().getColor(R.color.White));
                changenement.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                all.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                all.setBackgroundColor(getActivity().getResources().getColor(R.color.White));


                mViewModel.getAllOil().observe(getActivity(), new Observer<List<Oil>>() {
                    @Override
                    public void onChanged(List<Oil> oils) {
                        if (oils.isEmpty()){
                            noData.setVisibility(View.VISIBLE);
                            noData.setText("No Data Found");
                            nestedScrollView8.setVisibility(View.GONE);

                        }else {
                            noData.setVisibility(View.GONE);
                            nestedScrollView8.setVisibility(View.VISIBLE);
                        oilAdapter.submitList(oils);
                        oilAdapter.notifyDataSetChanged();}

                    }
                });
            }
        });


        miseaniveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setTextColor(getActivity().getResources().getColor(R.color.White));
                all.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                filtrageB.setTextColor(getActivity().getResources().getColor(R.color.White));
                filtrageB.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                changenement.setTextColor(getActivity().getResources().getColor(R.color.White));
                changenement.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                miseaniveau.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                miseaniveau.setBackgroundColor(getActivity().getResources().getColor(R.color.White));
               mViewModel.getOilByAction("Mise à niveau").observe(getActivity(), new Observer<List<Oil>>() {
                   @Override
                   public void onChanged(List<Oil> oils) {
                       if (oils.isEmpty()){
                           noData.setVisibility(View.VISIBLE);
                           noData.setText("No Data Found");
                           nestedScrollView8.setVisibility(View.GONE);

                       }else {
                           noData.setVisibility(View.GONE);
                           nestedScrollView8.setVisibility(View.VISIBLE);
                           oilAdapter.submitList(oils);
                           oilAdapter.notifyDataSetChanged();
                       }
                   }
               });
            }
        });

        changenement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all.setTextColor(getActivity().getResources().getColor(R.color.White));
                all.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                filtrageB.setTextColor(getActivity().getResources().getColor(R.color.White));
                filtrageB.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                miseaniveau.setTextColor(getActivity().getResources().getColor(R.color.White));
                miseaniveau.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                changenement.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                changenement.setBackgroundColor(getActivity().getResources().getColor(R.color.White));
                mViewModel.getOilByAction("Changement").observe(getActivity(), new Observer<List<Oil>>() {
                    @Override
                    public void onChanged(List<Oil> oils) {
                        if (oils.isEmpty()){
                            noData.setVisibility(View.VISIBLE);
                            noData.setText("No Data Found");
                            nestedScrollView8.setVisibility(View.GONE);

                        }else {
                            noData.setVisibility(View.GONE);
                            nestedScrollView8.setVisibility(View.VISIBLE);
                            oilAdapter.submitList(oils);
                            oilAdapter.notifyDataSetChanged();

                        }
                    }
                });

            }
        });
        filtrageB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                all.setTextColor(getActivity().getResources().getColor(R.color.White));
                all.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                changenement.setTextColor(getActivity().getResources().getColor(R.color.White));
                changenement.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                miseaniveau.setTextColor(getActivity().getResources().getColor(R.color.White));
                miseaniveau.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                filtrageB.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                filtrageB.setBackgroundColor(getActivity().getResources().getColor(R.color.White));
                mViewModel.getOilByAction("Filtrage").observe(getActivity(), new Observer<List<Oil>>() {
                    @Override
                    public void onChanged(List<Oil> oils) {
                        if (oils.isEmpty()){
                            noData.setVisibility(View.VISIBLE);
                            noData.setText("No Data Found");
                            nestedScrollView8.setVisibility(View.GONE);

                        }else {
                            noData.setVisibility(View.GONE);
                            nestedScrollView8.setVisibility(View.VISIBLE);
                        oilAdapter.submitList(oils);
                        oilAdapter.notifyDataSetChanged();}


                    }
                });

            }
        });


// suppression oil and update
        oilAdapter.setOnItemClickListener(new OilAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Oil oil) {
                update.setVisibility(View.VISIBLE);
                affichage.setVisibility(View.GONE);
                textInputLayout4.getEditText().setText(String.valueOf(oil.getFiltrage()));
                calender.setText(oil.getDateUtilisation().toString());
                spinner.setSelection(getIndex(spinner, oil.getAction().toString()));


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!StaticUse.validateEmpty(textInputLayout4,"Filtrage")
                        ){return;}else {

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            try {
                                oil.setDateUtilisation(simpleDateFormat.parse(calender.getText().toString()));
                                oil.setCreationDate(new Date());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            oil.setPost(namePost);
                            oil.setFiltrage(Float.parseFloat(textInputLayout4.getEditText().getText().toString()));
                            oil.setAction(valeur);


                            mViewModel.update(oil);
                            Toast.makeText(getActivity(), "oil Action update Successfully", Toast.LENGTH_SHORT).show();

affichage.setVisibility(View.VISIBLE);
update.setVisibility(View.GONE);

                            StaticUse.SaveNotification(getActivity(),mainActivityViewModel,getActivity(),"Oil Control"
                                    ,"has updates a new "+valeur+" for "
                                    ,namePost,null,null,R.drawable.ic_add_circle_blue_24dp);



                        }

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mViewModel.getOilById(0).observe(getActivity(), new Observer<List<Oil>>() {
                                    @Override
                                    public void onChanged(List<Oil> oils) {
                                        StaticUse.SaveHistory(getActivity(),deviceHistoryViewModel,getActivity(),"Oil Control",
                                                "has updated a new action for ",namePost,0,valeur);

                                    }
                                });

                                handler.removeCallbacksAndMessages(null);
                            }
                        }, 500);



                    }
                });

            }


        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                affichage.setVisibility(View.VISIBLE);
                update.setVisibility(View.GONE);
            }
        });


        //delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView OilRecycleView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AskOptionPro(getContext(),oilAdapter.getOiltAt(viewHolder.getAdapterPosition()),getActivity());

            }
        }).attachToRecyclerView(OilRecycleView);





        return view;
    }


    public static void dismissMessage() {
        alerti.dismiss();
    }

    public static AlertDialog AskOptionPro(final Context context, final Oil account, final LifecycleOwner activity)
    {
        final AlertDialog.Builder alerto = new AlertDialog.Builder(context);
        LayoutInflater layoutInflatero =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogueViewo =layoutInflatero.inflate(R.layout.delete_dialogue,null);
        alerto.setView(dialogueViewo);
        Button delete,cancelo;
        alerto.setTitle("Delete Action  N° "+account.getId());
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


                StaticUse.SaveHistory(activity,deviceHistoryViewModel,(Activity)context,"Oil Control",
                        "has deleted a action ",
                        "",account.getId(),"");


                alertio.dismiss();
                Toast.makeText(context, "The Trace of "+account.getId()+" has been deleted", Toast.LENGTH_SHORT).show();
                alertio.dismiss();
            }
        });

        return alertio;
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
