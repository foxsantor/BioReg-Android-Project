package com.example.bioregproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

import com.bumptech.glide.Glide;
import com.example.bioregproject.R;
import com.example.bioregproject.Utils.StaticUse;
import com.example.bioregproject.entities.CategorieTache;
import com.example.bioregproject.entities.History;
import com.example.bioregproject.entities.Tache;
import com.example.bioregproject.ui.Cleaning.ListPlanningCleanViewModel;
import com.example.bioregproject.ui.Settings.GeneralSettings.GeneralSettingsViewModel;

import java.util.List;


public class TacheAdapter extends ListAdapter<Tache, TacheAdapter.TacheHolder> {
    private Context mContext;
    private OnItemClickLisnter listener;


    public TacheAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<Tache> DIFF_CALLBACK = new DiffUtil.ItemCallback<Tache>() {
        @Override
        public boolean areItemsTheSame(@NonNull Tache oldItem, @NonNull Tache newItem) {
            return oldItem.getIdtask() == newItem.getIdtask();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Tache oldItem, @NonNull Tache newItem) {
            return false;
        }
    };


    @NonNull
    @Override
    public TacheHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tache, parent, false);
        return new TacheHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TacheHolder holder, int position) {
        Tache currentTache = getItem(position);
        holder.textViewTime.setText(currentTache.getDate().toString());
       holder.textViewSurface.setText(currentTache.getIdSurface());
       holder.nameCat.setText(currentTache.getIdCategorie());
       holder.user.setText(currentTache.getUser());


        if (currentTache.isStatus()){
           holder.checkDone.setChecked(true);
           holder.checkDone.setEnabled(false);
           holder.imageDone.setVisibility(View.VISIBLE);
           holder.imageOnhold.setVisibility(View.GONE);
       }


    }



    public Tache getTacheAt(int position) {
        return getItem(position);
    }

    class TacheHolder extends RecyclerView.ViewHolder {
        private TextView textViewTime;
        private TextView textViewSurface;
        private ImageView imageOnhold, imageDone ,imageCat;
        private CheckBox checkDone;
        private ImageButton imageButton;
        private TextView nameCat,user;


        public TacheHolder(View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.textViewtime);
            textViewSurface = itemView.findViewById(R.id.textView12);
            imageOnhold = itemView.findViewById(R.id.imageViewOnHold);
            imageDone = itemView.findViewById(R.id.imageViewDone);
            checkDone = itemView.findViewById(R.id.checkBox2);
            imageButton=itemView.findViewById(R.id.imageButton);
            nameCat =itemView.findViewById(R.id.nameCat);
            imageCat=itemView.findViewById(R.id.imageCat);
            user=itemView.findViewById(R.id.user);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position));
                }
            });
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemUpdateClick(getItem(position));
                }
            });


       ;

        }

    }

    public interface OnItemClickLisnter {
        void onItemClick(Tache tache);
        void onItemUpdateClick(Tache tache);


    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}
