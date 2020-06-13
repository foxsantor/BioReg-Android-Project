package com.example.bioregproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Storage;


public class ProduitRecpDetails extends ListAdapter <Storage, ProduitRecpDetails.StorageHolder> {
    private OnItemClickLisnter listener;
    private Context mContext;


    public ProduitRecpDetails(Context context) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recp, parent, false);
        return new StorageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageHolder holder, int position) {
        Storage currentStorage = getItem(position);
       holder.fournisseur.setText(currentStorage.getFournisseur());
        holder.date.setText(currentStorage.getDateReception().toString());
        holder.owner.setText(currentStorage.getOwner());
        if (currentStorage.getNatureProduit().equals("Solid")){
            holder.quantite.setText(String.valueOf(currentStorage.getQuantite())+"Kg");
        }
        else{
            holder.quantite.setText(String.valueOf(currentStorage.getQuantite())+"L");
        }


        if (currentStorage.getStatus()){
            holder.accepted.setText("Accepted");
           // holder.accepted.setTextColor("#00b200");

        }else{
            holder.accepted.setText("Refused");
            //holder.accepted.setTextColor("#ff0000");
        }





    }

    class StorageHolder extends RecyclerView.ViewHolder{
       private TextView  date,quantite,fournisseur,accepted,owner;
        public StorageHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.DTextView);
            quantite=itemView.findViewById(R.id.textView86);
            fournisseur=itemView.findViewById(R.id.textView87);
            accepted = itemView.findViewById(R.id.textView90);
            owner =itemView.findViewById(R.id.textView75);



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