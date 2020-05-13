package com.example.bioregproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Storage;


public class StorageAdapter extends ListAdapter <Storage, StorageAdapter.StorageHolder> {
    private OnItemClickLisnter listener;
    private Context mContext;


    public StorageAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<Storage> DIFF_CALLBACK = new DiffUtil.ItemCallback<Storage>() {
        @Override
        public boolean areItemsTheSame(@NonNull Storage oldItem, @NonNull Storage newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Storage oldItem, @NonNull Storage newItem) {
            return oldItem.getDateReception().equals(newItem.getDateReception());
        }
    };




    @Override
    public StorageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_storage, parent, false);
        return new StorageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageHolder holder, int position) {
        Storage currentStorage = getItem(position);
        holder.categorieandProduit.setText(currentStorage.getCategorie()+","+currentStorage.getProduit());
        holder.fournisseur.setText(currentStorage.getFournisseur());
        holder.date.setText(currentStorage.getDateReception().toString());
        holder.quantite.setText(String.valueOf(currentStorage.getQuantite()));
        holder.temperature.setText(String.valueOf(currentStorage.getTemperature()));

            holder.nature.setText("Kg");


        if (currentStorage.getStatus()){
           holder.view26.setVisibility(View.VISIBLE);
            holder.accepted.setVisibility(View.VISIBLE);
            holder.acceptedImage.setVisibility(View.VISIBLE);
            holder.viewnon.setVisibility(View.GONE);
            holder.refusedImage.setVisibility(View.GONE);
            holder.noaccepted.setVisibility(View.GONE);



        }else{
            holder.view26.setVisibility(View.GONE);
            holder.accepted.setVisibility(View.GONE);
            holder.acceptedImage.setVisibility(View.GONE);
            holder.viewnon.setVisibility(View.VISIBLE);
            holder.refusedImage.setVisibility(View.VISIBLE);
            holder.noaccepted.setVisibility(View.VISIBLE);

        }





    }

    class StorageHolder extends RecyclerView.ViewHolder{
       private TextView categorieandProduit ,date,time,nature,quantite,temperature,fournisseur,accepted,noaccepted;
       private View view26 , viewnon;
       private Button supp , update;
       private ImageView acceptedImage,refusedImage;
        public StorageHolder(@NonNull View itemView) {
            super(itemView);
            categorieandProduit=itemView.findViewById(R.id.categorieandnameProduit);
            date=itemView.findViewById(R.id.daterecep);
            time=itemView.findViewById(R.id.textView49);
            nature=itemView.findViewById(R.id.textView44);
            quantite=itemView.findViewById(R.id.textView43);
            fournisseur=itemView.findViewById(R.id.frsName);
            view26=itemView.findViewById(R.id.view26);
            viewnon=itemView.findViewById(R.id.viewnon);
            temperature=itemView.findViewById(R.id.textView57);
            supp=itemView.findViewById(R.id.button10);
            update=itemView.findViewById(R.id.button11);
            accepted = itemView.findViewById(R.id.accepted);
            noaccepted = itemView.findViewById(R.id.refuser);
            acceptedImage = itemView.findViewById(R.id.imageAccepted);
            refusedImage = itemView.findViewById(R.id.imageNonAccepted);








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
        void onItemClick(Storage Storage);
    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}