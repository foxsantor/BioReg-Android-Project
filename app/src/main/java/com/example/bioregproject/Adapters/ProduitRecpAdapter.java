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
import com.example.bioregproject.entities.Produit;


public class ProduitRecpAdapter extends ListAdapter <Produit, ProduitRecpAdapter.ProduitHolder> {
    private OnItemClickLisnter listener;
    private Context mContext;
    int index_row= -1;



    public ProduitRecpAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<Produit> DIFF_CALLBACK = new DiffUtil.ItemCallback<Produit>() {
        @Override
        public boolean areItemsTheSame(@NonNull Produit oldItem, @NonNull Produit newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Produit oldItem, @NonNull Produit newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    };




    @Override
    public ProduitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recp, parent, false);
        return new ProduitHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitHolder holder, int position) {
        Produit currentProduit = getItem(position);
        holder.nameProduit.setText(currentProduit.getName());



        holder.cardchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index_row=position;
                notifyDataSetChanged(); }
        });
        if (index_row==position){

            holder.cardchange.setBackgroundColor(Color.parseColor("#3797DD"));
            holder.nameProduit.setTextColor(Color.parseColor("#3797DD"));

        }else{

            holder.cardchange.setBackgroundColor(Color.parseColor("#65696D72"));
            holder.nameProduit.setTextColor(Color.parseColor("#65696D72"));

        }



    }

    class ProduitHolder extends RecyclerView.ViewHolder{
       private TextView nameProduit;
        private CardView cardchange;

        public ProduitHolder(@NonNull View itemView) {
            super(itemView);
            nameProduit=itemView.findViewById(R.id.textView41);
            cardchange=itemView.findViewById(R.id.cardchange);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                        getItem(position).getName();
                        getItem(position).getCategorie();
                        getItem(position).getNature();
                }}
            });

        }
    }

    public interface OnItemClickLisnter {
        void onItemClick(Produit Produit);
    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}