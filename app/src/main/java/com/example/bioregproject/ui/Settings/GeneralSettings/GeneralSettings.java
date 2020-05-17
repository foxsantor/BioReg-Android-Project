package com.example.bioregproject.ui.Settings.GeneralSettings;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
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
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bioregproject.Adapters.CategoryAdapter;
import com.example.bioregproject.Adapters.PostSettingAdapter;
import com.example.bioregproject.Adapters.SurfaceforCategroryAdapter;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.CategorieTache;
import com.example.bioregproject.entities.Post;
import com.example.bioregproject.entities.Realtions.CategorywithSurfaces;
import com.example.bioregproject.entities.SettingOil;
import com.example.bioregproject.entities.Surface;
import com.google.android.material.textfield.TextInputLayout;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class GeneralSettings extends Fragment {

    private GeneralSettingsViewModel mViewModel;
    private TextInputLayout namecat,textInputaddSurface ;
    private Button saveCateBtn,cancelCategorie,addNew,buttonAddSurface;
    private ConstraintLayout addCategorie , Details;
    private Button indicateur1,indicateur2,indicateur3;
    private RecyclerView categorie,surfaces;
    private TextView nameCategorie;
    private SurfaceforCategroryAdapter adapter3;
    private ImageView imageButton2;
    private ImageView addimage;
    private static final int GALLERY_REQUEST = 1;
    private static final int  MY_CAMERA_PERMISSION_CODE = 2;
    private static final int CAMERA_REQUEST=3;
    private Bitmap bitmapContainer;
    private ConstraintLayout imageViewsC;
    private ImageButton close,addImage,seeAlone;
    private TextView imagenote,imageHinter;
    private ImageView imageView10;
    private RoundedImageView imageContainer;
    private CardView buttons;
    private Button button7,button9;
    private NestedScrollView nestedCate , nestedSurface,affichage;
    private ImageButton closeDetails;
    private CategoryAdapter adapter2;
    private ImageView imageCategoryDetail;
    private Button doneDetails,CancelDetails;
    private RecyclerView postRecycleView ;
    private PostSettingAdapter postAdapter;
    private Button add,save,cancel,updateB,cancel1;
    private ConstraintLayout ajout,updateConstraint;
    private TextInputLayout namePost,namePost1;
    private ImageButton exit,exit1;
    private NestedScrollView nestes;
    private Button indicateurPost1, indicateurPost2 ,indicateurPost3 ,indicateurPost4;
    private ConstraintLayout veriffiltrage;
    private Switch switch1;
    private SettingOil settingFiltrage;
    private int pos;
    private LifecycleOwner lifecycleOwner;
    private ImageButton info;






    public static GeneralSettings newInstance() {
        return new GeneralSettings();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.general_settings_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(GeneralSettingsViewModel.class);



        addCategorie=view.findViewById(R.id.ajoutcategories);
        cancelCategorie=view.findViewById(R.id.cancelBu);
        closeDetails=view.findViewById(R.id.closeDetails);
        addimage = view.findViewById(R.id.imageView14);
        addImage= view.findViewById(R.id.addImage);
        seeAlone= view.findViewById(R.id.seealone);
        close = view.findViewById(R.id.closeimage);
        imageViewsC=view.findViewById(R.id.imageViewsC);
        imagenote = view.findViewById(R.id.imagenote);
        imageView10= view.findViewById(R.id.imageView10);
        imageContainer = view.findViewById(R.id.image_container);
        buttons = view.findViewById(R.id.buttons);
        imageHinter = view.findViewById(R.id.buttontext);
        button7= view.findViewById(R.id.button7);
        button9 = view.findViewById(R.id.button9);
        namecat =view.findViewById(R.id.editTextcat);
        saveCateBtn=(Button)view.findViewById(R.id.saveBu);
        indicateur1=view.findViewById(R.id.indicateurCat);
        indicateur2=view.findViewById(R.id.indicateurCat2);
        addNew=view.findViewById(R.id.addNew);
        surfaces=view.findViewById(R.id.listeSurface);
        categorie=view.findViewById(R.id.categ);
        nestedCate=view.findViewById(R.id.nestedCate);
        nestedSurface=view.findViewById(R.id.nestedSurface);
        indicateur3=view.findViewById(R.id.indicateur3);
        Details=view.findViewById(R.id.Details);
        textInputaddSurface=view.findViewById(R.id.textInputaddSurface);
        buttonAddSurface=view.findViewById(R.id.buttonAddSurface);
        imageButton2=view.findViewById(R.id.imageButton2);
        affichage=view.findViewById(R.id.NestedAffichage);
        nameCategorie=view.findViewById(R.id.textView62);
        imageCategoryDetail=view.findViewById(R.id.imageCategorieDetail);
        CancelDetails=view.findViewById(R.id.cancelDetails);
        doneDetails=view.findViewById(R.id.doneDetails);
        lifecycleOwner = this;
        info=view.findViewById(R.id.info);




        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.staticOilTest);


            }
        });




        //oil
        postRecycleView = view.findViewById(R.id.postRecy);
        add = view.findViewById(R.id.addNewPost);
        namePost = view.findViewById(R.id.namePost);
        save = view.findViewById(R.id.save);
        cancel = view.findViewById(R.id.cancel);
        ajout = view.findViewById(R.id.ajout);
        exit = view.findViewById(R.id.exit);
        namePost1 = view.findViewById(R.id.namePost1);
        updateB = view.findViewById(R.id.update);
        updateConstraint=view.findViewById(R.id.updateConstraint);
        cancel1 = view.findViewById(R.id.cancel1);
        exit1 = view.findViewById(R.id.exit1);
        nestes = view.findViewById(R.id.nestedPost);
        indicateurPost1 = view.findViewById(R.id.indicateurPost);
        indicateurPost2 = view.findViewById(R.id.indicateurPost2);
        indicateurPost3 = view.findViewById(R.id.indicateurPost4);
        indicateurPost4 = view.findViewById(R.id.indicateurPost5);
        veriffiltrage=view.findViewById(R.id.verifFiltrage);
        switch1 = view.findViewById(R.id.switch1);
















        //oil Settings


        indicateurPost1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicateurPost1.setVisibility(View.GONE);
                indicateurPost2.setVisibility(View.VISIBLE);
                add.setVisibility(View.VISIBLE);

            }
        });

        indicateurPost2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicateurPost2.setVisibility(View.GONE);
                indicateurPost1.setVisibility(View.VISIBLE);
                add.setVisibility(View.GONE);
                nestes.setVisibility(View.GONE);

            }
        });

        indicateurPost3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicateurPost3.setVisibility(View.GONE);
                indicateurPost4.setVisibility(View.VISIBLE);
                veriffiltrage.setVisibility(View.VISIBLE);

            }
        });

        indicateurPost4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicateurPost3.setVisibility(View.VISIBLE);
                indicateurPost4.setVisibility(View.GONE);
                veriffiltrage.setVisibility(View.GONE);
            }
        });





        postAdapter = new PostSettingAdapter(getActivity());
        postRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        postRecycleView.setHasFixedSize(true);
        postRecycleView.setAdapter(postAdapter);
        mViewModel.getAllPost().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                if (posts.size()>0) {
                    nestes.setVisibility(View.VISIBLE); }
                    postAdapter.submitList(posts);
                    postAdapter.notifyDataSetChanged();

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView postRecycleView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
               mViewModel.deleteOil(postAdapter.getPostAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Post deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(postRecycleView);

        postAdapter.setOnItemClickListener(new PostSettingAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Post Post) {
                updateConstraint.setVisibility(View.VISIBLE);
                affichage.setVisibility(View.GONE);
                namePost1.getEditText().setText(Post.getName());
                updateB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Post.setName(namePost1.getEditText().getText().toString());
                        Post.setUpdatedAT(new Date());
                        mViewModel.updateOil(Post);
                        updateConstraint.setVisibility(View.GONE);
                        affichage.setVisibility(View.VISIBLE);


                    }
                });
            }


        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout.setVisibility(View.VISIBLE);
                affichage.setVisibility(View.GONE);

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post();
                post.setName(namePost.getEditText().getText().toString());
                post.setCreation(new Date());
                post.setUpdatedAT(new Date());
                mViewModel.insertOil(post);
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


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajout.setVisibility(View.GONE);
                affichage.setVisibility(View.VISIBLE);

            }
        });

        cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateConstraint.setVisibility(View.GONE);
                affichage.setVisibility(View.VISIBLE);

            }
        });


        exit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateConstraint.setVisibility(View.GONE);
                affichage.setVisibility(View.VISIBLE);

            }
        });


        //switch  affichage
        mViewModel.getAllSetting().observe(this, new Observer<List<SettingOil>>() {
            @Override
            public void onChanged(List<SettingOil> settingOils) {

    if(!settingOils.isEmpty()){
                    mViewModel.insertSetting(new SettingOil(false));

                    settingFiltrage = settingOils.get(0);
                    switch1.setChecked(settingFiltrage.getVerif());


                switch1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (switch1.isChecked()){

                                        settingFiltrage.setVerif(true);
                                        mViewModel.updateSetting(settingFiltrage);
                                        switch1.setChecked(true);

                        }
                        else{
                            settingFiltrage.setVerif(false);
                            mViewModel.updateSetting(settingFiltrage);
                            switch1.setChecked(false);

                        }
                    }
                });

            }else
    {
        return;
    }
            mViewModel.getAllSetting().removeObservers(lifecycleOwner);
            }
        });












        //Setting Cleaning


        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategorie.setVisibility(View.VISIBLE);
                affichage.setClickable(false);
            }
        });


        saveCateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = namecat.getEditText().getText().toString();
                if (name.trim().isEmpty()){
                    Toast.makeText(getActivity(),"please insert name",Toast.LENGTH_SHORT);
                    return;
                }
                else{
                    addNewCategorie(namecat,addimage);



                }

            }
        });



        indicateur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nestedCate.setVisibility(View.GONE);
                addNew.setVisibility(View.GONE);
                indicateur1.setVisibility(View.VISIBLE);
                indicateur2.setVisibility(View.GONE);

            }
        });



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageContainer.setVisibility(View.GONE);
                imageHinter.setVisibility(View.VISIBLE);
                close.setVisibility(View.GONE);
                buttons.setVisibility(View.VISIBLE);
                bitmapContainer=null;

            }
        });


        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmapContainer == null )
                {
                    Toast.makeText(getActivity(), "You didn't choose an image", Toast.LENGTH_SHORT).show();
                }else
                {
                    addimage.setImageBitmap(bitmapContainer);
                    imageViewsC.setVisibility(View.GONE);

                }
            }
        });



        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewsC.setVisibility(View.GONE);
            }
        });


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

            }
        });

        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewsC.setVisibility(View.VISIBLE);

            }
        });
        seeAlone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {

                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }

            }
        });


        cancelCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animFadeOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_out);
                addCategorie.startAnimation(animFadeOut);
                affichage.setClickable(true);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addCategorie.setVisibility(View.GONE);     }
                }, 550);

            }
        });




        adapter3 = new SurfaceforCategroryAdapter(getActivity());
        surfaces.setLayoutManager(new LinearLayoutManager(getContext()));
        surfaces.setHasFixedSize(true);
        surfaces.setAdapter(adapter3);
        adapter2 =new CategoryAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        categorie.setLayoutManager(layoutManager);
        categorie.setHasFixedSize(true);
        categorie.setAdapter(adapter2);
        mViewModel.getCategories().observe(this, new Observer<List<CategorywithSurfaces>>() {
            @Override
            public void onChanged(List<CategorywithSurfaces> categorywithSurfaces) {

                indicateur1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addNew.setVisibility(View.VISIBLE);
                        adapter2.notifyDataSetChanged();
                        indicateur2.setVisibility(View.VISIBLE);
                        indicateur1.setVisibility(View.GONE);
                        adapter2.submitList(categorywithSurfaces);

                            if (categorywithSurfaces.isEmpty()) {

                                nestedCate.setVisibility(View.GONE);

                            }else {
                                nestedCate.setVisibility(View.VISIBLE);

                            }
                    }
                });



            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView categorie, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.delete(adapter2.getCategoryAt(viewHolder.getAdapterPosition()).categorieTache);
                Toast.makeText(getActivity(), "Category delete", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(categorie);





        indicateur3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAddSurface.setVisibility(View.VISIBLE);
                textInputaddSurface.setVisibility(View.VISIBLE);
            }
        });








        adapter2.setOnItemClickListener(new CategoryAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(CategorywithSurfaces categorywithSurfaces) {
                Details.setVisibility(View.VISIBLE);
                nameCategorie.setText(categorywithSurfaces.categorieTache.getName());
                final byte[] image = categorywithSurfaces.categorieTache.getCategorieImage();
                Glide.with(getContext()).asBitmap().load(image).into(imageCategoryDetail);
                affichage.setVisibility(View.GONE);
                if (categorywithSurfaces.surfaces.size()!=0){
                nestedSurface.setVisibility(View.VISIBLE);}
                adapter3.submitList(categorywithSurfaces.surfaces);
                adapter3.notifyDataSetChanged();


                //add surface
                buttonAddSurface.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nameS =textInputaddSurface.getEditText().getText().toString();
                        if (nameS.trim().isEmpty()  ){
                            Toast.makeText(getActivity(),"please insert name",Toast.LENGTH_SHORT);
                            return;
                        }
                        else{
                            Surface surface = new Surface();
                            surface.setNameSurface(nameS);
                            surface.setIdCategorie(categorywithSurfaces.categorieTache.getIdCat());
                            mViewModel.insert(surface);

                        }

                    }
                });



                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)  {
                    @Override
                    public boolean onMove(@NonNull RecyclerView surfaces, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        switch (direction){
                            case ItemTouchHelper.LEFT:
                                mViewModel.delete(adapter3.getSurfacesAt(viewHolder.getAdapterPosition()));
                                Toast.makeText(getActivity(), "Surface delete", Toast.LENGTH_SHORT).show();
                                break;
                            case ItemTouchHelper.RIGHT:


                                break;
                        }
                    }
                    @Override
                    public void onChildDraw (Canvas c, RecyclerView surfaces, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

                        new RecyclerViewSwipeDecorator.Builder(c, surfaces, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                .addSwipeLeftLabel("Delete")
                                .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                                .create()
                                .decorate();

                        super.onChildDraw(c, surfaces, viewHolder, dX, dY, actionState, isCurrentlyActive);


                    }

                }).attachToRecyclerView(surfaces);


            }

            @Override
            public void onDelete(CategorywithSurfaces categorywithSurfaces) {
                mViewModel.delete(categorywithSurfaces.categorieTache);
            }
        });



        closeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details.setVisibility(View.GONE);
                affichage.setVisibility(View.VISIBLE);
            }
        });

        doneDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details.setVisibility(View.GONE);
                affichage.setVisibility(View.VISIBLE);
            }
        });

        CancelDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details.setVisibility(View.GONE);
                affichage.setVisibility(View.VISIBLE);
            }
        });




        return view;
    }












    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        if(lengthbmp >= 1000000*10)
                        {
                            imagenote.setText(Html.fromHtml("<font color=red>"+imagenote.getText().toString()+"</font>"));
                            return;
                        }

                        imageContainer.setVisibility(View.VISIBLE);
                        imageHinter.setVisibility(View.GONE);
                        close.setVisibility(View.VISIBLE);
                        buttons.setVisibility(View.GONE);

                        Glide.with(getActivity()).asBitmap().load(bitmap).into(imageContainer);
                        //container.setImageBitmap(bitmap);
                        // Glide.with(getActivity()).asBitmap().load(bitmap).into(ImageView10);
                        imageView10.setImageBitmap(bitmap);
                        bitmapContainer = bitmap;


                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageContainer.setVisibility(View.VISIBLE);
            imageHinter.setVisibility(View.GONE);
            close.setVisibility(View.VISIBLE);
            buttons.setVisibility(View.GONE);
            //container.setImageBitmap(photo);
            Glide.with(getActivity()).asBitmap().load(photo).into(imageContainer);
            //Glide.with(getActivity()).asBitmap().load(photo).into(ImageView10);
            imageView10.setImageBitmap(photo);
            bitmapContainer = photo;



        }

    }


    private void addNewCategorie(TextInputLayout name, ImageView profile)
    {
        CategorieTache categorieTache = new CategorieTache();
        categorieTache.setName(name.getEditText().getText().toString());

        categorieTache.setCategorieImage(StaticUse.imageGetter(profile));
        mViewModel.insert(categorieTache);

        Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT);

        Animation animFadeOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_out);
        addCategorie.startAnimation(animFadeOut);
        addCategorie.setVisibility(View.GONE);
        affichage.setClickable(true);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addCategorie.setVisibility(View.GONE);     }
        }, 550);




    }


}
