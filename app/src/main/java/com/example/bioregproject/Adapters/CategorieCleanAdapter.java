package com.example.bioregproject.Adapters;

import android.content.Context;
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
import com.example.bioregproject.entities.CategorieTache;

import java.util.ArrayList;
import java.util.List;

public class CategorieCleanAdapter extends ListAdapter<CategorieTache,CategorieCleanAdapter.CategorieHolder> {
 private List<CategorieTache> categories = new ArrayList<>();
    private OnItemClickLisnter listener;
    private Context mContext;
    int index_row= -1;



    public CategorieCleanAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }
    private static final DiffUtil.ItemCallback<CategorieTache> DIFF_CALLBACK = new DiffUtil.ItemCallback<CategorieTache>() {
        @Override
        public boolean areItemsTheSame(@NonNull CategorieTache oldItem, @NonNull CategorieTache newItem) {
            return oldItem.getIdCat() == newItem.getIdCat();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CategorieTache oldItem, @NonNull CategorieTache newItem) {
            return  oldItem.getName().equals(newItem.getName());
        }
    };


    @NonNull
    @Override
    public CategorieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recherche_categorie,parent,false);
        return new CategorieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorieHolder holder, int position) {
        final CategorieTache currentCategorie = categories.get(position);
        holder.textViewTitle.setText(currentCategorie.getName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories (List<CategorieTache> categories){
        this.categories=categories;
        notifyDataSetChanged();
    }
    public CategorieTache getCatAT (int position)
    {
        return categories.get(position);
    }
    class CategorieHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        public CardView cardView;

        public CategorieHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            cardView =itemView.findViewById(R.id.cardViewZone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(categories.get(position));
                    }

                }
            });
        }
        }


    public interface OnItemClickLisnter{
        void onItemClick(CategorieTache categorie_tache);

    }
    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}