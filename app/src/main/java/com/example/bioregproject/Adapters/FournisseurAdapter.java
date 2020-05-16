package com.example.bioregproject.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Fournisseur;

import java.util.Calendar;


public class FournisseurAdapter extends ListAdapter <Fournisseur, FournisseurAdapter.FournisseurHolder> {
    private OnItemClickLisnter listener;
    private Context mContext;
    int index_row= -1;



    public FournisseurAdapter(Context context) {
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
            return oldItem.getName().equals(newItem.getName());
        }
    };




    @Override
    public FournisseurHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recp, parent, false);
        return new FournisseurHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FournisseurHolder holder, int position) {
        Fournisseur currentFournisseur = getItem(position);
        holder.nameFournisseur.setText(currentFournisseur.getName());





    }

    class FournisseurHolder extends RecyclerView.ViewHolder{
       private TextView nameFournisseur;
       private CardView cardchange;
        public FournisseurHolder(@NonNull View itemView) {
            super(itemView);
            nameFournisseur=itemView.findViewById(R.id.textView41);
         cardchange=itemView.findViewById(R.id.cardchange);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                        itemView.setBackgroundColor(Color.parseColor("#3797DD"));


                    }
                }
            });

        }
    }

    public interface OnItemClickLisnter {
        void onItemClick(Fournisseur Fournisseur);
    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}