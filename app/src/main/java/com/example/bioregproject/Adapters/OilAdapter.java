package com.example.bioregproject.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Oil;
import com.example.bioregproject.entities.SettingOil;
import com.example.bioregproject.ui.Settings.GeneralSettings.GeneralSettingsViewModel;

import java.util.List;


public class OilAdapter extends ListAdapter <Oil,OilAdapter.OilHolder> {
    private OnItemClickLisnter listener;
    private Context mContext;
    private Fragment fragment;



    public OilAdapter(Context context , Fragment fragment) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.fragment=fragment;

    }

    private static final DiffUtil.ItemCallback<Oil> DIFF_CALLBACK = new DiffUtil.ItemCallback<Oil>() {
        @Override
        public boolean areItemsTheSame(@NonNull Oil oldItem, @NonNull Oil newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Oil oldItem, @NonNull Oil newItem) {
            return oldItem.getDateUtilisation().equals(newItem.getDateUtilisation());


        }
    };


    @Override
    public OilHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oil, parent, false);
        return new OilHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OilHolder holder, int position) {
        Oil currentOil = getItem(position);
        holder.post.setText(currentOil.getPost());
        holder.time.setText(currentOil.getDateUtilisation().toString());
        holder.action.setText(currentOil.getAction());
        holder.filtrage.setText(String.valueOf(currentOil.getFiltrage()));
        GeneralSettingsViewModel mViewModel = ViewModelProviders.of(fragment).get(GeneralSettingsViewModel.class);
mViewModel.getAllSetting().observe(fragment, new Observer<List<SettingOil>>() {
    @Override
    public void onChanged(List<SettingOil> settingOils) {
       if (settingOils.get(0).getVerif())
       {

           holder.err.setVisibility(View.VISIBLE);
           holder.message.setVisibility(View.GONE);
           if (currentOil.getFiltrage()<20){
               holder.message.setText("Everything is in order");
               holder.message.setTextColor(Color.parseColor("#28a745"));
               holder.suu.setVisibility(View.VISIBLE);           }
           else if((currentOil.getFiltrage()<24) && (currentOil.getFiltrage()>24)){
               holder.message.setText("We will have to think about replace oil");
               holder.message.setTextColor(Color.parseColor("#ffc107"));
               holder.wa.setVisibility(View.VISIBLE);

       }
           else{
               holder.message.setText("L’huile doit être remplacée");
               holder.message.setTextColor(Color.parseColor("#dc3545"));
           holder.err.setVisibility(View.VISIBLE);


    }

       }
       else{
           holder.err.setVisibility(View.GONE);
           holder.wa.setVisibility(View.GONE);
           holder.suu.setVisibility(View.GONE);

           holder.message.setVisibility(View.GONE);


       }
    }
});

    }

    class OilHolder extends RecyclerView.ViewHolder{
       private TextView post,time,filtrage,message,action;
       private ImageView suu,err,wa;

        public OilHolder(@NonNull View itemView) {
            super(itemView);
            post=itemView.findViewById(R.id.post);
            time=itemView.findViewById(R.id.time);
            action=itemView.findViewById(R.id.action);
            filtrage=itemView.findViewById(R.id.filtrage);
            message=itemView.findViewById(R.id.message);
            suu=itemView.findViewById(R.id.imageButton3);
            wa=itemView.findViewById(R.id.imageButton4);
            err=itemView.findViewById(R.id.imageButton5);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position));

                }
            });


        }
    }

    public interface OnItemClickLisnter {
        void onItemClick(Oil oil);

    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}