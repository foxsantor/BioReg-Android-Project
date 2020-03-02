package com.example.bioregproject.ui.Traceability.ImageFlow;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Size;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.CustomViewPager;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.Products;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PageItemClickListener;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class imageFlowMainHall extends Fragment {

    private ImageFlowMainHallViewModel mViewModel;
    private ConstraintLayout constraintLayout;
    private List<Products> products;
    private static int size=0;
    private imageFlowMainHall imageFlowMainHall;
    public static imageFlowMainHall newInstance() {
        return new imageFlowMainHall();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image_flow_main_hall_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        size = bundle.getInt("size",0);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageFlowMainHall = this;

        CustomViewPager myCustomViewPager = view.findViewById(R.id.pagi);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).navigate(R.id.action_imageFlowMainHall_to_imageFlowHome);

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        constraintLayout = view.findViewById(R.id.constraintLayout);
        StaticUse.backgroundAnimator(constraintLayout);
        PagerContainer container = view.findViewById(R.id.pager_container);
        ViewPager pager = container.getViewPager();
        pager.setAdapter(new MyPagerAdapter());
        pager.setClipChildren(false);
        //
        pager.setOffscreenPageLimit(15);
        container.setClickable(true);
        container.setPageItemClickListener(new PageItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),"position:" + position,Toast.LENGTH_SHORT).show();
            }
        });

        boolean showTransformer = getActivity().getIntent().getBooleanExtra("showTransformer",false);


        if(showTransformer){

            new CoverFlow.Builder()
                    .with(pager)
                    .scale(0.3f)
                    .pagerMargin(-30)
                    .spaceSize(0f)
                    .build();

        }else{
            pager.setPageMargin(30);
        }
    }
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container,final int position ) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_cover,null);
            final ImageView imageView = view.findViewById(R.id.imageView7);
            final TextView created = view.findViewById(R.id.created);
            mViewModel = ViewModelProviders.of(imageFlowMainHall).get(ImageFlowMainHallViewModel.class);
            mViewModel.getAllProducts().observe(imageFlowMainHall, new Observer<List<Products>>() {
                @Override
                public void onChanged(List<Products> product) {
                    products = product;
                    String newstring = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(product.get(position).getCreationDate());
                    created.setText("Taken at: "+newstring);
                    Glide.with(getActivity()).asBitmap().load(products.get(position).getImage()).into(imageView);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    size = product.size();
                }
            });


            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return size;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }




}
