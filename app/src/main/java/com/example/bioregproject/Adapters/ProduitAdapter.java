package com.example.bioregproject.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bioregproject.R;
import com.example.bioregproject.entities.Produit;


public class ProduitAdapter extends ListAdapter <Produit, ProduitAdapter.ProduitHolder> {
    private OnItemClickLisnter listener;
    private Context mContext;


    public ProduitAdapter(Context context) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produit, parent, false);
        return new ProduitHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitHolder holder, int position) {
        Produit currentProduit = getItem(position);
        holder.name.setText(currentProduit.getName());
       holder.nbr.setText( String.valueOf(currentProduit.getQuantite()));
       holder.textView40.setText(currentProduit.getCategorie());
        if (currentProduit.getNature().equals("Solid")){
        holder.unite.setText("Kg");}
        else{
            holder.unite.setText("L");
        }

        if(currentProduit.getCategorie().equals("Dairy products")){
            holder.viewColor.setBackgroundColor(Color.parseColor("#A7D2CB"));
            holder.textView40.setTextColor(Color.parseColor("#A7D2CB"));

        }else if(currentProduit.getCategorie().equals("Meat, fish and eggs")) {
            holder.viewColor.setBackgroundColor(Color.parseColor("#C98474"));
            holder.textView40.setTextColor(Color.parseColor("#C98474"));


        }
        else if(currentProduit.getCategorie().equals("Fruits and vegetables")){
            holder.viewColor.setBackgroundColor(Color.parseColor("#849974"));
            holder.textView40.setTextColor(Color.parseColor("#849974"));

        }

        else if(currentProduit.getCategorie().equals("Cereals")){
            holder.viewColor.setBackgroundColor(Color.parseColor("#F2D388"));
            holder.textView40.setTextColor(Color.parseColor("#F2D388"));
        }
        else if(currentProduit.getCategorie().equals("High-fat products")){
            holder.viewColor.setBackgroundColor(Color.parseColor("#F2D388"));
            holder.textView40.setTextColor(Color.parseColor("#F2D388"));

        }
        else if(currentProduit.getCategorie().equals("Sweetened products")){
            holder.viewColor.setBackgroundColor(Color.parseColor("#E9DCCD"));
            holder.textView40.setTextColor(Color.parseColor("#E9DCCD"));

        }
        else {    holder.viewColor.setBackgroundColor(Color.parseColor("#3797DD"));
            holder.textView40.setTextColor(Color.parseColor("#3797DD"));
        }





    }

    class ProduitHolder extends RecyclerView.ViewHolder{
       private TextView name,nbr,unite,textView40;
       private Button supp,edit;
       private View viewColor;

        public ProduitHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textView36);
            edit=itemView.findViewById(R.id.imageButton7);
            supp=itemView.findViewById(R.id.imageButton6);
            nbr=itemView.findViewById(R.id.nbr);
            unite=itemView.findViewById(R.id.unite);
            textView40=itemView.findViewById(R.id.textView40);
            viewColor = itemView.findViewById(R.id.view24);





            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position));

                }
            });
            supp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.delete(getItem(position));
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.update(getItem(position));
                }
            });

        }
    }

    public interface OnItemClickLisnter {
        void onItemClick(Produit Produit);
        void delete(Produit Produit);
        void update(Produit Produit);

    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}