package com.example.bioregproject.ui.Settings.OilSetting;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bioregproject.Adapters.PostSettingAdapter;
import com.example.bioregproject.R;
import com.example.bioregproject.entities.Post;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.List;

public class OilSettingFragment extends Fragment {

    private OilSettingViewModel mViewModel;
    private RecyclerView postRecycleView ;
    private PostSettingAdapter postAdapter;
    private Button add,save,cancel,updateB,cancel1;
    private ConstraintLayout ajout,updateConstraint;
    private TextInputLayout namePost,namePost1;
    private ImageButton exit,exit1;
    private CardView affichage;
    private TextView vide;
    private NestedScrollView nestes;

    public static OilSettingFragment newInstance() {
        return new OilSettingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.oil_setting_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(OilSettingViewModel.class);

        postRecycleView = view.findViewById(R.id.recyclePost);
        add = view.findViewById(R.id.create);
        namePost = view.findViewById(R.id.namePost);
        save = view.findViewById(R.id.save);
        cancel = view.findViewById(R.id.cancel);
        ajout = view.findViewById(R.id.ajout);
        exit = view.findViewById(R.id.exit);
        affichage=view.findViewById(R.id.cardView4);
        namePost1 = view.findViewById(R.id.namePost1);
        updateB = view.findViewById(R.id.update);
        updateConstraint=view.findViewById(R.id.updateConstraint);
        cancel1 = view.findViewById(R.id.cancel1);
        exit1 = view.findViewById(R.id.exit1);
        nestes = view.findViewById(R.id.nestedScrollView4);
        vide = view.findViewById(R.id.textView33);




        postAdapter = new PostSettingAdapter(getActivity());
        postRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        postRecycleView.setHasFixedSize(true);
        postRecycleView.setAdapter(postAdapter);
        mViewModel.getAllPost().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                if (posts.size()>0) {
                    nestes.setVisibility(View.VISIBLE);
                    vide.setVisibility(View.GONE);
                    postAdapter.submitList(posts);
                    postAdapter.notifyDataSetChanged();
                }
            }
        });

        postAdapter.setOnItemClickListener(new PostSettingAdapter.OnItemClickLisnter() {
            @Override
            public void onItemClick(Post Post) {

            }

            @Override
            public void delete(Post Post) {
                mViewModel.delete(Post);
            }

            @Override
            public void update(Post Post) {
                updateConstraint.setVisibility(View.VISIBLE);
                affichage.setVisibility(View.GONE);
                namePost1.getEditText().setText(Post.getName());
                updateB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Post.setName(namePost1.getEditText().getText().toString());
                        Post.setUpdatedAT(new Date());
                        mViewModel.update(Post);
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
                mViewModel.insert(post);
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





        return view;
    }



}
