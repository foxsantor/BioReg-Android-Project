package com.example.bioregproject.ui.Traceability.ImageFlow;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.bioregproject.R;

import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Products;

import java.util.List;

public class ImageFlowHome extends Fragment {

    private ImageFlowMainHallViewModel mViewModel;
    private ConstraintLayout constraintLayout,imageItems;
    private ImageView imageView8;
    private TextView number;
    private ProgressBar progressBar;
    private ImageButton gallary,addImage,scanQr,bindData;
    private int size=0;

    public static ImageFlowHome newInstance() {
        return new ImageFlowHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_flow_home_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(ImageFlowHomeViewModel.class);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.mainMenu);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        constraintLayout = view.findViewById(R.id.constraintLayout);
        StaticUse.backgroundAnimator(constraintLayout);
        gallary= view.findViewById(R.id.galarie);
        addImage= view.findViewById(R.id.addImage);
        scanQr= view.findViewById(R.id.scanQR);
        bindData= view.findViewById(R.id.Bind);
        number = view.findViewById(R.id.number);
        imageItems = view.findViewById(R.id.imageItems);
        imageView8 = view.findViewById(R.id.imageView8);
        mViewModel = ViewModelProviders.of(this).get(ImageFlowMainHallViewModel.class);
        mViewModel.getAllProductsbyType().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> product) {
                if(product.size()!=0) {
                     RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.cover_image)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .priority(Priority.HIGH)
                            .dontAnimate()
                            .dontTransform();
                    Glide.with(getActivity()).asBitmap().load(product.get(0).getImage()).apply(options).into(imageView8);
                    size = product.size();
                    number.setText("" + product.size());
                }else{
                    imageItems.setBackgroundColor(getActivity().getResources().getColor(R.color.bpWhite));

                    Glide.with(getActivity()).asDrawable().load(R.drawable.cover_image).into(imageView8);
                  }
            }
        });


        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(size != 0)
                {
                    Bundle bundle = new Bundle();
                    bundle.putInt("size",size);
                    Navigation.findNavController(v).navigate(R.id.action_imageFlowHome_to_imageFlowMainHall,bundle);
                }else
                {
                    Toast.makeText(getActivity(), "No image found", Toast.LENGTH_SHORT).show();
                }

            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_imageFlowHome_to_imageFlowAddImage);
            }
        });

        scanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_imageFlowHome_to_QRInputImageFlow);
            }
        });
        bindData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_imageFlowHome_to_manageData);
            }
        });
    }
}
