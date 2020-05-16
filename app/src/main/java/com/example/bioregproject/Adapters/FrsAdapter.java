package com.example.bioregproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Fournisseur;


public class FrsAdapter extends ListAdapter <Fournisseur, FrsAdapter.frsHolder>  {
    private OnItemClickLisnter listener;
    private Context mContext;
    private LiveData<Fournisseur>list;



    public FrsAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<Fournisseur> DIFF_CALLBACK = new DiffUtil.ItemCallback<Fournisseur>() {
        @Override
        public boolean areItemsTheSame(@NonNull Fournisseur oldItem, @NonNull Fournisseur newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Fournisseur oldItem, @NonNull Fournisseur newItem) {
            return oldItem.getName().equals(newItem.getName())&&
                    oldItem.getAdresse().equals(newItem.getAdresse())&&
                    oldItem.getEmail().equals(newItem.getEmail())&&
                    oldItem.getNumero().equals(newItem.getNumero());


        }
    };


    @Override
    public frsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frs, parent, false);
        return new frsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull frsHolder holder, int position) {
        Fournisseur currentFournisseur = getItem(position);
        holder.name.setText(currentFournisseur.getName());
        holder.email.setText(currentFournisseur.getEmail());
        holder.numero.setText(currentFournisseur.getNumero());
        holder.adresse.setText(currentFournisseur.getAdresse());
        holder.categorie.setText(currentFournisseur.getCategorieCommerciale().toString());
       holder.textView38.setText(String.valueOf(currentFournisseur.getName().toUpperCase().charAt(0)));


    }

    class frsHolder extends RecyclerView.ViewHolder{
       private TextView name,email,numero,adresse,categorie,textView38;

        public frsHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            categorie=itemView.findViewById(R.id.categorie);
            email=itemView.findViewById(R.id.email);
            adresse=itemView.findViewById(R.id.adresse);
            numero=itemView.findViewById(R.id.numero);
            textView38 = itemView.findViewById(R.id.textView38);


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
        void onItemClick(Fournisseur fournisseur);


    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }





}