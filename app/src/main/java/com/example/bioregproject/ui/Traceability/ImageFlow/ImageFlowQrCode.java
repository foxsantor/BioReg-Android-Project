package com.example.bioregproject.ui.Traceability.ImageFlow;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bioregproject.Adapters.QrItemAdapater;
import com.example.bioregproject.MainActivity;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Account;
import com.example.bioregproject.entities.Products;

import java.text.SimpleDateFormat;
import java.util.List;

public class ImageFlowQrCode extends Fragment {

    private ImageFlowQrCodeViewModel mViewModel;
    private RecyclerView recyclerView;
    private TextView id,creation;
    private ImageButton scan,print;
    private ImageView imageView;
    private AlertDialog alerti;
    private QrItemAdapater qrItemAdapater;
    private boolean hasEntredFirst= false;
    private static byte[] image;

    public static ImageFlowQrCode newInstance() {
        return new ImageFlowQrCode();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_flow_qr_code_fragment, container, false);
    }



    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hasEntredFirst = true;

        mViewModel = ViewModelProviders.of(this).get(ImageFlowQrCodeViewModel.class);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_imageFlowQrCode_to_imageFlowHome);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);



        recyclerView = view.findViewById(R.id.recycli);
        imageView= view.findViewById(R.id.imagePreview);
        id= view.findViewById(R.id.id);
        creation= view.findViewById(R.id.created);
        scan= view.findViewById(R.id.Bind1);
        print= view.findViewById(R.id.Bind);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        qrItemAdapater= new QrItemAdapater(getActivity(),getActivity());
        recyclerView.setAdapter(qrItemAdapater);
        recyclerView.hasFixedSize();

        mViewModel.getAllProducts().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                if(hasEntredFirst)
                {
                    id.setText("Image Id: "+products.get(0).getId());
                    Glide.with(getActivity()).asBitmap().load(products.get(0).getImage()).into(imageView);
                    String newstring = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(products.get(0).getCreationDate());
                    creation.setText("Created at: "+newstring);
                    hasEntredFirst = false;
                    image=products.get(0).getImage();

                }
                qrItemAdapater.submitList(products);
            }
        });

        qrItemAdapater.setOnIteemClickListener(new QrItemAdapater.OnItemClickLisnter() {
            @Override
            public void OnItemClick(Products account) {
                id.setText("Image Id: "+account.getId());
                Glide.with(getActivity()).asBitmap().load(account.getImage()).into(imageView);
                String newstring = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(account.getCreationDate());
                creation.setText("Created at: "+newstring);
                image=account.getImage();
            }
        });

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater =  (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogueView =layoutInflater.inflate(R.layout.poup_qrcode,null,false);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialogueView.getParent() != null) {
                    ((ViewGroup)dialogueView.getParent()).removeView(dialogueView); // <- fix
                }
                alert.setView(dialogueView);
                alerti =alert.show();
                alerti.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            }
        });

        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle bundle = new Bundle();
                bundle.putString("id",id.getText().toString().trim());
                bundle.putString("created",creation.getText().toString().trim());
                bundle.putByteArray("image",image);
                Navigation.findNavController(view).navigate(R.id.action_imageFlowQrCode_to_iamgeFlowPrinting,bundle);
            }
        });



    }
}
