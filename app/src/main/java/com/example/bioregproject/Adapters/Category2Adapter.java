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
import com.example.bioregproject.entities.Realtions.CategorywithSurfaces;


public class Category2Adapter extends ListAdapter <CategorywithSurfaces, Category2Adapter.Category2Holder> {
    private OnItemClickLisnter listener;
    private Context mContext;
    int index_row =-1;


    public Category2Adapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<CategorywithSurfaces> DIFF_CALLBACK = new DiffUtil.ItemCallback<CategorywithSurfaces>() {
        @Override
        public boolean areItemsTheSame(@NonNull CategorywithSurfaces oldItem, @NonNull CategorywithSurfaces newItem) {
            return oldItem.categorieTache.getIdCat()==newItem.categorieTache.getIdCat();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CategorywithSurfaces oldItem, @NonNull CategorywithSurfaces newItem) {
            return oldItem.categorieTache.getName().equals(newItem.categorieTache.getName());
        }
    };




    @Override
    public Category2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zone, parent, false);
        return new Category2Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Category2Holder holder, int position) {
        CategorywithSurfaces currentCategorie = getItem(position);
        holder.namecategory.setText(currentCategorie.categorieTache.getName());


    }
    class Category2Holder extends RecyclerView.ViewHolder{
        private TextView namecategory;
        private CardView cardViewZone;
        public Category2Holder(@NonNull View itemView) {
            super(itemView);
            namecategory=itemView.findViewById(R.id.textViewTitle);
            cardViewZone=itemView.findViewById(R.id.cardViewZone);


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
        void onItemClick(CategorywithSurfaces categorywithSurfaces);
    }

    public void setOnItemClickListener(OnItemClickLisnter listener) {
        this.listener = listener;
    }
}